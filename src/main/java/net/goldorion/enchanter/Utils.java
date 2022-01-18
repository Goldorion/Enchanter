package net.goldorion.enchanter;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.concurrent.atomic.AtomicBoolean;

public class Utils {

    public static boolean hasEnchantment(ItemStack itemstack, Enchantment enchantment) {
        return EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) != 0;
    }

    public static boolean hasEnchantment(Iterable<ItemStack> stack, Enchantment enchantment) {
        AtomicBoolean retval = new AtomicBoolean(false);
        stack.forEach(itemStack -> {
            if (!retval.get())
                retval.set(hasEnchantment(itemStack, enchantment));
        });

        return retval.get();
    }

    public static boolean hasEnchantmentWithLevel(ItemStack itemstack, Enchantment enchantment, int level) {
        return EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemstack) >= level;
    }
}
