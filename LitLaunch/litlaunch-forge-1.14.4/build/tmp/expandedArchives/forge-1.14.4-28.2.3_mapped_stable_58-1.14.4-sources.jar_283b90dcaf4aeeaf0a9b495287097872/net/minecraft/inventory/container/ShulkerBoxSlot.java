package net.minecraft.inventory.container;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ShulkerBoxSlot extends Slot {
   public ShulkerBoxSlot(IInventory inventoryIn, int slotIndexIn, int xPosition, int yPosition) {
      super(inventoryIn, slotIndexIn, xPosition, yPosition);
   }

   public boolean isItemValid(ItemStack stack) {
      return !(Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock);
   }
}