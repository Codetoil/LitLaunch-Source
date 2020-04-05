package net.minecraft.entity.boss.dragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;

public class EnderDragonPartEntity extends Entity {
   public final EnderDragonEntity dragon;
   public final String field_213853_c;
   private final EntitySize field_213854_d;

   public EnderDragonPartEntity(EnderDragonEntity dragon, String p_i50232_2_, float p_i50232_3_, float p_i50232_4_) {
      super(dragon.getType(), dragon.world);
      this.field_213854_d = EntitySize.flexible(p_i50232_3_, p_i50232_4_);
      this.recalculateSize();
      this.dragon = dragon;
      this.field_213853_c = p_i50232_2_;
   }

   protected void registerData() {
   }

   protected void readAdditional(CompoundNBT compound) {
   }

   protected void writeAdditional(CompoundNBT compound) {
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      return this.isInvulnerableTo(source) ? false : this.dragon.func_213403_a(this, source, amount);
   }

   public boolean isEntityEqual(Entity entityIn) {
      return this == entityIn || this.dragon == entityIn;
   }

   public IPacket<?> createSpawnPacket() {
      throw new UnsupportedOperationException();
   }

   public EntitySize getSize(Pose poseIn) {
      return this.field_213854_d;
   }
}