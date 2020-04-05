package net.minecraft.client.gui.screen.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreativeCraftingListener implements IContainerListener {
   private final Minecraft mc;

   public CreativeCraftingListener(Minecraft mc) {
      this.mc = mc;
   }

   public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {
   }

   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
      this.mc.playerController.sendSlotPacket(stack, slotInd);
   }

   public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {
   }
}