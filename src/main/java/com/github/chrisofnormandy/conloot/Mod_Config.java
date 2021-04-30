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

        ConfigGroup oreGroup = new ConfigGroup();
        oreGroup.addString("ore_name", name + "_ore", "The ore " + name + " will generate as.");
        oreGroup.addInteger("min_xp", 0, "The minimum amount of experience to give when mined.");
        oreGroup.addInteger("max_xp", 0, "The maximum amount of experience to give when mined. Should be larger than min.");
        oreGroup.addInteger("harvest_level", 2, "The ore hardness level. Gold: 0; Wood: 0; Stone: 1; Iron: 2; Diamond: 3; Netherite: 4");
        oreGroup.addFloat("hardness", 3.0f, "The hardness of the ore block. 3 is vanilla ore hardness. Ancient debris is 30.");
        cfg.addSubgroup("Ore", oreGroup);

        ConfigGroup toolMaterialGroup = new ConfigGroup();
        toolMaterialGroup.addInteger("level", 1, "Tool level.");
        toolMaterialGroup.addInteger("max_damage", 1, "Max damage.");
        toolMaterialGroup.addFlag("immune_to_fire", false, "Is the tool immune to fire?");
        toolMaterialGroup.addString("rarity", "common", "Item rarity. Accepts common (white), uncommon (yellow), rare (aqua), epic (light purple).");
        toolMaterialGroup.addFlag("no_repair", false, "Is the tool unrepairable?");
        toolMaterialGroup.addString("resource_type", resourceType, "Used for classification. Accepts gem, ingot, material.");
        cfg.addSubgroup("ToolMaterial", toolMaterialGroup);
    }

    void mainConfig(Config config, ConfigGroup gemGroup, ConfigGroup metalGroup) {
        gemGroup.addFlag("allow_gems", true, "Should gems be a thing?");
        gemGroup.addFlag("allow_metals", true, "Should metals be a thing?");

        List<String> gemList = new ArrayList<String>();
        gemList.add("garnet");
        gemList.add("amethyst");
        gemList.add("aquamarine");
        gemList.add("bloodstone");
        gemList.add("pearl");
        gemList.add("moonstone");
        gemList.add("alexandrite");
        gemList.add("ruby");
        gemList.add("peridot");
        gemList.add("spinel");
        gemList.add("sapphire");
        gemList.add("opal");
        gemList.add("tourmaline");
        gemList.add("topaz");
        gemList.add("citrine");
        gemList.add("turquoise");
        gemList.add("zircon");
        gemList.add("tanzanite");
        gemGroup.addStringList("gem_list", gemList, "A list of default gem types. DO NOT MODIFY THIS LIST.");

        List<String> alt_gemList = new ArrayList<String>();
        gemGroup.addStringList("alt_gem_list", alt_gemList, "A list of custom gem types.");

        config.addSubgroup("Gems", gemGroup);

        gemGroup.addFlag("allow_gems", true, "Should gems be a thing?");

        List<String> metalList = new ArrayList<String>();
        metalList.add("lead");
        metalList.add("copper");
        metalList.add("tin");
        metalList.add("aluminum");
        metalList.add("nickel");
        metalList.add("zinc");
        metalList.add("cobalt");
        metalList.add("silver");
        metalGroup.addStringList("metal_list", metalList, "A list of default metal types. DO NOT MODIFY THIS LIST.");

        List<String> alt_metalList = new ArrayList<String>();
        metalGroup.addStringList("alt_metal_list", alt_metalList, "A list of custom metal types.");

        config.addSubgroup("Metals", metalGroup);

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

    public void Init() {
        Config config = new Config(Main.MOD_ID);
        ConfigGroup gemGroup = new ConfigGroup();
        ConfigGroup metalGroup = new ConfigGroup();

        mainConfig(config, gemGroup, metalGroup);
        buildGemConfigs(gemGroup);
        buildMetalConfigs(metalGroup);
    }
}
