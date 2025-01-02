package com.davigj.change_of_cart.core.mixin;

import com.davigj.change_of_cart.core.ChangeOfCart;
import com.davigj.change_of_cart.core.CCConfig;
import com.davigj.change_of_cart.core.other.CCBlockTags;
import com.llamalad7.mixinextras.sugar.Local;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.MinecartSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecartSoundInstance.class)
public abstract class MinecartSoundInstanceMixin extends AbstractTickableSoundInstance {
    protected MinecartSoundInstanceMixin(SoundEvent p_235076_, SoundSource p_235077_, RandomSource p_235078_) {
        super(p_235076_, p_235077_, p_235078_);
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/resources/sounds/MinecartSoundInstance;volume:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void injected(MinecartSoundInstance instance, float value, @Local float f) {
        AbstractMinecart minecart = ((IMinecartSoundInstanceAccessor) this).getMinecart();
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        boolean waxMuffle = manager.getValue(instance.minecart, ChangeOfCart.WAXED) && CCConfig.COMMON.waxingMuffles.get();
        boolean woolMuffle = minecart.level().getBlockState(minecart.blockPosition().below()).is(CCBlockTags.MINECART_MUFFLERS) && CCConfig.COMMON.mufflingBlocks.get();
        if (CCConfig.COMMON.silenceStacks.get() && waxMuffle && woolMuffle) {
            ((IAbstractSoundInstanceAccessor) this).setVolume(0.0F);
        } else if (waxMuffle || woolMuffle) {
            ((IAbstractSoundInstanceAccessor) this).setVolume((float) Mth.lerp(Mth.clamp(f, 0.0F, 0.5F), 0.0F,
                    CCConfig.COMMON.maxCartVolume.get() * (1 - CCConfig.COMMON.muffleReductionPercent.get())));
        } else {
            ((IAbstractSoundInstanceAccessor) this).setVolume(value);
        }
    }
}
