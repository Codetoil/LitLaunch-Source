package net.minecraft.entity.ai.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.pathfinding.GroundPathNavigator;

public class RestrictSunGoal extends Goal {
   private final CreatureEntity entity;

   public RestrictSunGoal(CreatureEntity creature) {
      this.entity = creature;
   }

   public boolean shouldExecute() {
      return this.entity.world.isDaytime() && this.entity.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty() && this.entity.getNavigator() instanceof GroundPathNavigator;
   }

   public void startExecuting() {
      ((GroundPathNavigator)this.entity.getNavigator()).setAvoidSun(true);
   }

   public void resetTask() {
      ((GroundPathNavigator)this.entity.getNavigator()).setAvoidSun(false);
   }
}