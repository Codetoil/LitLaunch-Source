package net.minecraft.inventory.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class FurnaceFuelSlot extends Slot {
   private final AbstractFurnaceContainer field_216939_a;

   public FurnaceFuelSlot(AbstractFurnaceContainer p_i50084_1_, IInventory p_i50084_2_, int p_i50084_3_, int p_i50084_4_, int p_i50084_5_) {
      super(p_i50084_2_, p_i50084_3_, p_i50084_4_, p_i50084_5_);
      this.field_216939_a = p_i50084_1_;
   }

   public boolean isItemValid(ItemStack stack) {
      return this.field_216939_a.isFuel(stack) || isBucket(stack);
   }

   public int getItemStackLimit(ItemStack stack) {
      return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
   }

   public static boolean isBucket(ItemStack stack) {
      return stack.getItem() == Items.BUCKET;
   }
}