package net.minecraft.world.gen.carver;

import java.util.BitSet;
import java.util.Random;
import net.minecraft.world.chunk.IChunk;

public class ConfiguredCarver<WC extends ICarverConfig> {
   public final WorldCarver<WC> carver;
   public final WC config;

   public ConfiguredCarver(WorldCarver<WC> p_i49928_1_, WC p_i49928_2_) {
      this.carver = p_i49928_1_;
      this.config = p_i49928_2_;
   }

   public boolean shouldCarve(Random p_222730_1_, int p_222730_2_, int p_222730_3_) {
      return this.carver.shouldCarve(p_222730_1_, p_222730_2_, p_222730_3_, this.config);
   }

   public boolean carve(IChunk p_222731_1_, Random p_222731_2_, int p_222731_3_, int p_222731_4_, int p_222731_5_, int p_222731_6_, int p_222731_7_, BitSet p_222731_8_) {
      return this.carver.carve(p_222731_1_, p_222731_2_, p_222731_3_, p_222731_4_, p_222731_5_, p_222731_6_, p_222731_7_, p_222731_8_, this.config);
   }
}