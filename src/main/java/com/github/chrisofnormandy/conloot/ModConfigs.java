package com.github.chrisofnormandy.conloot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conloot.configs.blocks.BlockConfig;
import com.github.chrisofnormandy.conloot.configs.blocks.CropConfig;
import com.github.chrisofnormandy.conloot.configs.blocks.OreConfig;
import com.github.chrisofnormandy.conloot.configs.ui.ItemGroupConfig;
import com.github.chrisofnormandy.conloot.configs.worldgen.BiomeConfig;

public class ModConfigs {
    public final HashMap<String, Config> configs = new HashMap<String, Config>();

    public final HashMap<String, HashMap<String, Config>> uiContent = new HashMap<String, HashMap<String, Config>>();

    public final HashMap<String, HashMap<String, Config>> blockContent = new HashMap<String, HashMap<String, Config>>();
    public final HashMap<String, HashMap<String, Config>> itemContent = new HashMap<String, HashMap<String, Config>>();
    public final HashMap<String, HashMap<String, Config>> worldGenContent = new HashMap<String, HashMap<String, Config>>();

    // Config Builders

    private void buildCropConfigs(List<String> list) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        list.forEach((String crop) -> {
            Config cfg = new Config("conloot/crops", crop);
            CropConfig.create(crop, cfg);
            cfg.Build();
            map.put(crop, cfg);
        });

