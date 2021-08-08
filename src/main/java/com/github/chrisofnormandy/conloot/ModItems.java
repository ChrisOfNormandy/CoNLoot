package com.github.chrisofnormandy.conloot;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.registry.Items;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;
import com.github.chrisofnormandy.conloot.content.items.CreationBase;
import com.github.chrisofnormandy.conloot.content.ui.CustomItemGroup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ModItems {
    public static JsonBuilder jsonBuilder = new JsonBuilder();

    private static void plainItemSetup(String name, Config config, ItemGroup itemGroup) {
        Items.register(name, new Item.Properties(), itemGroup);
        CreationBase.registerItemFromConfig(name, config);
    }

    private static void pickaxeSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerPickaxe(name, config, toolGroup);
    }

    private static void axeSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerAxe(name, config, toolGroup);
    }

    private static void shovelSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerShovel(name, config, toolGroup);
    }

    private static void hoeSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerHoe(name, config, toolGroup);
    }

    public static void Init() {
        Main.config.itemContent.forEach((String key, HashMap<String, Config> map) -> {
            if (map.isEmpty()) {
                Main.LOG.debug("Item map " + key + " is empty.");
                Main.LOG.debug(map.size());
                return;
            }

            Main.LOG.debug("ItemContent: " + key + " -> " + map.size());

            String[] keyPath = key.split("\\.");

            Main.LOG.debug("Item content -> " + key + " | " + keyPath.length);

            switch (keyPath[0]) {
                case "tool": {
                    switch (keyPath[1]) {
                        case "pickaxe": {
                            map.forEach((String name, Config config) -> pickaxeSetup(name, config,
                                    CustomItemGroup.cache.get("tools")));
                            break;
                        }
                        case "axe": {
                            map.forEach((String name, Config config) -> axeSetup(name, config,
                                    CustomItemGroup.cache.get("tools")));
                            break;
                        }
                        case "shovel": {
                            map.forEach((String name, Config config) -> shovelSetup(name, config,
                                    CustomItemGroup.cache.get("tools")));
                            break;
                        }
                        case "hoe": {
                            map.forEach((String name, Config config) -> hoeSetup(name, config,
                                    CustomItemGroup.cache.get("tools")));
                            break;
                        }
                    }
                    break;
                }
                default: {
                    map.forEach((String name, Config config) -> plainItemSetup(name, config,
                            CustomItemGroup.cache.get("items")));
                    break;
                }
            }
        });

        Main.LOG.debug("Writing lang file.");
        AssetPackBuilder.Lang.write();

        Main.LOG.debug("Writing tag files.");
        DataPackBuilder.Tags.write();
    }
}
