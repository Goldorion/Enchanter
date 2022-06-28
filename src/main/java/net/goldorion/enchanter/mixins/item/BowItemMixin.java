package net.goldorion.enchanter.mixins.item;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.Utils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public abstract class BowItemMixin {

    @Inject(method = "use", at = @At("HEAD"))
    public void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (Utils.hasEnchantment(player.getItemInHand(interactionHand), ModEnchantments.SLIPPERY_ROPE)) {
            if (!level.isClientSide && Math.random() < 0.2f * EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.SLIPPERY_ROPE, player.getItemInHand(interactionHand)))
                cir.setReturnValue(new InteractionResultHolder<>(InteractionResult.CONSUME, player.getItemInHand(interactionHand)));
        }
    }
}
