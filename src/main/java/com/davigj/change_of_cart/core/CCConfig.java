package com.davigj.change_of_cart.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CCConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Boolean> waxingMuffles;
        public final ForgeConfigSpec.ConfigValue<Boolean> mufflingBlocks;
        public final ForgeConfigSpec.ConfigValue<Double> maxMuffleVol;
        public final ForgeConfigSpec.ConfigValue<Integer> railBeddingBonus;
        public final ForgeConfigSpec.ConfigValue<Integer> waxFrictionBonus;

        Common (ForgeConfigSpec.Builder builder) {
            builder.push("changes");
            waxingMuffles = builder.comment("Whether waxing a cart muffles it or not").define("Waxing muffles", true);
            mufflingBlocks = builder.comment("Whether carts are muffled by riding on the minecart_mufflers blocktag").define("Blocks muffle", true);
            maxMuffleVol = builder.comment("Max volume of a muffled minecart. Clamped between 0.0 and 0.75").define("Maximum minecart volume", 0.15);
            railBeddingBonus = builder.comment("Reduced friction bonus for rail bedding, integer from 1 to 10.").define("rail bedding bonus", 6);
            waxFrictionBonus = builder.comment("Reduced friction bonus for waxed carts, integer from 1 to 10.").define("waxy cart bonus", 0);
            builder.pop();
        }
    }

    static final ForgeConfigSpec COMMON_SPEC;
    public static final CCConfig.Common COMMON;


    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CCConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
