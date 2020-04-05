package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class WalkRandomlyTask extends Task<CreatureEntity> {
   private final float field_220431_a;

   public WalkRandomlyTask(float p_i50364_1_) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
      this.field_220431_a = p_i50364_1_;
   }

   protected boolean func_212832_a_(ServerWorld worldIn, CreatureEntity owner) {
      return !worldIn.isSkyLightMax(new BlockPos(owner));
   }

   protected void func_212831_a_(ServerWorld worldIn, CreatureEntity entityIn, long gameTimeIn) {
      BlockPos blockpos = new BlockPos(entityIn);
      List<BlockPos> list = BlockPos.getAllInBox(blockpos.add(-1, -1, -1), blockpos.add(1, 1, 1)).map(BlockPos::toImmutable).collect(Collectors.toList());
      Collections.shuffle(list);
      Optional<BlockPos> optional = list.stream().filter((p_220428_1_) -> {
         return !worldIn.isSkyLightMax(p_220428_1_);
      }).filter((p_220427_2_) -> {
         return worldIn.isTopSolid(p_220427_2_, entityIn);
      }).filter((p_220429_2_) -> {
         return worldIn.areCollisionShapesEmpty(entityIn);
      }).findFirst();
      optional.ifPresent((p_220430_2_) -> {
         entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(p_220430_2_, this.field_220431_a, 0));
      });
   }
}