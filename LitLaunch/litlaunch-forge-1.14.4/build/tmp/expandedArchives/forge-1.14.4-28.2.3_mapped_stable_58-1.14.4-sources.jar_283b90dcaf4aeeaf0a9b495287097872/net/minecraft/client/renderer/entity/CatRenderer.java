package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.layers.CatCollarLayer;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatRenderer extends MobRenderer<CatEntity, CatModel<CatEntity>> {
   public CatRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new CatModel<>(0.0F), 0.4F);
      this.addLayer(new CatCollarLayer(this));
   }

   @Nullable
   protected ResourceLocation getEntityTexture(CatEntity entity) {
      return entity.getCatTypeName();
   }

   protected void preRenderCallback(CatEntity entitylivingbaseIn, float partialTickTime) {
      super.preRenderCallback(entitylivingbaseIn, partialTickTime);
      GlStateManager.scalef(0.8F, 0.8F, 0.8F);
   }

   protected void applyRotations(CatEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
      super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
      float f = entityLiving.func_213408_v(partialTicks);
      if (f > 0.0F) {
         GlStateManager.translatef(0.4F * f, 0.15F * f, 0.1F * f);
         GlStateManager.rotatef(MathHelper.func_219805_h(f, 0.0F, 90.0F), 0.0F, 0.0F, 1.0F);
         BlockPos blockpos = new BlockPos(entityLiving);

         for(PlayerEntity playerentity : entityLiving.world.getEntitiesWithinAABB(PlayerEntity.class, (new AxisAlignedBB(blockpos)).grow(2.0D, 2.0D, 2.0D))) {
            if (playerentity.isSleeping()) {
               GlStateManager.translatef(0.15F * f, 0.0F, 0.0F);
               break;
            }
         }
      }

   }
}