package net.minecraft.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;

public class FindHidingPlaceDuringRaidTask extends FindHidingPlaceTask {
   public FindHidingPlaceDuringRaidTask(int p_i50360_1_, float p_i50360_2_) {
      super(p_i50360_1_, p_i50360_2_, 1);
   }

   protected boolean func_212832_a_(ServerWorld worldIn, LivingEntity owner) {
      Raid raid = worldIn.findRaid(new BlockPos(owner));
      return super.func_212832_a_(worldIn, owner) && raid != null && raid.isActive() && !raid.isVictory() && !raid.isLoss();
   }
}