package net.goldorion.enchanter.events;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.enchantments.Farmer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class UseHoeEvent {

    @SubscribeEvent
    public static void onItemUsed(net.minecraftforge.event.entity.player.UseHoeEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            ItemStack itemstack = event.getContext().getItemInHand();
            if (itemstack.getItem() instanceof HoeItem && EnchantmentHelper.getItemEnchantmentLevel(
                    ModEnchantments.FARMER, itemstack) != 0) {
                Farmer.applyEffectOnBlocks(player, event.getContext().getHand(), event.getContext().getClickedPos(), itemstack,
                        EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.FARMER, itemstack));
            }
        }
    }
}
