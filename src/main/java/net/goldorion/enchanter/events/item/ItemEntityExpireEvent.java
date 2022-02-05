package net.goldorion.enchanter.events.item;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
public class ItemEntityExpireEvent {

    @SubscribeEvent
    public static void itemExpires(ItemExpireEvent event) {
        ItemStack stack = event.getEntityItem().getItem();
        if (Utils.hasEnchantment(stack, ModEnchantments.UNVANISHABLE)) {
            if (Utils.getEnchantment(ModEnchantments.UNVANISHABLE, stack) != null) {
                EnchantmentHelper.setEnchantmentLevel(Utils.getEnchantment(ModEnchantments.UNVANISHABLE, stack),
                        EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.UNVANISHABLE, stack) - 1);
                if (Utils.hasEnchantmentWithLevel(stack, ModEnchantments.UNVANISHABLE, 0))
                    Utils.removeEnchant(ModEnchantments.UNVANISHABLE, stack);
                event.getEntityItem().setExtendedLifetime();
            }
        }
    }
}
