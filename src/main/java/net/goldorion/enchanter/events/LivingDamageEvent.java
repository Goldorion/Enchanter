package net.goldorion.enchanter.events;

import net.goldorion.enchanter.ModEnchantments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LivingDamageEvent {

    @SubscribeEvent
    public static void onEntityDamagedEvent(net.minecraftforge.event.entity.living.LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            //Magma Walker enchantment
            if (event.getSource() == DamageSource.HOT_FLOOR) {
                player.getArmorSlots().forEach(itemstack -> {
                    if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MAGMA_WALKER, itemstack) != 0) {
                        event.setCanceled(true);
                    }
                });
            }
        }
    }
}
