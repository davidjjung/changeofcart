package com.davigj.change_of_cart.common.dispenser;

import com.davigj.change_of_cart.core.ChangeOfCart;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class HoneycombDispenseBehavior extends OptionalDispenseItemBehavior {
    protected @NotNull ItemStack execute(BlockSource p_123580_, @NotNull ItemStack stack) {
        ServerLevel level = p_123580_.getLevel();
        if (!level.isClientSide()) {
            BlockPos blockpos = p_123580_.getPos().relative(p_123580_.getBlockState().getValue(DispenserBlock.FACING));
            this.setSuccess(tryWaxCart(level, blockpos));
            if (this.isSuccess()) {
                stack.shrink(1);
            }
        }

        return stack;
    }

    private static boolean tryWaxCart(ServerLevel level, BlockPos pos) {
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        for(AbstractMinecart cart : level.getEntitiesOfClass(AbstractMinecart.class, new AABB(pos), EntitySelector.NO_SPECTATORS)) {
            if (!manager.getValue(cart, ChangeOfCart.WAXED)) {
                manager.setValue(cart, ChangeOfCart.WAXED, true);
                level.playSound((Player)null, cart.getX(), cart.getY(), cart.getZ(), SoundEvents.HONEYCOMB_WAX_ON, SoundSource.NEUTRAL, 1.0F, 0.8F);
                if (!level.isClientSide) {
                    for(int i = 0; i < 5; ++i) {
                        RandomSource random = level.getRandom();
                        level.sendParticles(ParticleTypes.WAX_ON, cart.getX() + random.nextDouble() - 0.5,
                                cart.getEyeY() + random.nextDouble(), cart.getZ() + random.nextDouble() - 0.5,
                                1,0.0D, 0.0D, 0.0D,1.0D);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
