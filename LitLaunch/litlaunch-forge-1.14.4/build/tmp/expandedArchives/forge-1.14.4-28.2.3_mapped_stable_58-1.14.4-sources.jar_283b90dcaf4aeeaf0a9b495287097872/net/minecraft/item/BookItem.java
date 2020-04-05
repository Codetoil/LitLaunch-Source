package net.minecraft.item;

public class BookItem extends Item {
   public BookItem(Item.Properties builder) {
      super(builder);
   }

   public boolean isEnchantable(ItemStack stack) {
      return stack.getCount() == 1;
   }

   public int getItemEnchantability() {
      return 1;
   }
}