        blockContent.put("resource.plant.crop", map);
    }

    private void buildGemConfigs(List<String> list) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        list.forEach((String gem) -> {
            try {
                Config cfg = new Config("conloot/gems", gem);
                OreConfig.create(gem, cfg, "gem");
                cfg.Build();
                map.put(gem, cfg);
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
                cfg.Build();
                map.put(metal, cfg);
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for metal resource: " + metal);
                err.printStackTrace();
            }
        });

        blockContent.put("resource.ore.metal", map);
    }

    private Config blockConfig(String name, String model, String color) {
        try {
            Config cfg = new Config("conloot/blocks/" + model, name);
            BlockConfig.create(name, cfg, model, color);
            cfg.Build();
            return cfg;
        } catch (Exception err) {
            Main.LOG.error("Failed to create config for block: " + name);
            err.printStackTrace();
        }

        return null;
    }

    private Config blockConfig(String name, String model) {
        return blockConfig(name, model, "black");
    }

    private HashMap<String, Config> getBlockMap(List<String> list, HashMap<String, Config> map) {
        list.forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors)
                    map.put(block, blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "block", clr));
            } else
                map.put(block, blockConfig(block, "block"));
        });

        return map;
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
    }

    private void buildSuiteBlockConfigs(ConfigGroup config) {
        config.getStringListValue("wood_suite_list").forEach((String name) -> {
            blockContent.get("block.generic.block").put(name + "_log", blockConfig(name + "_log", "block"));
            blockContent.get("block.generic.block").put("stripped_" + name + "_log", blockConfig(
                    "stripped_" + name + "_log", "block"));
            blockContent.get("block.generic.block").put(name + "_wood", blockConfig(name + "_wood", "block"));
            blockContent.get("block.generic.block").put("stripped_" + name + "_wood", blockConfig(
                    "stripped_" + name + "_wood", "block"));

            blockContent.get("block.generic.block").put(name + "_planks", blockConfig(name + "_planks", "block"));

            blockContent.get("block.generic.slab").put(name + "_slab", blockConfig(name + "_slab", "slab"));
            blockContent.get("block.generic.stairs").put(name + "_stairs", blockConfig(name + "_stairs", "stairs"));
            blockContent.get("block.generic.fence").put(name + "_fence", blockConfig(name + "_fence", "fence"));

            blockContent.get("block.redstone.fence_gate").put(name + "_fence_gate", blockConfig(
                    name + "_fence_gate", "fence_gate"));
            blockContent.get("block.redstone.door").put(name + "_door", blockConfig(name + "_door", "door"));
            blockContent.get("block.redstone.trapdoor").put(name + "_trapdoor", blockConfig(name + "_trapdoor", "trapdoor"));
        });

        config.getStringListValue("stone_suite_list").forEach((String name) -> {
            blockContent.get("block.generic.block").put(name, blockConfig(name, "block"));

            blockContent.get("block.generic.slab").put(name + "_slab", blockConfig(name + "_slab", "slab"));
            blockContent.get("block.generic.stairs").put(name + "_stairs", blockConfig(name + "_stairs", "stairs"));
            blockContent.get("block.generic.wall").put(name + "_wall", blockConfig(name + "_wall", "wall"));
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

        Main.LOG.debug(
                "Finished barrel config builds with: " + blockContent.get("block.interactive.storage.barrel").size());

        Main.LOG.debug(
                "Finished shulker config builds with: " + blockContent.get("block.interactive.storage.shulker").size());
    }

    private void buildResourceConfigs(ConfigGroup group) {
        buildMetalConfigs(group.getSubgroup("Ores").getStringListValue("metal_list"));
        buildGemConfigs(group.getSubgroup("Ores").getStringListValue("gem_list"));
        buildCropConfigs(group.getSubgroup("Plants").getStringListValue("crop_list"));
    }

    private void buildBiomeConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("biome_list").forEach((String biome) -> {
            try {
                Config cfg = new Config("conloot/worldgen/biomes", biome);
                BiomeConfig.create(biome, cfg);
                cfg.Build();
                map.put(biome, cfg);
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for biome: " + biome);
                err.printStackTrace();
            }
        });

        worldGenContent.put("worldgen.biome", map);
    }

    public void buildWorldGenConfigs(ConfigGroup config) {
        buildBiomeConfigs(config.getSubgroup("Biomes"));
    }

    public void buildUIConfigs(ConfigGroup config) {
        buildItemGroupConfigs(config.getSubgroup("ItemGroups"));
    }

    public void buildItemGroupConfigs(ConfigGroup config) {
        HashMap<String, Config> map = new HashMap<String, Config>();

        config.getStringListValue("tabs").forEach((String tab) -> {
            try {
                Config cfg = new Config("conloot/ui/itemGroups", tab);
                ItemGroupConfig.create(tab, cfg);
                cfg.Build();
                map.put(tab, cfg);
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for itemGroup: " + tab);
                err.printStackTrace();
            }
        });

        uiContent.put("ui.itemGroup", map);
    }

    private void mainConfig(Config config) {
        // Blocks
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

        // Items
        ConfigGroup items = new ConfigGroup();

        ConfigGroup tool = new ConfigGroup();

        tool.addStringList("pickaxe_list", new ArrayList<String>(), "A list of pickaxes.");
        tool.addStringList("axe_list", new ArrayList<String>(), "A list of pickaxes.");
        tool.addStringList("shovel_list", new ArrayList<String>(), "A list of pickaxes.");
        tool.addStringList("hoe_list", new ArrayList<String>(), "A list of pickaxes.");
        items.addSubgroup("Tool", tool);

        config.addSubgroup("Items", items);

        // World Gen
        ConfigGroup worldGen = new ConfigGroup();

        ConfigGroup biomes = new ConfigGroup();

        biomes.addStringList("biome_list", new ArrayList<String>(), "A list of biomes to modify.");
        worldGen.addSubgroup("Biomes", biomes);

        config.addSubgroup("WorldGen", worldGen);

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

        // Item Groups
        ConfigGroup ui = new ConfigGroup();

        ConfigGroup itemGroups = new ConfigGroup();

        itemGroups.addStringList("tabs", new ArrayList<String>(), "A list of creative tab names.");

        ui.addSubgroup("ItemGroups", itemGroups);
        
        config.addSubgroup("UI", ui);

        config.Build();

        configs.put(Main.MOD_ID, config);

        Main.LOG.debug("Building block configs...");
        buildBlockConfigs(blocks);

        Main.LOG.debug("Building resource configs...");
        buildResourceConfigs(resources);

        Main.LOG.debug("Building worldGen configs...");
        buildWorldGenConfigs(worldGen);

        Main.LOG.debug("Building UI configs...");
        buildUIConfigs(ui);
    }

    public void Init() {
        mainConfig(new Config(Main.MOD_ID));
    }
}