package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ActiveRenderInfo {
   private boolean valid;
   private IBlockReader world;
   private Entity renderViewEntity;
   private Vec3d pos = Vec3d.ZERO;
   private final BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
   private Vec3d look;
   private Vec3d up;
   private Vec3d field_216796_h;
   private float pitch;
   private float yaw;
   private boolean thirdPerson;
   private boolean thirdPersonReverse;
   private float height;
   private float previousHeight;

   public void update(IBlockReader worldIn, Entity renderViewEntity, boolean thirdPersonIn, boolean thirdPersonReverseIn, float partialTicks) {
      this.valid = true;
      this.world = worldIn;
      this.renderViewEntity = renderViewEntity;
      this.thirdPerson = thirdPersonIn;
      this.thirdPersonReverse = thirdPersonReverseIn;
      this.setDirection(renderViewEntity.getYaw(partialTicks), renderViewEntity.getPitch(partialTicks));
      this.setPosition(MathHelper.lerp((double)partialTicks, renderViewEntity.prevPosX, renderViewEntity.posX), MathHelper.lerp((double)partialTicks, renderViewEntity.prevPosY, renderViewEntity.posY) + (double)MathHelper.lerp(partialTicks, this.previousHeight, this.height), MathHelper.lerp((double)partialTicks, renderViewEntity.prevPosZ, renderViewEntity.posZ));
      if (thirdPersonIn) {
         if (thirdPersonReverseIn) {
            this.yaw += 180.0F;
            this.pitch += -this.pitch * 2.0F;
            this.updateLook();
         }

         this.movePosition(-this.calcCameraDistance(4.0D), 0.0D, 0.0D);
      } else if (renderViewEntity instanceof LivingEntity && ((LivingEntity)renderViewEntity).isSleeping()) {
         Direction direction = ((LivingEntity)renderViewEntity).getBedDirection();
         this.setDirection(direction != null ? direction.getHorizontalAngle() - 180.0F : 0.0F, 0.0F);
         this.movePosition(0.0D, 0.3D, 0.0D);
      }
      net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup cameraSetup = net.minecraftforge.client.ForgeHooksClient.onCameraSetup(net.minecraft.client.Minecraft.getInstance().gameRenderer, this, partialTicks, this.yaw, this.pitch, 0f);
      this.pitch = cameraSetup.getPitch();
      this.yaw = cameraSetup.getYaw();
      GlStateManager.rotatef(cameraSetup.getRoll(), 0, 0, 1);

      GlStateManager.rotatef(this.pitch, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotatef(this.yaw + 180.0F, 0.0F, 1.0F, 0.0F);
   }

   public void interpolateHeight() {
      if (this.renderViewEntity != null) {
         this.previousHeight = this.height;
         this.height += (this.renderViewEntity.getEyeHeight() - this.height) * 0.5F;
      }

   }

   private double calcCameraDistance(double startingDistance) {
      for(int i = 0; i < 8; ++i) {
         float f = (float)((i & 1) * 2 - 1);
         float f1 = (float)((i >> 1 & 1) * 2 - 1);
         float f2 = (float)((i >> 2 & 1) * 2 - 1);
         f = f * 0.1F;
         f1 = f1 * 0.1F;
         f2 = f2 * 0.1F;
         Vec3d vec3d = this.pos.add((double)f, (double)f1, (double)f2);
         Vec3d vec3d1 = new Vec3d(this.pos.x - this.look.x * startingDistance + (double)f + (double)f2, this.pos.y - this.look.y * startingDistance + (double)f1, this.pos.z - this.look.z * startingDistance + (double)f2);
         RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this.renderViewEntity));
         if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
            double d0 = raytraceresult.getHitVec().distanceTo(this.pos);
            if (d0 < startingDistance) {
               startingDistance = d0;
            }
         }
      }

      return startingDistance;
   }

   protected void movePosition(double distanceOffset, double verticalOffset, double horizontalOffset) {
      double d0 = this.look.x * distanceOffset + this.up.x * verticalOffset + this.field_216796_h.x * horizontalOffset;
      double d1 = this.look.y * distanceOffset + this.up.y * verticalOffset + this.field_216796_h.y * horizontalOffset;
      double d2 = this.look.z * distanceOffset + this.up.z * verticalOffset + this.field_216796_h.z * horizontalOffset;
      this.setPostion(new Vec3d(this.pos.x + d0, this.pos.y + d1, this.pos.z + d2));
   }

   protected void updateLook() {
      float f = MathHelper.cos((this.yaw + 90.0F) * ((float)Math.PI / 180F));
      float f1 = MathHelper.sin((this.yaw + 90.0F) * ((float)Math.PI / 180F));
      float f2 = MathHelper.cos(-this.pitch * ((float)Math.PI / 180F));
      float f3 = MathHelper.sin(-this.pitch * ((float)Math.PI / 180F));
      float f4 = MathHelper.cos((-this.pitch + 90.0F) * ((float)Math.PI / 180F));
      float f5 = MathHelper.sin((-this.pitch + 90.0F) * ((float)Math.PI / 180F));
      this.look = new Vec3d((double)(f * f2), (double)f3, (double)(f1 * f2));
      this.up = new Vec3d((double)(f * f4), (double)f5, (double)(f1 * f4));
      this.field_216796_h = this.look.crossProduct(this.up).scale(-1.0D);
   }

   protected void setDirection(float pitchIn, float yawIn) {
      this.pitch = yawIn;
      this.yaw = pitchIn;
      this.updateLook();
   }

   protected void setPosition(double x, double y, double z) {
      this.setPostion(new Vec3d(x, y, z));
   }

   protected void setPostion(Vec3d posIn) {
      this.pos = posIn;
      this.blockPos.setPos(posIn.x, posIn.y, posIn.z);
   }

   public Vec3d getProjectedView() {
      return this.pos;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }

   public float getPitch() {
      return this.pitch;
   }

   public float getYaw() {
      return this.yaw;
   }

   public Entity getRenderViewEntity() {
      return this.renderViewEntity;
   }

   public boolean isValid() {
      return this.valid;
   }

   public boolean isThirdPerson() {
      return this.thirdPerson;
   }

   public IFluidState getFluidState() {
      if (!this.valid) {
         return Fluids.EMPTY.getDefaultState();
      } else {
         IFluidState ifluidstate = this.world.getFluidState(this.blockPos);
         return !ifluidstate.isEmpty() && this.pos.y >= (double)((float)this.blockPos.getY() + ifluidstate.getActualHeight(this.world, this.blockPos)) ? Fluids.EMPTY.getDefaultState() : ifluidstate;
      }
   }

   public final Vec3d getLookDirection() {
      return this.look;
   }

   public final Vec3d getUpDirection() {
      return this.up;
   }

   public void clear() {
      this.world = null;
      this.renderViewEntity = null;
      this.valid = false;
   }

   public net.minecraft.block.BlockState getBlockAtCamera() {
      if (!this.valid)
         return net.minecraft.block.Blocks.AIR.getDefaultState();
      else
         return this.world.getBlockState(this.blockPos).getStateAtViewpoint(this.world, this.blockPos, this.pos);
   }
}