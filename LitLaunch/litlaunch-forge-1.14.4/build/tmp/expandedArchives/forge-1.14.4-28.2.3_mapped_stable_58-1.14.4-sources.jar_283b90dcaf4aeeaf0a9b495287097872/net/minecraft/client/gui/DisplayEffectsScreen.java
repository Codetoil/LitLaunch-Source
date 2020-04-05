package net.minecraft.client.gui;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.Collection;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DisplayEffectsScreen<T extends Container> extends ContainerScreen<T> {
   protected boolean hasActivePotionEffects;

   public DisplayEffectsScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
      super(screenContainer, inv, titleIn);
   }

   protected void init() {
      super.init();
      this.updateActivePotionEffects();
   }

   protected void updateActivePotionEffects() {
      if (this.minecraft.player.getActivePotionEffects().isEmpty()) {
         this.guiLeft = (this.width - this.xSize) / 2;
         this.hasActivePotionEffects = false;
      } else {
         if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent(this))) this.guiLeft = (this.width - this.xSize) / 2; else
         this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
         this.hasActivePotionEffects = true;
      }

   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      super.render(p_render_1_, p_render_2_, p_render_3_);
      if (this.hasActivePotionEffects) {
         this.drawActivePotionEffects();
      }

   }

   private void drawActivePotionEffects() {
      int i = this.guiLeft - 124;
      Collection<EffectInstance> collection = this.minecraft.player.getActivePotionEffects();
      if (!collection.isEmpty()) {
         GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableLighting();
         int j = 33;
         if (collection.size() > 5) {
            j = 132 / (collection.size() - 1);
         }

         Iterable<EffectInstance> iterable = collection.stream().filter( effectInstance -> effectInstance.getPotion().shouldRender(effectInstance)).sorted().collect(java.util.stream.Collectors.toList());
         this.drawActivePotionEffectsBackgrounds(i, j, iterable);
         this.drawActivePotionEffectsIcons(i, j, iterable);
         this.drawActivePotionEffectsNames(i, j, iterable);
      }
   }

   private void drawActivePotionEffectsBackgrounds(int x, int height, Iterable<EffectInstance> effectsIn) {
      this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
      int i = this.guiTop;

      for(EffectInstance effectinstance : effectsIn) {
         GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.blit(x, i, 0, 166, 140, 32);
         i += height;
      }

   }

   private void drawActivePotionEffectsIcons(int x, int height, Iterable<EffectInstance> effectsIn) {
      this.minecraft.getTextureManager().bindTexture(AtlasTexture.LOCATION_EFFECTS_TEXTURE);
      PotionSpriteUploader potionspriteuploader = this.minecraft.getPotionSpriteUploader();
      int i = this.guiTop;

      for(EffectInstance effectinstance : effectsIn) {
         Effect effect = effectinstance.getPotion();
         blit(x + 6, i + 7, this.blitOffset, 18, 18, potionspriteuploader.getSprite(effect));
         i += height;
      }

   }

   private void drawActivePotionEffectsNames(int x, int height, Iterable<EffectInstance> effectsIn) {
      int i = this.guiTop;

      for(EffectInstance effectinstance : effectsIn) {
         effectinstance.getPotion().renderInventoryEffect(effectinstance, this, x, i, this.blitOffset);
         if (!effectinstance.getPotion().shouldRenderInvText(effectinstance)) { i += height; continue; }
         String s = I18n.format(effectinstance.getPotion().getName());
         if (effectinstance.getAmplifier() >= 1 && effectinstance.getAmplifier() <= 9) {
            s = s + ' ' + I18n.format("enchantment.level." + (effectinstance.getAmplifier() + 1));
         }

         this.font.drawStringWithShadow(s, (float)(x + 10 + 18), (float)(i + 6), 16777215);
         String s1 = EffectUtils.getPotionDurationString(effectinstance, 1.0F);
         this.font.drawStringWithShadow(s1, (float)(x + 10 + 18), (float)(i + 6 + 10), 8355711);
         i += height;
      }

   }
}