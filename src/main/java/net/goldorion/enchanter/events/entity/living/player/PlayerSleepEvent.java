package net.goldorion.enchanter.events.entity.living.player;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerSleepEvent {

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        if (Utils.hasEnchantment(event.getPlayer().getArmorSlots(), ModEnchantments.SLEEPLESS_NIGHT)) {
            event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
        }
    }
}
