package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SpriteUploader extends ReloadListener<AtlasTexture.SheetData> implements AutoCloseable {
   private final AtlasTexture textureAtlas;

   public SpriteUploader(TextureManager p_i50905_1_, ResourceLocation atlasTextureLocation, String p_i50905_3_) {
      this.textureAtlas = new AtlasTexture(p_i50905_3_);
      p_i50905_1_.loadTickableTexture(atlasTextureLocation, this.textureAtlas);
   }

   protected abstract Iterable<ResourceLocation> getKnownKeys();

   protected TextureAtlasSprite getSprite(ResourceLocation p_215282_1_) {
      return this.textureAtlas.getSprite(p_215282_1_);
   }

   protected AtlasTexture.SheetData prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {
      profilerIn.startTick();
      profilerIn.startSection("stitching");
      AtlasTexture.SheetData atlastexture$sheetdata = this.textureAtlas.stitch(resourceManagerIn, this.getKnownKeys(), profilerIn);
      profilerIn.endSection();
      profilerIn.endTick();
      return atlastexture$sheetdata;
   }

   protected void apply(AtlasTexture.SheetData splashList, IResourceManager resourceManagerIn, IProfiler profilerIn) {
      profilerIn.startTick();
      profilerIn.startSection("upload");
      this.textureAtlas.upload(splashList);
      profilerIn.endSection();
      profilerIn.endTick();
   }

   public void close() {
      this.textureAtlas.clear();
   }
}