package net.goldorion.enchanter.mixins.item;

import net.goldorion.enchanter.ModEnchantments;
import net.goldorion.enchanter.enchantments.Farmer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoeItem.class)
public class HoeItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"))
    public void onEntityDamagedEvent(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemstack = useOnContext.getItemInHand();
        if (itemstack.getItem() instanceof HoeItem && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.FARMER, itemstack) != 0 &&
                (useOnContext.getLevel().getBlockState(useOnContext.getClickedPos()).getBlock() instanceof GrassBlock ||
                        useOnContext.getLevel().getBlockState(useOnContext.getClickedPos()).getBlock() == Blocks.DIRT)) {
            Farmer.applyEffectOnBlocks(useOnContext.getPlayer(), useOnContext.getHand(), useOnContext.getClickedPos(), itemstack,
                    EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.FARMER, itemstack));
        }
    }
}
