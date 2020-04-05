package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

public class BigMushroomFeatureConfig implements IFeatureConfig {
   public final boolean planted;

   public BigMushroomFeatureConfig(boolean planted) {
      this.planted = planted;
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
      return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("planted"), ops.createBoolean(this.planted))));
   }

   public static <T> BigMushroomFeatureConfig deserialize(Dynamic<T> p_222853_0_) {
      boolean flag = p_222853_0_.get("planted").asBoolean(false);
      return new BigMushroomFeatureConfig(flag);
   }
}