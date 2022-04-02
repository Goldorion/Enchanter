package net.goldorion.enchanter.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

public class MagmaWalker extends Enchantment {
    public MagmaWalker(Rarity rarity, EquipmentSlot slot) {
        super(rarity, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{slot});
    }

    public static void onEntityMoved(LivingEntity entity, Level level, BlockPos blockPos, int enchantLevel) {
        if (entity.isOnGround()) {
            BlockState blockstate = Blocks.MAGMA_BLOCK.defaultBlockState();
            float f = (float) Math.min(16, 2 + enchantLevel);
            BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();

            for (BlockPos blockpos : BlockPos.betweenClosed(blockPos.offset(-f, -1.0D, -f), blockPos.offset(f, -1.0D, f))) {
                if (blockpos.closerToCenterThan(entity.position(), f)) {
                    mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = level.getBlockState(mutableblockpos);
                    if (blockstate1.isAir()) {
                        BlockState blockstate2 = level.getBlockState(blockpos);
                        boolean isFull = blockstate2.getBlock() == Blocks.LAVA && blockstate2.getValue(LiquidBlock.LEVEL) == 0;
                        if (blockstate2.getMaterial() == Material.LAVA && isFull && blockstate.canSurvive(level, blockpos) &&
                                level.isUnobstructed(blockstate, blockpos, CollisionContext.empty()) && !ForgeEventFactory.onBlockPlace(
                                entity, BlockSnapshot.create(level.dimension(), level, blockpos), Direction.UP)) {
                            level.setBlockAndUpdate(blockpos, blockstate);
                            level.scheduleTick(blockpos, Blocks.MAGMA_BLOCK, Mth.nextInt(entity.getRandom(), 60, 120));
                        }
                    }
                }
            }

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

    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && enchantment != Enchantments.DEPTH_STRIDER && enchantment != Enchantments.FROST_WALKER;
    }
}
