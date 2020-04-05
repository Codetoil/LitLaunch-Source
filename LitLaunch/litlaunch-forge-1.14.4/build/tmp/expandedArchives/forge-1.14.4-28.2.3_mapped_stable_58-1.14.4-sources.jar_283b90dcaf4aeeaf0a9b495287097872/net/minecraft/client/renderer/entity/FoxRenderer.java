package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.layers.FoxHeldItemLayer;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FoxRenderer extends MobRenderer<FoxEntity, FoxModel<FoxEntity>> {
   private static final ResourceLocation FOX = new ResourceLocation("textures/entity/fox/fox.png");
   private static final ResourceLocation SLEEPING_FOX = new ResourceLocation("textures/entity/fox/fox_sleep.png");
   private static final ResourceLocation SNOW_FOX = new ResourceLocation("textures/entity/fox/snow_fox.png");
   private static final ResourceLocation SLEEPING_SNOW_FOX = new ResourceLocation("textures/entity/fox/snow_fox_sleep.png");

   public FoxRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new FoxModel<>(), 0.4F);
      this.addLayer(new FoxHeldItemLayer(this));
   }

   protected void applyRotations(FoxEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
      super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
      if (entityLiving.func_213480_dY() || entityLiving.isStuck()) {
         GlStateManager.rotatef(-MathHelper.lerp(partialTicks, entityLiving.prevRotationPitch, entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
      }

   }

   @Nullable
   protected ResourceLocation getEntityTexture(FoxEntity entity) {
      if (entity.getVariantType() == FoxEntity.Type.RED) {
         return entity.isSleeping() ? SLEEPING_FOX : FOX;
      } else {
         return entity.isSleeping() ? SLEEPING_SNOW_FOX : SNOW_FOX;
      }
   }
}