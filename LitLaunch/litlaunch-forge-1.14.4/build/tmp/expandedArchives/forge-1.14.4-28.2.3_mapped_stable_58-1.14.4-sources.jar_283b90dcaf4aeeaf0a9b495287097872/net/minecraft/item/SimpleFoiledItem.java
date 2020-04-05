package net.minecraft.item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SimpleFoiledItem extends Item {
   public SimpleFoiledItem(Item.Properties builder) {
      super(builder);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasEffect(ItemStack stack) {
      return true;
   }
}