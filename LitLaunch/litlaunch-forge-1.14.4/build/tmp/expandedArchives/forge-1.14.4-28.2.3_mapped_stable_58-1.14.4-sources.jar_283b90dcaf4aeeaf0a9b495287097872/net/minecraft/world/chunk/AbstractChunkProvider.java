package net.minecraft.world.chunk;

import java.io.IOException;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractChunkProvider implements IChunkLightProvider, AutoCloseable {
   @Nullable
   public Chunk getChunk(int chunkX, int chunkZ, boolean load) {
      return (Chunk)this.getChunk(chunkX, chunkZ, ChunkStatus.FULL, load);
   }

   @Nullable
   public Chunk func_225313_a(int p_225313_1_, int p_225313_2_) {
      return this.getChunk(p_225313_1_, p_225313_2_, false);
   }

   @Nullable
   public IBlockReader getChunkForLight(int chunkX, int chunkZ) {
      return this.getChunk(chunkX, chunkZ, ChunkStatus.EMPTY, false);
   }

   public boolean chunkExists(int x, int z) {
      return this.getChunk(x, z, ChunkStatus.FULL, false) != null;
   }

   @Nullable
   public abstract IChunk getChunk(int chunkX, int chunkZ, ChunkStatus requiredStatus, boolean load);

   @OnlyIn(Dist.CLIENT)
   public abstract void tick(BooleanSupplier hasTimeLeft);

   public abstract String makeString();

   public abstract ChunkGenerator<?> getChunkGenerator();

   public void close() throws IOException {
   }

   public abstract WorldLightManager func_212863_j_();

   public void setAllowedSpawnTypes(boolean hostile, boolean peaceful) {
   }

   public void forceChunk(ChunkPos pos, boolean add) {
   }

   public boolean isChunkLoaded(Entity entityIn) {
      return true;
   }

   public boolean isChunkLoaded(ChunkPos pos) {
      return true;
   }

   public boolean canTick(BlockPos pos) {
      return true;
   }
}