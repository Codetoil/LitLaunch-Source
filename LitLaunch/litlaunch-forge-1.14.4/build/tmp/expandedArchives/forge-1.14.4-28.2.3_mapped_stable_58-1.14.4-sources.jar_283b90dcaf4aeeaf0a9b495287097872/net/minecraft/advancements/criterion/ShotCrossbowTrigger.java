package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class ShotCrossbowTrigger implements ICriterionTrigger<ShotCrossbowTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("shot_crossbow");
   private final Map<PlayerAdvancements, ShotCrossbowTrigger.Listeners> listeners = Maps.newHashMap();

   public ResourceLocation getId() {
      return ID;
   }

   public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<ShotCrossbowTrigger.Instance> listener) {
      ShotCrossbowTrigger.Listeners shotcrossbowtrigger$listeners = this.listeners.get(playerAdvancementsIn);
      if (shotcrossbowtrigger$listeners == null) {
         shotcrossbowtrigger$listeners = new ShotCrossbowTrigger.Listeners(playerAdvancementsIn);
         this.listeners.put(playerAdvancementsIn, shotcrossbowtrigger$listeners);
      }

      shotcrossbowtrigger$listeners.addListener(listener);
   }

   public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<ShotCrossbowTrigger.Instance> listener) {
      ShotCrossbowTrigger.Listeners shotcrossbowtrigger$listeners = this.listeners.get(playerAdvancementsIn);
      if (shotcrossbowtrigger$listeners != null) {
         shotcrossbowtrigger$listeners.removeListener(listener);
         if (shotcrossbowtrigger$listeners.isListenersEmpty()) {
            this.listeners.remove(playerAdvancementsIn);
         }
      }

   }

   public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
      this.listeners.remove(playerAdvancementsIn);
   }

   public ShotCrossbowTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
      ItemPredicate itempredicate = ItemPredicate.deserialize(json.get("item"));
      return new ShotCrossbowTrigger.Instance(itempredicate);
   }

   public void func_215111_a(ServerPlayerEntity shooter, ItemStack stack) {
      ShotCrossbowTrigger.Listeners shotcrossbowtrigger$listeners = this.listeners.get(shooter.getAdvancements());
      if (shotcrossbowtrigger$listeners != null) {
         shotcrossbowtrigger$listeners.call(stack);
      }

   }

   public static class Instance extends CriterionInstance {
      private final ItemPredicate itemPredicate;

      public Instance(ItemPredicate itemPredicateIn) {
         super(ShotCrossbowTrigger.ID);
         this.itemPredicate = itemPredicateIn;
      }

      public static ShotCrossbowTrigger.Instance create(IItemProvider itemProvider) {
         return new ShotCrossbowTrigger.Instance(ItemPredicate.Builder.create().item(itemProvider).build());
      }

      public boolean func_215121_a(ItemStack p_215121_1_) {
         return this.itemPredicate.test(p_215121_1_);
      }

      public JsonElement serialize() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("item", this.itemPredicate.serialize());
         return jsonobject;
      }
   }

   static class Listeners {
      private final PlayerAdvancements playerAdvancements;
      private final Set<ICriterionTrigger.Listener<ShotCrossbowTrigger.Instance>> listeners = Sets.newHashSet();

      public Listeners(PlayerAdvancements playerAdvancementsIn) {
         this.playerAdvancements = playerAdvancementsIn;
      }

      public boolean isListenersEmpty() {
         return this.listeners.isEmpty();
      }

      public void addListener(ICriterionTrigger.Listener<ShotCrossbowTrigger.Instance> listenerIn) {
         this.listeners.add(listenerIn);
      }

      public void removeListener(ICriterionTrigger.Listener<ShotCrossbowTrigger.Instance> listenerIn) {
         this.listeners.remove(listenerIn);
      }

      public void call(ItemStack stack) {
         List<ICriterionTrigger.Listener<ShotCrossbowTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<ShotCrossbowTrigger.Instance> listener : this.listeners) {
            if (listener.getCriterionInstance().func_215121_a(stack)) {
               if (list == null) {
                  list = Lists.newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<ShotCrossbowTrigger.Instance> listener1 : list) {
               listener1.grantCriterion(this.playerAdvancements);
            }
         }

      }
   }
}