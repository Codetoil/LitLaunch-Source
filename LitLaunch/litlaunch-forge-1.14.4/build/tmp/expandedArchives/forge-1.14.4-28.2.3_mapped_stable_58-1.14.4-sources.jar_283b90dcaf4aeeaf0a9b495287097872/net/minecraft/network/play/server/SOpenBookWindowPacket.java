package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SOpenBookWindowPacket implements IPacket<IClientPlayNetHandler> {
   private Hand hand;

   public SOpenBookWindowPacket() {
   }

   public SOpenBookWindowPacket(Hand hand) {
      this.hand = hand;
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.hand = buf.readEnumValue(Hand.class);
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeEnumValue(this.hand);
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleOpenBookPacket(this);
   }

   @OnlyIn(Dist.CLIENT)
   public Hand getHand() {
      return this.hand;
   }
}