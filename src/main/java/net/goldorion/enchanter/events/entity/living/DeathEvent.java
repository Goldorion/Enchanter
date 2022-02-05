package net.goldorion.enchanter.events.entity.living;

import net.goldorion.enchanter.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@Mod.EventBusSubscriber
public class DeathEvent {

    @SubscribeEvent
    public static void onEntityDeath(net.minecraftforge.event.entity.living.LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        //Second chance enchantment
        if (entity instanceof Player player) {
            Iterable<ItemStack> armorSlots = player.getArmorSlots();
            armorSlots.forEach((itemstack) -> {
                if (itemstack.getItem() instanceof ArmorItem armorItem) {
                    if (armorItem.getSlot() == EquipmentSlot.CHEST && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SECOND_CHANCE, itemstack) != 0) {
                        if (event.getSource() != DamageSource.OUT_OF_WORLD) {
                            event.setCanceled(true);
                            player.setHealth(1);
                            if (!player.getLevel().isClientSide())
                                player.getLevel().playSound(null, new BlockPos(player.getX(), player.getY(), player.getZ()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS
                                        .getValue(new ResourceLocation("item.totem.use"))), SoundSource.PLAYERS, 1, 1);
                            if (itemstack.getDamageValue() == itemstack.getMaxDamage() - 1)
                                player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                            else
                                itemstack.setDamageValue(itemstack.getMaxDamage() - 1);
                        }
                    }
                }
            });
        }
    }
}
