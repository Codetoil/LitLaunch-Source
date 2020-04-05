package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CheckboxButton extends AbstractButton {
   private static final ResourceLocation field_212944_b = new ResourceLocation("textures/gui/checkbox.png");
   boolean checked;

   public CheckboxButton(int p_i51140_1_, int p_i51140_2_, int p_i51140_3_, int p_i51140_4_, String p_i51140_5_, boolean p_i51140_6_) {
      super(p_i51140_1_, p_i51140_2_, p_i51140_3_, p_i51140_4_, p_i51140_5_);
      this.checked = p_i51140_6_;
   }

   public void onPress() {
      this.checked = !this.checked;
   }

   public boolean isChecked() {
      return this.checked;
   }

   public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
      Minecraft minecraft = Minecraft.getInstance();
      minecraft.getTextureManager().bindTexture(field_212944_b);
      GlStateManager.enableDepthTest();
      FontRenderer fontrenderer = minecraft.fontRenderer;
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
      GlStateManager.enableBlend();
      GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      blit(this.x, this.y, 0.0F, this.checked ? 20.0F : 0.0F, 20, this.height, 32, 64);
      this.renderBg(minecraft, p_renderButton_1_, p_renderButton_2_);
      int i = 14737632;
      this.drawString(fontrenderer, this.getMessage(), this.x + 24, this.y + (this.height - 8) / 2, 14737632 | MathHelper.ceil(this.alpha * 255.0F) << 24);
   }
}