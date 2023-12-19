package com.davigj.change_of_cart.core.other;

import com.davigj.change_of_cart.core.ChangeOfCart;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CCBlockTags {
    public static final TagKey<Block> MINECART_MUFFLERS = blockTag("minecart_mufflers");
    public static final TagKey<Block> RAIL_BEDDING = blockTag("rail_bedding");

    private static TagKey<Block> blockTag(String name) {
        return TagUtil.blockTag(ChangeOfCart.MOD_ID, name);
    }
}
