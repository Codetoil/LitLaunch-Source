package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;

public class CreeperSwellGoal extends Goal {
   private final CreeperEntity field_75269_a;
   private LivingEntity field_75268_b;

   public CreeperSwellGoal(CreeperEntity entitycreeperIn) {
      this.field_75269_a = entitycreeperIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean shouldExecute() {
      LivingEntity livingentity = this.field_75269_a.getAttackTarget();
      return this.field_75269_a.getCreeperState() > 0 || livingentity != null && this.field_75269_a.getDistanceSq(livingentity) < 9.0D;
   }

   public void startExecuting() {
      this.field_75269_a.getNavigator().clearPath();
      this.field_75268_b = this.field_75269_a.getAttackTarget();
   }

   public void resetTask() {
      this.field_75268_b = null;
   }

   public void tick() {
      if (this.field_75268_b == null) {
         this.field_75269_a.setCreeperState(-1);
      } else if (this.field_75269_a.getDistanceSq(this.field_75268_b) > 49.0D) {
         this.field_75269_a.setCreeperState(-1);
      } else if (!this.field_75269_a.getEntitySenses().canSee(this.field_75268_b)) {
         this.field_75269_a.setCreeperState(-1);
      } else {
         this.field_75269_a.setCreeperState(1);
      }
   }
}