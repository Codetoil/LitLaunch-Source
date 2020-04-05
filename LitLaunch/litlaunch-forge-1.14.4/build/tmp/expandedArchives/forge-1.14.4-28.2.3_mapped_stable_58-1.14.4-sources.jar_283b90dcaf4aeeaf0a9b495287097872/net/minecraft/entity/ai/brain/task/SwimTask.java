package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.server.ServerWorld;

public class SwimTask extends Task<MobEntity> {
   private final float field_220589_a;
   private final float field_220590_b;

   public SwimTask(float p_i50339_1_, float p_i50339_2_) {
      super(ImmutableMap.of());
      this.field_220589_a = p_i50339_1_;
      this.field_220590_b = p_i50339_2_;
   }

   protected boolean func_212832_a_(ServerWorld worldIn, MobEntity owner) {
      return owner.isInWater() && owner.getSubmergedHeight() > (double)this.field_220589_a || owner.isInLava();
   }

   protected boolean func_212834_g_(ServerWorld worldIn, MobEntity entityIn, long gameTimeIn) {
      return this.func_212832_a_(worldIn, entityIn);
   }

   protected void func_212833_d_(ServerWorld worldIn, MobEntity owner, long gameTime) {
      if (owner.getRNG().nextFloat() < this.field_220590_b) {
         owner.getJumpController().setJumping();
      }

   }
}