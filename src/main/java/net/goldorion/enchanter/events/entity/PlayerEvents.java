package net.goldorion.enchanter.events.entity;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.goldorion.enchanter.enchantments.Farmer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        if (Utils.hasEnchantment(event.getPlayer().getArmorSlots(), ModEnchantments.SLEEPLESS_NIGHT.get())) {
            event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
        }
    }

    @SubscribeEvent
    public static void arrownockEvent(ArrowNockEvent event) {
        if (Utils.hasEnchantment(event.getBow(), ModEnchantments.SLIPPERY_ROPE.get())) {
            if (!event.getWorld().isClientSide && Math.random() < 0.2f * EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SLIPPERY_ROPE.get(), event.getBow()))
                event.setAction(new InteractionResultHolder<>(InteractionResult.CONSUME, event.getBow()));

        }
    }

    @SubscribeEvent
    public static void shieldBlocksDamage(ShieldBlockEvent event) {
        if (Utils.hasEnchantment(event.getEntityLiving().getUseItem(), ModEnchantments.HYLIAN_SHIELD.get()))
            event.setShieldTakesDamage(false);
    }

    @SubscribeEvent
    public static void onItemUsed(net.minecraftforge.event.entity.player.UseHoeEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            ItemStack itemstack = event.getContext().getItemInHand();
            if (itemstack.getItem() instanceof HoeItem && EnchantmentHelper.getItemEnchantmentLevel(
                    ModEnchantments.FARMER.get(), itemstack) != 0) {
                Farmer.applyEffectOnBlocks(player, event.getContext().getHand(), event.getContext().getClickedPos(), itemstack,
                        EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.FARMER.get(), itemstack));
            }
        }
    }
}
