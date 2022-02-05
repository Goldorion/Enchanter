package net.goldorion.enchanter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static int getHighestLevel(Iterable<ItemStack> stacks, Enchantment enchantment) {
        AtomicInteger level = new AtomicInteger();
        stacks.forEach(stack -> {
            if (hasEnchantment(stack, enchantment) && level.get() < EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack))
                level.set(EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack));
        });

        return level.get();
    }

    public static CompoundTag getEnchantment(Enchantment enchantment, ItemStack stack) {
        ResourceLocation resourcelocation = EnchantmentHelper.getEnchantmentId(enchantment);
        ListTag listtag = stack.getEnchantmentTags();

        for (int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            ResourceLocation resourcelocation1 = EnchantmentHelper.getEnchantmentId(compoundtag);
            if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
                return compoundtag;
            }
        }

        return null;
    }


    public static void removeEnchant(Enchantment enchantment, ItemStack stack) {
        ResourceLocation resourcelocation = EnchantmentHelper.getEnchantmentId(enchantment);
        ListTag listtag = stack.getEnchantmentTags();

        for (int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            ResourceLocation rl = EnchantmentHelper.getEnchantmentId(compoundtag);
            if (rl != null && rl.equals(resourcelocation)) {
                listtag.remove(i);
                break;
            }
        }
    }
}
