package net.goldorion.enchanter.enchantments;

import net.goldorion.enchanter.EnchantmentBuilder;
import net.goldorion.enchanter.GenericEnchantment;
import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;

public class Miner extends GenericEnchantment {

    public Miner() {
        super(new EnchantmentBuilder(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, ModEnchantments.HANDS));
    }

    public static void applyEffectOnBlocks(LevelAccessor level, Player player, BlockPos pos, ItemStack stack, int radius) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (!player.isShiftKeyDown()) {
            radius++;
            if (player.getXRot() > 65 + 3.5 * radius || player.getXRot() < -65 - 3.5 * radius) {
                for (int i = 0; i < radius; i++) {
                    for (int j = 0; j < radius; j++) {
                        breakBlock(level, player, new BlockPos(x + i, y, z + j), stack);
                        breakBlock(level, player, new BlockPos(x + i, y, z - j), stack);
                        breakBlock(level, player, new BlockPos(x - i, y, z + j), stack);
                        breakBlock(level, player, new BlockPos(x - i, y, z - j), stack);
                    }
                }
            } else if ((player.getDirection()) == Direction.NORTH || (player.getDirection()) == Direction.SOUTH) {
                for (int i = 0; i < radius; i++) {
                    for (int j = 0; j < radius; j++) {
                        breakBlock(level, player, new BlockPos(x + i, y + j, z), stack);
                        breakBlock(level, player, new BlockPos(x + i, y - j, z), stack);
                        breakBlock(level, player, new BlockPos(x - i, y + j, z), stack);
                        breakBlock(level, player, new BlockPos(x - i, y - j, z), stack);
                    }
                }
            } else if ((player.getDirection()) == Direction.WEST || (player.getDirection()) == Direction.EAST) {
                for (int i = 0; i < radius; i++) {
                    for (int j = 0; j < radius; j++) {
                        breakBlock(level, player, new BlockPos(x, y + i, z + j), stack);
                        breakBlock(level, player, new BlockPos(x, y + i, z - j), stack);
                        breakBlock(level, player, new BlockPos(x, y - i, z + j), stack);
                        breakBlock(level, player, new BlockPos(x, y - i, z - j), stack);
                    }
                }
            }
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    private static void breakBlock(LevelAccessor level, Player player, BlockPos pos, ItemStack stack) {
        if (level.getBlockState(pos).canOcclude() && stack.getItem().isCorrectToolForDrops((level.getBlockState(pos)))
                && level.getBlockState(pos).getDestroySpeed(level, pos) >= 0) {
            if (Utils.checkGamemode(player, GameType.CREATIVE)) {
                level.destroyBlock(pos, false);
            } else {
                level.destroyBlock(pos, true);
                if (stack.hurt(1, RandomSource.create(), null)) {
                    stack.shrink(1);
                    stack.setDamageValue(0);
                }
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
