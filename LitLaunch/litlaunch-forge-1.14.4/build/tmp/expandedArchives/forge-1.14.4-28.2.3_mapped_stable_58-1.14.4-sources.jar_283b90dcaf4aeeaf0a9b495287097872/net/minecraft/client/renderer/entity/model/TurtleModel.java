package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurtleModel<T extends TurtleEntity> extends QuadrupedModel<T> {
   private final RendererModel pregnant;

   public TurtleModel(float p_i48834_1_) {
      super(12, p_i48834_1_);
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.headModel = new RendererModel(this, 3, 0);
      this.headModel.addBox(-3.0F, -1.0F, -3.0F, 6, 5, 6, 0.0F);
      this.headModel.setRotationPoint(0.0F, 19.0F, -10.0F);
      this.body = new RendererModel(this);
      this.body.setTextureOffset(7, 37).addBox(-9.5F, 3.0F, -10.0F, 19, 20, 6, 0.0F);
      this.body.setTextureOffset(31, 1).addBox(-5.5F, 3.0F, -13.0F, 11, 18, 3, 0.0F);
      this.body.setRotationPoint(0.0F, 11.0F, -10.0F);
      this.pregnant = new RendererModel(this);
      this.pregnant.setTextureOffset(70, 33).addBox(-4.5F, 3.0F, -14.0F, 9, 18, 1, 0.0F);
      this.pregnant.setRotationPoint(0.0F, 11.0F, -10.0F);
      int i = 1;
      this.legBackRight = new RendererModel(this, 1, 23);
      this.legBackRight.addBox(-2.0F, 0.0F, 0.0F, 4, 1, 10, 0.0F);
      this.legBackRight.setRotationPoint(-3.5F, 22.0F, 11.0F);
      this.legBackLeft = new RendererModel(this, 1, 12);
      this.legBackLeft.addBox(-2.0F, 0.0F, 0.0F, 4, 1, 10, 0.0F);
      this.legBackLeft.setRotationPoint(3.5F, 22.0F, 11.0F);
      this.legFrontRight = new RendererModel(this, 27, 30);
      this.legFrontRight.addBox(-13.0F, 0.0F, -2.0F, 13, 1, 5, 0.0F);
      this.legFrontRight.setRotationPoint(-5.0F, 21.0F, -4.0F);
      this.legFrontLeft = new RendererModel(this, 27, 24);
      this.legFrontLeft.addBox(0.0F, 0.0F, -2.0F, 13, 1, 5, 0.0F);
      this.legFrontLeft.setRotationPoint(5.0F, 21.0F, -4.0F);
   }

   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (this.isChild) {
         float f = 6.0F;
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.16666667F, 0.16666667F, 0.16666667F);
         GlStateManager.translatef(0.0F, 120.0F * scale, 0.0F);
         this.headModel.render(scale);
         this.body.render(scale);
         this.legBackRight.render(scale);
         this.legBackLeft.render(scale);
         this.legFrontRight.render(scale);
         this.legFrontLeft.render(scale);
         GlStateManager.popMatrix();
      } else {
         GlStateManager.pushMatrix();
         if (entityIn.hasEgg()) {
            GlStateManager.translatef(0.0F, -0.08F, 0.0F);
         }

         this.headModel.render(scale);
         this.body.render(scale);
         GlStateManager.pushMatrix();
         this.legBackRight.render(scale);
         this.legBackLeft.render(scale);
         GlStateManager.popMatrix();
         this.legFrontRight.render(scale);
         this.legFrontLeft.render(scale);
         if (entityIn.hasEgg()) {
            this.pregnant.render(scale);
         }

         GlStateManager.popMatrix();
      }

   }

   public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
      super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount;
      this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * limbSwingAmount;
      this.legFrontRight.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * limbSwingAmount;
      this.legFrontLeft.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount;
      this.legFrontRight.rotateAngleX = 0.0F;
      this.legFrontLeft.rotateAngleX = 0.0F;
      this.legFrontRight.rotateAngleY = 0.0F;
      this.legFrontLeft.rotateAngleY = 0.0F;
      this.legBackRight.rotateAngleY = 0.0F;
      this.legBackLeft.rotateAngleY = 0.0F;
      this.pregnant.rotateAngleX = ((float)Math.PI / 2F);
      if (!entityIn.isInWater() && entityIn.onGround) {
         float f = entityIn.isDigging() ? 4.0F : 1.0F;
         float f1 = entityIn.isDigging() ? 2.0F : 1.0F;
         float f2 = 5.0F;
         this.legFrontRight.rotateAngleY = MathHelper.cos(f * limbSwing * 5.0F + (float)Math.PI) * 8.0F * limbSwingAmount * f1;
         this.legFrontRight.rotateAngleZ = 0.0F;
         this.legFrontLeft.rotateAngleY = MathHelper.cos(f * limbSwing * 5.0F) * 8.0F * limbSwingAmount * f1;
         this.legFrontLeft.rotateAngleZ = 0.0F;
         this.legBackRight.rotateAngleY = MathHelper.cos(limbSwing * 5.0F + (float)Math.PI) * 3.0F * limbSwingAmount;
         this.legBackRight.rotateAngleX = 0.0F;
         this.legBackLeft.rotateAngleY = MathHelper.cos(limbSwing * 5.0F) * 3.0F * limbSwingAmount;
         this.legBackLeft.rotateAngleX = 0.0F;
      }

   }
}