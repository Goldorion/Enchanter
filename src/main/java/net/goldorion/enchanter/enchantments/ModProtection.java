package net.goldorion.enchanter.enchantments;

import net.goldorion.enchanter.EnchantmentBuilder;
import net.goldorion.enchanter.GenericEnchantment;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModProtection extends GenericEnchantment {

    private final Type type;

    public ModProtection(Rarity rarity, Type type, EnchantmentCategory category, EquipmentSlot slot) {
        this(rarity, type, category, new EquipmentSlot[]{slot});
    }

    public ModProtection(Rarity rarity, Type type, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(new EnchantmentBuilder(rarity, category, slots));
        this.type = type;
    }

    private static boolean hasNoProtectionEnchants(ItemStack item) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.ALL_DAMAGE_PROTECTION, item) != 0 &&
                EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLAST_PROTECTION, item) != 0 &&
                EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FALL_PROTECTION, item) != 0 &&
                EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_PROTECTION, item) != 0 &&
                EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, item) != 0;
    }

    @Override
    public int getMinCost(int level) {
        return this.type.getMinCost() + (level - 1) * this.type.getLevelCost();
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + this.type.getLevelCost();
    }

    @Override
    public int getDamageProtection(int level, @NotNull DamageSource damageSource) {
        if (type.getSources().contains(damageSource))
            return level * type.getDamageProtectionMultiplier();
        else
            return 0;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean canEnchant(@NotNull ItemStack itemstack) {
        return super.canEnchant(itemstack) && hasNoProtectionEnchants(itemstack);
    }

    public enum Type {
        CACTUS(10, 10, 5, DamageSource.CACTUS),
        FALLING_BLOCK(5, 6, 3, DamageSource.ANVIL, DamageSource.FALLING_BLOCK),
        LIGHTNING_BOLT(15, 12, 10, DamageSource.LIGHTNING_BOLT);

        private final int minCost;
        private final int levelCost;
        private final int damageProtectionMultiplier;
        private final List<DamageSource> sources;

        Type(int minCost, int levelCost, int damageProtectionMultiplier, DamageSource... sources) {
            this.minCost = minCost;
            this.levelCost = levelCost;
            this.damageProtectionMultiplier = damageProtectionMultiplier;
            this.sources = List.of(sources);
        }

        public int getMinCost() {
            return this.minCost;
        }

        public int getLevelCost() {
            return this.levelCost;
        }

        public int getDamageProtectionMultiplier() {
            return damageProtectionMultiplier;
        }

        public List<DamageSource> getSources() {
            return sources;
        }
    }
}
