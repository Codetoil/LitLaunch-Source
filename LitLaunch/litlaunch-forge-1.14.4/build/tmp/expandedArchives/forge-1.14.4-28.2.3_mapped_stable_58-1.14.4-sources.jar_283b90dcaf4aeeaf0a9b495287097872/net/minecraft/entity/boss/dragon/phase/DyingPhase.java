package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;

public class DyingPhase extends Phase {
   private Vec3d targetLocation;
   private int time;

   public DyingPhase(EnderDragonEntity dragonIn) {
      super(dragonIn);
   }

   public void clientTick() {
      if (this.time++ % 10 == 0) {
         float f = (this.dragon.getRNG().nextFloat() - 0.5F) * 8.0F;
         float f1 = (this.dragon.getRNG().nextFloat() - 0.5F) * 4.0F;
         float f2 = (this.dragon.getRNG().nextFloat() - 0.5F) * 8.0F;
         this.dragon.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.dragon.posX + (double)f, this.dragon.posY + 2.0D + (double)f1, this.dragon.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
      }

   }

   public void serverTick() {
      ++this.time;
      if (this.targetLocation == null) {
         BlockPos blockpos = this.dragon.world.getHeight(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION);
         this.targetLocation = new Vec3d((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
      }

      double d0 = this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
      if (!(d0 < 100.0D) && !(d0 > 22500.0D) && !this.dragon.collidedHorizontally && !this.dragon.collidedVertically) {
         this.dragon.setHealth(1.0F);
      } else {
         this.dragon.setHealth(0.0F);
      }

   }

   public void initPhase() {
      this.targetLocation = null;
      this.time = 0;
   }

   public float getMaxRiseOrFall() {
      return 3.0F;
   }

   @Nullable
   public Vec3d getTargetLocation() {
      return this.targetLocation;
   }

   public PhaseType<DyingPhase> getType() {
      return PhaseType.DYING;
   }
}