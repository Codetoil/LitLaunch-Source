package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SUpdateHealthPacket implements IPacket<IClientPlayNetHandler> {
   private float health;
   private int foodLevel;
   private float saturationLevel;

   public SUpdateHealthPacket() {
   }

   public SUpdateHealthPacket(float healthIn, int foodLevelIn, float saturationLevelIn) {
      this.health = healthIn;
      this.foodLevel = foodLevelIn;
      this.saturationLevel = saturationLevelIn;
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.health = buf.readFloat();
      this.foodLevel = buf.readVarInt();
      this.saturationLevel = buf.readFloat();
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeFloat(this.health);
      buf.writeVarInt(this.foodLevel);
      buf.writeFloat(this.saturationLevel);
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleUpdateHealth(this);
   }

   @OnlyIn(Dist.CLIENT)
   public float getHealth() {
      return this.health;
   }

   @OnlyIn(Dist.CLIENT)
   public int getFoodLevel() {
      return this.foodLevel;
   }

   @OnlyIn(Dist.CLIENT)
   public float getSaturationLevel() {
      return this.saturationLevel;
   }
}