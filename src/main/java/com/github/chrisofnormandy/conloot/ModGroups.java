package com.github.chrisofnormandy.conloot;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.itemgroup.CreativeTab;
import com.github.chrisofnormandy.conlib.registry.Items;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

import net.minecraft.item.ItemGroup;

public class ModGroups {
        public static HashMap<String, ItemGroup> cache = new HashMap<String, ItemGroup>();

        public static void Init() {
                AssetPackBuilder.createItem("item_group");
                AssetPackBuilder.createItem("block_group");
                AssetPackBuilder.createItem("tool_group");
                AssetPackBuilder.createItem("decoration_group");

                cache.put("test", CreativeTab.createGroup("item_group", Items.register("item_group_icon")));

                cache.put("blocks", CreativeTab.createGroup("block_group", Items.register("block_group_icon")));

                cache.put("tools", CreativeTab.createGroup("tool_group", Items.register("tool_group_icon")));

                cache.put("decorations",
                                CreativeTab.createGroup("decoration_group", Items.register("decore_group_icon")));

                cache.put("items", ItemGroup.TAB_MISC);

                cache.put("blocks", ItemGroup.TAB_BUILDING_BLOCKS);

                cache.put("tools", ItemGroup.TAB_TOOLS);

                cache.put("decorations", ItemGroup.TAB_DECORATIONS);
        }
}
