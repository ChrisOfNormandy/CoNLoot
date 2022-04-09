package com.github.chrisofnormandy.conloot.content.ui;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.itemgroup.CreativeTab;
import com.github.chrisofnormandy.conlib.registry.ItemRegistry;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

import net.minecraft.item.ItemGroup;

public class CustomItemGroup {
    public static HashMap<String, ItemGroup> cache = new HashMap<String, ItemGroup>();

    static {
        cache.put("items", ItemGroup.TAB_MISC);

        cache.put("blocks", ItemGroup.TAB_BUILDING_BLOCKS);

        cache.put("tools", ItemGroup.TAB_TOOLS);

        cache.put("weapons", ItemGroup.TAB_COMBAT);

        cache.put("armour", ItemGroup.TAB_COMBAT);

        cache.put("decorations", ItemGroup.TAB_DECORATIONS);

        cache.put("misc", ItemGroup.TAB_MISC);

        cache.put("food", ItemGroup.TAB_FOOD);
    }

    public static void registerFromConfig(String name, Config config) {
        Main.LOG.info("Generating new creative tab:" + name);

        String itemName = config.getStringValue("item_name");
        String tabName = config.getStringValue("tab_name");

        AssetPackBuilder.createBlockItem(itemName);

        cache.put(name, CreativeTab.createGroup(tabName, ItemRegistry.register(itemName)));

        AssetPackBuilder.Lang.addGroup(name, StringUtil.wordCaps_repl(tabName));
    }
}
