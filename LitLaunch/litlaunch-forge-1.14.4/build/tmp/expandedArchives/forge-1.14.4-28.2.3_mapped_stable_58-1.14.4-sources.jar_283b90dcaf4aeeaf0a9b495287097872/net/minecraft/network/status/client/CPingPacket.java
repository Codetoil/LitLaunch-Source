package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.IServerStatusNetHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CPingPacket implements IPacket<IServerStatusNetHandler> {
   private long clientTime;

   public CPingPacket() {
   }

   @OnlyIn(Dist.CLIENT)
   public CPingPacket(long clientTimeIn) {
      this.clientTime = clientTimeIn;
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.clientTime = buf.readLong();
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeLong(this.clientTime);
   }

   public void processPacket(IServerStatusNetHandler handler) {
      handler.processPing(this);
   }

   public long getClientTime() {
      return this.clientTime;
   }
}