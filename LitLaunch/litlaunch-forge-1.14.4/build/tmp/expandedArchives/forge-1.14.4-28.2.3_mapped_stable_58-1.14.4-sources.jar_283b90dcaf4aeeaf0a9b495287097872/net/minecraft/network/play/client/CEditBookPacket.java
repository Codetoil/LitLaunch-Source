package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CEditBookPacket implements IPacket<IServerPlayNetHandler> {
   private ItemStack stack;
   private boolean updateAll;
   private Hand hand;

   public CEditBookPacket() {
   }

   @OnlyIn(Dist.CLIENT)
   public CEditBookPacket(ItemStack stackIn, boolean updateAllIn, Hand handIn) {
      this.stack = stackIn.copy();
      this.updateAll = updateAllIn;
      this.hand = handIn;
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.stack = buf.readItemStack();
      this.updateAll = buf.readBoolean();
      this.hand = buf.readEnumValue(Hand.class);
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeItemStack(this.stack);
      buf.writeBoolean(this.updateAll);
      buf.writeEnumValue(this.hand);
   }

   public void processPacket(IServerPlayNetHandler handler) {
      handler.processEditBook(this);
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public boolean shouldUpdateAll() {
      return this.updateAll;
   }

   public Hand getHand() {
      return this.hand;
   }
}