package com.davigj.change_of_cart.core.mixin;

import com.davigj.change_of_cart.core.ChangeOfCart;
import com.davigj.change_of_cart.core.CCConfig;
import com.davigj.change_of_cart.core.other.CCBlockTags;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.client.resources.sounds.MinecartSoundInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecartSoundInstance.class)
public class MinecartSoundInstanceMixin {

    @Shadow
    public final AbstractMinecart minecart;

    public MinecartSoundInstanceMixin(AbstractMinecart minecart) {
        this.minecart = minecart;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void minecartSoundTick(CallbackInfo ci) {
        if (!minecart.isRemoved()) {
            MinecartSoundInstance sound = (MinecartSoundInstance) (Object) this;
            TrackedDataManager manager = TrackedDataManager.INSTANCE;
            boolean waxMuffle = manager.getValue(minecart, ChangeOfCart.WAXED) && CCConfig.COMMON.waxingMuffles.get();
            boolean woolMuffle = minecart.level.getBlockState(minecart.blockPosition().below()).is(CCBlockTags.MINECART_MUFFLERS) && CCConfig.COMMON.mufflingBlocks.get();
            if (waxMuffle || woolMuffle) {
                float f = (float)sound.minecart.getDeltaMovement().horizontalDistance();
                if (f >= 0.01F) {
                    float maxVolume = (float) Mth.clamp(CCConfig.COMMON.maxMuffleVol.get(), 0, 0.75);
                    sound.volume = Mth.lerp(Mth.clamp(f, 0.0F, 0.5F), 0.0F, maxVolume);
                }
            }
            if (CCConfig.COMMON.silenceStacks.get() && waxMuffle && woolMuffle) {
                sound.volume = 0.0F;
            }
        }
    }
}
