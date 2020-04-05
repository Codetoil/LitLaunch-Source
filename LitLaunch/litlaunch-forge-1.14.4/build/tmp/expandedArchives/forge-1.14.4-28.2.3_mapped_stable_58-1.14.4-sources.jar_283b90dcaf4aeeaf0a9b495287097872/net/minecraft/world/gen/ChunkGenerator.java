package net.minecraft.world.gen;

import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.EntityClassification;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

public abstract class ChunkGenerator<C extends GenerationSettings> {
   protected final IWorld world;
   protected final long seed;
   protected final BiomeProvider biomeProvider;
   protected final C settings;

   public ChunkGenerator(IWorld worldIn, BiomeProvider biomeProviderIn, C generationSettingsIn) {
      this.world = worldIn;
      this.seed = worldIn.getSeed();
      this.biomeProvider = biomeProviderIn;
      this.settings = generationSettingsIn;
   }

   public void generateBiomes(IChunk chunkIn) {
      ChunkPos chunkpos = chunkIn.getPos();
      int i = chunkpos.x;
      int j = chunkpos.z;
      Biome[] abiome = this.biomeProvider.getBiomeBlock(i * 16, j * 16, 16, 16);
      chunkIn.setBiomes(abiome);
   }

   protected Biome getBiome(IChunk chunkIn) {
      return chunkIn.getBiome(BlockPos.ZERO);
   }

   protected Biome getBiome(WorldGenRegion worldRegionIn, BlockPos pos) {
      return this.biomeProvider.getBiome(pos);
   }

   public void carve(IChunk chunkIn, GenerationStage.Carving carvingSettings) {
      SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
      int i = 8;
      ChunkPos chunkpos = chunkIn.getPos();
      int j = chunkpos.x;
      int k = chunkpos.z;
      BitSet bitset = chunkIn.getCarvingMask(carvingSettings);

      for(int l = j - 8; l <= j + 8; ++l) {
         for(int i1 = k - 8; i1 <= k + 8; ++i1) {
            List<ConfiguredCarver<?>> list = this.getBiome(chunkIn).getCarvers(carvingSettings);
            ListIterator<ConfiguredCarver<?>> listiterator = list.listIterator();

            while(listiterator.hasNext()) {
               int j1 = listiterator.nextIndex();
               ConfiguredCarver<?> configuredcarver = listiterator.next();
               sharedseedrandom.setLargeFeatureSeed(this.seed + (long)j1, l, i1);
               if (configuredcarver.shouldCarve(sharedseedrandom, l, i1)) {
                  configuredcarver.carve(chunkIn, sharedseedrandom, this.getSeaLevel(), l, i1, j, k, bitset);
               }
            }
         }
      }

   }

   @Nullable
   public BlockPos findNearestStructure(World worldIn, String name, BlockPos pos, int radius, boolean p_211403_5_) {
      Structure<?> structure = Feature.STRUCTURES.get(name.toLowerCase(Locale.ROOT));
      return structure != null ? structure.findNearest(worldIn, this, pos, radius, p_211403_5_) : null;
   }

   public void decorate(WorldGenRegion region) {
      int i = region.getMainChunkX();
      int j = region.getMainChunkZ();
      int k = i * 16;
      int l = j * 16;
      BlockPos blockpos = new BlockPos(k, 0, l);
      Biome biome = this.getBiome(region, blockpos.add(8, 8, 8));
      SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
      long i1 = sharedseedrandom.setDecorationSeed(region.getSeed(), k, l);

      for(GenerationStage.Decoration generationstage$decoration : GenerationStage.Decoration.values()) {
         try {
            biome.decorate(generationstage$decoration, this, region, i1, sharedseedrandom, blockpos);
         } catch (Exception exception) {
            CrashReport crashreport = CrashReport.makeCrashReport(exception, "Biome decoration");
            crashreport.makeCategory("Generation").func_71507_a("CenterX", i).func_71507_a("CenterZ", j).func_71507_a("Step", generationstage$decoration).func_71507_a("Seed", i1).func_71507_a("Biome", Registry.BIOME.getKey(biome));
            throw new ReportedException(crashreport);
         }
      }

   }

