package com.github.chrisofnormandy.conloot;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.registry.Items;
import com.github.chrisofnormandy.conlib.registry.Tools;
import com.github.chrisofnormandy.conlib.tool.ToolMaterial;
import com.github.chrisofnormandy.conlib.tool.ToolMaterial.type;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.ToolType;

public class ModItems {
    public static JsonBuilder jsonBuilder = new JsonBuilder();

    private static void plainItemSetup(String name, Config config, ItemGroup itemGroup) {
        Items.register(name, new Item.Properties(), itemGroup);
    }

    private static void pickaxeSetup(String name, Config config, ItemGroup toolGroup) {
        Tools.register(name, new ToolMaterial(1, 100, false, Rarity.COMMON, false, type.material), ToolType.PICKAXE, 1,
                toolGroup);
    }

    private static void axeSetup(String name, Config config, ItemGroup toolGroup) {
        Tools.register(name, new ToolMaterial(1, 100, false, Rarity.COMMON, false, type.material), ToolType.AXE, 1,
                toolGroup);
    }

    private static void shovelSetup(String name, Config config, ItemGroup toolGroup) {
        Tools.register(name, new ToolMaterial(1, 100, false, Rarity.COMMON, false, type.material), ToolType.SHOVEL, 1,
                toolGroup);
    }

    private static void hoeSetup(String name, Config config, ItemGroup toolGroup) {
        Tools.register(name, new ToolMaterial(1, 100, false, Rarity.COMMON, false, type.material), ToolType.HOE, 1,
                toolGroup);
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
                            map.forEach((String name, Config config) -> pickaxeSetup(name, config, ModGroups.cache.get("tools")));
                            break;
                        }
                        case "axe": {
                            map.forEach(
                                    (String name, Config config) -> axeSetup(name, config, ModGroups.cache.get("tools")));
                            break;
                        }
                        case "shovel": {
                            map.forEach(
                                    (String name, Config config) -> shovelSetup(name, config, ModGroups.cache.get("tools")));
                            break;
                        }
                        case "hoe": {
                            map.forEach(
                                    (String name, Config config) -> hoeSetup(name, config, ModGroups.cache.get("tools")));
                            break;
                        }
                    }
                    break;
                }
                default: {
                    map.forEach((String name, Config config) -> plainItemSetup(name, config, ModGroups.cache.get("items")));
                    break;
                }
            }
        });
    }
}
