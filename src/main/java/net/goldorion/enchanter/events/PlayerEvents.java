package net.goldorion.enchanter.events;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.goldorion.enchanter.enchantments.Farmer;
import net.goldorion.enchanter.enchantments.MagmaWalkerEnchantment;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@Mod.EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
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

    @SubscribeEvent
    public static void onEntityDamagedEvent(LivingDamageEvent event) {
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

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            if (Utils.hasEnchantment(player.getItemBySlot(EquipmentSlot.FEET), ModEnchantments.MAGMA_WALKER)) {
                MagmaWalkerEnchantment.onEntityMoved(player, player.getLevel(), player.blockPosition(),
                        EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MAGMA_WALKER, player.getItemBySlot(EquipmentSlot.FEET)));
            }
        }
    }

    @SubscribeEvent
    public static void onItemUsed(UseHoeEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            ItemStack itemstack = event.getContext().getItemInHand();
            if (itemstack.getItem() instanceof HoeItem && EnchantmentHelper.getItemEnchantmentLevel(
                    ModEnchantments.FARMER, itemstack) != 0) {
                Farmer.applyEffectOnBlocks(player, event.getContext().getHand(), event.getContext().getClickedPos(), itemstack,
                        EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.FARMER, itemstack));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        if (Utils.hasEnchantment(event.getPlayer().getArmorSlots(), ModEnchantments.SLEEPLESS_NIGHT)) {
            event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
        }
    }
}
