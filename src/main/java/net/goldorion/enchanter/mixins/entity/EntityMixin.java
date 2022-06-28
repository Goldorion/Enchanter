package net.goldorion.enchanter.mixins.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract double getY();

    @Shadow public abstract boolean isRemoved();

    @Shadow public abstract double getX();

    @Shadow public abstract void setPos(double d, double e, double f);

    @Shadow public abstract int getId();

    @Shadow public Level level;
    @Shadow public abstract BlockPos blockPosition();
}
