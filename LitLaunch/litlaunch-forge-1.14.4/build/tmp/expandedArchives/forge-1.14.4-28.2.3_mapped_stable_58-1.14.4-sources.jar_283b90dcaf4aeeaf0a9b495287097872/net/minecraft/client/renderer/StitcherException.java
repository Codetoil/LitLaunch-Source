package net.minecraft.client.renderer;

import java.util.Collection;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StitcherException extends RuntimeException {
   private final Collection<TextureAtlasSprite> field_225332_a;

   public StitcherException(TextureAtlasSprite p_i51736_1_, Collection<TextureAtlasSprite> p_i51736_2_) {
      super(String.format("Unable to fit: %s - size: %dx%d - Maybe try a lower resolution resourcepack?", p_i51736_1_.getName(), p_i51736_1_.getWidth(), p_i51736_1_.getHeight()));
      this.field_225332_a = p_i51736_2_;
   }

   public Collection<TextureAtlasSprite> func_225331_a() {
      return this.field_225332_a;
   }
}