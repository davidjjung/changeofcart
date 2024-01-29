package com.davigj.change_of_cart.core.mixin;

import com.davigj.change_of_cart.core.CCConfig;
import com.davigj.change_of_cart.core.ChangeOfCart;
import com.davigj.change_of_cart.core.other.CCBlockTags;
import com.davigj.change_of_cart.core.other.CCItemTags;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecart.class)
public class AbstractMinecartMixin {

    @Inject(method = "applyNaturalSlowdown", at = @At("HEAD"), cancellable = true)
    private void applyNaturalSlowdown(CallbackInfo ci) {
        AbstractMinecart cart = (AbstractMinecart) (Object) this;
        double frictionBonus = 0;
        if (cart.level().getBlockState(cart.blockPosition().below()).is(CCBlockTags.RAIL_BEDDING)) {
            frictionBonus += Mth.clamp(CCConfig.COMMON.railBeddingBonus.get() , 0, 10) * 0.1 * .003;
        }
        if (TrackedDataManager.INSTANCE.getValue(cart, ChangeOfCart.WAXED)) {
            frictionBonus += Mth.clamp(CCConfig.COMMON.railBeddingBonus.get() , 0, 10) * 0.1 * .003;
        }
        if (frictionBonus != 0) {
            frictionBonus = Mth.clamp(frictionBonus, 0, 0.003);
            double d0 = cart.isVehicle() ? 0.997D + frictionBonus : 0.96D + (frictionBonus * 10);
            Vec3 vec3 = cart.getDeltaMovement();
            vec3 = vec3.multiply(d0, 0.0D, d0);
            if (cart.isInWater()) {
                vec3 = vec3.scale((double)0.95F);
            }
            cart.setDeltaMovement(vec3);
            ci.cancel();
        }
    }

    // Copperative compat
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (ModList.get().isLoaded("copperative")) {
            AbstractMinecart cart = (AbstractMinecart) (Object) this;
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            if (player != null && (player.getMainHandItem().is(CCItemTags.WAX_INDICATORS) || player.getOffhandItem().is(CCItemTags.WAX_INDICATORS))) {
                TrackedDataManager manager = TrackedDataManager.INSTANCE;
                RandomSource random = cart.level().getRandom();
                if (cart.tickCount % 5 == 0 && player.level() instanceof ClientLevel && manager.getValue(cart, ChangeOfCart.WAXED)) {
                    cart.level().addParticle(ParticleTypes.WAX_ON, cart.getX() + random.nextDouble() - 0.5,
                            cart.getEyeY() + random.nextDouble() - 0.35, cart.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                }
            }
        }
    }

}
