package com.davigj.change_of_cart.core.other;

import com.davigj.change_of_cart.core.ChangeOfCart;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChangeOfCart.MOD_ID)
public class CCEvents {
    static TrackedDataManager manager = TrackedDataManager.INSTANCE;

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onWaxingCart(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        if (event.getTarget() instanceof AbstractMinecart cart && player != null && player.isCrouching()) {
            if (player.getItemInHand(event.getHand()).is(Items.HONEYCOMB)) {
                if (!manager.getValue(cart, ChangeOfCart.WAXED)) {
                    event.setCancellationResult(InteractionResult.CONSUME);
                    event.setCanceled(true);
                    manager.setValue(cart, ChangeOfCart.WAXED, true);
                    player.swing(event.getHand());
                    if (!player.getAbilities().instabuild) {
                        player.getItemInHand(event.getHand()).shrink(1);
                    }
                    RandomSource random = cart.level.getRandom();
                    if (player.level.isClientSide) {
                        player.level.playSound(player, cart.blockPosition(), SoundEvents.HONEYCOMB_WAX_ON, SoundSource.NEUTRAL, 1.0F, 0.8F);
                        for (int i = 0; i < 4; i++) {
                            cart.level.addParticle(ParticleTypes.WAX_ON, cart.getX() + random.nextDouble() - 0.5,
                                    cart.getEyeY() + random.nextDouble(), cart.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                        }
                    }
                }
            } else if (player.getItemInHand(event.getHand()).getItem() instanceof AxeItem) {
                if (manager.getValue(cart, ChangeOfCart.WAXED)) {
                    event.setCancellationResult(InteractionResult.CONSUME);
                    event.setCanceled(true);
                    manager.setValue(cart, ChangeOfCart.WAXED, false);
                    player.swing(event.getHand());
                    if (!player.getAbilities().instabuild) {
                        player.getItemInHand(event.getHand()).hurtAndBreak(1, player, (entity) -> {
                            entity.broadcastBreakEvent(player.getItemInHand(event.getHand()).isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND);
                        });
                    }
                    RandomSource random = cart.level.getRandom();
                    if (player.level.isClientSide) {
                        player.level.playSound(player, cart.blockPosition(), SoundEvents.AXE_WAX_OFF, SoundSource.NEUTRAL, 1.0F, 0.8F);
                        for (int i = 0; i < 4; i++) {
                            cart.level.addParticle(ParticleTypes.WAX_OFF, cart.getX() + random.nextDouble() - 0.5,
                                    cart.getEyeY() + random.nextDouble(), cart.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                        }
                    }
                }
            }
        }
    }
}