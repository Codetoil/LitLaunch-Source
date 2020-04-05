package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class ChannelingEnchantment extends Enchantment {
   public ChannelingEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
      super(rarityIn, EnchantmentType.TRIDENT, slots);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 25;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return 50;
   }

   public int getMaxLevel() {
      return 1;
   }

   public boolean canApplyTogether(Enchantment ench) {
      return super.canApplyTogether(ench);
   }
}