package net.goldorion.enchanter.enchantments;

import net.goldorion.enchanter.EnchantmentBuilder;
import net.goldorion.enchanter.GenericEnchantment;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;

public class MagmaWalker extends GenericEnchantment {
    public MagmaWalker(Rarity rarity, EquipmentSlot slot) {
        super(new EnchantmentBuilder(rarity, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{slot}));
    }

    public static void onEntityMoved(LivingEntity entity, Level level, BlockPos blockPos, int enchantLevel) {
        if (!entity.isOnGround())
            return;

        BlockState blockState = Blocks.MAGMA_BLOCK.defaultBlockState();
        float f = Math.min(16, 2 + enchantLevel);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (BlockPos blockPos2 : BlockPos.betweenClosed(blockPos.offset(-f, -1.0, -f), blockPos.offset(f, -1.0, f))) {
            BlockState blockState3;
            if (!blockPos2.closerToCenterThan(entity.position(), f)) continue;
            mutableBlockPos.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
            BlockState blockState2 = level.getBlockState(mutableBlockPos);
            if (!blockState2.isAir() || (blockState3 = level.getBlockState(blockPos2)).getMaterial() != Material.LAVA || blockState3.getValue(LiquidBlock.LEVEL) != 0 ||
                    !blockState.canSurvive(level, blockPos2) || !level.isUnobstructed(blockState, blockPos2, CollisionContext.empty())) continue;
            level.setBlockAndUpdate(blockPos2, blockState);
            level.scheduleTick(blockPos2, Blocks.MAGMA_BLOCK, Mth.nextInt(entity.getRandom(), 60, 120));
        }
    }

    public int getMinCost(int level) {
        return level * 10;
    }

    public int getMaxCost(int level) {
        return this.getMinCost(level) + 15;
    }

    public boolean isTreasureOnly() {
        return true;
    }

    public int getMaxLevel() {
        return 2;
    }

    public boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && enchantment != Enchantments.DEPTH_STRIDER && enchantment != Enchantments.FROST_WALKER;
    }
}
