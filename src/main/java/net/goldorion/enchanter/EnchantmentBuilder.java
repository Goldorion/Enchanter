package net.goldorion.enchanter;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Function;

public class EnchantmentBuilder {

    private final EnchantmentCategory category;
    private final EquipmentSlot[] slots;
    private final Enchantment.Rarity rarity;
    private int maxLevel = 1;
    private int minCost = 0;
    private int maxCost = 0;
    private Function<Enchantment, Boolean> checkCompatibility;
    private boolean isTreasure;
    private boolean isCurse;

    public EnchantmentBuilder(EnchantmentCategory category, EquipmentSlot[] slots) {
        this(Enchantment.Rarity.COMMON, category, slots);
    }
    public EnchantmentBuilder(Enchantment.Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        this.rarity = rarity;
        this.category = category;
        this.slots = slots;
    }

    public Enchantment.Rarity getRarity() {
        return rarity;
    }

    public EnchantmentCategory getCategory() {
        return category;
    }

    public EquipmentSlot[] getSlots() {
        return slots;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public EnchantmentBuilder setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public int getMinCost() {
        return minCost;
    }

    public EnchantmentBuilder setMinCost(int minCost) {
        this.minCost = minCost;
        return this;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public EnchantmentBuilder setMaxCost(int maxCost) {
        this.maxCost = maxCost;
        return this;
    }

    public Function<Enchantment, Boolean> getCheckCompatibility() {
        return checkCompatibility;
    }

    public EnchantmentBuilder setCheckCompatibility(Function<Enchantment, Boolean> checkCompatibility) {
        this.checkCompatibility = checkCompatibility;
        return this;
    }

    public boolean isTreasure() {
        return isTreasure;
    }

    public EnchantmentBuilder setTreasure() {
        isTreasure = true;
        return this;
    }

    public boolean isCurse() {
        return isCurse;
    }

    public EnchantmentBuilder setCurse() {
        isCurse = true;
        return this;
    }
}
