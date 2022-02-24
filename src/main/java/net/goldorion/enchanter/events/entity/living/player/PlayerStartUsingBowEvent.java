package net.goldorion.enchanter.events.entity.living.player;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerStartUsingBowEvent {

    @SubscribeEvent
    public static void arrownockEvent(ArrowNockEvent event) {
        if (Utils.hasEnchantment(event.getBow(), ModEnchantments.SLIPPERY_ROPE)) {
            if (!event.getWorld().isClientSide && Math.random() < 0.2f * EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SLIPPERY_ROPE, event.getBow()))
                event.setAction(new InteractionResultHolder<>(InteractionResult.CONSUME, event.getBow()));

        }
    }
}
