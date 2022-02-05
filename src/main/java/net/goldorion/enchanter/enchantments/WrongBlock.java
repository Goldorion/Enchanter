package net.goldorion.enchanter.enchantments;

import net.goldorion.enchanter.ModEnchantments;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class WrongBlock extends Enchantment {
    public WrongBlock() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR, ModEnchantments.ARMORS);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
