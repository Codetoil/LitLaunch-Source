package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class AquaAffinityEnchantment extends Enchantment {
   public AquaAffinityEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
      super(rarityIn, EnchantmentType.ARMOR_HEAD, slots);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 1;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 40;
   }

   public int getMaxLevel() {
      return 1;
   }
}