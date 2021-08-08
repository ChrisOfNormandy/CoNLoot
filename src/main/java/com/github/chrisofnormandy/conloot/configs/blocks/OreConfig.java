package com.github.chrisofnormandy.conloot.configs.blocks;

import java.util.ArrayList;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;

public class OreConfig {
    public static void create(String name, Config cfg, String resourceType) {
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

        ConfigGroup toolAsset = new ConfigGroup();
        toolAsset.addString("pickaxe_template", "pickaxe", "The PNG name to use as the pick head template.");
        toolAsset.addString("axe_template", "axe", "The PNG name to use as the axe head template.");
        toolAsset.addString("shovel_template", "shovel", "The PNG name to use as the shovel head template.");
        toolAsset.addString("hoe_template", "hoe", "The PNG name to use as the hoe head template.");
        toolAsset.addString("pickaxe_template_base", "pickaxe_base",
                "The PNG name to use as the pick handle template.");
        toolAsset.addString("axe_template_base", "axe_base", "The PNG name to use as the axe handle template.");
        toolAsset.addString("shovel_template_base", "shovel_base",
                "The PNG name to use as the shovel handle template.");
        toolAsset.addString("hoe_template_base", "hoe_base", "The PNG name to use as the hoe handle template.");

        cfg.addSubgroup("Colors", colors);
        cfg.addSubgroup("OreAssets", oreAsset);
        cfg.addSubgroup("ToolAssets", toolAsset);
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
        toolMaterialGroup.addString("resource_type", resourceType,
                "Used for classification. Accepts gem, ingot, material.");
        cfg.addSubgroup("ToolMaterial", toolMaterialGroup);
    }
}
