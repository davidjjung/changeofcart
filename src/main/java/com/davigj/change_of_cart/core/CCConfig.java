package com.davigj.change_of_cart.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CCConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Double> maxWaxVol;
        public final ForgeConfigSpec.ConfigValue<Double> railBeddingBonus;
        public final ForgeConfigSpec.ConfigValue<Double> waxFrictionBonus;

        Common (ForgeConfigSpec.Builder builder) {
            builder.push("changes");
            maxWaxVol = builder.comment("Max volume of the waxed minecart, Usually max volume is 0.75").define("Maximum minecart volume", 0.15);
            railBeddingBonus = builder.comment("Reduced friction bonus for rail bedding").define("rail bedding bonus", 0.0015);
            waxFrictionBonus = builder.comment("Reduced friction bonus for waxed carts").define("waxy cart bonus", 0.0015);
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
