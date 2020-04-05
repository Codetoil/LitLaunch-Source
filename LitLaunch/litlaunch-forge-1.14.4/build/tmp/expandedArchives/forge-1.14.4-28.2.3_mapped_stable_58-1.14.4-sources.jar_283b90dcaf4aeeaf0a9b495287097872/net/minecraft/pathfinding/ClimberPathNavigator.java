package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClimberPathNavigator extends GroundPathNavigator {
   private BlockPos targetPosition;

   public ClimberPathNavigator(MobEntity entityLivingIn, World worldIn) {
      super(entityLivingIn, worldIn);
   }

   public Path func_179680_a(BlockPos pos, int p_179680_2_) {
      this.targetPosition = pos;
      return super.func_179680_a(pos, p_179680_2_);
   }

   public Path func_75494_a(Entity entityIn, int p_75494_2_) {
      this.targetPosition = new BlockPos(entityIn);
      return super.func_75494_a(entityIn, p_75494_2_);
   }

   public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
      Path path = this.func_75494_a(entityIn, 0);
      if (path != null) {
         return this.setPath(path, speedIn);
      } else {
         this.targetPosition = new BlockPos(entityIn);
         this.speed = speedIn;
         return true;
      }
   }

   public void tick() {
      if (!this.noPath()) {
         super.tick();
      } else {
         if (this.targetPosition != null) {
            if (!this.targetPosition.withinDistance(this.entity.getPositionVec(), (double)this.entity.getWidth()) && (!(this.entity.posY > (double)this.targetPosition.getY()) || !(new BlockPos((double)this.targetPosition.getX(), this.entity.posY, (double)this.targetPosition.getZ())).withinDistance(this.entity.getPositionVec(), (double)this.entity.getWidth()))) {
               this.entity.getMoveHelper().setMoveTo((double)this.targetPosition.getX(), (double)this.targetPosition.getY(), (double)this.targetPosition.getZ(), this.speed);
            } else {
               this.targetPosition = null;
            }
         }

      }
   }
}