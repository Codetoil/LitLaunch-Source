package net.minecraft.inventory.container;

import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.MerchantInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.stats.Stats;

public class MerchantResultSlot extends Slot {
   private final MerchantInventory merchantInventory;
   private final PlayerEntity player;
   private int removeCount;
   private final IMerchant merchant;

   public MerchantResultSlot(PlayerEntity player, IMerchant merchant, MerchantInventory merchantInventory, int slotIndex, int xPosition, int yPosition) {
      super(merchantInventory, slotIndex, xPosition, yPosition);
      this.player = player;
      this.merchant = merchant;
      this.merchantInventory = merchantInventory;
   }

   public boolean isItemValid(ItemStack stack) {
      return false;
   }

   public ItemStack decrStackSize(int amount) {
      if (this.getHasStack()) {
         this.removeCount += Math.min(amount, this.getStack().getCount());
      }

      return super.decrStackSize(amount);
   }

   protected void onCrafting(ItemStack stack, int amount) {
      this.removeCount += amount;
      this.onCrafting(stack);
   }

   protected void onCrafting(ItemStack stack) {
      stack.onCrafting(this.player.world, this.player, this.removeCount);
      this.removeCount = 0;
   }

   public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
      this.onCrafting(stack);
      MerchantOffer merchantoffer = this.merchantInventory.func_214025_g();
      if (merchantoffer != null) {
         ItemStack itemstack = this.merchantInventory.getStackInSlot(0);
         ItemStack itemstack1 = this.merchantInventory.getStackInSlot(1);
         if (merchantoffer.doTransaction(itemstack, itemstack1) || merchantoffer.doTransaction(itemstack1, itemstack)) {
            this.merchant.onTrade(merchantoffer);
            thePlayer.addStat(Stats.TRADED_WITH_VILLAGER);
            this.merchantInventory.setInventorySlotContents(0, itemstack);
            this.merchantInventory.setInventorySlotContents(1, itemstack1);
         }

         this.merchant.setXP(this.merchant.getXp() + merchantoffer.getGivenExp());
      }

      return stack;
   }
}