package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class QuickChargeEnchantment extends Enchantment {
   public QuickChargeEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slotTypes) {
      super(rarityIn, EnchantmentType.CROSSBOW, slotTypes);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 12 + (enchantmentLevel - 1) * 20;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return 50;
   }

   public int getMaxLevel() {
      return 3;
   }
}