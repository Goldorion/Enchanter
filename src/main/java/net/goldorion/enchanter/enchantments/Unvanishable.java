package net.goldorion.enchanter.enchantments;

import net.goldorion.enchanter.ModEnchantments;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class Unvanishable extends Enchantment {
    public Unvanishable() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.VANISHABLE, ModEnchantments.ALL);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
