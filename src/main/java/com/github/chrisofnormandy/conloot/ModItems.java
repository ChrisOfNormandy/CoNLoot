package com.github.chrisofnormandy.conloot;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;
import com.github.chrisofnormandy.conloot.content.items.CreationBase;
import com.github.chrisofnormandy.conloot.content.ui.CustomItemGroup;

public class ModItems {
    public static JsonBuilder jsonBuilder = new JsonBuilder();

    public static void Init() {
        Main.config.itemContent.forEach((String key, HashMap<String, Config> map) -> {
            if (map.isEmpty())
                return;

            String[] keyPath = key.split("\\.");

            Main.LOG.debug("Item content -> " + key + " | " + keyPath.length);

            switch (keyPath[0]) {
                case "tool": {
                    switch (keyPath[1]) {
                        case "pickaxe": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerPickaxe(name, config, CustomItemGroup.cache.get("tools"));
                            });
                            break;
                        }
                        case "axe": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerAxe(name, config, CustomItemGroup.cache.get("tools"));
                            });
                            break;
                        }
                        case "shovel": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerShovel(name, config, CustomItemGroup.cache.get("tools"));
                            });
                            break;
                        }
                        case "hoe": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerHoe(name, config, CustomItemGroup.cache.get("tools"));
                            });
                            break;
                        }
                        case "flint_and_steel": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerFlintAndSteel(name, config, CustomItemGroup.cache.get("tools"));
                            });
                            break;
                        }
                        case "fishing_rod": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerFishingRod(name, config, CustomItemGroup.cache.get("tools"));
                            });
                            break;
                        }
                        case "bucket": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerBucket(name, config, CustomItemGroup.cache.get("misc"));
                            });
                            break;
                        }
                        case "shears": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerShears(name, config, CustomItemGroup.cache.get("tools"));
                            });
                            break;
                        }
                    }
                    break;
                }
                case "weapon": {
                    switch (keyPath[1]) {
                        case "sword": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerSword(name, config, CustomItemGroup.cache.get("weapons"));
                            });
                            break;
                        }
                        case "shield": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerShield(name, config, CustomItemGroup.cache.get("weapons"));
                            });
                            break;
                        }
                        case "bow": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerBow(name, config, CustomItemGroup.cache.get("weapons"));
                            });
                            break;
                        }
                        case "crossbow": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerCrossbow(name, config, CustomItemGroup.cache.get("weapons"));
                            });
                            break;
                        }
                        case "arrow": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerArrow(name, config, CustomItemGroup.cache.get("weapons"));
                                DataPackBuilder.Tags.addArrow(name);
                            });
                            break;
                        }
                    }
                    break;
                }
                case "wearable": {
                    switch (keyPath[1]) {
                        case "head": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerHelmet(name, config, CustomItemGroup.cache.get("armour"));
                            });
                            break;
                        }
                        case "chest": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerChestplate(name, config, CustomItemGroup.cache.get("armour"));
                            });
                            break;
                        }
                        case "legs": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerLeggings(name, config, CustomItemGroup.cache.get("armour"));
                            });
                            break;
                        }
                        case "feet": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerBoots(name, config, CustomItemGroup.cache.get("armour"));
                            });
                            break;
                        }
                    }
                    break;
                }
                case "consumable": {
                    switch (keyPath[1]) {
                        case "food": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerFood(name, config, CustomItemGroup.cache.get("food"));
                            });
                            break;
                        }
                    }
                }
                case "generic": {
                    switch (keyPath[1]) {
                        case "all": {
                            map.forEach((String name, Config config) -> {
                                CreationBase.registerGeneric(name, config, CustomItemGroup.cache.get("items"));
                            });
                            break;
                        }
                    }
                }
                default: {
                    // map.forEach((String name, Config config) -> plainItemSetup(name, config,
                    // CustomItemGroup.cache.get("items")));
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
