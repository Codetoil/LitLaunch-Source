package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PolarBearModel<T extends PolarBearEntity> extends QuadrupedModel<T> {
   public PolarBearModel() {
      super(12, 0.0F);
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.headModel = new RendererModel(this, 0, 0);
      this.headModel.addBox(-3.5F, -3.0F, -3.0F, 7, 7, 7, 0.0F);
      this.headModel.setRotationPoint(0.0F, 10.0F, -16.0F);
      this.headModel.setTextureOffset(0, 44).addBox(-2.5F, 1.0F, -6.0F, 5, 3, 3, 0.0F);
      this.headModel.setTextureOffset(26, 0).addBox(-4.5F, -4.0F, -1.0F, 2, 2, 1, 0.0F);
      RendererModel renderermodel = this.headModel.setTextureOffset(26, 0);
      renderermodel.mirror = true;
      renderermodel.addBox(2.5F, -4.0F, -1.0F, 2, 2, 1, 0.0F);
      this.body = new RendererModel(this);
      this.body.setTextureOffset(0, 19).addBox(-5.0F, -13.0F, -7.0F, 14, 14, 11, 0.0F);
      this.body.setTextureOffset(39, 0).addBox(-4.0F, -25.0F, -7.0F, 12, 12, 10, 0.0F);
      this.body.setRotationPoint(-2.0F, 9.0F, 12.0F);
      int i = 10;
      this.legBackRight = new RendererModel(this, 50, 22);
      this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 8, 0.0F);
      this.legBackRight.setRotationPoint(-3.5F, 14.0F, 6.0F);
      this.legBackLeft = new RendererModel(this, 50, 22);
      this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 8, 0.0F);
      this.legBackLeft.setRotationPoint(3.5F, 14.0F, 6.0F);
      this.legFrontRight = new RendererModel(this, 50, 40);
      this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 6, 0.0F);
      this.legFrontRight.setRotationPoint(-2.5F, 14.0F, -7.0F);
      this.legFrontLeft = new RendererModel(this, 50, 40);
      this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 6, 0.0F);
      this.legFrontLeft.setRotationPoint(2.5F, 14.0F, -7.0F);
      --this.legBackRight.rotationPointX;
      ++this.legBackLeft.rotationPointX;
      this.legBackRight.rotationPointZ += 0.0F;
      this.legBackLeft.rotationPointZ += 0.0F;
      --this.legFrontRight.rotationPointX;
      ++this.legFrontLeft.rotationPointX;
      --this.legFrontRight.rotationPointZ;
      --this.legFrontLeft.rotationPointZ;
      this.childZOffset += 2.0F;
   }

   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (this.isChild) {
         float f = 2.0F;
         this.childYOffset = 16.0F;
         this.childZOffset = 4.0F;
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.6666667F, 0.6666667F, 0.6666667F);
         GlStateManager.translatef(0.0F, this.childYOffset * scale, this.childZOffset * scale);
         this.headModel.render(scale);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.5F, 0.5F, 0.5F);
         GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
         this.body.render(scale);
         this.legBackRight.render(scale);
         this.legBackLeft.render(scale);
         this.legFrontRight.render(scale);
         this.legFrontLeft.render(scale);
         GlStateManager.popMatrix();
      } else {
         this.headModel.render(scale);
         this.body.render(scale);
         this.legBackRight.render(scale);
         this.legBackLeft.render(scale);
         this.legFrontRight.render(scale);
         this.legFrontLeft.render(scale);
      }

   }

   public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
      super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      float f = ageInTicks - (float)entityIn.ticksExisted;
      float f1 = entityIn.getStandingAnimationScale(f);
      f1 = f1 * f1;
      float f2 = 1.0F - f1;
      this.body.rotateAngleX = ((float)Math.PI / 2F) - f1 * (float)Math.PI * 0.35F;
      this.body.rotationPointY = 9.0F * f2 + 11.0F * f1;
      this.legFrontRight.rotationPointY = 14.0F * f2 - 6.0F * f1;
      this.legFrontRight.rotationPointZ = -8.0F * f2 - 4.0F * f1;
      this.legFrontRight.rotateAngleX -= f1 * (float)Math.PI * 0.45F;
      this.legFrontLeft.rotationPointY = this.legFrontRight.rotationPointY;
      this.legFrontLeft.rotationPointZ = this.legFrontRight.rotationPointZ;
      this.legFrontLeft.rotateAngleX -= f1 * (float)Math.PI * 0.45F;
      if (this.isChild) {
         this.headModel.rotationPointY = 10.0F * f2 - 9.0F * f1;
         this.headModel.rotationPointZ = -16.0F * f2 - 7.0F * f1;
      } else {
         this.headModel.rotationPointY = 10.0F * f2 - 14.0F * f1;
         this.headModel.rotationPointZ = -16.0F * f2 - 3.0F * f1;
      }

      this.headModel.rotateAngleX += f1 * (float)Math.PI * 0.15F;
   }
}