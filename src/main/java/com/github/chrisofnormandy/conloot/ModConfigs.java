package com.github.chrisofnormandy.conloot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conloot.configs.ConfigLoader;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;
import com.github.chrisofnormandy.conloot.configs.Generators;
import com.github.chrisofnormandy.conloot.configs.items.food.FoodConfig;
import com.github.chrisofnormandy.conloot.configs.items.tools.*;
import com.github.chrisofnormandy.conloot.configs.items.weapons.*;
import com.github.chrisofnormandy.conloot.configs.items.wearable.*;
import com.github.chrisofnormandy.conloot.configs.ui.ItemGroupConfig;
import com.github.chrisofnormandy.conloot.configs.worldgen.BiomeConfig;

public class ModConfigs {
    public final HashMap<String, Config> configs = new HashMap<String, Config>();

    public final HashMap<String, HashMap<String, Config>> uiContent = new HashMap<String, HashMap<String, Config>>();
    public final HashMap<String, HashMap<String, Config>> blockContent = new HashMap<String, HashMap<String, Config>>();
    public final HashMap<String, HashMap<String, Config>> itemContent = new HashMap<String, HashMap<String, Config>>();
    public final HashMap<String, HashMap<String, Config>> worldGenContent = new HashMap<String, HashMap<String, Config>>();

    public HashMap<String, Config> getBlockMap(String key) {
        if (!blockContent.containsKey(key))
            blockContent.put(key, new HashMap<String, Config>());

        return blockContent.get(key);
    }

    public HashMap<String, Config> getItemMap(String key) {
        if (!itemContent.containsKey(key))
            itemContent.put(key, new HashMap<String, Config>());

        return itemContent.get(key);
    }

    public static List<ConfigLoader> convertToLoaderList(List<String> list) {
        List<ConfigLoader> l = new ArrayList<ConfigLoader>();
        list.forEach((String s) -> l.add(new ConfigLoader(s)));
        return l;
    }

    public static List<ConfigLoader> convertToLoaderList(List<String> list, ConfigOptions options) {
        List<ConfigLoader> l = new ArrayList<ConfigLoader>();
        list.forEach((String s) -> l.add(new ConfigLoader(s, options)));
        return l;
    }

    private void generateGenericBlockMaps(ConfigGroup config) {
        Generators.Block.loadBlocks(getBlockMap("block.generic.block"), convertToLoaderList(config.getStringListValue("block_list")));
        Generators.Block.loadBlocks(getBlockMap("block.generic.slab"), convertToLoaderList(config.getStringListValue("slab_list")));
        Generators.Block.loadBlocks(getBlockMap("block.generic.stairs"), convertToLoaderList(config.getStringListValue("stairs_list")));
        Generators.Block.loadBlocks(getBlockMap("block.generic.wall"), convertToLoaderList(config.getStringListValue("wall_list")));
        Generators.Block.loadBlocks(getBlockMap("block.generic.block"), convertToLoaderList(config.getStringListValue("fence_list")));
    }

    private void generateRedstoneBlockMaps(ConfigGroup config) {
        Generators.Block.loadBlocks(getBlockMap("block.redstone.fence_gate"), convertToLoaderList(config.getStringListValue("fence_gate_list")));
        Generators.Block.loadBlocks(getBlockMap("block.redstone.door"), convertToLoaderList(config.getStringListValue("door_list")));
        Generators.Block.loadBlocks(getBlockMap("block.redstone.trapdoor"), convertToLoaderList(config.getStringListValue("trapdoor_list")));
        Generators.Block.loadBlocks(getBlockMap("block.redstone.pressure_plate"), convertToLoaderList(config.getStringListValue("pressure_plate_list")));
        Generators.Block.loadBlocks(getBlockMap("block.redstone.button"), convertToLoaderList(config.getStringListValue("button_list")));
    }

