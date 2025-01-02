package com.davigj.change_of_cart.core.mixin;

import com.davigj.change_of_cart.core.CCConfig;
import com.davigj.change_of_cart.core.ChangeOfCart;
import com.davigj.change_of_cart.core.other.CCBlockTags;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.client.resources.sounds.RidingMinecartSoundInstance;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RidingMinecartSoundInstance.class)
public class RidingMinecartSoundInstanceMixin {
    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clampedLerp(FFF)F"), index = 1)
    private float modifyClampedLerp(float p_144921_) {
        AbstractMinecart minecart = ((IRidingMinecartSoundInstanceAccessor) this).getMinecart();
        boolean waxMuffle = TrackedDataManager.INSTANCE.getValue(minecart, ChangeOfCart.WAXED) && CCConfig.CLIENT.waxingMuffles.get();
        boolean woolMuffle = minecart.level().getBlockState(minecart.blockPosition().below()).is(CCBlockTags.MINECART_MUFFLERS) && CCConfig.CLIENT.mufflingBlocks.get();
        if (CCConfig.CLIENT.silenceStacks.get() && waxMuffle && woolMuffle) {
            return 0.0F;
        }
        return waxMuffle || woolMuffle ? (float) (CCConfig.CLIENT.riderQuietMuffleMultiplier.get() * CCConfig.CLIENT.maxCartVolume.get()
                * (1 - CCConfig.CLIENT.muffleReductionPercent.get())) : (float) (p_144921_ * CCConfig.CLIENT.riderQuietMultiplier.get());
    }
}
