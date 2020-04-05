package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SalmonModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel bodyFront;
   private final RendererModel bodyRear;
   private final RendererModel head;
   private final RendererModel finTopFront;
   private final RendererModel finTopRear;
   private final RendererModel tail;
   private final RendererModel finRight;
   private final RendererModel finLeft;

   public SalmonModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      int i = 20;
      this.bodyFront = new RendererModel(this, 0, 0);
      this.bodyFront.addBox(-1.5F, -2.5F, 0.0F, 3, 5, 8);
      this.bodyFront.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.bodyRear = new RendererModel(this, 0, 13);
      this.bodyRear.addBox(-1.5F, -2.5F, 0.0F, 3, 5, 8);
      this.bodyRear.setRotationPoint(0.0F, 20.0F, 8.0F);
      this.head = new RendererModel(this, 22, 0);
      this.head.addBox(-1.0F, -2.0F, -3.0F, 2, 4, 3);
      this.head.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.tail = new RendererModel(this, 20, 10);
      this.tail.addBox(0.0F, -2.5F, 0.0F, 0, 5, 6);
      this.tail.setRotationPoint(0.0F, 0.0F, 8.0F);
      this.bodyRear.addChild(this.tail);
      this.finTopFront = new RendererModel(this, 2, 1);
      this.finTopFront.addBox(0.0F, 0.0F, 0.0F, 0, 2, 3);
      this.finTopFront.setRotationPoint(0.0F, -4.5F, 5.0F);
      this.bodyFront.addChild(this.finTopFront);
      this.finTopRear = new RendererModel(this, 0, 2);
      this.finTopRear.addBox(0.0F, 0.0F, 0.0F, 0, 2, 4);
      this.finTopRear.setRotationPoint(0.0F, -4.5F, -1.0F);
      this.bodyRear.addChild(this.finTopRear);
      this.finRight = new RendererModel(this, -4, 0);
      this.finRight.addBox(-2.0F, 0.0F, 0.0F, 2, 0, 2);
      this.finRight.setRotationPoint(-1.5F, 21.5F, 0.0F);
      this.finRight.rotateAngleZ = (-(float)Math.PI / 4F);
      this.finLeft = new RendererModel(this, 0, 0);
      this.finLeft.addBox(0.0F, 0.0F, 0.0F, 2, 0, 2);
      this.finLeft.setRotationPoint(1.5F, 21.5F, 0.0F);
      this.finLeft.rotateAngleZ = ((float)Math.PI / 4F);
   }

   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.bodyFront.render(scale);
      this.bodyRear.render(scale);
      this.head.render(scale);
      this.finRight.render(scale);
      this.finLeft.render(scale);
   }

   public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
      float f = 1.0F;
      float f1 = 1.0F;
      if (!entityIn.isInWater()) {
         f = 1.3F;
         f1 = 1.7F;
      }

      this.bodyRear.rotateAngleY = -f * 0.25F * MathHelper.sin(f1 * 0.6F * ageInTicks);
   }
}