package com.davigj.change_of_cart.core.other;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CCItemTags {
    public static final TagKey<Item> WAX_INDICATORS = itemTag("wax_indicators");
    private static TagKey<Item> itemTag(String name) {
        return TagUtil.itemTag("copperative", name);
    }
}
