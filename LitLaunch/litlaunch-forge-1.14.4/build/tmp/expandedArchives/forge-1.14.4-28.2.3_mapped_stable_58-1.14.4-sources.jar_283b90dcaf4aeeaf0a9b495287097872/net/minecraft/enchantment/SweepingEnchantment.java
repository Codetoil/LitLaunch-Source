package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class SweepingEnchantment extends Enchantment {
   public SweepingEnchantment(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
      super(rarityIn, EnchantmentType.WEAPON, slots);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 5 + (enchantmentLevel - 1) * 9;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 15;
   }

   public int getMaxLevel() {
      return 3;
   }

   public static float getSweepingDamageRatio(int p_191526_0_) {
      return 1.0F - 1.0F / (float)(p_191526_0_ + 1);
   }
}