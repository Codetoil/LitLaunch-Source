package net.minecraft.block;

import net.minecraft.item.DyeColor;
import net.minecraft.util.BlockRenderLayer;

public class StainedGlassBlock extends AbstractGlassBlock implements IBeaconBeamColorProvider {
   private final DyeColor color;

   public StainedGlassBlock(DyeColor colorIn, Block.Properties properties) {
      super(properties);
      this.color = colorIn;
   }

   public DyeColor getColor() {
      return this.color;
   }

   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }
}