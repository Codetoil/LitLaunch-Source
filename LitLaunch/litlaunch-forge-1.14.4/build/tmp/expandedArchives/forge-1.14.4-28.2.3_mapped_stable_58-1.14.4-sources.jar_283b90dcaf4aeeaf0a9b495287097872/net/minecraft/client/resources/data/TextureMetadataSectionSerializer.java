package net.minecraft.client.resources.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextureMetadataSectionSerializer implements IMetadataSectionSerializer<TextureMetadataSection> {
   public TextureMetadataSection deserialize(JsonObject json) {
      boolean flag = JSONUtils.getBoolean(json, "blur", false);
      boolean flag1 = JSONUtils.getBoolean(json, "clamp", false);
      return new TextureMetadataSection(flag, flag1);
   }

   public String getSectionName() {
      return "texture";
   }
}