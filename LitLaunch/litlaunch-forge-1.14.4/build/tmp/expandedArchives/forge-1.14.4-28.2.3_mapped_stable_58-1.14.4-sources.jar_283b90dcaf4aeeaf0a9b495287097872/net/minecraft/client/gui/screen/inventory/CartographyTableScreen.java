package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.CartographyContainer;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CartographyTableScreen extends ContainerScreen<CartographyContainer> {
   private static final ResourceLocation CONTAINER_TEXTURE = new ResourceLocation("textures/gui/container/cartography_table.png");

   public CartographyTableScreen(CartographyContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
      super(screenContainer, inv, titleIn);
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      super.render(p_render_1_, p_render_2_, p_render_3_);
      this.renderHoveredToolTip(p_render_1_, p_render_2_);
   }

   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      this.font.drawString(this.title.getFormattedText(), 8.0F, 4.0F, 4210752);
      this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      this.renderBackground();
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(CONTAINER_TEXTURE);
      int i = this.guiLeft;
      int j = this.guiTop;
      this.blit(i, j, 0, 0, this.xSize, this.ySize);
      Item item = this.container.getSlot(1).getStack().getItem();
      boolean flag = item == Items.MAP;
      boolean flag1 = item == Items.PAPER;
      boolean flag2 = item == Items.GLASS_PANE;
      ItemStack itemstack = this.container.getSlot(0).getStack();
      boolean flag3 = false;
      MapData mapdata;
      if (itemstack.getItem() == Items.FILLED_MAP) {
         mapdata = FilledMapItem.getData(itemstack, this.minecraft.world);
         if (mapdata != null) {
            if (mapdata.locked) {
               flag3 = true;
               if (flag1 || flag2) {
                  this.blit(i + 35, j + 31, this.xSize + 50, 132, 28, 21);
               }
            }

            if (flag1 && mapdata.scale >= 4) {
               flag3 = true;
               this.blit(i + 35, j + 31, this.xSize + 50, 132, 28, 21);
            }
         }
      } else {
         mapdata = null;
      }

      this.drawMap(mapdata, flag, flag1, flag2, flag3);
   }

   private void drawMap(@Nullable MapData mapDataIn, boolean isMap, boolean isPaper, boolean isGlassPane, boolean isLocked) {
      int i = this.guiLeft;
      int j = this.guiTop;
      if (isPaper && !isLocked) {
         this.blit(i + 67, j + 13, this.xSize, 66, 66, 66);
         this.drawMapItem(mapDataIn, i + 85, j + 31, 0.226F);
      } else if (isMap) {
         this.blit(i + 67 + 16, j + 13, this.xSize, 132, 50, 66);
         this.drawMapItem(mapDataIn, i + 86, j + 16, 0.34F);
         this.minecraft.getTextureManager().bindTexture(CONTAINER_TEXTURE);
         GlStateManager.pushMatrix();
         GlStateManager.translatef(0.0F, 0.0F, 1.0F);
         this.blit(i + 67, j + 13 + 16, this.xSize, 132, 50, 66);
         this.drawMapItem(mapDataIn, i + 70, j + 32, 0.34F);
         GlStateManager.popMatrix();
      } else if (isGlassPane) {
         this.blit(i + 67, j + 13, this.xSize, 0, 66, 66);
         this.drawMapItem(mapDataIn, i + 71, j + 17, 0.45F);
         this.minecraft.getTextureManager().bindTexture(CONTAINER_TEXTURE);
         GlStateManager.pushMatrix();
         GlStateManager.translatef(0.0F, 0.0F, 1.0F);
         this.blit(i + 66, j + 12, 0, this.ySize, 66, 66);
         GlStateManager.popMatrix();
      } else {
         this.blit(i + 67, j + 13, this.xSize, 0, 66, 66);
         this.drawMapItem(mapDataIn, i + 71, j + 17, 0.45F);
      }

   }

   private void drawMapItem(@Nullable MapData mapDataIn, int x, int y, float scale) {
      if (mapDataIn != null) {
         GlStateManager.pushMatrix();
         GlStateManager.translatef((float)x, (float)y, 1.0F);
         GlStateManager.scalef(scale, scale, 1.0F);
         this.minecraft.gameRenderer.getMapItemRenderer().renderMap(mapDataIn, true);
         GlStateManager.popMatrix();
      }

   }
}