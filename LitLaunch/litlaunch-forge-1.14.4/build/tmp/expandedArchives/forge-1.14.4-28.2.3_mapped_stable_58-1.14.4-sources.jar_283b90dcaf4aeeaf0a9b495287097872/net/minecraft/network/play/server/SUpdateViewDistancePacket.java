package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SUpdateViewDistancePacket implements IPacket<IClientPlayNetHandler> {
   private int viewDistance;

   public SUpdateViewDistancePacket() {
   }

   public SUpdateViewDistancePacket(int viewDistanceIn) {
      this.viewDistance = viewDistanceIn;
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.viewDistance = buf.readVarInt();
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeVarInt(this.viewDistance);
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleUpdateViewDistancePacket(this);
   }

   @OnlyIn(Dist.CLIENT)
   public int getViewDistance() {
      return this.viewDistance;
   }
}