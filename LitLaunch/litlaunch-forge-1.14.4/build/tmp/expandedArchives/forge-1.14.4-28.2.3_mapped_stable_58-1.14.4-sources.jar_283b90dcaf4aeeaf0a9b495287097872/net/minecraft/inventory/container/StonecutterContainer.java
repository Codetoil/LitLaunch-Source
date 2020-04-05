package net.minecraft.inventory.container;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StonecutterContainer extends Container {
   /** The list of items that can be accepted into the input slot of the Stonecutter container. */
   static final ImmutableList<Item> ACCEPTED_INPUT_ITEMS = ImmutableList.of(Items.STONE, Items.SANDSTONE, Items.RED_SANDSTONE, Items.QUARTZ_BLOCK, Items.COBBLESTONE, Items.STONE_BRICKS, Items.BRICKS, Items.NETHER_BRICKS, Items.RED_NETHER_BRICKS, Items.PURPUR_BLOCK, Items.PRISMARINE, Items.PRISMARINE_BRICKS, Items.DARK_PRISMARINE, Items.ANDESITE, Items.POLISHED_ANDESITE, Items.GRANITE, Items.POLISHED_GRANITE, Items.DIORITE, Items.POLISHED_DIORITE, Items.MOSSY_STONE_BRICKS, Items.MOSSY_COBBLESTONE, Items.SMOOTH_SANDSTONE, Items.SMOOTH_RED_SANDSTONE, Items.SMOOTH_QUARTZ, Items.END_STONE, Items.END_STONE_BRICKS, Items.SMOOTH_STONE, Items.CUT_SANDSTONE, Items.CUT_RED_SANDSTONE);
   private final IWorldPosCallable worldPosCallable;
   /** The index of the selected recipe in the GUI. */
   private final IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
   private final World world;
   private List<StonecuttingRecipe> recipes = Lists.newArrayList();
   /** The {@plainlink ItemStack} set in the input slot by the player. */
   private ItemStack itemStackInput = ItemStack.EMPTY;
   /**
    * Stores the game time of the last time the player took items from the the crafting result slot. This is used to
    * prevent the sound from being played multiple times on the same tick.
    */
   private long lastOnTake;
   final Slot inputInventorySlot;
   /** The inventory slot that stores the output of the crafting recipe. */
   final Slot outputInventorySlot;
   private Runnable inventoryUpdateListener = () -> {
   };
   public final IInventory inputInventory = new Inventory(1) {
      public void markDirty() {
         super.markDirty();
         StonecutterContainer.this.onCraftMatrixChanged(this);
         StonecutterContainer.this.inventoryUpdateListener.run();
      }
   };
   /** The inventory that stores the output of the crafting recipe. */
   private final CraftResultInventory inventory = new CraftResultInventory();

   public StonecutterContainer(int windowIdIn, PlayerInventory playerInventoryIn) {
      this(windowIdIn, playerInventoryIn, IWorldPosCallable.DUMMY);
   }

   public StonecutterContainer(int windowIdIn, PlayerInventory playerInventoryIn, final IWorldPosCallable worldPosCallableIn) {
      super(ContainerType.STONECUTTER, windowIdIn);
      this.worldPosCallable = worldPosCallableIn;
      this.world = playerInventoryIn.player.world;
      this.inputInventorySlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));
      this.outputInventorySlot = this.addSlot(new Slot(this.inventory, 1, 143, 33) {
         public boolean isItemValid(ItemStack stack) {
            return false;
         }

         public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
            ItemStack itemstack = StonecutterContainer.this.inputInventorySlot.decrStackSize(1);
            if (!itemstack.isEmpty()) {
               StonecutterContainer.this.updateRecipeResultSlot();
            }

            stack.getItem().onCreated(stack, thePlayer.world, thePlayer);
            worldPosCallableIn.consume((p_216954_1_, p_216954_2_) -> {
               long l = p_216954_1_.getGameTime();
               if (StonecutterContainer.this.lastOnTake != l) {
                  p_216954_1_.playSound((PlayerEntity)null, p_216954_2_, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                  StonecutterContainer.this.lastOnTake = l;
               }

            });
            return super.onTake(thePlayer, stack);
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
      }

      this.trackInt(this.selectedRecipe);
   }

   @OnlyIn(Dist.CLIENT)
   public int getSelectedRecipe() {
      return this.selectedRecipe.get();
   }

   @OnlyIn(Dist.CLIENT)
   public List<StonecuttingRecipe> getRecipeList() {
      return this.recipes;
   }

   @OnlyIn(Dist.CLIENT)
   public int getRecipeListSize() {
      return this.recipes.size();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasItemsinInputSlot() {
      return this.inputInventorySlot.getHasStack() && !this.recipes.isEmpty();
   }

   public boolean canInteractWith(PlayerEntity playerIn) {
      return isWithinUsableDistance(this.worldPosCallable, playerIn, Blocks.STONECUTTER);
   }

   public boolean enchantItem(PlayerEntity playerIn, int id) {
      if (id >= 0 && id < this.recipes.size()) {
         this.selectedRecipe.set(id);
         this.updateRecipeResultSlot();
      }

      return true;
   }

   public void onCraftMatrixChanged(IInventory inventoryIn) {
      ItemStack itemstack = this.inputInventorySlot.getStack();
      if (itemstack.getItem() != this.itemStackInput.getItem()) {
         this.itemStackInput = itemstack.copy();
         this.updateAvailableRecipes(inventoryIn, itemstack);
      }

   }

   private void updateAvailableRecipes(IInventory inventoryIn, ItemStack stack) {
      this.recipes.clear();
      this.selectedRecipe.set(-1);
      this.outputInventorySlot.putStack(ItemStack.EMPTY);
      if (!stack.isEmpty()) {
         this.recipes = this.world.getRecipeManager().getRecipes(IRecipeType.STONECUTTING, inventoryIn, this.world);
      }

   }

   private void updateRecipeResultSlot() {
      if (!this.recipes.isEmpty()) {
         StonecuttingRecipe stonecuttingrecipe = this.recipes.get(this.selectedRecipe.get());
         this.outputInventorySlot.putStack(stonecuttingrecipe.getCraftingResult(this.inputInventory));
      } else {
         this.outputInventorySlot.putStack(ItemStack.EMPTY);
      }

      this.detectAndSendChanges();
   }

   public ContainerType<?> getType() {
      return ContainerType.STONECUTTER;
   }

   @OnlyIn(Dist.CLIENT)
   public void setInventoryUpdateListener(Runnable listenerIn) {
      this.inventoryUpdateListener = listenerIn;
   }

   public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
      return false;
   }

   public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.inventorySlots.get(index);
      if (slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         Item item = itemstack1.getItem();
         itemstack = itemstack1.copy();
         if (index == 1) {
            item.onCreated(itemstack1, playerIn.world, playerIn);
            if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
               return ItemStack.EMPTY;
            }

            slot.onSlotChange(itemstack1, itemstack);
         } else if (index == 0) {
            if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.world.getRecipeManager().getRecipes(IRecipeType.STONECUTTING, new Inventory(itemstack1), this.world).isEmpty()) {
            if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 2 && index < 29) {
            if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
         }

         slot.onSlotChanged();
         if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(playerIn, itemstack1);
         this.detectAndSendChanges();
      }

      return itemstack;
   }

   public void onContainerClosed(PlayerEntity playerIn) {
      super.onContainerClosed(playerIn);
      this.inventory.removeStackFromSlot(1);
      this.worldPosCallable.consume((p_217079_2_, p_217079_3_) -> {
         this.clearContainer(playerIn, playerIn.world, this.inputInventory);
      });
   }
}