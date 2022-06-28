package net.goldorion.enchanter.events;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.world.entity.player.Player;

public class PlayerSleepEvent {

    public PlayerSleepEvent() {
        EntitySleepEvents.ALLOW_SLEEPING.register((entity, blockPos) -> {
            if (Utils.hasEnchantment(entity.getArmorSlots(), ModEnchantments.SLEEPLESS_NIGHT)) {
                return Player.BedSleepingProblem.OTHER_PROBLEM;
            }
            return null;
        });
    }
}
