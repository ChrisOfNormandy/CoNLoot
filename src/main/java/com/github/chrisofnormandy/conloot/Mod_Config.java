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
    HashMap<String, Config> biomeConfigs = new HashMap<String, Config>();
    
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
        colorList.add("0, 0, 0");

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
        cfg.addString("block_model", "full", "What model should the block adapt? Options: full, slab, stairs, wall | Other options: suite: register full block, slab, stairs. For stone, wall. For wood, fence and fence gate, door and trapdoor.");

        ConfigGroup colors = new ConfigGroup();
        List<String> colorList = new ArrayList<String>();
        colorList.add("0, 0, 0");

        colors.addStringList("color", colorList, "RGB value for default generated assets. Used for overlay texture.");
        colors.addString("blend_mode", "sharp", "How colors are distributed using the overlay template | gradient: a curve blend of colors; sharp: no blending between colors; spotted: uses the first color as a base and applies the rest as spots.");
        colors.addFlag("template_shading", true, "Should the template be registered as a brightness value when applying colors? False will use base layer instead.");
        cfg.addSubgroup("Colors", colors);

        ConfigGroup assets = new ConfigGroup();
        assets.addString("template", "noise", "The PNG name to use as the asset template. Should be a cutout of the colored / custom portion.");
        assets.addString("base", "noise", "The PNG name to use as the asset base. Should be a full block texture, such as stone, or a texture with the colored / custom portion removed.");
        cfg.addSubgroup("Assets", assets);

    }

    void buildSlabCfg(String name, Config cfg) {
        cfg.addInteger("harvest_level", 1, "The block hardness level. Gold: 0; Wood: 0; Stone: 1; Iron: 2; Diamond: 3; Netherite: 4");
        cfg.addDouble("strength", 1.5, "The ore hardness level. Stone: 1.5; Obsidian: 50");
        cfg.addString("block_type", "stone", "The base block type. Accepted values: stone, wood, bricks");
        cfg.addString("block_model", "full", "What model should the block adapt? Options: full, slab, stairs, wall | Other options: suite: register full block, slab, stairs. For stone, wall. For wood, fence and fence gate, door and trapdoor.");

        ConfigGroup colors = new ConfigGroup();
        List<String> colorList = new ArrayList<String>();
        colorList.add("0, 0, 0");

        colors.addStringList("color", colorList, "RGB value for default generated assets. Used for overlay texture.");
        colors.addString("blend_mode", "sharp", "How colors are distributed using the overlay template | gradient: a curve blend of colors; sharp: no blending between colors; spotted: uses the first color as a base and applies the rest as spots.");
        colors.addFlag("template_shading", true, "Should the template be registered as a brightness value when applying colors? False will use base layer instead.");
        cfg.addSubgroup("Colors", colors);

        ConfigGroup assets = new ConfigGroup();
        List<String> assetList = new ArrayList<String>();
        assetList.add("noise");
        assetList.add("noise");
        assetList.add("noise");

        assets.addStringList("templates", assetList, "The PNG names to use as the asset template. Should be a cutout of the colored / custom portion. Order: bottom, top, side.");
        assets.addStringList("bases", assetList, "The PNG names to use as the asset base. Should be a full block texture, such as stone, or a texture with the colored / custom portion removed. Order: bottom, top, side.");
        cfg.addSubgroup("Assets", assets);
    }

    void buildBiomeCfg(String name, Config cfg) {
        ConfigGroup effects = new ConfigGroup();
        ConfigGroup carvers = new ConfigGroup();
        ConfigGroup features = new ConfigGroup();
        ConfigGroup starts = new ConfigGroup();
        ConfigGroup spawners = new ConfigGroup();
        
        cfg.addDouble("scale", 0.05, "Scale.");
        cfg.addString("surface_builder", "minecraft:grass", "Surface builder.");
        cfg.addFlag("player_spawn_friendly", true, "Can the player spawn in this biome?");
        cfg.addString("precipitation", "rain", "Rain type.");
        cfg.addDouble("temperature", 0.8, "Temperature.");
        cfg.addDouble("downfall", 0.4, "Downfall.");
        cfg.addString("category", "plains", "Category.");
        cfg.addDouble("depth", 0.125, "Depth");

        cfg.addSubgroup("Effects", effects);
        cfg.addSubgroup("Carvers", carvers);
        cfg.addSubgroup("Features", features);
        cfg.addSubgroup("Starts", starts);
        cfg.addSubgroup("Spawners", spawners);

        ConfigGroup mood_sound = new ConfigGroup();
        mood_sound.addFlag("enable", false, "Enable mood sound.");
        mood_sound.addString("sound", "minecraft:ambient.cave", "Sound.");
        mood_sound.addInteger("tick_delay", 6000, "Tick delay.");
        mood_sound.addInteger("block_search_extent", 8, "Block search extent");
        mood_sound.addDouble("offset", 2.0, "Offset.");
        effects.addSubgroup("mood_sound", mood_sound);

        ConfigGroup additions_sound = new ConfigGroup();
        additions_sound.addFlag("enable", false, "Enable additions sound.");
        additions_sound.addString("sound", "none", "Ambient sound.");
        additions_sound.addDouble("tick_chance", 0.0, "Tick chance.");
        effects.addSubgroup("additions_sound", additions_sound);

        ConfigGroup music = new ConfigGroup();
        music.addFlag("enable", false, "Enable music.");
        music.addString("sound", "none", "Sound.");
        music.addInteger("min_delay", 12000, "Min delay.");
        music.addInteger("max_delay", 24000, "Max delay.");
        music.addFlag("replace_current_music", false, "Replace current music.");
        effects.addSubgroup("music", music);

        ConfigGroup ambient_sound = new ConfigGroup();
        ambient_sound.addFlag("enable", false, "Enable.");
        ambient_sound.addString("sound", "none", "Sound.");
        effects.addSubgroup("ambient_sound", ambient_sound);

        effects.addInteger("sky_color", 7907327, "Sky color.");
        effects.addInteger("fog_color", 12638463, "Fog color.");
        effects.addInteger("water_color", 4159204, "Water color.");
        effects.addInteger("water_fog_color", 329011, "Water fog color.");

        carvers.addStringList("air", new ArrayList<String>(), "Air carvers.");
        carvers.addStringList("liquid", new ArrayList<String>(), "Liquid carvers.");

        features.addStringList("raw_generation", new ArrayList<String>(), "Raw generation.");
        features.addStringList("lakes", new ArrayList<String>(), "Lakes.");
        features.addStringList("local_modifications", new ArrayList<String>(), "Local modifications.");
        features.addStringList("underground_structures", new ArrayList<String>(), "Underground structures.");
        features.addStringList("surface_structures", new ArrayList<String>(), "Surface structures.");
        features.addStringList("strongholds", new ArrayList<String>(), "Strongholds.");
        features.addStringList("underground_ores", new ArrayList<String>(), "Underground ores.");
        features.addStringList("underground_decoration", new ArrayList<String>(), "Underground decoration.");
        features.addStringList("vegetal_decoration", new ArrayList<String>(), "Vegetal decoration.");
        features.addStringList("top_layer_modification", new ArrayList<String>(), "Top layer modification");

        starts.addStringList("starts", new ArrayList<String>(), "Starts.");

        spawners.addStringList("monster", new ArrayList<String>(), "Monsters.");
        spawners.addStringList("creature", new ArrayList<String>(), "Creatures.");
        spawners.addStringList("ambient", new ArrayList<String>(), "Ambient creatures.");
        spawners.addStringList("water_creature", new ArrayList<String>(), "Water creatures.");
        spawners.addStringList("water_ambient", new ArrayList<String>(), "Ambient water creatures.");
        spawners.addStringList("misc", new ArrayList<String>(), "Misc.");
    }

    void mainConfig(Config config) {
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
        blockGroup.addStringList("suite_list", blockList, "A list of content that should generate a block, slab, stair, and depending on type, wall or fence.");

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

    void buildGemConfigs(ConfigGroup gemGroup) {
        gemGroup.getStringListValue("gem_list").forEach((String gem) -> {
            Config cfg = new Config("conloot/gems", gem);
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
    }

    void buildCropConfigs(ConfigGroup cropConfig) {
        cropConfig.getStringListValue("crop_list").forEach((String crop) -> {
            Config cfg = new Config("conloot/crops", crop);
            buildCropCfg(crop, cfg);
            cfg.Build();
            plantConfigs.put(crop, cfg);
        });
    }

    void bCfg(String block) {
        Config cfg = new Config("conloot/blocks", block);
        buildBlockCfg(block, cfg);
        cfg.Build();
        blockConfigs.put(block, cfg);
    }

    void sbCfg(String block) {
        Config cfg = new Config("conloot/blocks/slabs", block + "_slab");
        buildSlabCfg(block + "_slab", cfg);
        cfg.Build();
        blockConfigs.put(block + "_slab", cfg);
    }

    void stCfg(String block) {
        Config cfg = new Config("conloot/blocks/stairs", block + "_stairs");
        buildSlabCfg(block + "_stairs", cfg);
        cfg.Build();
        blockConfigs.put(block + "_stairs", cfg);
    }

    void wCfg(String block) {
        Config cfg = new Config("conloot/blocks/walls", block + "_wall");
        buildBlockCfg(block + "_wall", cfg);
        cfg.Build();
        blockConfigs.put(block + "_wall", cfg);
    }

    void buildBlockConfigs(ConfigGroup blockConfig) {
        blockConfig.getStringListValue("block_list").forEach((String block) -> {
            bCfg(block);
        });

        blockConfig.getStringListValue("slab_list").forEach((String block) -> {
            sbCfg(block);
        });

        blockConfig.getStringListValue("stairs_list").forEach((String block) -> {
            stCfg(block);
        });

        blockConfig.getStringListValue("wall_list").forEach((String block) -> {
            wCfg(block);
        });

        blockConfig.getStringListValue("suite_list").forEach((String block) -> {
            bCfg(block);
            sbCfg(block);
            stCfg(block);
            wCfg(block);
        });
    }

    void buildBiomeConfigs(ConfigGroup worldGenGroup) {
        worldGenGroup.getStringListValue("biome_list").forEach((String biome) -> {
            Config cfg = new Config("conloot/worldgen/biomes", biome);
            buildBiomeCfg(biome, cfg);
            cfg.Build();
            biomeConfigs.put(biome, cfg);
        });
    }

    public void Init() {
        Config config = new Config(Main.MOD_ID);

        mainConfig(config);
    }
}
