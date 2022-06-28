package net.goldorion.enchanter.enchantments;

import net.goldorion.enchanter.EnchantmentBuilder;
import net.goldorion.enchanter.GenericEnchantment;
import net.goldorion.enchanter.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import org.jetbrains.annotations.NotNull;

public class Farmer extends GenericEnchantment {
    public Farmer() {
        super(new EnchantmentBuilder(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, ModEnchantments.HANDS));
    }

    public static void applyEffectOnBlocks(Player player, InteractionHand hand, BlockPos pos, ItemStack stack, int level) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        level++;
        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                changeBlock(player, new BlockPos(x + i, y, z + j), stack, hand);
                changeBlock(player, new BlockPos(x - i, y, z - j), stack, hand);
                changeBlock(player, new BlockPos(x + i, y, z - j), stack, hand);
                changeBlock(player, new BlockPos(x - i, y, z + j), stack, hand);
            }
        }
    }

    private static void changeBlock(Player player, BlockPos pos, ItemStack stack, InteractionHand hand) {
        if (player.getLevel().isEmptyBlock(pos.above())) {
            if (player.getLevel().getBlockState(pos).getBlock() instanceof GrassBlock || player.getLevel().getBlockState(pos).getBlock() == Blocks.DIRT) {
                player.getLevel().setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 3);
                stack.hurtAndBreak(1, player, player_ -> player_.broadcastBreakEvent(hand));
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean canEnchant(@NotNull ItemStack stack) {
        return stack.getItem() instanceof HoeItem;
    }
}
