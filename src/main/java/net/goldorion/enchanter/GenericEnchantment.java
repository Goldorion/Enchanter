package net.goldorion.enchanter;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class GenericEnchantment extends Enchantment {

    private final EnchantmentBuilder builder;

    public GenericEnchantment(EnchantmentBuilder builder) {
        super(builder.getRarity(), builder.getCategory(), builder.getSlots());
        this.builder = builder;
    }

    @Override
    public int getMaxLevel() {
        return builder.getMaxLevel();
    }

    @Override
    public int getMinCost(int level) {
        return builder.getMinCost() != 0 ? level * builder.getMinCost() : super.getMinCost(level);
    }

    @Override
    public int getMaxCost(int level) {
        return builder.getMaxCost() != 0 ? level * builder.getMaxCost() : super.getMaxCost(level);
    }

    @Override
    public boolean isTreasureOnly() {
        return builder.isTreasure();
    }

    @Override
    public boolean isCurse() {
        return builder.isCurse();
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && (builder.getCheckCompatibility() != null ? builder.getCheckCompatibility().apply(enchantment) : true);
    }

    @Override
    public boolean canEnchant(@NotNull ItemStack itemstack) {
        return super.canEnchant(itemstack) && (builder.getCanEnchant() != null ? builder.getCanEnchant().apply(itemstack) : true);
    }
}
