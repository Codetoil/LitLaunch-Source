package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.util.math.MathHelper;

public class OctavesNoiseGenerator implements INoiseGenerator {
   private final ImprovedNoiseGenerator[] octaves;

   public OctavesNoiseGenerator(Random seed, int octavesIn) {
      this.octaves = new ImprovedNoiseGenerator[octavesIn];

      for(int i = 0; i < octavesIn; ++i) {
         this.octaves[i] = new ImprovedNoiseGenerator(seed);
      }

   }

   public double func_205563_a(double p_205563_1_, double p_205563_3_, double p_205563_5_) {
      return this.getValue(p_205563_1_, p_205563_3_, p_205563_5_, 0.0D, 0.0D, false);
   }

   public double getValue(double x, double y, double z, double p_215462_7_, double p_215462_9_, boolean p_215462_11_) {
      double d0 = 0.0D;
      double d1 = 1.0D;

      for(ImprovedNoiseGenerator improvednoisegenerator : this.octaves) {
         d0 += improvednoisegenerator.func_215456_a(maintainPrecision(x * d1), p_215462_11_ ? -improvednoisegenerator.yCoord : maintainPrecision(y * d1), maintainPrecision(z * d1), p_215462_7_ * d1, p_215462_9_ * d1) / d1;
         d1 /= 2.0D;
      }

      return d0;
   }

   public ImprovedNoiseGenerator getOctave(int octaveIndex) {
      return this.octaves[octaveIndex];
   }

   public static double maintainPrecision(double p_215461_0_) {
      return p_215461_0_ - (double)MathHelper.lfloor(p_215461_0_ / 3.3554432E7D + 0.5D) * 3.3554432E7D;
   }

   public double noiseAt(double x, double y, double z, double p_215460_7_) {
      return this.getValue(x, y, 0.0D, z, p_215460_7_, false);
   }
}