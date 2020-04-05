package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IAttributeInstance {
   IAttribute getAttribute();

   double getBaseValue();

   void setBaseValue(double baseValue);

   Collection<AttributeModifier> func_220368_a(AttributeModifier.Operation p_220368_1_);

   Collection<AttributeModifier> getModifiers();

   boolean hasModifier(AttributeModifier modifier);

   @Nullable
   AttributeModifier getModifier(UUID uuid);

   void applyModifier(AttributeModifier modifier);

   void removeModifier(AttributeModifier modifier);

   void removeModifier(UUID p_188479_1_);

   @OnlyIn(Dist.CLIENT)
   void removeAllModifiers();

   double getValue();
}