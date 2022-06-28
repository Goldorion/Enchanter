package net.goldorion.enchanter.mixins.entity;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {

    @Shadow
    public abstract Player getPlayerOwner();

    @Inject(method = "retrieve", at = @At("HEAD"))
    public void onEntityDamagedEvent(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        if (Utils.hasEnchantment(getPlayerOwner().getItemInHand(getPlayerOwner().getUsedItemHand()), ModEnchantments.SLIPPERY_HOOK)) {
            if (!getPlayerOwner().getLevel().isClientSide() &&
                    Math.random() * 100 > (15 * EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SLIPPERY_HOOK, getPlayerOwner().getUseItem()))) {
                cir.cancel();
            }
        }
    }
}
