package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SKeepAlivePacket implements IPacket<IClientPlayNetHandler> {
   private long id;

   public SKeepAlivePacket() {
   }

   public SKeepAlivePacket(long idIn) {
      this.id = idIn;
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleKeepAlive(this);
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.id = buf.readLong();
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeLong(this.id);
   }

   @OnlyIn(Dist.CLIENT)
   public long getId() {
      return this.id;
   }
}