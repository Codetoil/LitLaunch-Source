package net.minecraft.world.biome.provider;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.structure.Structure;

public abstract class BiomeProvider {
   public static final List<Biome> BIOMES_TO_SPAWN_IN = Lists.newArrayList(Biomes.FOREST, Biomes.PLAINS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.WOODED_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_HILLS);
   protected final Map<Structure<?>, Boolean> hasStructureCache = Maps.newHashMap();
   protected final Set<BlockState> topBlocksCache = Sets.newHashSet();

   public List<Biome> getBiomesToSpawnIn() {
      return BIOMES_TO_SPAWN_IN;
   }

   public Biome getBiome(BlockPos pos) {
      return this.getBiome(pos.getX(), pos.getZ());
   }

   public abstract Biome getBiome(int x, int y);

   public Biome getBiomeAtFactorFour(int factorFourX, int factorFourZ) {
      return this.getBiome(factorFourX << 2, factorFourZ << 2);
   }

   public Biome[] getBiomeBlock(int x, int y, int width, int height) {
      return this.getBiomes(x, y, width, height, true);
   }

   public abstract Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag);

   public abstract Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength);

   @Nullable
   public abstract BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random);

   public float func_222365_c(int p_222365_1_, int p_222365_2_) {
      return 0.0F;
   }

   public abstract boolean hasStructure(Structure<?> structureIn);

   public abstract Set<BlockState> getSurfaceBlocks();
}