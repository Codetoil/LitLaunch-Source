package net.minecraft.inventory.container;

import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HorseInventoryContainer extends Container {
   private final IInventory horseInventory;
   private final AbstractHorseEntity horse;

   public HorseInventoryContainer(int p_i50077_1_, PlayerInventory p_i50077_2_, IInventory p_i50077_3_, final AbstractHorseEntity p_i50077_4_) {
      super((ContainerType<?>)null, p_i50077_1_);
      this.horseInventory = p_i50077_3_;
      this.horse = p_i50077_4_;
      int i = 3;
      p_i50077_3_.openInventory(p_i50077_2_.player);
      int j = -18;
      this.addSlot(new Slot(p_i50077_3_, 0, 8, 18) {
         public boolean isItemValid(ItemStack stack) {
            return stack.getItem() == Items.SADDLE && !this.getHasStack() && p_i50077_4_.canBeSaddled();
         }

         @OnlyIn(Dist.CLIENT)
         public boolean isEnabled() {
            return p_i50077_4_.canBeSaddled();
         }
      });
      this.addSlot(new Slot(p_i50077_3_, 1, 8, 36) {
         public boolean isItemValid(ItemStack stack) {
            return p_i50077_4_.isArmor(stack);
         }

         @OnlyIn(Dist.CLIENT)
         public boolean isEnabled() {
            return p_i50077_4_.wearsArmor();
         }

         public int getSlotStackLimit() {
            return 1;
         }
      });
      if (p_i50077_4_ instanceof AbstractChestedHorseEntity && ((AbstractChestedHorseEntity)p_i50077_4_).hasChest()) {
         for(int k = 0; k < 3; ++k) {
            for(int l = 0; l < ((AbstractChestedHorseEntity)p_i50077_4_).getInventoryColumns(); ++l) {
               this.addSlot(new Slot(p_i50077_3_, 2 + l + k * ((AbstractChestedHorseEntity)p_i50077_4_).getInventoryColumns(), 80 + l * 18, 18 + k * 18));
            }
         }
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.addSlot(new Slot(p_i50077_2_, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.addSlot(new Slot(p_i50077_2_, j1, 8 + j1 * 18, 142));
      }

   }

   public boolean canInteractWith(PlayerEntity playerIn) {
      return this.horseInventory.isUsableByPlayer(playerIn) && this.horse.isAlive() && this.horse.getDistance(playerIn) < 8.0F;
   }

   public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.inventorySlots.get(index);
      if (slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if (index < this.horseInventory.getSizeInventory()) {
            if (!this.mergeItemStack(itemstack1, this.horseInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack()) {
            if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(0).isItemValid(itemstack1)) {
            if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.horseInventory.getSizeInventory() <= 2 || !this.mergeItemStack(itemstack1, 2, this.horseInventory.getSizeInventory(), false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
         } else {
            slot.onSlotChanged();
         }
      }

      return itemstack;
   }

   public void onContainerClosed(PlayerEntity playerIn) {
      super.onContainerClosed(playerIn);
      this.horseInventory.closeInventory(playerIn);
   }
}