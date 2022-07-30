package net.goldorion.enchanter.events.entity;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.goldorion.enchanter.enchantments.MagmaWalker;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

@Mod.EventBusSubscriber
public class LivingEvents {

    @SubscribeEvent
    public static void entityTp(EntityTeleportEvent event) {
        if (event.getEntity() instanceof Player player && Utils.hasEnchantment(player.getArmorSlots(), ModEnchantments.WRONG_BLOCK.get())) {
            int level = Utils.getHighestLevel(player.getArmorSlots(), ModEnchantments.WRONG_BLOCK.get());
            int i = new Random().nextInt(2 + level);
            event.setTargetX(event.getTargetX() + ((new Random().nextBoolean()) ? i * -1 : i));
            i = new Random().nextInt(2 + level);
            event.setTargetZ(event.getTargetZ() + ((new Random().nextBoolean()) ? i * -1 : i));
        }
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (Utils.hasEnchantment(player.getItemBySlot(EquipmentSlot.FEET), ModEnchantments.MAGMA_WALKER.get())) {
                MagmaWalker.onEntityMoved(player, player.getLevel(), player.blockPosition(), EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.MAGMA_WALKER.get(), player.getItemBySlot(EquipmentSlot.FEET)));
            }
        }
    }

    @SubscribeEvent
    public static void onStopUsingItem(LivingEntityUseItemEvent.Stop event) {
        if (event.getItem().getItem() instanceof BowItem) {
            if (Utils.hasEnchantment(event.getItem(), ModEnchantments.MULTI_ARROWS.get())) {
                int level = EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.MULTI_ARROWS.get(), event.getItem());
                for (int i = 0; i < level; i++) {
                    event.getItem().releaseUsing(event.getEntity().level, event.getEntity(), event.getEntity().getUseItemRemainingTicks());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDeath(net.minecraftforge.event.entity.living.LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        //Second chance enchantment
        if (entity instanceof Player player) {
            Iterable<ItemStack> armorSlots = player.getArmorSlots();
            armorSlots.forEach((itemstack) -> {
                if (itemstack.getItem() instanceof ArmorItem armorItem) {
                    if (armorItem.getSlot() == EquipmentSlot.CHEST && EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.SECOND_CHANCE.get(), itemstack) != 0) {
                        if (event.getSource() != DamageSource.OUT_OF_WORLD) {
                            event.setCanceled(true);
                            player.setHealth(1);
                            if (!player.getLevel().isClientSide())
                                player.getLevel().playSound(null, new BlockPos(player.getX(), player.getY(), player.getZ()), Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use"))), SoundSource.PLAYERS, 1, 1);
                            if (itemstack.getDamageValue() == itemstack.getMaxDamage() - 1)
                                player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                            else itemstack.setDamageValue(itemstack.getMaxDamage() - 1);
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onEntityDamagedEvent(net.minecraftforge.event.entity.living.LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            //Magma Walker enchantment
            if (event.getSource() == DamageSource.HOT_FLOOR) {
                player.getArmorSlots().forEach(itemstack -> {
                    if (EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.MAGMA_WALKER.get(), itemstack) != 0) {
                        event.setCanceled(true);
                    }
                });
            }
        }
    }
}
