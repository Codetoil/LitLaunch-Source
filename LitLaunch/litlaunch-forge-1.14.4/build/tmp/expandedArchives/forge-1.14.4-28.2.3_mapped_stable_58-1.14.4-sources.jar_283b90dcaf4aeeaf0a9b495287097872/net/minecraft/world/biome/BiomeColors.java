package net.minecraft.world.biome;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.CubeCoordinateIterator;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BiomeColors {
   private static final BiomeColors.IColorResolver GRASS_COLOR = Biome::getGrassColor;
   private static final BiomeColors.IColorResolver FOLIAGE_COLOR = Biome::getFoliageColor;
   private static final BiomeColors.IColorResolver WATER_COLOR = (p_210280_0_, p_210280_1_) -> {
      return p_210280_0_.getWaterColor();
   };
   private static final BiomeColors.IColorResolver WATER_FOG_COLOR = (p_210279_0_, p_210279_1_) -> {
      return p_210279_0_.getWaterFogColor();
   };

   private static int getColor(IEnviromentBlockReader reader, BlockPos pos, BiomeColors.IColorResolver resolver) {
      int i = 0;
      int j = 0;
      int k = 0;
      int l = Minecraft.getInstance().gameSettings.biomeBlendRadius;
      if (l == 0) {
         return resolver.getColor(reader.getBiome(pos), pos);
      } else {
         int i1 = (l * 2 + 1) * (l * 2 + 1);
         CubeCoordinateIterator cubecoordinateiterator = new CubeCoordinateIterator(pos.getX() - l, pos.getY(), pos.getZ() - l, pos.getX() + l, pos.getY(), pos.getZ() + l);

         int j1;
         for(BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(); cubecoordinateiterator.hasNext(); k += j1 & 255) {
            blockpos$mutableblockpos.setPos(cubecoordinateiterator.getX(), cubecoordinateiterator.getY(), cubecoordinateiterator.getZ());
            j1 = resolver.getColor(reader.getBiome(blockpos$mutableblockpos), blockpos$mutableblockpos);
            i += (j1 & 16711680) >> 16;
            j += (j1 & '\uff00') >> 8;
         }

         return (i / i1 & 255) << 16 | (j / i1 & 255) << 8 | k / i1 & 255;
      }
   }

   public static int getGrassColor(IEnviromentBlockReader reader, BlockPos pos) {
      return getColor(reader, pos, GRASS_COLOR);
   }

   public static int getFoliageColor(IEnviromentBlockReader reader, BlockPos pos) {
      return getColor(reader, pos, FOLIAGE_COLOR);
   }

   public static int getWaterColor(IEnviromentBlockReader reader, BlockPos pos) {
      return getColor(reader, pos, WATER_COLOR);
   }

   @OnlyIn(Dist.CLIENT)
   interface IColorResolver {
      int getColor(Biome p_getColor_1_, BlockPos p_getColor_2_);
   }
}