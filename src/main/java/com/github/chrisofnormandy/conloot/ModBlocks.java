package com.github.chrisofnormandy.conloot;

import java.util.HashMap;

import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;
import com.github.chrisofnormandy.conloot.content.CustomCrop;
import com.github.chrisofnormandy.conloot.content.CustomResource;
import com.github.chrisofnormandy.conloot.content.blocks.CreationBase;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder;

public class ModBlocks {
    public static JsonBuilder jsonBuilder = new JsonBuilder();

    private static void registerBlock(String name, Config config) {
        CreationBase.registerBlockFromConfig(name, config, ModGroups.cache.get("blocks"));
    }

    private static void registerSlab(String name, Config config) {
        CreationBase.registerBlockFromConfig(name, config, ModGroups.cache.get("blocks"));
        DataPackBuilder.Tags.addSlab(name);
    }

    private static void registerStairs(String name, Config config) {
        CreationBase.registerBlockFromConfig(name, config, ModGroups.cache.get("blocks"));
        DataPackBuilder.Tags.addStairs(name);
    }

    private static void registerWall(String name, Config config) {
        CreationBase.registerBlockFromConfig(name, config, ModGroups.cache.get("blocks"));
        DataPackBuilder.Tags.addWall(name);
    }

    private static void registerFence(String name, Config config) {
        CreationBase.registerBlockFromConfig(name, config, ModGroups.cache.get("blocks"));
        DataPackBuilder.Tags.addFence(name);
    }

    private static void registerFenceGate(String name, Config config) {
        CreationBase.registerBlockFromConfig(name, config, ModGroups.cache.get("blocks"));
        DataPackBuilder.Tags.addFenceGate(name);
    }

    private static void registerDoor(String name, Config config) {
        CreationBase.registerBlockFromConfig(name, config, ModGroups.cache.get("blocks"));
        DataPackBuilder.Tags.addDoor(name);
    }

    private static void registerTrapdoor(String name, Config config) {
        CreationBase.registerBlockFromConfig(name, config, ModGroups.cache.get("blocks"));
        DataPackBuilder.Tags.addTrapdoor(name);
    }

    private static void genericBlockSetup(String type, HashMap<String, Config> map) {
        if (map.isEmpty())
            return;

        switch (type) {
            case "block": {
                map.forEach((String name, Config config) -> registerBlock(name, config));
                break;
            }
            case "slab": {
                map.forEach((String name, Config config) -> registerSlab(name, config));
                break;
            }
            case "stairs": {
                map.forEach((String name, Config config) -> registerStairs(name, config));
                break;
            }
            case "wall": {
                map.forEach((String name, Config config) -> registerWall(name, config));
                break;
            }
            case "fence": {
                map.forEach((String name, Config config) -> registerFence(name, config));
                break;
            }
        }
    }

    private static void redstoneBlockSetup(String type, HashMap<String, Config> map) {
        if (map.isEmpty())
            return;

        switch (type) {
            case "fence_gate": {
                map.forEach((String name, Config config) -> registerFenceGate(name, config));
                break;
            }
            case "door": {
                map.forEach((String name, Config config) -> registerDoor(name, config));
                break;
            }
            case "trapdoor": {
                map.forEach((String name, Config config) -> registerTrapdoor(name, config));
                break;
            }
        }
    }

    private static void interactiveBlockSetup(String type, HashMap<String, Config> map) {
        if (map.size() == 0)
            return;
    }

    private static void interactiveBlockSetup(String type, String subtype, HashMap<String, Config> map) {
        if (map.isEmpty())
            return;

        switch (type) {
            case "storage": {
                switch (subtype) {
                    case "barrel": {
                    }
                    case "shulker": {
                        map.forEach((String name, Config config) -> CreationBase.registerBlockFromConfig(name, config,
                                ModGroups.cache.get("decorations")));
                        break;
                    }
                }
                break;
            }
        }
    }

    private static void plantResourceSetup(String type, HashMap<String, Config> map) {
        if (map.isEmpty())
            return;

        switch (type) {
            case "crop": {
                map.forEach((String name, Config config) -> CustomCrop.registerFromConfig(name, config,
                        ModGroups.cache.get("decorations")));
                break;
            }
        }
    }

    private static void oreResourceSetup(String type, HashMap<String, Config> map) {
        if (map.isEmpty())
            return;

        switch (type) {
            case "metal": {
            }
            case "gem": {
                map.forEach((String name, Config config) -> CustomResource.registerFromConfig(name, config,
                        ModGroups.cache.get("items"), ModGroups.cache.get("tools"), ModGroups.cache.get("blocks")));
                break;
            }
        }
    }

    public static void Init() {
        Main.config.blockContent.forEach((String key, HashMap<String, Config> map) -> {
            if (map.isEmpty()) {
                Main.LOG.debug("Block map " + key + " is empty.");
                Main.LOG.debug(map.size());
                return;
            }

            Main.LOG.debug("BlockContent: " + key + " -> " + map.size());

            String[] keyPath = key.split("\\.");

            Main.LOG.debug("Block content -> " + key + " | " + keyPath.length);

            switch (keyPath[0]) {
                case "block": {
                    Main.LOG.debug("Running setup for: " + keyPath[1]);

                    switch (keyPath[1]) {
                        case "generic": {
                            if (keyPath.length > 2)
                                genericBlockSetup(keyPath[2], map);
                            else
                                Main.LOG.error("Failed to find collection to register: " + key);

                            break;
                        }
                        case "redstone": {
                            if (keyPath.length > 2)
                                redstoneBlockSetup(keyPath[2], map);
                            else
                                Main.LOG.error("Failed to find collection to register: " + key);

                            break;
                        }
                        case "interactive": {
                            if (keyPath.length > 2) {
                                if (keyPath.length > 3)
                                    interactiveBlockSetup(keyPath[2], keyPath[3], map);
                                else
                                    interactiveBlockSetup(keyPath[2], map);
                            } else
                                Main.LOG.error("Failed to find collection to register: " + key);

                            break;
                        }
                    }

                    break;
                }
                case "resource": {
                    switch (keyPath[1]) {
                        case "plant": {
                            if (keyPath.length > 2)
                                plantResourceSetup(keyPath[2], map);
                            else
                                Main.LOG.error("Failed to find collection to register: " + key);

                            break;
                        }
                        case "ore": {
                            if (keyPath.length > 2)
                                oreResourceSetup(keyPath[2], map);
                            else
                                Main.LOG.error("Failed to find collection to register: " + key);

                            break;
                        }
                    }

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