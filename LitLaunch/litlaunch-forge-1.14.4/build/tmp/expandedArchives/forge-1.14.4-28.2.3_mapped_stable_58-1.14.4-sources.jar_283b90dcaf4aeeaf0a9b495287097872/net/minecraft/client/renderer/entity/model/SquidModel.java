package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SquidModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel body;
   private final RendererModel[] legs = new RendererModel[8];

   public SquidModel() {
      int i = -16;
      this.body = new RendererModel(this, 0, 0);
      this.body.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
      this.body.rotationPointY += 8.0F;

      for(int j = 0; j < this.legs.length; ++j) {
         this.legs[j] = new RendererModel(this, 48, 0);
         double d0 = (double)j * Math.PI * 2.0D / (double)this.legs.length;
         float f = (float)Math.cos(d0) * 5.0F;
         float f1 = (float)Math.sin(d0) * 5.0F;
         this.legs[j].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
         this.legs[j].rotationPointX = f;
         this.legs[j].rotationPointZ = f1;
         this.legs[j].rotationPointY = 15.0F;
         d0 = (double)j * Math.PI * -2.0D / (double)this.legs.length + (Math.PI / 2D);
         this.legs[j].rotateAngleY = (float)d0;
      }

   }

   public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
      for(RendererModel renderermodel : this.legs) {
         renderermodel.rotateAngleX = ageInTicks;
      }

   }

   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.body.render(scale);

      for(RendererModel renderermodel : this.legs) {
         renderermodel.render(scale);
      }

   }
}