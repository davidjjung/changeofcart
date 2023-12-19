package com.davigj.change_of_cart.core.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(method = "startRiding", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/sounds/SoundManager;play(Lnet/minecraft/client/resources/sounds/SoundInstance;)V"), cancellable = true)
    private void forgiveMe(Entity entity, boolean momComePickMeUp, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
