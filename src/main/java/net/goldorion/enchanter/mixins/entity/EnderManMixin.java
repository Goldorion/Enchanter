package net.goldorion.enchanter.mixins.entity;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public abstract class EnderManMixin extends LivingEntityMixin {

    @Inject(method = "isLookingAtMe", at = @At("HEAD"), cancellable = true)
    public void isLookingAtMe(Player player, CallbackInfoReturnable<Boolean> info) {
        if (Utils.hasEnchantment(player.getItemBySlot(EquipmentSlot.HEAD), ModEnchantments.ENDER_FRIEND))
            info.setReturnValue(false);
    }
}
