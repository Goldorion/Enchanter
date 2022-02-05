package net.goldorion.enchanter.events.entity.living;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.goldorion.enchanter.enchantments.MagmaWalkerEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityUpdateEvent {

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            if (Utils.hasEnchantment(player.getItemBySlot(EquipmentSlot.FEET), ModEnchantments.MAGMA_WALKER)) {
                MagmaWalkerEnchantment.onEntityMoved(player, player.getLevel(), player.blockPosition(),
                        EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MAGMA_WALKER, player.getItemBySlot(EquipmentSlot.FEET)));
            }
        }
    }
}
