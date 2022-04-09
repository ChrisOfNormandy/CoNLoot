package com.github.chrisofnormandy.conloot.configs.blocks;

import java.util.ArrayList;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conloot.configs.ConfigBase;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;

public class OreConfig {
    public static void create(String name, Config cfg, ConfigOptions options) {

        ConfigBase.create(name, cfg, options);

        cfg.addFlag("generate_ore", true, "Should an ore block be registered?");
        cfg.addFlag("generate_tools", true, "Should tools be registered for this material?");
        cfg.addFlag("generage_armour", true, "Should armour be registered for this material?");

        ConfigGroup assets = new ConfigGroup();
        assets.addFlag("use_asset", true, "Should an asset pack be generated for this resource?");

        ConfigGroup colors = new ConfigGroup();
        colors.addStringList("ore_color", new ArrayList<String>(),
                "RGB value for default generated assets. Used for ore texture.");
        colors.addStringList("resource_color", new ArrayList<String>(),
                "RGB value for default generated assets. Used for item, tool texture.");
        colors.addString("ore_blend_mode", "sharp",
                "How colors are distributed using the ore template | gradient: a curve blend of colors; sharp: no blending between colors; spotted: uses the first color as a base and applies the rest as spots.");
        colors.addString("resource_blend_mode", "sharp",
                "How colors are distributed using the item template | gradient: a curve blend of colors; sharp: no blending between colors; spotted: uses the first color as a base and applies the rest as spots.");
        colors.addFlag("template_shading", true,
                "Should the template be registered as a brightness value when applying colors? False will use base layer instead.");

        ConfigGroup oreAsset = new ConfigGroup();
        oreAsset.addString("ore_template", "ore",
                "The PNG name to use as the ore template. Should be a cutout of the ore vein.");
        oreAsset.addString("ore_template_base", "ore",
                "The PNG name to use as the ore base. Should be a full block texture, such as stone, or a texture with the vein portion removed.");

        cfg.addSubgroup("Colors", colors);
        cfg.addSubgroup("OreAssets", oreAsset);
        cfg.addSubgroup("Assets", assets);

        ConfigGroup oreGroup = new ConfigGroup();
        oreGroup.addString("ore_name", name + "_ore", "The ore " + name + " will generate as.");
        oreGroup.addInteger("harvest_level", 2,
                "The ore hardness level. Gold: 0; Wood: 0; Stone: 1; Iron: 2; Diamond: 3; Netherite: 4");
        oreGroup.addDouble("strength", 3.0, "The ore hardness level. Iron Ore: 3; Obsidian: 50");
        cfg.addSubgroup("Ore", oreGroup);

        ConfigGroup toolMaterialGroup = new ConfigGroup();
        oreGroup.addString("resource_name", name, "The resource name " + name + " will be defined as.");
        toolMaterialGroup.addInteger("level", 1, "Tool level.");
        toolMaterialGroup.addInteger("max_damage", 1, "Max damage.");
        toolMaterialGroup.addFlag("immune_to_fire", false, "Is the tool immune to fire?");
        toolMaterialGroup.addString("rarity", "common",
                "Item rarity. Accepts common (white), uncommon (yellow), rare (aqua), epic (light purple).");
        toolMaterialGroup.addFlag("no_repair", false, "Is the tool unrepairable?");
        toolMaterialGroup.addString("resource_type", "gem",
                "Used for classification. Accepts gem, ingot, material.");
        cfg.addSubgroup("ToolMaterial", toolMaterialGroup);
    }
}
