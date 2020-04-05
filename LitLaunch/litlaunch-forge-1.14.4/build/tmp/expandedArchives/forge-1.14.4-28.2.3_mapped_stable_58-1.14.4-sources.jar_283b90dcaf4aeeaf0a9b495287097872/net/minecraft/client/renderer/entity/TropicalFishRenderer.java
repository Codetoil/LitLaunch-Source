package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.layers.TropicalFishPatternLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.TropicalFishAModel;
import net.minecraft.client.renderer.entity.model.TropicalFishBModel;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TropicalFishRenderer extends MobRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> {
   private final TropicalFishAModel<TropicalFishEntity> modelB = new TropicalFishAModel<>();
   private final TropicalFishBModel<TropicalFishEntity> modelA = new TropicalFishBModel<>();

   public TropicalFishRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new TropicalFishAModel<>(), 0.15F);
      this.addLayer(new TropicalFishPatternLayer(this));
   }

   @Nullable
   protected ResourceLocation getEntityTexture(TropicalFishEntity entity) {
      return entity.getBodyTexture();
   }

   public void doRender(TropicalFishEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
      this.entityModel = (EntityModel<TropicalFishEntity>)(entity.getSize() == 0 ? this.modelB : this.modelA);
      float[] afloat = entity.func_204219_dC();
      GlStateManager.color3f(afloat[0], afloat[1], afloat[2]);
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected void applyRotations(TropicalFishEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
      super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
      float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
      GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
      if (!entityLiving.isInWater()) {
         GlStateManager.translatef(0.2F, 0.1F, 0.0F);
         GlStateManager.rotatef(90.0F, 0.0F, 0.0F, 1.0F);
      }

   }
}