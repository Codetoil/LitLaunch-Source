package net.minecraft.entity.ai.controller;

import net.minecraft.entity.MobEntity;

public class JumpController {
   private final MobEntity mob;
   protected boolean isJumping;

   public JumpController(MobEntity mob) {
      this.mob = mob;
   }

   public void setJumping() {
      this.isJumping = true;
   }

   public void tick() {
      this.mob.setJumping(this.isJumping);
      this.isJumping = false;
   }
}