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

    private static void flintAndSteelSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerFlintAndSteel(name, config, toolGroup);
    }

    private static void fishingRodSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerFishingRod(name, config, toolGroup);
    }

    private static void shearsSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerShears(name, config, toolGroup);
    }

    private static void swordSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerSword(name, config, toolGroup);
    }

    private static void shieldSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerShield(name, config, toolGroup);
    }

    private static void bowSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerBow(name, config, toolGroup);
    }

    private static void crossbowSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerCrossbow(name, config, toolGroup);
    }

    private static void arrowSetup(String name, Config config, ItemGroup toolGroup) {
        CreationBase.registerArrow(name, config, toolGroup);
        DataPackBuilder.Tags.addArrow(name);
    }

    private static void helmetSetup(String name, Config config, ItemGroup armourGroup) {
        CreationBase.registerHelmet(name, config, armourGroup);
    }

    private static void chestplateSetup(String name, Config config, ItemGroup armourGroup) {
        CreationBase.registerChestplate(name, config, armourGroup);
    }

    private static void leggingsSetup(String name, Config config, ItemGroup armourGroup) {
        CreationBase.registerLeggings(name, config, armourGroup);
    }

    private static void bootsSetup(String name, Config config, ItemGroup armourGroup) {
        CreationBase.registerBoots(name, config, armourGroup);
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
                        case "flint_and_steel": {
                            map.forEach((String name, Config config) -> flintAndSteelSetup(name, config,
                                    CustomItemGroup.cache.get("tools")));
                            break;
                        }
                        case "fishing_rod": {
                            map.forEach((String name, Config config) -> fishingRodSetup(name, config,
                                    CustomItemGroup.cache.get("tools")));
                            break;
                        }
                        case "shears": {
                            map.forEach((String name, Config config) -> shearsSetup(name, config,
                                    CustomItemGroup.cache.get("tools")));
                            break;
                        }
                    }
                    break;
                }
                case "weapon": {
                    switch (keyPath[1]) {
                        case "sword": {
                            map.forEach((String name, Config config) -> swordSetup(name, config,
                                    CustomItemGroup.cache.get("weapons")));
                            break;
                        }
                        case "shield": {
                            map.forEach((String name, Config config) -> shieldSetup(name, config,
                                    CustomItemGroup.cache.get("weapons")));
                            break;
                        }
                        case "bow": {
                            map.forEach((String name, Config config) -> bowSetup(name, config,
                                    CustomItemGroup.cache.get("weapons")));
                            break;
                        }
                        case "crossbow": {
                            map.forEach((String name, Config config) -> crossbowSetup(name, config,
                                    CustomItemGroup.cache.get("weapons")));
                            break;
                        }
                        case "arrow": {
                            map.forEach((String name, Config config) -> arrowSetup(name, config,
                                    CustomItemGroup.cache.get("weapons")));
                            break;
                        }
                    }
                    break;
                }
                case "wearable": {
                    switch (keyPath[1]) {
                        case "head": {
                            map.forEach((String name, Config config) -> helmetSetup(name, config,
                                    CustomItemGroup.cache.get("armour")));
                            break;
                        }
                        case "chest": {
                            map.forEach((String name, Config config) -> chestplateSetup(name, config,
                                    CustomItemGroup.cache.get("armour")));
                            break;
                        }
                        case "legs": {
                            map.forEach((String name, Config config) -> leggingsSetup(name, config,
                                    CustomItemGroup.cache.get("armour")));
                            break;
                        }
                        case "feet": {
                            map.forEach((String name, Config config) -> bootsSetup(name, config,
                                    CustomItemGroup.cache.get("armour")));
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
