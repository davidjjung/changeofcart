package com.davigj.change_of_cart.core.other;

import com.davigj.change_of_cart.common.dispenser.HoneycombDispenseBehavior;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;

public class CCCompat {
    public static void registerCompat() {
        registerDispenserBehaviors();
    }
    private static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(Items.HONEYCOMB, new HoneycombDispenseBehavior());
    }
}
