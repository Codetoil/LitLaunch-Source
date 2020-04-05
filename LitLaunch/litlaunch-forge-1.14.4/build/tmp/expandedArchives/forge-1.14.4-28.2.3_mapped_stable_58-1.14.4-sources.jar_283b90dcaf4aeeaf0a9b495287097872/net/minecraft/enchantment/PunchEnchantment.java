package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class PunchEnchantment extends Enchantment {
   public PunchEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
      super(rarityIn, EnchantmentType.BOW, slots);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 12 + (enchantmentLevel - 1) * 20;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 25;
   }

   public int getMaxLevel() {
      return 2;
   }
}