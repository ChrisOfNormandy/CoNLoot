package com.github.chrisofnormandy.conloot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conloot.configs.blocks.BlockConfig;
import com.github.chrisofnormandy.conloot.configs.blocks.CropConfig;
import com.github.chrisofnormandy.conloot.configs.blocks.OreConfig;
import com.github.chrisofnormandy.conloot.configs.worldgen.BiomeConfig;

public class ModConfigs {
    public final HashMap<String, Config> configs = new HashMap<String, Config>();

    // Blocks
    public final HashMap<String, Config> gemConfigs = new HashMap<String, Config>();
    public final HashMap<String, Config> metalConfigs = new HashMap<String, Config>();
    public final HashMap<String, Config> plantConfigs = new HashMap<String, Config>();
    public final HashMap<String, Config> cropConfigs = new HashMap<String, Config>();
    public final HashMap<String, Config> blockConfigs = new HashMap<String, Config>();

    // Items
    public final HashMap<String, Config> plainItemConfigs = new HashMap<String, Config>();
    public final HashMap<String, Config> pickaxeConfigs = new HashMap<String, Config>();
    public final HashMap<String, Config> axeConfigs = new HashMap<String, Config>();
    public final HashMap<String, Config> shovelConfigs = new HashMap<String, Config>();
    public final HashMap<String, Config> hoeConfigs = new HashMap<String, Config>();

    // World Gen
    public final HashMap<String, Config> biomeConfigs = new HashMap<String, Config>();

    // Config Builders
    private void mainConfig(Config config) {
        ConfigGroup gemGroup = new ConfigGroup();
        ConfigGroup metalGroup = new ConfigGroup();
        ConfigGroup cropGroup = new ConfigGroup();
        ConfigGroup blockGroup = new ConfigGroup();
        ConfigGroup worldGenGroup = new ConfigGroup();

        // Gems
        List<String> gemList = new ArrayList<String>();
        gemGroup.addStringList("gem_list", gemList, "A list of gem types.");
        config.addSubgroup("Gems", gemGroup);

        // Metals
        List<String> metalList = new ArrayList<String>();
        metalGroup.addStringList("metal_list", metalList, "A list of metal types.");
        config.addSubgroup("Metals", metalGroup);

        // Crops
        List<String> cropList = new ArrayList<String>();
        cropGroup.addStringList("crop_list", cropList, "A list of crops.");

        config.addSubgroup("Crops", cropGroup);

        // Blocks
        List<String> blockList = new ArrayList<String>();
        blockGroup.addStringList("block_list", blockList, "A list of blocks.");
        blockGroup.addStringList("slab_list", blockList, "A list of slabs.");
        blockGroup.addStringList("stairs_list", blockList, "A list of stairs.");
        blockGroup.addStringList("wall_list", blockList, "A list of walls.");
        blockGroup.addStringList("fence_list", blockList, "A list of fences.");
        blockGroup.addStringList("fence_gate_list", blockList, "A list of fence gates.");
        blockGroup.addStringList("door_list", blockList, "A list of doors.");
        blockGroup.addStringList("trapdoor_list", blockList, "A list of trapdoors.");

        blockGroup.addStringList("suite_list", blockList,
                "A list of content that should generate a block, slab, stair, and depending on type, wall or fence.");

        config.addSubgroup("Blocks", blockGroup);

        // World Gen
        List<String> biomeList = new ArrayList<String>();
        worldGenGroup.addStringList("biome_list", biomeList, "A list of biomes to modify.");

        config.addSubgroup("WorldGen", worldGenGroup);

        config.Build();
        configs.put(Main.MOD_ID, config);

        buildGemConfigs(gemGroup);
        buildMetalConfigs(metalGroup);

        buildCropConfigs(cropGroup);

        buildBlockConfigs(blockGroup);

        buildBiomeConfigs(worldGenGroup);
    }

