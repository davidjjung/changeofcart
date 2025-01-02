package com.davigj.change_of_cart.core.mixin;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractSoundInstance.class)
public interface IAbstractSoundInstanceAccessor {
    @Accessor(value = "volume")
    void setVolume(float volume);
}
