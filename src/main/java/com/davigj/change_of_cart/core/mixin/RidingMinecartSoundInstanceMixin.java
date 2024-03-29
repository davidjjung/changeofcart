package com.davigj.change_of_cart.core.mixin;

import com.davigj.change_of_cart.core.CCConfig;
import com.davigj.change_of_cart.core.ChangeOfCart;
import com.davigj.change_of_cart.core.other.CCBlockTags;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.client.resources.sounds.RidingMinecartSoundInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RidingMinecartSoundInstance.class)
public class RidingMinecartSoundInstanceMixin {
    @Shadow
    private final AbstractMinecart minecart;

    public RidingMinecartSoundInstanceMixin(AbstractMinecart minecart) {
        this.minecart = minecart;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clampedLerp(FFF)F", shift = At.Shift.AFTER))
    private void injectAfterClampedLerp(CallbackInfo info) {
        RidingMinecartSoundInstance sound = (RidingMinecartSoundInstance) (Object) this;
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        boolean waxMuffle = manager.getValue(minecart, ChangeOfCart.WAXED) && CCConfig.COMMON.waxingMuffles.get();
        boolean woolMuffle = minecart.level.getBlockState(minecart.blockPosition().below()).is(CCBlockTags.MINECART_MUFFLERS) && CCConfig.COMMON.mufflingBlocks.get();
        if (waxMuffle || woolMuffle) {
            sound.volume = Mth.clampedLerp(0.0F, ((float) Mth.clamp(CCConfig.COMMON.maxMuffleVol.get().floatValue(), 0, 0.75)),
                    (float)this.minecart.getDeltaMovement().horizontalDistance());
        }
        if (CCConfig.COMMON.silenceStacks.get() && waxMuffle && woolMuffle) {
            sound.volume = Mth.clampedLerp(0.0F, ((float) Mth.clamp(0, 0, 0.0)),
                    (float)this.minecart.getDeltaMovement().horizontalDistance());
        }
    }
}