    void buildCropConfigs(ConfigGroup cropConfig) {
        cropConfig.getStringListValue("crop_list").forEach((String crop) -> {
            Config cfg = new Config("conloot/crops", crop);
            CropConfig.create(crop, cfg);
            cfg.Build();
            plantConfigs.put(crop, cfg);
        });
    }

    private void buildGemConfigs(ConfigGroup gemGroup) {
        gemGroup.getStringListValue("gem_list").forEach((String gem) -> {
            try {
                Config cfg = new Config("conloot/gems", gem);
                OreConfig.create(gem, cfg, "gem");
                cfg.Build();
                gemConfigs.put(gem, cfg);
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for gem resource: " + gem);
                Main.LOG.error(err.getStackTrace());
            }
        });
    }

    private void buildMetalConfigs(ConfigGroup metalGroup) {
        metalGroup.getStringListValue("metal_list").forEach((String metal) -> {
            try {
                Config cfg = new Config("conloot/metals", metal);
                OreConfig.create(metal, cfg, "ingot");
                cfg.Build();
                metalConfigs.put(metal, cfg);
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for metal resource: " + metal);
                Main.LOG.error(err.getStackTrace());
            }
        });
    }

    private void blockConfig(String name, String model, String color) {
        try {
            Config cfg = new Config("conloot/blocks/" + model, name);
            BlockConfig.create(name, cfg, model, color);
            cfg.Build();
            blockConfigs.put(name, cfg);
        } catch (Exception err) {
            Main.LOG.error("Failed to create config for block: " + name);
            Main.LOG.error(err.getStackTrace());
        }
    }

    private void blockConfig(String name, String model) {
        blockConfig(name, model, "black");
    }

    private void buildBlockConfigs(ConfigGroup blockConfig) {
        blockConfig.getStringListValue("block_list").forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors)
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "block", clr);
            } else
                blockConfig(block, "block");
        });

        blockConfig.getStringListValue("slab_list").forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors)
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "slab", clr);
            } else
                blockConfig(block, "slab");
        });

        blockConfig.getStringListValue("stairs_list").forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors)
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "stairs", clr);
            } else
                blockConfig(block, "stairs");
        });

        blockConfig.getStringListValue("wall_list").forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors)
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "wall", clr);
            } else
                blockConfig(block, "wall");
        });

        blockConfig.getStringListValue("fence_list").forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors)
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "fence", clr);
            } else
                blockConfig(block, "fence");
        });

        blockConfig.getStringListValue("fence_gate_list").forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors)
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "fence_gate", clr);
            } else
                blockConfig(block, "fence_gate");
        });

        blockConfig.getStringListValue("door_list").forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors)
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "door", clr);
            } else
                blockConfig(block, "door");
        });

        blockConfig.getStringListValue("trapdoor_list").forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors)
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "trapdoor", clr);
            } else
                blockConfig(block, "trapdoor");
        });

        blockConfig.getStringListValue("suite_list").forEach((String block) -> {
            if (Patterns.dye.matcher(block).find()) {
                for (String clr : Patterns.colors) {
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "block", clr);
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "slab", clr);
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "stairs", clr);
                    blockConfig(block.replaceAll(Patterns.dye.pattern(), clr), "wall", clr);
                }
            } else {
                blockConfig(block, "block");
                blockConfig(block, "slab");
                blockConfig(block, "stairs");
                blockConfig(block, "wall");
            }
        });
    }

    private void buildBiomeConfigs(ConfigGroup worldGenGroup) {
        worldGenGroup.getStringListValue("biome_list").forEach((String biome) -> {
            try {
                Config cfg = new Config("conloot/worldgen/biomes", biome);
                BiomeConfig.create(biome, cfg);
                cfg.Build();
                biomeConfigs.put(biome, cfg);
            } catch (Exception err) {
                Main.LOG.error("Failed to create config for biome: " + biome);
                Main.LOG.error(err.getStackTrace());
            }
        });
    }

    public void Init() {
        Config config = new Config(Main.MOD_ID);

        mainConfig(config);
    }
}
