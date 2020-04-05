package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class FlameEnchantment extends Enchantment {
   public FlameEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
      super(rarityIn, EnchantmentType.BOW, slots);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 20;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return 50;
   }

   public int getMaxLevel() {
      return 1;
   }
}