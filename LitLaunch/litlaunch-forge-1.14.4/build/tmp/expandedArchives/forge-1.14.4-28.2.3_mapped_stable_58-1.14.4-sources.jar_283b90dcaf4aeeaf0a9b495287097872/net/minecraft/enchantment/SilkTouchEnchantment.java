package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class SilkTouchEnchantment extends Enchantment {
   protected SilkTouchEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
      super(rarityIn, EnchantmentType.DIGGER, slots);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 15;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return super.getMinEnchantability(enchantmentLevel) + 50;
   }

   public int getMaxLevel() {
      return 1;
   }

   public boolean canApplyTogether(Enchantment ench) {
      return super.canApplyTogether(ench) && ench != Enchantments.FORTUNE;
   }
}