package net.goldorion.enchanter.mixins.entity;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ThrownEnderpearl.class)
public abstract class ThrownEnderPearlMixin extends ProjectileMixin {

    @Inject(method = "onHit", at = @At("HEAD"))
    public void onEntityDamagedEvent(HitResult hitResult, CallbackInfo ci) {
        if (this.getOwner() instanceof ServerPlayer player && Utils.hasEnchantment(player.getArmorSlots(), ModEnchantments.WRONG_BLOCK)
                && !level.isClientSide && !this.isRemoved()) {
            int level = Utils.getHighestLevel(player.getArmorSlots(), ModEnchantments.WRONG_BLOCK);
            int i = new Random().nextInt(2 + level);
            double x = this.getX() + ((new Random().nextBoolean()) ? i * -1 : i);
            i = new Random().nextInt(2 + level);
            double z = ((new Random().nextBoolean()) ? i * -1 : i);
            this.setPos(x, this.getY(), z);
        }
    }
}
