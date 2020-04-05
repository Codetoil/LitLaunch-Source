package net.minecraft.client.renderer.model;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.fluid.IFluidState;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelManager extends ReloadListener<ModelBakery> {
   private Map<ResourceLocation, IBakedModel> modelRegistry = new java.util.HashMap<>();
   private final AtlasTexture texMap;
   private final BlockModelShapes modelProvider;
   private final BlockColors field_224743_d;
   private IBakedModel defaultModel;
   private Object2IntMap<BlockState> field_224744_f;

   public ModelManager(AtlasTexture p_i51734_1_, BlockColors p_i51734_2_) {
      this.texMap = p_i51734_1_;
      this.field_224743_d = p_i51734_2_;
      this.modelProvider = new BlockModelShapes(this);
   }

   public IBakedModel getModel(ResourceLocation modelLocation) {
      return this.modelRegistry.getOrDefault(modelLocation, this.defaultModel);
   }

   public IBakedModel getModel(ModelResourceLocation modelLocation) {
      return this.modelRegistry.getOrDefault(modelLocation, this.defaultModel);
   }

   public IBakedModel getMissingModel() {
      return this.defaultModel;
   }

   public BlockModelShapes getBlockModelShapes() {
      return this.modelProvider;
   }

   protected ModelBakery prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {
      profilerIn.startTick();
      net.minecraftforge.client.model.ModelLoader modelbakery = new net.minecraftforge.client.model.ModelLoader(resourceManagerIn, this.texMap, this.field_224743_d, profilerIn);
      profilerIn.endTick();
      return modelbakery;
   }

   protected void apply(ModelBakery splashList, IResourceManager resourceManagerIn, IProfiler profilerIn) {
      profilerIn.startTick();
      profilerIn.startSection("upload");
      splashList.func_217844_a(profilerIn);
      this.modelRegistry = splashList.func_217846_a();
      this.field_224744_f = splashList.func_225354_b();
      this.defaultModel = this.modelRegistry.get(ModelBakery.MODEL_MISSING);
      net.minecraftforge.client.ForgeHooksClient.onModelBake(this, this.modelRegistry, (net.minecraftforge.client.model.ModelLoader) splashList);
      profilerIn.endStartSection("cache");
      this.modelProvider.reloadModels();
      profilerIn.endSection();
      profilerIn.endTick();
   }

   public boolean func_224742_a(BlockState p_224742_1_, BlockState p_224742_2_) {
      if (p_224742_1_ == p_224742_2_) {
         return false;
      } else {
         int i = this.field_224744_f.getInt(p_224742_1_);
         if (i != -1) {
            int j = this.field_224744_f.getInt(p_224742_2_);
            if (i == j) {
               IFluidState ifluidstate = p_224742_1_.getFluidState();
               IFluidState ifluidstate1 = p_224742_2_.getFluidState();
               return ifluidstate != ifluidstate1;
            }
         }

         return true;
      }
   }

   // TODO
   //@Override
   public net.minecraftforge.resource.IResourceType getResourceType() {
      return net.minecraftforge.resource.VanillaResourceType.MODELS;
   }
}