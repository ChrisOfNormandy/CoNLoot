package com.github.chrisofnormandy.conloot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;

public class Mod_Config {
    HashMap<String, Config> configs = new HashMap<String, Config>();
    HashMap<String, Config> gemConfigs = new HashMap<String, Config>();
    HashMap<String, Config> metalConfigs = new HashMap<String, Config>();
    HashMap<String, Config> plantConfigs = new HashMap<String, Config>();
    HashMap<String, Config> blockConfigs = new HashMap<String, Config>();
    
    /**
     * 
     * @param name
     * @param cfg
     * @param resourceType gem, ingot or material
     */
    void buildResourceCfg(String name, Config cfg, String resourceType) {
        cfg.addFlag("generate_ore", true, "Should an ore block be registered?");
        cfg.addFlag("generate_tools", true, "Should tools be registered for this material?");
        cfg.addFlag("generage_armour", true, "Should armour be registered for this material?");

        ConfigGroup assets = new ConfigGroup();
        assets.addFlag("use_asset", true, "Should an asset pack be generated for this resource?");

        ConfigGroup colors = new ConfigGroup();
        List<String> colorList = new ArrayList<String>();

        colors.addStringList("ore_color", colorList, "RGB value for default generated assets. Used for ore texture.");
        colors.addStringList("resource_color", colorList, "RGB value for default generated assets. Used for item, tool texture.");
        colors.addString("ore_blend_mode", "sharp", "How colors are distributed using the ore template | gradient: a curve blend of colors; sharp: no blending between colors; spotted: uses the first color as a base and applies the rest as spots.");
        colors.addString("resource_blend_mode", "sharp", "How colors are distributed using the item template | gradient: a curve blend of colors; sharp: no blending between colors; spotted: uses the first color as a base and applies the rest as spots.");
        colors.addFlag("template_shading", true, "Should the template be registered as a brightness value when applying colors? False will use base layer instead.");

        ConfigGroup oreAsset = new ConfigGroup();
        oreAsset.addString("ore_template", "ore", "The PNG name to use as the ore template. Should be a cutout of the ore vein.");
        oreAsset.addString("ore_template_base", "ore", "The PNG name to use as the ore base. Should be a full block texture, such as stone, or a texture with the vein portion removed.");

        ConfigGroup toolAsset = new ConfigGroup();
        toolAsset.addString("pickaxe_template", "pickaxe", "The PNG name to use as the pick head template.");
        toolAsset.addString("axe_template", "axe", "The PNG name to use as the axe head template.");
        toolAsset.addString("shovel_template", "shovel", "The PNG name to use as the shovel head template.");
        toolAsset.addString("hoe_template", "hoe", "The PNG name to use as the hoe head template.");
        toolAsset.addString("pickaxe_template_base", "pickaxe_base", "The PNG name to use as the pick handle template.");
        toolAsset.addString("axe_template_base", "axe_base", "The PNG name to use as the axe handle template.");
        toolAsset.addString("shovel_template_base", "shovel_base", "The PNG name to use as the shovel handle template.");
        toolAsset.addString("hoe_template_base", "hoe_base", "The PNG name to use as the hoe handle template.");

        cfg.addSubgroup("Colors", colors);
        cfg.addSubgroup("OreAssets", oreAsset);
        cfg.addSubgroup("ToolAssets", toolAsset);
        cfg.addSubgroup("Assets", assets);

        ConfigGroup oreGroup = new ConfigGroup();
        oreGroup.addString("ore_name", name + "_ore", "The ore " + name + " will generate as.");
        oreGroup.addInteger("harvest_level", 2, "The ore hardness level. Gold: 0; Wood: 0; Stone: 1; Iron: 2; Diamond: 3; Netherite: 4");
        oreGroup.addDouble("strength", 3.0, "The ore hardness level. Iron Ore: 3; Obsidian: 50");
        cfg.addSubgroup("Ore", oreGroup);

        ConfigGroup toolMaterialGroup = new ConfigGroup();
        oreGroup.addString("resource_name", name, "The resource name " + name + " will be defined as.");
        toolMaterialGroup.addInteger("level", 1, "Tool level.");
        toolMaterialGroup.addInteger("max_damage", 1, "Max damage.");
        toolMaterialGroup.addFlag("immune_to_fire", false, "Is the tool immune to fire?");
        toolMaterialGroup.addString("rarity", "common", "Item rarity. Accepts common (white), uncommon (yellow), rare (aqua), epic (light purple).");
        toolMaterialGroup.addFlag("no_repair", false, "Is the tool unrepairable?");
        toolMaterialGroup.addString("resource_type", resourceType, "Used for classification. Accepts gem, ingot, material.");
        cfg.addSubgroup("ToolMaterial", toolMaterialGroup);
    }

    void buildCropCfg(String name, Config cfg) {
        cfg.addFlag("generate_wild", true, "Should this crop be generated in the wild?");
    }

