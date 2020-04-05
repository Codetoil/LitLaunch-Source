package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CKeepAlivePacket implements IPacket<IServerPlayNetHandler> {
   private long key;

   public CKeepAlivePacket() {
   }

   @OnlyIn(Dist.CLIENT)
   public CKeepAlivePacket(long idIn) {
      this.key = idIn;
   }

   public void processPacket(IServerPlayNetHandler handler) {
      handler.processKeepAlive(this);
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.key = buf.readLong();
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeLong(this.key);
   }

   public long getKey() {
      return this.key;
   }
}