package net.goldorion.enchanter.mixins.entity;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityExpireEvent {

    @Shadow
    public abstract ItemStack getItem();

    @Shadow
    public abstract void setExtendedLifetime();

    @Inject(method = "tick", at = @At("HEAD"))
    public void onEntityDamagedEvent(CallbackInfo ci) {
        ItemStack stack = this.getItem();
        if (Utils.hasEnchantment(stack, ModEnchantments.UNVANISHABLE)) {
            if (Utils.getEnchantment(ModEnchantments.UNVANISHABLE, stack) != null) {
                EnchantmentHelper.setEnchantmentLevel(Utils.getEnchantment(ModEnchantments.UNVANISHABLE, stack),
                        EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.UNVANISHABLE, stack) - 1);
                if (Utils.hasEnchantmentWithLevel(stack, ModEnchantments.UNVANISHABLE, 0))
                    Utils.removeEnchant(ModEnchantments.UNVANISHABLE, stack);
                this.setExtendedLifetime();
            }
        }
    }
}
