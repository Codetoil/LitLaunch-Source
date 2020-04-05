package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UndeadHorseRenderer extends AbstractHorseRenderer<AbstractHorseEntity, HorseModel<AbstractHorseEntity>> {
   private static final Map<Class<?>, ResourceLocation> UNDEAD_HORSE_TEXTURES = Maps.newHashMap(ImmutableMap.of(ZombieHorseEntity.class, new ResourceLocation("textures/entity/horse/horse_zombie.png"), SkeletonHorseEntity.class, new ResourceLocation("textures/entity/horse/horse_skeleton.png")));

   public UndeadHorseRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new HorseModel<>(0.0F), 1.0F);
   }

   protected ResourceLocation getEntityTexture(AbstractHorseEntity entity) {
      return UNDEAD_HORSE_TEXTURES.get(entity.getClass());
   }
}