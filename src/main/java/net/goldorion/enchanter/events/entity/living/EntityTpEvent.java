package net.goldorion.enchanter.events.entity.living;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class EntityTpEvent {

    @SubscribeEvent
    public static void entityTp(EntityTeleportEvent event) {
        if (event.getEntity() instanceof Player player && Utils.hasEnchantment(player.getArmorSlots(), ModEnchantments.WRONG_BLOCK)) {
            int level = Utils.getHighestLevel(player.getArmorSlots(), ModEnchantments.WRONG_BLOCK);
            System.out.println(event.getTarget());
            int i = new Random().nextInt(2 + level);
            event.setTargetX(event.getTargetX() + ((new Random().nextBoolean()) ? i * -1 : i));
            i = new Random().nextInt(2 + level);
            event.setTargetZ(event.getTargetZ() + ((new Random().nextBoolean()) ? i * -1 : i));

            System.out.println(event.getTarget());
        }
    }
}
