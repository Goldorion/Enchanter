package net.goldorion.enchanter.mixins.level;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.enchantments.Miner;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Final
    @Shadow
    protected ServerPlayer player;

    @Inject(method = "destroyBlock", at = @At("HEAD"))
    public void onEntityDamagedEvent(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemstack = player.getItemInHand(player.getUsedItemHand());
        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MINER, itemstack) != 0) {
            Miner.applyEffectOnBlocks(this.player.level, player, blockPos, itemstack,
                    EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MINER, itemstack));
        }
    }
}
