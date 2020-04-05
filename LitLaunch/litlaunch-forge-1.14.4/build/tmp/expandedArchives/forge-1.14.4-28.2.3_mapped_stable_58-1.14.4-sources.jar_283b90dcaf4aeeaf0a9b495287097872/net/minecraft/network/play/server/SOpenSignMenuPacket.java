package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SOpenSignMenuPacket implements IPacket<IClientPlayNetHandler> {
   private BlockPos signPosition;

   public SOpenSignMenuPacket() {
   }

   public SOpenSignMenuPacket(BlockPos posIn) {
      this.signPosition = posIn;
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleSignEditorOpen(this);
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.signPosition = buf.readBlockPos();
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeBlockPos(this.signPosition);
   }

   @OnlyIn(Dist.CLIENT)
   public BlockPos getSignPosition() {
      return this.signPosition;
   }
}