    private void generateStorageBlockMaps(ConfigGroup config) {
        Generators.Block.loadBlocks(getBlockMap("block.storage.chest"),
                convertToLoaderList(config.getStringListValue("chest_list"),
                        new ConfigOptions()
                                .Material("wood")
                                .Sound("wood")
                                .Model("chest")
                                .RenderModel("chest")
                                .Tool("axe")));

        Generators.Block.loadBlocks(getBlockMap("block.storage.barrel"),
                convertToLoaderList(config.getStringListValue("barrel_list"),
                        new ConfigOptions()
                                .Material("wood")
                                .Sound("wood")
                                .Model("barrel")
                                .RenderModel("bottom_top")
                                .Tool("axe")));

        Generators.Block.loadBlocks(getBlockMap("block.storage.shulker"), convertToLoaderList(config.getStringListValue("shulker_list")));
    }

    private void loadSuitesToBlockMaps(ConfigGroup config) {
        config.getStringListValue("wood_suite_list").forEach((String name) -> {

            // Log
            Generators.Block.loadBlock(getBlockMap("block.generic.block"),
                    new ConfigLoader(name + "_log",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .RenderModel("column")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_log_top")
                                    .Texture("minecraft:block/oak_log")));

            // Stripped Log
            Generators.Block.loadBlock(getBlockMap("block.generic.block"),
                    new ConfigLoader("stripped_" + name + "_log",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .RenderModel("column")
                                    .Tool("axe")
                                    .Texture("minecraft:block/stripped_oak_log_top")
                                    .Texture("minecraft:block/stripped_oak_log")));

            // Bark
            Generators.Block.loadBlock(getBlockMap("block.generic.block"),
                    new ConfigLoader(name + "_wood",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_log")));

            // Stripped Bark
            Generators.Block.loadBlock(getBlockMap("block.generic.block"),
                    new ConfigLoader("stripped_" + name + "_wood",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Tool("axe")
                                    .Texture("minecraft:block/stripped_oak_log")));

            // Planks
            Generators.Block.loadBlock(getBlockMap("block.generic.block"),
                    new ConfigLoader(name + "_planks",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_planks")));

            // Planks Slab
            Generators.Block.loadBlock(getBlockMap("block.generic.slab"),
                    new ConfigLoader(name + "_slab",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Model("slab")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_planks")));

            // Planks Stairs
            Generators.Block.loadBlock(getBlockMap("block.generic.stairs"),
                    new ConfigLoader(name + "_stairs",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Model("stairs")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_planks")));

            // Planks Fence
            Generators.Block.loadBlock(getBlockMap("block.generic.fence"),
                    new ConfigLoader(name + "_fence",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Model("fence")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_planks")));

            // Planks Fence Gate
            Generators.Block.loadBlock(getBlockMap("block.redstone.fence_gate"),
                    new ConfigLoader(name + "_fence_gate",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Model("fence_gate")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_planks")));

            // Planks Door
            Generators.Block.loadBlock(getBlockMap("block.redstone.door"),
                    new ConfigLoader(name + "_door",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Model("door")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_door_bottom")
                                    .Texture("minecraft:block/oak_door_top")));

            // Planks Trapdoor
            Generators.Block.loadBlock(getBlockMap("block.redstone.trapdoor"),
                    new ConfigLoader(name + "_trapdoor",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Model("trapdoor")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_trapdoor")));

            // Planks Pressure Plate
            Generators.Block.loadBlock(getBlockMap("block.redstone.pressure_plate"),
                    new ConfigLoader(name + "_pressure_plate",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Model("pressure_plate")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_planks")));

            // Planks Button
            Generators.Block.loadBlock(getBlockMap("block.redstone.button"),
                    new ConfigLoader(name + "_button",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Model("button")
                                    .Tool("axe")
                                    .Texture("minecraft:block/oak_planks")));

            // Planks Chest
            Generators.Block.loadBlock(getBlockMap("block.storage.chest"),
                    new ConfigLoader(name + "_chest",
                            new ConfigOptions()
                                    .Material("wood")
                                    .Sound("wood")
                                    .Model("chest")
                                    .Tool("axe")));
        });

        config.getStringListValue("stone_suite_list").forEach((String name) -> {
            // Stone
            Generators.Block.loadBlock(getBlockMap("block.generic.block"),
                    new ConfigLoader(name,
                            new ConfigOptions()
                                    .Texture("minecraft:block/stone")));

            // Cobbled Stone
            Generators.Block.loadBlock(getBlockMap("block.generic.block"),
                    new ConfigLoader("cobbled_" + name,
                            new ConfigOptions()
                                    .Texture("minecraft:block/cobblestone")));

            // Bricks
            Generators.Block.loadBlock(getBlockMap("block.generic.block"),
                    new ConfigLoader(name + "_bricks",
                            new ConfigOptions()
                                    .Texture("minecraft:block/stone_bricks")));

            // Polished Stone
            Generators.Block.loadBlock(getBlockMap("block.generic.block"),
                    new ConfigLoader("polished_" + name,
                            new ConfigOptions()
                                    .Texture("minecraft:block/smooth_stone")));

            // Stone Slab
            Generators.Block.loadBlock(getBlockMap("block.generic.slab"),
                    new ConfigLoader(name + "_slab",
                            new ConfigOptions()
                                    .Model("slab")
                                    .Texture("minecraft:block/stone")
                                    .DoubleSlabTexture("minecraft:block/stone")));

            // Cobbled Stone Slab
            Generators.Block.loadBlock(getBlockMap("block.generic.slab"),
                    new ConfigLoader("cobbled_" + name + "_slab",
                            new ConfigOptions()
                                    .Model("slab")
                                    .Texture("minecraft:block/cobblestone")
                                    .DoubleSlabTexture("minecraft:block/cobblestone")));

            // Polished Stone Slab
            Generators.Block.loadBlock(getBlockMap("block.generic.slab"),
                    new ConfigLoader("polished_" + name + "_slab",
                            new ConfigOptions()
                                    .Model("slab")
                                    .Texture("minecraft:block/smooth_stone")
                                    .DoubleSlabTexture("minecraft:block/smooth_stone_slab_side")));

            // Stone Stairs
            Generators.Block.loadBlock(getBlockMap("block.generic.stairs"),
                    new ConfigLoader(name + "_stairs",
                            new ConfigOptions()
                                    .Model("stairs")
                                    .Texture("minecraft:block/stone")));

            // Cobbled Stone Stairs
            Generators.Block.loadBlock(getBlockMap("block.generic.stairs"),
                    new ConfigLoader("cobbled_" + name + "_stairs",
                            new ConfigOptions()
                                    .Model("stairs")
                                    .Texture("minecraft:block/cobblestone")));

            // Polished Stone Stairs
            Generators.Block.loadBlock(getBlockMap("block.generic.stairs"),
                    new ConfigLoader("polished_" + name + "_stairs",
                            new ConfigOptions()
                                    .Model("stairs")
                                    .Texture("minecraft:block/smooth_stone")));

            // Stone Wall
            Generators.Block.loadBlock(getBlockMap("block.generic.wall"),
                    new ConfigLoader(name + "_wall",
                            new ConfigOptions()
                                    .Model("wall")
                                    .Texture("minecraft:block/stone")));

            // Cobbled Stone Wall
            Generators.Block.loadBlock(getBlockMap("block.generic.wall"),
                    new ConfigLoader("cobbled_" + name + "_wall",
                            new ConfigOptions()
                                    .Model("wall")
                                    .Texture("minecraft:block/cobblestone")));

            // Polished Stone Wall
            Generators.Block.loadBlock(getBlockMap("block.generic.wall"),
                    new ConfigLoader("polished_" + name + "_wall",
                            new ConfigOptions()
                                    .Model("wall")
                                    .Texture("minecraft:block/smooth_stone")));

            // Stone Pressure Plate
            Generators.Block.loadBlock(getBlockMap("block.redstone.pressure_plate"),
                    new ConfigLoader(name + "_pressure_plate",
                            new ConfigOptions()
                                    .Model("pressure_plate")
                                    .Texture("minecraft:block/stone")));

            // Cobbled Stone Pressure Plate
            Generators.Block.loadBlock(getBlockMap("block.redstone.pressure_plate"),
                    new ConfigLoader(name + "_pressure_plate",
                            new ConfigOptions()
                                    .Model("pressure_plate")
                                    .Texture("minecraft:block/cobblestone")));

            // Polished Stone Pressure Plate
            Generators.Block.loadBlock(getBlockMap("block.redstone.pressure_plate"),
                    new ConfigLoader(name + "_pressure_plate",
                            new ConfigOptions()
                                    .Model("pressure_plate")
                                    .Texture("minecraft:block/smooth_stone")));

            // Stone Button
            Generators.Block.loadBlock(getBlockMap("block.redstone.button"),
                    new ConfigLoader(name + "_button",
                            new ConfigOptions()
                                    .Model("button")
                                    .Texture("minecraft:block/stone")));

            // Cobbled Stone Button
            Generators.Block.loadBlock(getBlockMap("block.redstone.button"),
                    new ConfigLoader("cobbled_" + name + "_button",
                            new ConfigOptions()
                                    .Model("button")
                                    .Texture("minecraft:block/stone")));

            // Polished Stone Button
            Generators.Block.loadBlock(getBlockMap("block.redstone.button"),
                    new ConfigLoader("polished_" + name + "_button",
                            new ConfigOptions()
                                    .Model("button")
                                    .Texture("minecraft:block/stone")));
        });
    }

    // Resources

    private void generatePlantBlockMaps(ConfigGroup config) {
        // Block.loadBlocks(getBlockMap("decoration.plant.generic"),
        // convertToLoaderList(config.getStringListValue("plant_list")));
    }

    private void generatePlantResourceBlockMaps(ConfigGroup config) {
        Generators.Resource.Plant.loadCrops(getBlockMap("resource.plant.crop"), convertToLoaderList(config.getStringListValue("crop_list")));
    }

    private void generateOreBlockMaps(ConfigGroup config) {
        Generators.Resource.Ore.loadGems(getBlockMap("resource.ore.gem"), convertToLoaderList(config.getStringListValue("gem_list")));
        Generators.Resource.Ore.loadMetals(getBlockMap("resource.ore.gem"), convertToLoaderList(config.getStringListValue("metal_list")));
    }

    private void buildBlockConfigs(ConfigGroup config) {
        generateGenericBlockMaps(config.getSubgroup("Generic"));
        generateRedstoneBlockMaps(config.getSubgroup("Redstone"));
        generateStorageBlockMaps(config.getSubgroup("Storage"));
        loadSuitesToBlockMaps(config.getSubgroup("Suite"));
    }

    private void buildResourceConfigs(ConfigGroup config) {
        generatePlantBlockMaps(config);
        generatePlantResourceBlockMaps(config);
        generateOreBlockMaps(config);
    }

    private void buildAxeConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("axe_list").forEach((String axe) -> {
            ConfigOptions options = new ConfigOptions()
                    .Texture("minecraft:item/iron_axe");

            try {
                map.put(axe,
                        AxeConfig.create(axe, new Config("conloot/items/tools/axes", axe), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for axe: " + axe);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.axe", map);
    }

    private void buildShovelConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("shovel_list").forEach((String shovel) -> {
            ConfigOptions options = new ConfigOptions()
                    .Texture("minecraft:item/iron_shovel");

            try {
                map.put(shovel,
                        ShovelConfig.create(shovel, new Config("conloot/items/tools/shovels", shovel), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for shovel: " + shovel);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.shovel", map);
    }

    private void buildHoeConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("hoe_list").forEach((String hoe) -> {
            ConfigOptions options = new ConfigOptions()
                    .Texture("minecraft:item/iron_hoe");

            try {
                map.put(hoe,
                        HoeConfig.create(hoe, new Config("conloot/items/tools/hoes", hoe), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for hoe: " + hoe);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.hoe", map);
    }

    private void buildFlintAndSteelConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("flint_and_steel_list").forEach((String fas) -> {
            ConfigOptions options = new ConfigOptions()
                    .Texture("minecraft:item/flint_and_steel");

            try {
                map.put(fas,
                        FlintAndSteelConfig.create(fas, new Config("conloot/items/tools/flint_and_steels", fas), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for flint and steel: " + fas);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.flint_and_steel", map);
    }

    private void buildFishingRodConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("fishing_rod_list").forEach((String rod) -> {
            ConfigOptions options = new ConfigOptions()
                    .Texture("minecraft:item/fishing_rod");

            try {
                map.put(rod,
                        FishingRodConfig.create(rod, new Config("conloot/items/tools/fishing_rods", rod), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for fishing rod: " + rod);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.fishing_rod", map);
    }

    private void buildBucketConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("bucket_list").forEach((String bucket) -> {
            ConfigOptions options = new ConfigOptions()
                    .Texture("minecraft:item/bucket");

            try {
                map.put(bucket,
                        ShearsConfig.create(bucket, new Config("conloot/items/tools/buckets", bucket), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for buckets: " + bucket);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.bucket", map);
    }

    private void buildShearsConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("shears_list").forEach((String shears) -> {
            ConfigOptions options = new ConfigOptions()
                    .Texture("minecraft:item/shears");

            try {
                map.put(shears,
                        ShearsConfig.create(shears, new Config("conloot/items/tools/shears", shears), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for shears: " + shears);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.shears", map);
    }

    private void buildToolConfigs(ConfigGroup config) {
        buildPickaxeConfigs(config);
        buildAxeConfigs(config);
        buildShovelConfigs(config);
        buildHoeConfigs(config);

        buildFlintAndSteelConfigs(config);
        buildFishingRodConfigs(config);
        buildBucketConfigs(config);
        buildShearsConfigs(config);
    }

    private void buildSwordConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("sword_list").forEach((String sword) -> {
            ConfigOptions options = new ConfigOptions()
                    .Texture("minecraft:item/iron_sword");

            try {
                map.put(sword,
                        SwordConfig.create(sword, new Config("conloot/items/weapons/swords", sword), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for sword: " + sword);
                err.printStackTrace();
            }
        });

        itemContent.put("weapon.sword", map);
    }

    private void buildShieldConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("shield_list").forEach((String shield) -> {
            try {
                map.put(shield,
                        ShieldConfig.create(shield, new Config("conloot/items/weapons/shields", shield)).Build()); // Needs
                                                                                                                   // defaults
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for shield: " + shield);
                err.printStackTrace();
            }
        });

        itemContent.put("weapon.shield", map);
    }

    private void buildBowConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("bow_list").forEach((String bow) -> {
            try {
                map.put(bow, BowConfig.create(bow, new Config("conloot/items/weapons/bows", bow), new ConfigOptions()).Build()); // Needs
                // defaults
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for bow: " + bow);
                err.printStackTrace();
            }
        });

        itemContent.put("weapon.bow", map);
    }

    private void buildCrossbowConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("crossbow_list").forEach((String crossbow) -> {
            try {
                map.put(crossbow, CrossbowConfig
                        .create(crossbow, new Config("conloot/items/weapons/crossbows", crossbow), new ConfigOptions()).Build()); // Needs
                // defaults
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for crossbow: " + crossbow);
                err.printStackTrace();
            }
        });

        itemContent.put("weapon.crossbow", map);
    }

    private void buildArrowConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("arrow_list").forEach((String arrow) -> {
            try {
                map
                        .put(arrow, ArrowConfig.create(arrow, new Config("conloot/items/weapons/arrows", arrow),
                                new ConfigOptions()
                                        .Texture(
                                                "minecraft:item/arrow"))
                                .Build()); // Needs defaults
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for arrow: " + arrow);
                err.printStackTrace();
            }
        });

        itemContent.put("weapon.arrow", map);
    }

    private void buildWeaponConfigs(ConfigGroup config) {
        buildSwordConfigs(config);
        buildShieldConfigs(config);
        buildBowConfigs(config);
        buildCrossbowConfigs(config);
        buildArrowConfigs(config);
    }

    private void buildWearableHeadConfigs(List<String> list) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        list.forEach((String piece) -> {
            ConfigOptions options = new ConfigOptions()
                    .Texture("minecraft:item/iron_helmet");

            try {
                map.put(piece,
                        HeadConfig.create(piece, new Config("conloot/items/wearable/head", piece), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for wearable head piece: " + piece);
                err.printStackTrace();
            }
        });

        itemContent.put("wearable.head", map);
    }

    private void buildWearableChestConfigs(List<String> list) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        list.forEach((String piece) -> {
            try {
                ConfigOptions options = new ConfigOptions()
                        .Texture("minecraft:item/iron_chestplate");

                map.put(piece,
                        ChestConfig.create(piece, new Config("conloot/items/wearable/chest", piece), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for wearable chest piece: " + piece);
                err.printStackTrace();
            }
        });

        itemContent.put("wearable.chest", map);
    }

    private void buildWearableLegsConfigs(List<String> list) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        list.forEach((String piece) -> {
            try {
                ConfigOptions options = new ConfigOptions()
                        .Texture("minecraft:item/iron_leggings");

                map.put(piece,
                        LegsConfig.create(piece, new Config("conloot/items/wearable/legs", piece), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for wearable legs piece: " + piece);
                err.printStackTrace();
            }
        });

        itemContent.put("wearable.legs", map);
    }

    private void buildWearableFeetConfigs(List<String> list) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        list.forEach((String piece) -> {
            ConfigOptions options = new ConfigOptions()
                    .Texture("minecraft:item/iron_boots");

            try {
                map.put(piece,
                        FeetConfig.create(piece, new Config("conloot/items/wearable/feet", piece), options).Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for wearable feet piece: " + piece);
                err.printStackTrace();
            }
        });

        itemContent.put("wearable.feet", map);
    }

    private void buildWearableConfigs(ConfigGroup config) {
        List<String> head = config.getStringListValue("head_list");
        List<String> chest = config.getStringListValue("chest_list");
        List<String> legs = config.getStringListValue("legs_list");
        List<String> feet = config.getStringListValue("feet_list");

        config.getStringListValue("wearable_set_list").forEach((String s) -> {
            head.add(s + "_helmet");
            chest.add(s + "_chestplate");
            legs.add(s + "_leggings");
            feet.add(s + "_boots");
        });

        buildWearableHeadConfigs(head);
        buildWearableChestConfigs(chest);
        buildWearableLegsConfigs(legs);
        buildWearableFeetConfigs(feet);
    }

    private void buildConsumableConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("food_list").forEach((String food) -> {
            try {
                map.put(food, FoodConfig.create(food, new Config("conloot/items/consumable/food", food), new ConfigOptions()).Build()); // Needs
                // defaults
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for food: " + food);
                err.printStackTrace();
            }
        });

        itemContent.put("consumable.food", map);
    }

    private void buildGenericItemConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("generic_list").forEach((String item) -> {
            try {
                map.put(item, FoodConfig.create(item, new Config("conloot/items/generic/all", item), new ConfigOptions()).Build()); // Needs
                // defaults
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for item: " + item);
                err.printStackTrace();
            }
        });

        itemContent.put("generic.all", map);
    }

    private void buildBiomeConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("biome_list").forEach((String biome) -> {
            try {
                Config cfg = new Config("conloot/worldgen/biomes", biome);
                BiomeConfig.create(biome, cfg);
                map.put(biome, cfg.Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for biome: " + biome);
                err.printStackTrace();
            }
        });

        worldGenContent.put("worldgen.biome", map);
    }

    private void buildWorldGenConfigs(ConfigGroup config) {
        buildBiomeConfigs(config.getSubgroup("Biomes"));
    }

    private void buildUIConfigs(ConfigGroup config) {
        buildItemGroupConfigs(config.getSubgroup("ItemGroups"));
    }

    private void buildItemGroupConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("tabs").forEach((String tab) -> {
            try {
                Config cfg = new Config("conloot/ui/itemGroups", tab);
                ItemGroupConfig.create(tab, cfg);
                map.put(tab, cfg.Build());
            }
            catch (Exception err) {
                Main.LOG.error("Failed to create config for itemGroup: " + tab);
                err.printStackTrace();
            }
        });

        uiContent.put("ui.itemGroup", map);
    }

    private void composeBlockConfigs(Config config) {
        ConfigGroup blocks = new ConfigGroup();

        ConfigGroup generic = new ConfigGroup();

        generic.addStringList("block_list", new ArrayList<String>(), "A list of blocks.");
        generic.addStringList("slab_list", new ArrayList<String>(), "A list of slabs.");
        generic.addStringList("stairs_list", new ArrayList<String>(), "A list of stairs.");
        generic.addStringList("wall_list", new ArrayList<String>(), "A list of walls.");
        generic.addStringList("fence_list", new ArrayList<String>(), "A list of fences.");
        blocks.addSubgroup("Generic", generic);

        ConfigGroup redstone = new ConfigGroup();

        redstone.addStringList("fence_gate_list", new ArrayList<String>(), "A list of fence gates.");
        redstone.addStringList("door_list", new ArrayList<String>(), "A list of doors.");
        redstone.addStringList("trapdoor_list", new ArrayList<String>(), "A list of trapdoors.");
        redstone.addStringList("pressure_plate_list", new ArrayList<String>(), "A list of pressure plates.");
        redstone.addStringList("button_list", new ArrayList<String>(), "A list of buttons.");
        blocks.addSubgroup("Redstone", redstone);

        ConfigGroup interactive = new ConfigGroup();

        interactive.addStringList("barrel_list", new ArrayList<String>(), "A list of barrels.");
        interactive.addStringList("shulker_list", new ArrayList<String>(), "A list of shulker.");
        blocks.addSubgroup("Interactive", interactive);

        ConfigGroup suite = new ConfigGroup();

        suite.addStringList("stone_suite_list", new ArrayList<String>(),
                "A list of content that generates a block, slab, stair and wall.");
        suite.addStringList("wood_suite_list", new ArrayList<String>(),
                "A list of content that generates a block, slab, stair, fence, fence gate, door and trapdoor.");
        blocks.addSubgroup("Suite", suite);

        config.addSubgroup("Blocks", blocks);

        // Resources
        ConfigGroup resources = new ConfigGroup();

        ConfigGroup ores = new ConfigGroup();

        ores.addStringList("gem_list", new ArrayList<String>(), "A list of gem types.");
        ores.addStringList("metal_list", new ArrayList<String>(), "A list of metal types.");
        resources.addSubgroup("Ores", ores);

        ConfigGroup plants = new ConfigGroup();

        plants.addStringList("crop_list", new ArrayList<String>(), "A list of crops.");
        resources.addSubgroup("Plants", plants);

        config.addSubgroup("Resources", resources);

        config.Build();

        configs.put(Main.MOD_ID + "_blocks", config);

        Main.LOG.debug("Building block configs...");
        buildBlockConfigs(blocks);

        Main.LOG.debug("Building resource configs...");
        buildResourceConfigs(resources);
    }

    // Item Configs
    private void composeItemConfigs(Config config) {
        ConfigGroup tools = new ConfigGroup();

        tools.addStringList("pickaxe_list", new ArrayList<String>(), "A list of pickaxes.");
        tools.addStringList("axe_list", new ArrayList<String>(), "A list of axes.");
        tools.addStringList("shovel_list", new ArrayList<String>(), "A list of shovels.");
        tools.addStringList("hoe_list", new ArrayList<String>(), "A list of hoes.");
        tools.addStringList("fishing_rod_list", new ArrayList<String>(), "A list of fishing rods.");
        tools.addStringList("bucket_list", new ArrayList<String>(), "A list of buckets.");
        tools.addStringList("shears_list", new ArrayList<String>(), "A list of shears.");
        tools.addStringList("flint_and_steel_list", new ArrayList<String>(), "A list of fire starters.");

        config.addSubgroup("Tools", tools);

        ConfigGroup weapons = new ConfigGroup();

        weapons.addStringList("sword_list", new ArrayList<String>(), "A list of swords.");
        weapons.addStringList("shield_list", new ArrayList<String>(), "A list of shields.");
        weapons.addStringList("bow_list", new ArrayList<String>(), "A list of bows.");
        weapons.addStringList("crossbow_list", new ArrayList<String>(), "A list of crossbows.");
        weapons.addStringList("arrow_list", new ArrayList<String>(), "A list of arrows.");

        config.addSubgroup("Weapons", weapons);

        ConfigGroup wearable = new ConfigGroup();

        wearable.addStringList("wearable_set_list", new ArrayList<String>(),
                "A list of full wearable sets (generates all pieces as a set).");
        wearable.addStringList("head_list", new ArrayList<String>(), "A list of helmets.");
        wearable.addStringList("chest_list", new ArrayList<String>(), "A list of chestplates.");
        wearable.addStringList("legs_list", new ArrayList<String>(), "A list of leggings.");
        wearable.addStringList("feet_list", new ArrayList<String>(), "A list of boots.");

        config.addSubgroup("Wearable", wearable);

        ConfigGroup consumable = new ConfigGroup();

        consumable.addStringList("food_list", new ArrayList<String>(),
                "A list of food items.");

        config.addSubgroup("Consumable", consumable);

        ConfigGroup generic = new ConfigGroup();

        generic.addStringList("generic_list", new ArrayList<String>(), "A list of generic items with no particular use (like sticks).");

        config.addSubgroup("Generic", generic);

        config.Build();

        configs.put(Main.MOD_ID + "_items", config);

        Main.LOG.debug("Building tool configs...");
        buildToolConfigs(tools);

        Main.LOG.debug("Building weapons configs...");
        buildWeaponConfigs(weapons);

        Main.LOG.debug("Building wearable configs...");
        buildWearableConfigs(wearable);

        Main.LOG.debug("Building consumable configs...");
        buildConsumableConfigs(consumable);

        Main.LOG.debug("Building generic item configs...");
        buildGenericItemConfigs(generic);
    }

    private void composeWorldGenConfigs(Config config) {
        // World Gen
        ConfigGroup worldGen = new ConfigGroup();

        ConfigGroup biomes = new ConfigGroup();

        biomes.addStringList("biome_list", new ArrayList<String>(), "A list of biomes to modify.");
        worldGen.addSubgroup("Biomes", biomes);

        config.addSubgroup("WorldGen", worldGen);

        config.Build();

        configs.put(Main.MOD_ID + "_worldgen", config);

        Main.LOG.debug("Building worldGen configs...");
        buildWorldGenConfigs(worldGen);
    }

    private void composeUIConfigs(Config config) {
        ConfigGroup ui = new ConfigGroup();

        ConfigGroup itemGroups = new ConfigGroup();

        itemGroups.addStringList("tabs", new ArrayList<String>(), "A list of creative tab names.");

        ui.addSubgroup("ItemGroups", itemGroups);

        config.addSubgroup("UI", ui);

        config.Build();

        configs.put(Main.MOD_ID + "_ui", config);

        Main.LOG.debug("Building UI configs...");
        buildUIConfigs(ui);
    }

    public void Init() {
        // Blocks
        composeBlockConfigs(new Config(Main.MOD_ID + "_blocks"));

        // Items
        composeItemConfigs(new Config(Main.MOD_ID + "_items"));

        // World Gen
        composeWorldGenConfigs(new Config(Main.MOD_ID + "_worldgen"));

        // UI
        composeUIConfigs(new Config(Main.MOD_ID + "_ui"));
    }
}