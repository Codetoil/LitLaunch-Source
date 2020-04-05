package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChickenModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel head;
   private final RendererModel body;
   private final RendererModel rightLeg;
   private final RendererModel leftLeg;
   private final RendererModel rightWing;
   private final RendererModel leftWing;
   private final RendererModel field_78137_g;
   private final RendererModel field_78143_h;

   public ChickenModel() {
      int i = 16;
      this.head = new RendererModel(this, 0, 0);
      this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
      this.head.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.field_78137_g = new RendererModel(this, 14, 0);
      this.field_78137_g.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
      this.field_78137_g.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.field_78143_h = new RendererModel(this, 14, 4);
      this.field_78143_h.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
      this.field_78143_h.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.body = new RendererModel(this, 0, 9);
      this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
      this.body.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.rightLeg = new RendererModel(this, 26, 0);
      this.rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.rightLeg.setRotationPoint(-2.0F, 19.0F, 1.0F);
      this.leftLeg = new RendererModel(this, 26, 0);
      this.leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.leftLeg.setRotationPoint(1.0F, 19.0F, 1.0F);
      this.rightWing = new RendererModel(this, 24, 13);
      this.rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
      this.rightWing.setRotationPoint(-4.0F, 13.0F, 0.0F);
      this.leftWing = new RendererModel(this, 24, 13);
      this.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
      this.leftWing.setRotationPoint(4.0F, 13.0F, 0.0F);
   }

   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (this.isChild) {
         float f = 2.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translatef(0.0F, 5.0F * scale, 2.0F * scale);
         this.head.render(scale);
         this.field_78137_g.render(scale);
         this.field_78143_h.render(scale);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.5F, 0.5F, 0.5F);
         GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
         this.body.render(scale);
         this.rightLeg.render(scale);
         this.leftLeg.render(scale);
         this.rightWing.render(scale);
         this.leftWing.render(scale);
         GlStateManager.popMatrix();
      } else {
         this.head.render(scale);
         this.field_78137_g.render(scale);
         this.field_78143_h.render(scale);
         this.body.render(scale);
         this.rightLeg.render(scale);
         this.leftLeg.render(scale);
         this.rightWing.render(scale);
         this.leftWing.render(scale);
      }

   }

   public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
      this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
      this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
      this.field_78137_g.rotateAngleX = this.head.rotateAngleX;
      this.field_78137_g.rotateAngleY = this.head.rotateAngleY;
      this.field_78143_h.rotateAngleX = this.head.rotateAngleX;
      this.field_78143_h.rotateAngleY = this.head.rotateAngleY;
      this.body.rotateAngleX = ((float)Math.PI / 2F);
      this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
      this.rightWing.rotateAngleZ = ageInTicks;
      this.leftWing.rotateAngleZ = -ageInTicks;
   }
}