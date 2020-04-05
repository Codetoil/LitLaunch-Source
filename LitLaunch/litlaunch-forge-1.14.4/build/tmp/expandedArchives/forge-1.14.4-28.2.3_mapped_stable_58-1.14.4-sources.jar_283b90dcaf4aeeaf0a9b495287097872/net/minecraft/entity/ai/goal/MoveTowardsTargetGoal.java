package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

public class MoveTowardsTargetGoal extends Goal {
   private final CreatureEntity creature;
   private LivingEntity field_75429_b;
   private double movePosX;
   private double movePosY;
   private double movePosZ;
   private final double speed;
   private final float maxTargetDistance;

   public MoveTowardsTargetGoal(CreatureEntity creature, double speedIn, float targetMaxDistance) {
      this.creature = creature;
      this.speed = speedIn;
      this.maxTargetDistance = targetMaxDistance;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean shouldExecute() {
      this.field_75429_b = this.creature.getAttackTarget();
      if (this.field_75429_b == null) {
         return false;
      } else if (this.field_75429_b.getDistanceSq(this.creature) > (double)(this.maxTargetDistance * this.maxTargetDistance)) {
         return false;
      } else {
         Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.creature, 16, 7, new Vec3d(this.field_75429_b.posX, this.field_75429_b.posY, this.field_75429_b.posZ));
         if (vec3d == null) {
            return false;
         } else {
            this.movePosX = vec3d.x;
            this.movePosY = vec3d.y;
            this.movePosZ = vec3d.z;
            return true;
         }
      }
   }

   public boolean shouldContinueExecuting() {
      return !this.creature.getNavigator().noPath() && this.field_75429_b.isAlive() && this.field_75429_b.getDistanceSq(this.creature) < (double)(this.maxTargetDistance * this.maxTargetDistance);
   }

   public void resetTask() {
      this.field_75429_b = null;
   }

   public void startExecuting() {
      this.creature.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.speed);
   }
}