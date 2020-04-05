package net.minecraft.world;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public interface IWorldWriter {
   boolean setBlockState(BlockPos pos, BlockState newState, int flags);

   boolean removeBlock(BlockPos pos, boolean isMoving);

   boolean destroyBlock(BlockPos pos, boolean dropBlock);

   default boolean addEntity(Entity entityIn) {
      return false;
   }
}