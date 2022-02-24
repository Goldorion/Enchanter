package net.goldorion.enchanter.events.entity.living.player;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ShieldEvent {

    @SubscribeEvent
    public static void shieldBlocksDamage(ShieldBlockEvent event) {
        if (Utils.hasEnchantment(event.getEntityLiving().getUseItem(), ModEnchantments.HYLIAN_SHIELD))
            event.setShieldTakesDamage(false);
    }
}