    void buildBlockCfg(String name, Config cfg) {
        cfg.addInteger("harvest_level", 1, "The block hardness level. Gold: 0; Wood: 0; Stone: 1; Iron: 2; Diamond: 3; Netherite: 4");
        cfg.addDouble("strength", 1.5, "The ore hardness level. Stone: 1.5; Obsidian: 50");
        cfg.addString("block_type", "stone", "The base block type. Accepted values: stone, wood, bricks");

        ConfigGroup colors = new ConfigGroup();
        List<String> colorList = new ArrayList<String>();

        colors.addStringList("color", colorList, "RGB value for default generated assets. Used for overlay texture.");
        colors.addString("blend_mode", "sharp", "How colors are distributed using the overlay template | gradient: a curve blend of colors; sharp: no blending between colors; spotted: uses the first color as a base and applies the rest as spots.");
        colors.addFlag("template_shading", true, "Should the template be registered as a brightness value when applying colors? False will use base layer instead.");
        cfg.addSubgroup("Colors", colors);

        ConfigGroup assets = new ConfigGroup();
        assets.addString("template", "stone_overlay", "The PNG name to use as the asset template. Should be a cutout of the colored / custom portion.");
        assets.addString("base", "stone_base", "The PNG name to use as the asset base. Should be a full block texture, such as stone, or a texture with the colored / custom portion removed.");
        cfg.addSubgroup("Assets", assets);

    }

    void mainConfig(Config config, ConfigGroup gemGroup, ConfigGroup metalGroup, ConfigGroup cropGroup, ConfigGroup blockGroup) {
        gemGroup.addFlag("allow_gems", true, "Should gems be a thing?");
        gemGroup.addFlag("allow_metals", true, "Should metals be a thing?");
        gemGroup.addFlag("allow_crops", true, "Should crops be a thing?");
        gemGroup.addFlag("allow_blocks", true, "Should generic blocks be a thing?");

        // Gems
        List<String> gemList = new ArrayList<String>();
        gemGroup.addStringList("gem_list", gemList, "A list of gem types.");

        // Alternate Gems
        List<String> alt_gemList = new ArrayList<String>();
        gemGroup.addStringList("alt_gem_list", alt_gemList, "A list of custom gem types.");

        config.addSubgroup("Gems", gemGroup);

        gemGroup.addFlag("allow_gems", true, "Should gems be a thing?");

        // Metals
        List<String> metalList = new ArrayList<String>();
        metalGroup.addStringList("metal_list", metalList, "A list of metal types.");

        // Alternate Metals
        List<String> alt_metalList = new ArrayList<String>();
        metalGroup.addStringList("alt_metal_list", alt_metalList, "A list of custom metal types.");

        config.addSubgroup("Metals", metalGroup);

        // Crops
        List<String> cropList = new ArrayList<String>();
        cropGroup.addStringList("crop_list", cropList, "A list of crops.");

        config.addSubgroup("Crops", cropGroup);

        // Blocks
        List<String> blockList = new ArrayList<String>();
        blockGroup.addStringList("block_list", blockList, "A list of blocks.");

        config.addSubgroup("Blocks", blockGroup);

        config.Build();
        configs.put(Main.MOD_ID, config);
    }

    void buildGemConfigs(ConfigGroup gemGroup) {
        gemGroup.getStringListValue("gem_list").forEach((String gem) -> {
            Config cfg = new Config("conloot/gems", gem);
            buildResourceCfg(gem, cfg, "gem");
            cfg.Build();
            gemConfigs.put(gem, cfg);
        });

        gemGroup.getStringListValue("alt_gem_list").forEach((String gem) -> {
            Config cfg = new Config("conloot/alt_gems", gem);
            buildResourceCfg(gem, cfg, "gem");
            cfg.Build();
            gemConfigs.put(gem, cfg);
        });
    }

    void buildMetalConfigs(ConfigGroup metalGroup) {
        metalGroup.getStringListValue("metal_list").forEach((String metal) -> {
            Config cfg = new Config("conloot/metals", metal);
            buildResourceCfg(metal, cfg, "ingot");
            cfg.Build();
            metalConfigs.put(metal, cfg);
        });

        metalGroup.getStringListValue("alt_metal_list").forEach((String metal) -> {
            Config cfg = new Config("conloot/alt_metals", metal);
            buildResourceCfg(metal, cfg, "ingot");
            cfg.Build();
            metalConfigs.put(metal, cfg);
        });
    }

    void buildCropConfigs(ConfigGroup cropConfig) {
        cropConfig.getStringListValue("crop_list").forEach((String crop) -> {
            Config cfg = new Config("conloot/crops", crop);
            buildCropCfg(crop, cfg);
            cfg.Build();
            plantConfigs.put(crop, cfg);
        });
    }

    void buildBlockConfigs(ConfigGroup blockConfig) {
        blockConfig.getStringListValue("block_list").forEach((String block) -> {
            Config cfg = new Config("conloot/blocks", block);
            buildBlockCfg(block, cfg);
            cfg.Build();
            blockConfigs.put(block, cfg);
        });
    }

    public void Init() {
        Config config = new Config(Main.MOD_ID);

        ConfigGroup gemGroup = new ConfigGroup();
        ConfigGroup metalGroup = new ConfigGroup();
        ConfigGroup cropGroup = new ConfigGroup();
        ConfigGroup blockGroup = new ConfigGroup();

        mainConfig(config, gemGroup, metalGroup, cropGroup, blockGroup);

        buildGemConfigs(gemGroup);
        buildMetalConfigs(metalGroup);
        buildCropConfigs(cropGroup);
        buildBlockConfigs(blockGroup);
    }
}
