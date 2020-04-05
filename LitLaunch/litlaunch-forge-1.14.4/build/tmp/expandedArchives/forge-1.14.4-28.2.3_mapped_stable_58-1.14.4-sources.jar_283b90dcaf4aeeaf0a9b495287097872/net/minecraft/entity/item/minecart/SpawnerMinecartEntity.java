package net.minecraft.entity.item.minecart;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpawnerMinecartEntity extends AbstractMinecartEntity {
   private final AbstractSpawner mobSpawnerLogic = new AbstractSpawner() {
      public void broadcastEvent(int id) {
         SpawnerMinecartEntity.this.world.setEntityState(SpawnerMinecartEntity.this, (byte)id);
      }

      public World getWorld() {
         return SpawnerMinecartEntity.this.world;
      }

      public BlockPos getSpawnerPosition() {
         return new BlockPos(SpawnerMinecartEntity.this);
      }

      @Override
      @javax.annotation.Nullable
      public net.minecraft.entity.Entity getSpawnerEntity() {
         return SpawnerMinecartEntity.this;
      }
   };

   public SpawnerMinecartEntity(EntityType<? extends SpawnerMinecartEntity> p_i50114_1_, World p_i50114_2_) {
      super(p_i50114_1_, p_i50114_2_);
   }

   public SpawnerMinecartEntity(World worldIn, double x, double y, double z) {
      super(EntityType.SPAWNER_MINECART, worldIn, x, y, z);
   }

   public AbstractMinecartEntity.Type getMinecartType() {
      return AbstractMinecartEntity.Type.SPAWNER;
   }

   public BlockState getDefaultDisplayTile() {
      return Blocks.SPAWNER.getDefaultState();
   }

   protected void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.mobSpawnerLogic.read(compound);
   }

   protected void writeAdditional(CompoundNBT compound) {
      super.writeAdditional(compound);
      this.mobSpawnerLogic.write(compound);
   }

   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
      this.mobSpawnerLogic.setDelayToMin(id);
   }

   public void tick() {
      super.tick();
      this.mobSpawnerLogic.tick();
   }

   public boolean ignoreItemEntityData() {
      return true;
   }
}