package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.monster.ElderGuardianEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElderGuardianRenderer extends GuardianRenderer {
   private static final ResourceLocation GUARDIAN_ELDER_TEXTURE = new ResourceLocation("textures/entity/guardian_elder.png");

   public ElderGuardianRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, 1.2F);
   }

   protected void preRenderCallback(GuardianEntity entitylivingbaseIn, float partialTickTime) {
      GlStateManager.scalef(ElderGuardianEntity.field_213629_b, ElderGuardianEntity.field_213629_b, ElderGuardianEntity.field_213629_b);
   }

   protected ResourceLocation getEntityTexture(GuardianEntity entity) {
      return GUARDIAN_ELDER_TEXTURE;
   }
}