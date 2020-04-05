package net.minecraft.block;

import net.minecraft.util.BlockRenderLayer;

public class GlassBlock extends AbstractGlassBlock {
   public GlassBlock(Block.Properties properties) {
      super(properties);
   }

   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }
}