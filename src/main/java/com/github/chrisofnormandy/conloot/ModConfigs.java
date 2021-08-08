package com.github.chrisofnormandy.conloot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conloot.configs.blocks.BlockConfig;
import com.github.chrisofnormandy.conloot.configs.blocks.CropConfig;
import com.github.chrisofnormandy.conloot.configs.blocks.OreConfig;
import com.github.chrisofnormandy.conloot.configs.items.tools.ToolConfigBase;
import com.github.chrisofnormandy.conloot.configs.ui.ItemGroupConfig;
import com.github.chrisofnormandy.conloot.configs.worldgen.BiomeConfig;

public class ModConfigs {
    public final HashMap<String, Config> configs = new HashMap<String, Config>();

    public final HashMap<String, HashMap<String, Config>> uiContent = new HashMap<String, HashMap<String, Config>>();

    public final HashMap<String, HashMap<String, Config>> blockContent = new HashMap<String, HashMap<String, Config>>();
    public final HashMap<String, HashMap<String, Config>> itemContent = new HashMap<String, HashMap<String, Config>>();
    public final HashMap<String, HashMap<String, Config>> worldGenContent = new HashMap<String, HashMap<String, Config>>();

    // Block Configs
    private Config blockConfig(String name, String model, String material, String subtype, String color) {
        try {
            Config cfg = new Config("conloot/blocks/" + model, name);
            BlockConfig.create(name, cfg, model, material, subtype, color);
            return cfg.Build();
        } catch (Exception err) {
            Main.LOG.error("Failed to create config for block: " + name);
            err.printStackTrace();
        }

        return null;
    }

