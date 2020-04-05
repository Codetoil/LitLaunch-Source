package net.minecraft.block;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class RedstoneBlock extends Block {
   public RedstoneBlock(Block.Properties properties) {
      super(properties);
   }

   public boolean canProvidePower(BlockState state) {
      return true;
   }

   public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return 15;
   }
}