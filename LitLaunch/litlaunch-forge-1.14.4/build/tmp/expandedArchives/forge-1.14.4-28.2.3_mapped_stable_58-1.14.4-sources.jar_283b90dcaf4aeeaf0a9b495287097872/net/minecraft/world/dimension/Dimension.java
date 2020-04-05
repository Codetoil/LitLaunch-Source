package net.minecraft.world.dimension;

import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class Dimension implements net.minecraftforge.common.extensions.IForgeDimension {
   public static final float[] MOON_PHASE_FACTORS = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   protected final World world;
   private final DimensionType type;
   protected boolean doesWaterVaporize;
   protected boolean nether;
   protected final float[] lightBrightnessTable = new float[16];
   private final float[] colorsSunriseSunset = new float[4];

   public Dimension(World worldIn, DimensionType typeIn) {
      this.world = worldIn;
      this.type = typeIn;
      this.generateLightBrightnessTable();
   }

   protected void generateLightBrightnessTable() {
      float f = 0.0F;

      for(int i = 0; i <= 15; ++i) {
         float f1 = 1.0F - (float)i / 15.0F;
         this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 1.0F + 0.0F;
      }

   }

   public int getMoonPhase(long worldTime) {
      return (int)(worldTime / 24000L % 8L + 8L) % 8;
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
      float f = 0.4F;
      float f1 = MathHelper.cos(celestialAngle * ((float)Math.PI * 2F)) - 0.0F;
      float f2 = -0.0F;
      if (f1 >= -0.4F && f1 <= 0.4F) {
         float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.5F;
         float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * (float)Math.PI)) * 0.99F;
         f4 = f4 * f4;
         this.colorsSunriseSunset[0] = f3 * 0.3F + 0.7F;
         this.colorsSunriseSunset[1] = f3 * f3 * 0.7F + 0.2F;
         this.colorsSunriseSunset[2] = f3 * f3 * 0.0F + 0.2F;
         this.colorsSunriseSunset[3] = f4;
         return this.colorsSunriseSunset;
      } else {
         return null;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public float getCloudHeight() {
      return this.getWorld().getWorldInfo().getGenerator().getCloudHeight();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isSkyColored() {
      return true;
   }

   @Nullable
   public BlockPos getSpawnCoordinate() {
      return null;
   }

   @OnlyIn(Dist.CLIENT)
   public double getVoidFogYFactor() {
      return this.world.getWorldInfo().getGenerator().voidFadeMagnitude();
   }

   public boolean doesWaterVaporize() {
      return this.doesWaterVaporize;
   }

   public boolean hasSkyLight() {
      return this.type.hasSkyLight();
   }

   public boolean isNether() {
      return this.nether;
   }

   public float[] getLightBrightnessTable() {
      return this.lightBrightnessTable;
   }

   public WorldBorder createWorldBorder() {
      return new WorldBorder();
   }

   public void onWorldSave() {
   }

   public void tick() {
   }

   @Deprecated //Forge: Use WorldType.createChunkGenerator
   public abstract ChunkGenerator<?> createChunkGenerator();

   @Nullable
   public abstract BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid);

   @Nullable
   public abstract BlockPos findSpawn(int posX, int posZ, boolean checkValid);

   public abstract float calculateCelestialAngle(long worldTime, float partialTicks);

   public abstract boolean isSurfaceWorld();

   @OnlyIn(Dist.CLIENT)
   public abstract Vec3d getFogColor(float celestialAngle, float partialTicks);

   public abstract boolean canRespawnHere();

   @OnlyIn(Dist.CLIENT)
   public abstract boolean doesXZShowFog(int x, int z);

   public DimensionType getType() {
       return this.type;
   }

   /*======================================= Forge Start =========================================*/
   private net.minecraftforge.client.IRenderHandler skyRenderer = null;
   private net.minecraftforge.client.IRenderHandler cloudRenderer = null;
   private net.minecraftforge.client.IRenderHandler weatherRenderer = null;

   @Nullable
   @OnlyIn(Dist.CLIENT)
   @Override
   public net.minecraftforge.client.IRenderHandler getSkyRenderer() {
      return this.skyRenderer;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void setSkyRenderer(net.minecraftforge.client.IRenderHandler skyRenderer) {
      this.skyRenderer = skyRenderer;
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   @Override
   public net.minecraftforge.client.IRenderHandler getCloudRenderer() {
      return cloudRenderer;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void setCloudRenderer(net.minecraftforge.client.IRenderHandler renderer) {
      cloudRenderer = renderer;
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   @Override
   public net.minecraftforge.client.IRenderHandler getWeatherRenderer() {
      return weatherRenderer;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void setWeatherRenderer(net.minecraftforge.client.IRenderHandler renderer) {
      weatherRenderer = renderer;
   }

   @Override
   public void resetRainAndThunder() {
      world.getWorldInfo().setRainTime(0);
      world.getWorldInfo().setRaining(false);
      world.getWorldInfo().setThunderTime(0);
      world.getWorldInfo().setThundering(false);
   }

   @Override
   public World getWorld() {
      return this.world;
   }
}