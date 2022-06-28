package net.goldorion.enchanter.mixins.entity;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.goldorion.enchanter.enchantments.MagmaWalker;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    @Inject(method = "onChangedBlock", at = @At("HEAD"))
    public void onChangedBlock(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (Utils.hasEnchantment(entity.getItemBySlot(EquipmentSlot.FEET), ModEnchantments.MAGMA_WALKER)) {
            MagmaWalker.onEntityMoved(entity, this.level, this.blockPosition(),
                    EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MAGMA_WALKER, entity.getItemBySlot(EquipmentSlot.FEET)));
        }
    }

    @Inject(method = "releaseUsingItem()V", at = @At("HEAD"))
    public void onStopUsingItem(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        ItemStack useItem = entity.getUseItem();
        if (useItem.getItem() instanceof BowItem && Utils.hasEnchantment(useItem, ModEnchantments.MULTI_ARROWS)) {
            int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MULTI_ARROWS, useItem);
            for (int i = 0; i < level; i++) {
                useItem.releaseUsing(this.level, entity, entity.getUseItemRemainingTicks());
            }
        }
    }

    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    public void die(DamageSource source, CallbackInfo ci) {
        if (((LivingEntity) (Object) this) instanceof Player player) {
            Iterable<ItemStack> armorSlots = player.getArmorSlots();
            armorSlots.forEach((itemstack) -> {
                if (itemstack.getItem() instanceof ArmorItem armorItem) {
                    if (armorItem.getSlot() == EquipmentSlot.CHEST && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SECOND_CHANCE, itemstack) != 0) {
                        if (source != DamageSource.OUT_OF_WORLD) {
                            ci.cancel();
                            player.setHealth(1);
                            if (!player.getLevel().isClientSide())
                                player.getLevel().playSound(null, new BlockPos(player.getX(), player.getY(), player.getZ()),
                                        new SoundEvent(new ResourceLocation("item.totem.use")), SoundSource.PLAYERS, 1, 1);
                            if (itemstack.getDamageValue() == itemstack.getMaxDamage() - 1)
                                player.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                            else itemstack.setDamageValue(itemstack.getMaxDamage() - 1);
                        }
                    }
                }
            });
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void shieldBlocksDamage(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (Utils.hasEnchantment(((LivingEntity) ((LivingEntity) (Object) this)).getUseItem(), ModEnchantments.HYLIAN_SHIELD))
            cir.setReturnValue(false);
    }

    @Inject(method = "actuallyHurt", at = @At("HEAD"), cancellable = true)
    public void onEntityDamagedEvent(DamageSource source, float amount, CallbackInfo ci) {
        if (((LivingEntity) (Object) this) instanceof Player player) {
            //Magma Walker enchantment
            if (source == DamageSource.HOT_FLOOR) {
                player.getArmorSlots().forEach(itemstack -> {
                    if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MAGMA_WALKER, itemstack) != 0) {
                        ci.cancel();
                    }
                });
            }
        }
    }
}
