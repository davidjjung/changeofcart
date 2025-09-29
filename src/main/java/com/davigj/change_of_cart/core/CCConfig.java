package com.davigj.change_of_cart.core;


import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CCConfig {
    public static class Common {
        public final ModConfigSpec.ConfigValue<Integer> railBeddingBonus;
        public final ModConfigSpec.ConfigValue<Integer> waxFrictionBonus;

        Common (ModConfigSpec.Builder builder) {
            builder.push("common");
            railBeddingBonus = builder.comment("Reduced friction bonus for rail bedding, 1 to 10. 0 disables the bonus").defineInRange("rail bedding bonus", 8, 0, 10);
            waxFrictionBonus = builder.comment("Reduced friction bonus for waxed carts, 1 to 10. 0 disables the bonus").defineInRange("waxy cart bonus", 0, 0, 10);
            builder.pop();
        }
    }

    public static class Client {
        public final ModConfigSpec.ConfigValue<Boolean> waxingMuffles;
        public final ModConfigSpec.ConfigValue<Boolean> mufflingBlocks;
        public final ModConfigSpec.ConfigValue<Double> muffleReductionPercent;
        public final ModConfigSpec.ConfigValue<Double> maxCartVolume;
        public final ModConfigSpec.ConfigValue<Boolean> silenceStacks;
        public final ModConfigSpec.ConfigValue<Double> riderQuietMuffleMultiplier;
        public final ModConfigSpec.ConfigValue<Double> riderQuietMultiplier;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("client");
            waxingMuffles = builder.comment("Whether waxing a cart muffles it or not").define("Waxing muffles", true);
            mufflingBlocks = builder.comment("Whether carts are muffled by riding on the minecart_mufflers blocktag").define("Blocks muffle", true);
            muffleReductionPercent = builder.comment("Percent reduction of muffled minecart sound. Higher the number, quieter the muffled cart").defineInRange("Muffled minecart sound reduction percentage", 0.8, 0, 1);
            maxCartVolume = builder.comment("How loud minecarts can be in general, ridden or not").defineInRange("Max minecart volume", 0.75, 0, 0.75);
            silenceStacks = builder.comment("Waxed carts on minecart_mufflers are completely silent").define("Muffling properties stack", false);
            riderQuietMuffleMultiplier = builder.comment("In vanilla, minecarts are *much* louder for the rider. This noise multiplier counters this when the cart is muffled. Disabled by default, as a multiplier of 1 does nothing.").defineInRange("Rider quiet muffle multiplier", 1.0, 0, 1);
            riderQuietMultiplier = builder.comment("Alternatively, counter loudness for riders in general; makes for smoother transitions.").defineInRange("Rider quiet multiplier", 0.8, 0, 1);
            builder.pop();
        }
    }


    static final ModConfigSpec COMMON_SPEC;
    public static final CCConfig.Common COMMON;

    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(CCConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();

        Pair<Client, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
