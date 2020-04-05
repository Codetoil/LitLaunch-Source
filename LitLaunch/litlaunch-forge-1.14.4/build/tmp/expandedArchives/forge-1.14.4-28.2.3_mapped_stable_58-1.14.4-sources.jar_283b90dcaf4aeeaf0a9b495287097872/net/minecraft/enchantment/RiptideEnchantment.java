package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class RiptideEnchantment extends Enchantment {
   public RiptideEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
      super(rarityIn, EnchantmentType.TRIDENT, slots);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 10 + enchantmentLevel * 7;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return 50;
   }

   public int getMaxLevel() {
      return 3;
   }

   public boolean canApplyTogether(Enchantment ench) {
      return super.canApplyTogether(ench) && ench != Enchantments.LOYALTY && ench != Enchantments.CHANNELING;
   }
}