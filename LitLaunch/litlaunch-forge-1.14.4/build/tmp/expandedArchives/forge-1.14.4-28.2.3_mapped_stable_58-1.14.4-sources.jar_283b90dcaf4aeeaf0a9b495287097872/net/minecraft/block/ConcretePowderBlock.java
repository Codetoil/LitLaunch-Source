package net.minecraft.block;

import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ConcretePowderBlock extends FallingBlock {
   private final BlockState solidifiedState;

   public ConcretePowderBlock(Block p_i48423_1_, Block.Properties properties) {
      super(properties);
      this.solidifiedState = p_i48423_1_.getDefaultState();
   }

   public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState, BlockState hitState) {
      if (causesSolidify(hitState)) {
         worldIn.setBlockState(pos, this.solidifiedState, 3);
      }

   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      IBlockReader iblockreader = context.getWorld();
      BlockPos blockpos = context.getPos();
      return !causesSolidify(iblockreader.getBlockState(blockpos)) && !isTouchingLiquid(iblockreader, blockpos) ? super.getStateForPlacement(context) : this.solidifiedState;
   }

   private static boolean isTouchingLiquid(IBlockReader p_196441_0_, BlockPos p_196441_1_) {
      boolean flag = false;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_196441_1_);

      for(Direction direction : Direction.values()) {
         BlockState blockstate = p_196441_0_.getBlockState(blockpos$mutableblockpos);
         if (direction != Direction.DOWN || causesSolidify(blockstate)) {
            blockpos$mutableblockpos.setPos(p_196441_1_).move(direction);
            blockstate = p_196441_0_.getBlockState(blockpos$mutableblockpos);
            if (causesSolidify(blockstate) && !blockstate.func_224755_d(p_196441_0_, p_196441_1_, direction.getOpposite())) {
               flag = true;
               break;
            }
         }
      }

      return flag;
   }

   private static boolean causesSolidify(BlockState p_212566_0_) {
      return p_212566_0_.getFluidState().isTagged(FluidTags.WATER);
   }

   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      return isTouchingLiquid(worldIn, currentPos) ? this.solidifiedState : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }
}