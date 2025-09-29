package com.davigj.change_of_cart.core;

import com.davigj.change_of_cart.core.other.CCCompat;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(ChangeOfCart.MOD_ID)
public class ChangeOfCart {
    public static final String MOD_ID = "change_of_cart";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

    public static final TrackedData<Boolean> WAXED = TrackedData.Builder.create(ByteBufCodecs.BOOL, () -> false).build();

    public ChangeOfCart(IEventBus bus, ModContainer container) {

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);

        container.registerConfig(ModConfig.Type.COMMON, CCConfig.COMMON_SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, CCConfig.CLIENT_SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        TrackedDataManager.INSTANCE.registerData(ChangeOfCart.location("waxed"), WAXED);
        event.enqueueWork(() -> {
            event.enqueueWork(CCCompat::registerCompat);
        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    private void dataSetup(GatherDataEvent event) {

    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}