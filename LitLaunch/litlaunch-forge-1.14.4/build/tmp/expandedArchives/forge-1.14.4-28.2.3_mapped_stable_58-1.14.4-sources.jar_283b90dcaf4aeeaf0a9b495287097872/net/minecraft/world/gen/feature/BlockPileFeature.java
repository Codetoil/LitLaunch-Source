package net.minecraft.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;

public abstract class BlockPileFeature extends Feature<NoFeatureConfig> {
   public BlockPileFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49914_1_) {
      super(p_i49914_1_);
   }

   public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      if (pos.getY() < 5) {
         return false;
      } else {
         int i = 2 + rand.nextInt(2);
         int j = 2 + rand.nextInt(2);

         for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-i, 0, -j), pos.add(i, 1, j))) {
            int k = pos.getX() - blockpos.getX();
            int l = pos.getZ() - blockpos.getZ();
            if ((float)(k * k + l * l) <= rand.nextFloat() * 10.0F - rand.nextFloat() * 6.0F) {
               this.tryPlace(worldIn, blockpos, rand);
            } else if ((double)rand.nextFloat() < 0.031D) {
               this.tryPlace(worldIn, blockpos, rand);
            }
         }

         return true;
      }
   }

   private boolean canPlaceOn(IWorld worldIn, BlockPos pos, Random random) {
      BlockPos blockpos = pos.down();
      BlockState blockstate = worldIn.getBlockState(blockpos);
      return blockstate.getBlock() == Blocks.GRASS_PATH ? random.nextBoolean() : blockstate.func_224755_d(worldIn, blockpos, Direction.UP);
   }

   private void tryPlace(IWorld worldIn, BlockPos pos, Random random) {
      if (worldIn.isAirBlock(pos) && this.canPlaceOn(worldIn, pos, random)) {
         worldIn.setBlockState(pos, this.getRandomBlock(worldIn), 4);
      }

   }

   protected abstract BlockState getRandomBlock(IWorld worldIn);
}