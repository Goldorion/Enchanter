package net.goldorion.enchanter.enchantments;

import net.goldorion.enchanter.ModEnchantments;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SlipperyRope extends Enchantment {

    public SlipperyRope() {
        super(Rarity.RARE, EnchantmentCategory.BOW, ModEnchantments.HANDS);
    }

    public int getMinCost(int level) {
        return 20;
    }

    public int getMaxCost(int level) {
        return 50;
    }

    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }
}
