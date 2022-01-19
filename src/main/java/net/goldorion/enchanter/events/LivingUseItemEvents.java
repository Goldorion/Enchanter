package net.goldorion.enchanter.events;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LivingUseItemEvents {

    @SubscribeEvent
    public static void onStopUsingItem(LivingEntityUseItemEvent.Stop event) {
        if (event.getItem().getItem() instanceof BowItem) {
            if (Utils.hasEnchantment(event.getItem(), ModEnchantments.MULTI_ARROWS)) {
                int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MULTI_ARROWS, event.getItem());
                for (int i = 0; i < level; i++) {
                    event.getItem().releaseUsing(event.getEntityLiving().level, event.getEntityLiving(), event.getEntityLiving().getUseItemRemainingTicks());
                }
            }
        }
    }
}
