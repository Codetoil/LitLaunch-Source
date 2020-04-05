package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TropicalFishBModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel body;
   private final RendererModel tail;
   private final RendererModel finRight;
   private final RendererModel finLeft;
   private final RendererModel finTop;
   private final RendererModel finBottom;

   public TropicalFishBModel() {
      this(0.0F);
   }

   public TropicalFishBModel(float p_i48891_1_) {
      this.textureWidth = 32;
      this.textureHeight = 32;
      int i = 19;
      this.body = new RendererModel(this, 0, 20);
      this.body.addBox(-1.0F, -3.0F, -3.0F, 2, 6, 6, p_i48891_1_);
      this.body.setRotationPoint(0.0F, 19.0F, 0.0F);
      this.tail = new RendererModel(this, 21, 16);
      this.tail.addBox(0.0F, -3.0F, 0.0F, 0, 6, 5, p_i48891_1_);
      this.tail.setRotationPoint(0.0F, 19.0F, 3.0F);
      this.finRight = new RendererModel(this, 2, 16);
      this.finRight.addBox(-2.0F, 0.0F, 0.0F, 2, 2, 0, p_i48891_1_);
      this.finRight.setRotationPoint(-1.0F, 20.0F, 0.0F);
      this.finRight.rotateAngleY = ((float)Math.PI / 4F);
      this.finLeft = new RendererModel(this, 2, 12);
      this.finLeft.addBox(0.0F, 0.0F, 0.0F, 2, 2, 0, p_i48891_1_);
      this.finLeft.setRotationPoint(1.0F, 20.0F, 0.0F);
      this.finLeft.rotateAngleY = (-(float)Math.PI / 4F);
      this.finTop = new RendererModel(this, 20, 11);
      this.finTop.addBox(0.0F, -4.0F, 0.0F, 0, 4, 6, p_i48891_1_);
      this.finTop.setRotationPoint(0.0F, 16.0F, -3.0F);
      this.finBottom = new RendererModel(this, 20, 21);
      this.finBottom.addBox(0.0F, 0.0F, 0.0F, 0, 4, 6, p_i48891_1_);
      this.finBottom.setRotationPoint(0.0F, 22.0F, -3.0F);
   }

   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.body.render(scale);
      this.tail.render(scale);
      this.finRight.render(scale);
      this.finLeft.render(scale);
      this.finTop.render(scale);
      this.finBottom.render(scale);
   }

   public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
      float f = 1.0F;
      if (!entityIn.isInWater()) {
         f = 1.5F;
      }

      this.tail.rotateAngleY = -f * 0.45F * MathHelper.sin(0.6F * ageInTicks);
   }
}