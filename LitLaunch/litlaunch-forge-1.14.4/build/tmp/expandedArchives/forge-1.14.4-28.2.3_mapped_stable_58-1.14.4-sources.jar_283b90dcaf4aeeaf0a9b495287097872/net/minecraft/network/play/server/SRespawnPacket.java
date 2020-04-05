package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SRespawnPacket implements IPacket<IClientPlayNetHandler> {
   private DimensionType dimensionID;
   private GameType gameType;
   private WorldType worldType;
   private int dimensionInt;

   public SRespawnPacket() {
   }

   public SRespawnPacket(DimensionType dimensionIn, WorldType worldTypeIn, GameType gameTypeIn) {
      this.dimensionID = dimensionIn;
      this.gameType = gameTypeIn;
      this.worldType = worldTypeIn;
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleRespawn(this);
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.dimensionInt = buf.readInt();
      this.gameType = GameType.getByID(buf.readUnsignedByte());
      this.worldType = WorldType.byName(buf.readString(16));
      if (this.worldType == null) {
         this.worldType = WorldType.DEFAULT;
      }

   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeInt(this.dimensionID.getId());
      buf.writeByte(this.gameType.getID());
      buf.writeString(this.worldType.getName());
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionType getDimension() {
      return this.dimensionID == null ? this.dimensionID = net.minecraftforge.fml.network.NetworkHooks.getDummyDimType(this.dimensionInt) : this.dimensionID;
   }

   @OnlyIn(Dist.CLIENT)
   public GameType getGameType() {
      return this.gameType;
   }

   @OnlyIn(Dist.CLIENT)
   public WorldType getWorldType() {
      return this.worldType;
   }
}