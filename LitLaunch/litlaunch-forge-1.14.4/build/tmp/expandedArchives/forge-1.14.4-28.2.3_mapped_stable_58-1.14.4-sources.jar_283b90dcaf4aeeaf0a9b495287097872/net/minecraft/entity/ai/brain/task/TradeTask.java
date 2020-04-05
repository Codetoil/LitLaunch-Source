package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class TradeTask extends Task<VillagerEntity> {
   private final float speed;

   public TradeTask(float speedIn) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED), Integer.MAX_VALUE);
      this.speed = speedIn;
   }

   protected boolean func_212832_a_(ServerWorld worldIn, VillagerEntity owner) {
      PlayerEntity playerentity = owner.getCustomer();
      return owner.isAlive() && playerentity != null && !owner.isInWater() && !owner.velocityChanged && owner.getDistanceSq(playerentity) <= 16.0D && playerentity.openContainer != null;
   }

   protected boolean func_212834_g_(ServerWorld worldIn, VillagerEntity entityIn, long gameTimeIn) {
      return this.func_212832_a_(worldIn, entityIn);
   }

   protected void func_212831_a_(ServerWorld worldIn, VillagerEntity entityIn, long gameTimeIn) {
      this.walkAndLookCustomer(entityIn);
   }

   protected void func_212835_f_(ServerWorld worldIn, VillagerEntity entityIn, long gameTimeIn) {
      Brain<?> brain = entityIn.getBrain();
      brain.removeMemory(MemoryModuleType.WALK_TARGET);
      brain.removeMemory(MemoryModuleType.LOOK_TARGET);
   }

   protected void func_212833_d_(ServerWorld worldIn, VillagerEntity owner, long gameTime) {
      this.walkAndLookCustomer(owner);
   }

   protected boolean isTimedOut(long gameTime) {
      return false;
   }

   private void walkAndLookCustomer(VillagerEntity owner) {
      EntityPosWrapper entityposwrapper = new EntityPosWrapper(owner.getCustomer());
      Brain<?> brain = owner.getBrain();
      brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(entityposwrapper, this.speed, 2));
      brain.setMemory(MemoryModuleType.LOOK_TARGET, entityposwrapper);
   }
}