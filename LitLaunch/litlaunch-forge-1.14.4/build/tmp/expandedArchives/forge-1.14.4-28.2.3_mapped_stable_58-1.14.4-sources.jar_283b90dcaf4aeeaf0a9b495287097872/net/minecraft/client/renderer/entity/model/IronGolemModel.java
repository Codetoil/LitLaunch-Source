package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronGolemModel<T extends IronGolemEntity> extends EntityModel<T> {
   private final RendererModel ironGolemHead;
   private final RendererModel ironGolemBody;
   public final RendererModel ironGolemRightArm;
   private final RendererModel ironGolemLeftArm;
   private final RendererModel ironGolemLeftLeg;
   private final RendererModel ironGolemRightLeg;

   public IronGolemModel() {
      this(0.0F);
   }

   public IronGolemModel(float p_i1161_1_) {
      this(p_i1161_1_, -7.0F);
   }

   public IronGolemModel(float p_i46362_1_, float p_i46362_2_) {
      int i = 128;
      int j = 128;
      this.ironGolemHead = (new RendererModel(this)).setTextureSize(128, 128);
      this.ironGolemHead.setRotationPoint(0.0F, 0.0F + p_i46362_2_, -2.0F);
      this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, p_i46362_1_);
      this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, p_i46362_1_);
      this.ironGolemBody = (new RendererModel(this)).setTextureSize(128, 128);
      this.ironGolemBody.setRotationPoint(0.0F, 0.0F + p_i46362_2_, 0.0F);
      this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, p_i46362_1_);
      this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, p_i46362_1_ + 0.5F);
      this.ironGolemRightArm = (new RendererModel(this)).setTextureSize(128, 128);
      this.ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
      this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, p_i46362_1_);
      this.ironGolemLeftArm = (new RendererModel(this)).setTextureSize(128, 128);
      this.ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
      this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, p_i46362_1_);
      this.ironGolemLeftLeg = (new RendererModel(this, 0, 22)).setTextureSize(128, 128);
      this.ironGolemLeftLeg.setRotationPoint(-4.0F, 18.0F + p_i46362_2_, 0.0F);
      this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i46362_1_);
      this.ironGolemRightLeg = (new RendererModel(this, 0, 22)).setTextureSize(128, 128);
      this.ironGolemRightLeg.mirror = true;
      this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + p_i46362_2_, 0.0F);
      this.ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i46362_1_);
   }

   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.ironGolemHead.render(scale);
      this.ironGolemBody.render(scale);
      this.ironGolemLeftLeg.render(scale);
      this.ironGolemRightLeg.render(scale);
      this.ironGolemRightArm.render(scale);
      this.ironGolemLeftArm.render(scale);
   }

   public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
      this.ironGolemHead.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
      this.ironGolemHead.rotateAngleX = headPitch * ((float)Math.PI / 180F);
      this.ironGolemLeftLeg.rotateAngleX = -1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
      this.ironGolemRightLeg.rotateAngleX = 1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
      this.ironGolemLeftLeg.rotateAngleY = 0.0F;
      this.ironGolemRightLeg.rotateAngleY = 0.0F;
   }

   public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
      int i = entityIn.getAttackTimer();
      if (i > 0) {
         this.ironGolemRightArm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float)i - partialTick, 10.0F);
         this.ironGolemLeftArm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float)i - partialTick, 10.0F);
      } else {
         int j = entityIn.getHoldRoseTick();
         if (j > 0) {
            this.ironGolemRightArm.rotateAngleX = -0.8F + 0.025F * this.triangleWave((float)j, 70.0F);
            this.ironGolemLeftArm.rotateAngleX = 0.0F;
         } else {
            this.ironGolemRightArm.rotateAngleX = (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
            this.ironGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
         }
      }

   }

   private float triangleWave(float p_78172_1_, float p_78172_2_) {
      return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
   }

   public RendererModel getArmHoldingRose() {
      return this.ironGolemRightArm;
   }
}