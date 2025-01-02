package com.davigj.change_of_cart.core.mixin;

import net.minecraft.client.resources.sounds.MinecartSoundInstance;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecartSoundInstance.class)
public interface IMinecartSoundInstanceAccessor {
    @Accessor
    AbstractMinecart getMinecart();
}