    private HashMap<String, Config> getBlockMap(List<String> list, HashMap<String, Config> map) {
        list.forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors)
                    map.put(block,
                            blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "block", "stone", "all", clr));
            } else
                map.put(block, blockConfig(block, "block", "stone", "all", "white"));
        });

        return map;
    }

    private void buildCropConfigs(List<String> list) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        list.forEach((String crop) -> {
            Config cfg = new Config("conloot/crops", crop);
            CropConfig.create(crop, cfg);
            map.put(crop, cfg.Build());
        });

        blockContent.put("resource.plant.crop", map);
    }

    private void buildGemConfigs(List<String> list) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        list.forEach((String gem) -> {
            try {
                Config cfg = new Config("conloot/gems", gem);
                OreConfig.create(gem, cfg, "gem");
                map.put(gem, cfg.Build());
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for gem resource: " + gem);
                err.printStackTrace();
            }
        });

        blockContent.put("resource.ore.gem", map);
    }

    private void buildMetalConfigs(List<String> list) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        list.forEach((String metal) -> {
            try {
                Config cfg = new Config("conloot/metals", metal);
                OreConfig.create(metal, cfg, "ingot");
                map.put(metal, cfg.Build());
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for metal resource: " + metal);
                err.printStackTrace();
            }
        });

        blockContent.put("resource.ore.metal", map);
    }

    private void buildGenericBlockConfigs(ConfigGroup config) {
        blockContent.put("block.generic.block",
                getBlockMap(config.getStringListValue("block_list"), new HashMap<String, Config>()));

        blockContent.put("block.generic.slab",
                getBlockMap(config.getStringListValue("slab_list"), new HashMap<String, Config>()));

        blockContent.put("block.generic.stairs",
                getBlockMap(config.getStringListValue("stairs_list"), new HashMap<String, Config>()));

        blockContent.put("block.generic.wall",
                getBlockMap(config.getStringListValue("wall_list"), new HashMap<String, Config>()));

        blockContent.put("block.generic.fence",
                getBlockMap(config.getStringListValue("fence_list"), new HashMap<String, Config>()));
    }

    private void buildInteractiveBlockConfigs(ConfigGroup config) {
        blockContent.put("block.interactive.storage.barrel",
                getBlockMap(config.getStringListValue("barrel_list"), new HashMap<String, Config>()));

        blockContent.put("block.interactive.storage.shulker",
                getBlockMap(config.getStringListValue("shulker_list"), new HashMap<String, Config>()));
    }

    private void buildRedstoneBlockConfigs(ConfigGroup config) {
        blockContent.put("block.redstone.fence_gate",
                getBlockMap(config.getStringListValue("fence_gate_list"), new HashMap<String, Config>()));

        blockContent.put("block.redstone.door",
                getBlockMap(config.getStringListValue("door_list"), new HashMap<String, Config>()));

        blockContent.put("block.redstone.trapdoor",
                getBlockMap(config.getStringListValue("trapdoor_list"), new HashMap<String, Config>()));

        blockContent.put("block.redstone.pressure_plate",
                getBlockMap(config.getStringListValue("pressure_plate_list"), new HashMap<String, Config>()));

        blockContent.put("block.redstone.button",
                getBlockMap(config.getStringListValue("button_list"), new HashMap<String, Config>()));
    }

    private void buildSuiteBlockConfigs(ConfigGroup config) {
        config.getStringListValue("wood_suite_list").forEach((String name) -> {
            blockContent.get("block.generic.block").put(name + "_log",
                    blockConfig(name + "_log", "block", "wood", "column", "white"));
            blockContent.get("block.generic.block").put("stripped_" + name + "_log",
                    blockConfig("stripped_" + name + "_log", "block", "wood", "column", "white"));
            blockContent.get("block.generic.block").put(name + "_wood",
                    blockConfig(name + "_wood", "block", "wood", "all", "white"));
            blockContent.get("block.generic.block").put("stripped_" + name + "_wood",
                    blockConfig("stripped_" + name + "_wood", "block", "wood", "all", "white"));

            blockContent.get("block.generic.block").put(name + "_planks",
                    blockConfig(name + "_planks", "block", "wood", "all", "white"));

            blockContent.get("block.generic.slab").put(name + "_slab",
                    blockConfig(name + "_slab", "slab", "wood", "all", "white"));
            blockContent.get("block.generic.stairs").put(name + "_stairs",
                    blockConfig(name + "_stairs", "stairs", "wood", "all", "white"));
            blockContent.get("block.generic.fence").put(name + "_fence",
                    blockConfig(name + "_fence", "fence", "wood", "all", "white"));

            blockContent.get("block.redstone.fence_gate").put(name + "_fence_gate",
                    blockConfig(name + "_fence_gate", "fence_gate", "wood", "all", "white"));
            blockContent.get("block.redstone.door").put(name + "_door",
                    blockConfig(name + "_door", "door", "wood", "all", "white"));
            blockContent.get("block.redstone.trapdoor").put(name + "_trapdoor",
                    blockConfig(name + "_trapdoor", "trapdoor", "wood", "all", "white"));

            blockContent.get("block.redstone.pressure_plate").put(name + "_pressure_plate",
                    blockConfig(name + "_pressure_plate", "pressure_plate", "wood", "all", "white"));
            blockContent.get("block.redstone.button").put(name + "_button",
                    blockConfig(name + "_button", "button", "wood", "all", "white"));
        });

        config.getStringListValue("stone_suite_list").forEach((String name) -> {
            blockContent.get("block.generic.block").put(name, blockConfig(name, "block", "stone", "all", "white"));

            blockContent.get("block.generic.slab").put(name + "_slab",
                    blockConfig(name + "_slab", "slab", "stone", "all", "white"));
            blockContent.get("block.generic.stairs").put(name + "_stairs",
                    blockConfig(name + "_stairs", "stairs", "stone", "all", "white"));
            blockContent.get("block.generic.wall").put(name + "_wall",
                    blockConfig(name + "_wall", "wall", "stone", "all", "white"));

            blockContent.get("block.redstone.pressure_plate").put(name + "_pressure_plate",
                    blockConfig(name + "_pressure_plate", "pressure_plate", "stone", "all", "white"));
            blockContent.get("block.redstone.button").put(name + "_button",
                    blockConfig(name + "_button", "button", "stone", "all", "white"));
        });
    }

    private void buildBlockConfigs(ConfigGroup config) {
        buildGenericBlockConfigs(config.getSubgroup("Generic"));
        buildRedstoneBlockConfigs(config.getSubgroup("Redstone"));
        buildInteractiveBlockConfigs(config.getSubgroup("Interactive"));
        buildSuiteBlockConfigs(config.getSubgroup("Suite"));

        Main.LOG.debug("Finished block config builds with: " + blockContent.get("block.generic.block").size());

        Main.LOG.debug("Finished slab config builds with: " + blockContent.get("block.generic.slab").size());

        Main.LOG.debug("Finished stairs config builds with: " + blockContent.get("block.generic.stairs").size());

        Main.LOG.debug("Finished wall config builds with: " + blockContent.get("block.generic.wall").size());

        Main.LOG.debug("Finished fence config builds with: " + blockContent.get("block.generic.fence").size());

        Main.LOG.debug(
                "Finished fence gate config builds with: " + blockContent.get("block.redstone.fence_gate").size());

        Main.LOG.debug("Finished door config builds with: " + blockContent.get("block.redstone.door").size());

        Main.LOG.debug("Finished trapdoor config builds with: " + blockContent.get("block.redstone.trapdoor").size());

        Main.LOG.debug("Finished pressure plate config builds with: "
                + blockContent.get("block.redstone.pressure_plate").size());

        Main.LOG.debug("Finished button config builds with: " + blockContent.get("block.redstone.button").size());

        Main.LOG.debug(
                "Finished barrel config builds with: " + blockContent.get("block.interactive.storage.barrel").size());

        Main.LOG.debug(
                "Finished shulker config builds with: " + blockContent.get("block.interactive.storage.shulker").size());
    }

    private void buildResourceConfigs(ConfigGroup config) {
        buildMetalConfigs(config.getSubgroup("Ores").getStringListValue("metal_list"));
        buildGemConfigs(config.getSubgroup("Ores").getStringListValue("gem_list"));
        buildCropConfigs(config.getSubgroup("Plants").getStringListValue("crop_list"));
    }

    private void buildPickaxeConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("pickaxe_list").forEach((String pickaxe) -> {
            try {
                map.put(pickaxe,
                        ToolConfigBase.create(pickaxe, new Config("conloot/items/tools/pickaxes", pickaxe)).Build());
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for pickaxe: " + pickaxe);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.pickaxe", map);
    }

    private void buildAxeConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("axe_list").forEach((String axe) -> {
            try {
                map.put(axe, ToolConfigBase.create(axe, new Config("conloot/items/tools/axes", axe)).Build());
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for axe: " + axe);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.axe", map);
    }

    private void buildShovelConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("shovel_list").forEach((String shovel) -> {
            try {
                map.put(shovel,
                        ToolConfigBase.create(shovel, new Config("conloot/items/tools/shovels", shovel)).Build());
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for shovel: " + shovel);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.shovel", map);
    }

    private void buildHoeConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("hoe_list").forEach((String hoe) -> {
            try {
                map.put(hoe, ToolConfigBase.create(hoe, new Config("conloot/items/tools/hoes", hoe)).Build());
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for hoe: " + hoe);
                err.printStackTrace();
            }
        });

        itemContent.put("tool.hoe", map);
    }

    private void buildToolConfigs(ConfigGroup config) {
        buildPickaxeConfigs(config);
        buildAxeConfigs(config);
        buildShovelConfigs(config);
        buildHoeConfigs(config);
    }

    private void buildBiomeConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("biome_list").forEach((String biome) -> {
            try {
                Config cfg = new Config("conloot/worldgen/biomes", biome);
                BiomeConfig.create(biome, cfg);
                map.put(biome, cfg.Build());
            } catch (Exception err) {
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
            } catch (Exception err) {
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
        weapons.addStringList("bow_list", new ArrayList<String>(), "A list of bows.");
        weapons.addStringList("crossbow_list", new ArrayList<String>(), "A list of crossbows.");
        weapons.addStringList("shield_list", new ArrayList<String>(), "A list of shields.");

        config.addSubgroup("Weapons", weapons);

        ConfigGroup armour = new ConfigGroup();

        armour.addStringList("armour_set_list", new ArrayList<String>(),
                "A list of full armour sets (generates all pieces as a set).");
        armour.addStringList("helmet_list", new ArrayList<String>(), "A list of helmets.");
        armour.addStringList("chestplate_list", new ArrayList<String>(), "A list of chestplates.");
        armour.addStringList("leggings_list", new ArrayList<String>(), "A list of leggings.");
        armour.addStringList("boots_list", new ArrayList<String>(), "A list of boots.");

        config.addSubgroup("Armour", armour);

        config.Build();

        configs.put(Main.MOD_ID + "_items", config);

        Main.LOG.debug("Building tool configs...");
        buildToolConfigs(tools);

        Main.LOG.debug("Building weapons configs...");
        // buildUIConfigs(weapons);

        Main.LOG.debug("Building armour configs...");
        // buildUIConfigs(armour);
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