   public abstract void generateSurface(IChunk chunkIn);

   public void spawnMobs(WorldGenRegion region) {
   }

   public C getSettings() {
      return this.settings;
   }

   public abstract int getGroundHeight();

   public void func_203222_a(ServerWorld worldIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs) {
   }

   public boolean hasStructure(Biome biomeIn, Structure<? extends IFeatureConfig> structureIn) {
      return biomeIn.hasStructure(structureIn);
   }

   @Nullable
   public <C extends IFeatureConfig> C getStructureConfig(Biome biomeIn, Structure<C> structureIn) {
      return biomeIn.getStructureConfig(structureIn);
   }

   public BiomeProvider getBiomeProvider() {
      return this.biomeProvider;
   }

   public long getSeed() {
      return this.seed;
   }

   public int getMaxHeight() {
      return 256;
   }

   public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification creatureType, BlockPos pos) {
      return this.world.getBiome(pos).getSpawns(creatureType);
   }

   public void initStructureStarts(IChunk chunkIn, ChunkGenerator<?> generator, TemplateManager templateManagerIn) {
      for(Structure<?> structure : Feature.STRUCTURES.values()) {
         if (generator.getBiomeProvider().hasStructure(structure)) {
            SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
            ChunkPos chunkpos = chunkIn.getPos();
            StructureStart structurestart = StructureStart.DUMMY;
            if (structure.hasStartAt(generator, sharedseedrandom, chunkpos.x, chunkpos.z)) {
               Biome biome = this.getBiomeProvider().getBiome(new BlockPos(chunkpos.getXStart() + 9, 0, chunkpos.getZStart() + 9));
               StructureStart structurestart1 = structure.getStartFactory().create(structure, chunkpos.x, chunkpos.z, biome, MutableBoundingBox.getNewBoundingBox(), 0, generator.getSeed());
               structurestart1.init(this, templateManagerIn, chunkpos.x, chunkpos.z, biome);
               structurestart = structurestart1.isValid() ? structurestart1 : StructureStart.DUMMY;
            }

            chunkIn.putStructureStart(structure.getStructureName(), structurestart);
         }
      }

   }

   public void generateStructureStarts(IWorld worldIn, IChunk chunkIn) {
      int i = 8;
      int j = chunkIn.getPos().x;
      int k = chunkIn.getPos().z;
      int l = j << 4;
      int i1 = k << 4;

      for(int j1 = j - 8; j1 <= j + 8; ++j1) {
         for(int k1 = k - 8; k1 <= k + 8; ++k1) {
            long l1 = ChunkPos.asLong(j1, k1);

            for(Entry<String, StructureStart> entry : worldIn.getChunk(j1, k1).getStructureStarts().entrySet()) {
               StructureStart structurestart = entry.getValue();
               if (structurestart != StructureStart.DUMMY && structurestart.getBoundingBox().intersectsWith(l, i1, l + 15, i1 + 15)) {
                  chunkIn.addStructureReference(entry.getKey(), l1);
                  DebugPacketSender.sendStructureStart(worldIn, structurestart);
               }
            }
         }
      }

   }

   public abstract void makeBase(IWorld worldIn, IChunk chunkIn);

   public int getSeaLevel() {
      return world.getDimension().getSeaLevel();
   }

   public abstract int func_222529_a(int p_222529_1_, int p_222529_2_, Heightmap.Type heightmapType);

   public int func_222532_b(int p_222532_1_, int p_222532_2_, Heightmap.Type heightmapType) {
      return this.func_222529_a(p_222532_1_, p_222532_2_, heightmapType);
   }

   public int func_222531_c(int p_222531_1_, int p_222531_2_, Heightmap.Type heightmapType) {
      return this.func_222529_a(p_222531_1_, p_222531_2_, heightmapType) - 1;
   }
}