package net.goldorion.enchanter.events.entity;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.goldorion.enchanter.enchantments.Farmer;
import net.goldorion.enchanter.enchantments.Miner;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        if (Utils.hasEnchantment(event.getEntity().getArmorSlots(), ModEnchantments.SLEEPLESS_NIGHT.get())) {
            event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
        }
    }

    @SubscribeEvent
    public static void arrownockEvent(ArrowNockEvent event) {
        if (Utils.hasEnchantment(event.getBow(), ModEnchantments.SLIPPERY_ROPE.get())) {
            if (!event.getLevel().isClientSide && Math.random() < 0.2f * EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.SLIPPERY_ROPE.get(), event.getBow()))
                event.setAction(new InteractionResultHolder<>(InteractionResult.CONSUME, event.getBow()));

        }
    }

    @SubscribeEvent
    public static void itemFished(ItemFishedEvent event) {
        if (Utils.hasEnchantment(event.getEntity().getItemInHand(event.getEntity().getUsedItemHand()), ModEnchantments.SLIPPERY_HOOK.get())) {
            if (!event.getEntity().getLevel().isClientSide() &&
                    Math.random() * 100 > (15 * EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.SLIPPERY_HOOK.get(), event.getEntity().getUseItem()))) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void shieldBlocksDamage(ShieldBlockEvent event) {
        if (Utils.hasEnchantment(event.getEntity().getUseItem(), ModEnchantments.HYLIAN_SHIELD.get()))
            event.setShieldTakesDamage(false);
    }

    @SubscribeEvent
    public static void onItemUsed(BlockEvent.BlockToolModificationEvent event) {
        ItemStack itemstack = event.getContext().getItemInHand();
        if (event.getToolAction() == ToolActions.HOE_TILL) {
            if (itemstack.getItem() instanceof HoeItem && EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.FARMER.get(), itemstack) != 0 &&
                    (event.getContext().getLevel().getBlockState(event.getPos()).getBlock() instanceof GrassBlock ||
                            event.getContext().getLevel().getBlockState(event.getPos()).getBlock() == Blocks.DIRT)) {
                Farmer.applyEffectOnBlocks(event.getPlayer(), event.getContext().getHand(), event.getContext().getClickedPos(), itemstack,
                        EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.FARMER.get(), itemstack));
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        ItemStack itemstack = event.getPlayer().getItemInHand(event.getPlayer().getUsedItemHand());
        if (EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.MINER.get(), itemstack) != 0) {
            Miner.applyEffectOnBlocks(event.getLevel(), event.getPlayer(), event.getPos(), itemstack,
                    EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.MINER.get(), itemstack));
        }
    }
}
