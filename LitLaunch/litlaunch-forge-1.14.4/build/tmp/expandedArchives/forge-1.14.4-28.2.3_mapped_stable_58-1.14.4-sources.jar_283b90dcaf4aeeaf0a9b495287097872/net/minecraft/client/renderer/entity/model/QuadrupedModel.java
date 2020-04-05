package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QuadrupedModel<T extends Entity> extends EntityModel<T> {
   protected RendererModel headModel;
   protected RendererModel body;
   protected RendererModel legBackRight;
   protected RendererModel legBackLeft;
   protected RendererModel legFrontRight;
   protected RendererModel legFrontLeft;
   protected float childYOffset = 8.0F;
   protected float childZOffset = 4.0F;

   public QuadrupedModel(int height, float scale) {
      this.headModel = new RendererModel(this, 0, 0);
      this.headModel.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, scale);
      this.headModel.setRotationPoint(0.0F, (float)(18 - height), -6.0F);
      this.body = new RendererModel(this, 28, 8);
      this.body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, scale);
      this.body.setRotationPoint(0.0F, (float)(17 - height), 2.0F);
      this.legBackRight = new RendererModel(this, 0, 16);
      this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
      this.legBackRight.setRotationPoint(-3.0F, (float)(24 - height), 7.0F);
      this.legBackLeft = new RendererModel(this, 0, 16);
      this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
      this.legBackLeft.setRotationPoint(3.0F, (float)(24 - height), 7.0F);
      this.legFrontRight = new RendererModel(this, 0, 16);
      this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
      this.legFrontRight.setRotationPoint(-3.0F, (float)(24 - height), -5.0F);
      this.legFrontLeft = new RendererModel(this, 0, 16);
      this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
      this.legFrontLeft.setRotationPoint(3.0F, (float)(24 - height), -5.0F);
   }

   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (this.isChild) {
         float f = 2.0F;
         GlStateManager.pushMatrix();
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
      this.headModel.rotateAngleX = headPitch * ((float)Math.PI / 180F);
      this.headModel.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
      this.body.rotateAngleX = ((float)Math.PI / 2F);
      this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
      this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
      this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
   }
}