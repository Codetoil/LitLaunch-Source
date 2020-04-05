package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class RespirationEnchantment extends Enchantment {
   public RespirationEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
      super(rarityIn, EnchantmentType.ARMOR_HEAD, slots);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 10 * enchantmentLevel;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 30;
   }

   public int getMaxLevel() {
      return 3;
   }
}