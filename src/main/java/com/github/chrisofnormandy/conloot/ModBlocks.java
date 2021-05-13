package com.github.chrisofnormandy.conloot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.chrisofnormandy.conlib.block.ModBlock;
import com.github.chrisofnormandy.conlib.block.types.OreBase;
import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conlib.registry.ModRegister;
import com.github.chrisofnormandy.conlib.tool.ToolMaterial;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conlib.crop.CropBase;
import com.github.chrisofnormandy.conlib.crop.SeedBase;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.common.StringUtil;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class ModBlocks {
    public static JsonBuilder jsonBuilder = new JsonBuilder();

    static void registerCropFromConfig(String name, Config config, Groups cropGroup) {
        Main.LOG.info("Generating new crop:" + name);
        RenderType transparentRenderType = RenderType.cutoutMipped();

        Block crop = ModRegister.registerCrop(new CropBase(), name + "_crop");
        RenderTypeLookup.setRenderLayer(crop, transparentRenderType);
        
        ModRegister.registerFood(name, 1, 1, cropGroup);
        ModRegister.registerItem(name + "_seeds", new SeedBase(crop, new Item.Properties().tab(cropGroup)));
    }

    static void generateBlock(String name) {
        AssetPackBuilder.Blockstate.block(name);
        AssetPackBuilder.Model.Block.block(name);
        AssetPackBuilder.Model.Item.block(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    static void generateBlock(String name, String base, String template, String[] colors, String mode, Boolean templateShading) {
        AssetPackBuilder.Blockstate.block(name);
        AssetPackBuilder.Model.Block.block(name, base, template, colors, mode, templateShading);
        AssetPackBuilder.Model.Item.block(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    static void generateBlock(String name, String base, String[] colors, String mode, Boolean templateShading) {
        AssetPackBuilder.Blockstate.block(name);
        AssetPackBuilder.Model.Block.block(name, base, base, colors, mode, templateShading);
        AssetPackBuilder.Model.Item.block(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    static void registerBlockFromConfig(String name, Config config, Groups blockGroup) {
        Main.LOG.info("Generating new block:" + name);

        Integer harvestLevel = config.getIntegerValue("harvest_level");
        Float strength = config.getDoubleValue("strength").floatValue();
        String type = config.getStringValue("block_type");

        String[] colors = config.getSubgroup("Colors").getStringListValue("color").toArray(new String[0]);
        String mode = config.getSubgroup("Colors").getStringValue("blend_mode");

        String template = config.getSubgroup("Assets").getStringValue("template");
        String base = config.getSubgroup("Assets").getStringValue("base");

        Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

        switch (type) {
            case "stone": {
                ModBlock.Stone.register(name, harvestLevel, strength, blockGroup);
                break;
            }
            case "wood": {
                ModBlock.Wood.register(name, harvestLevel, strength, blockGroup);
                break;
            }
            case "bricks": {
                ModBlock.Bricks.register(name, harvestLevel, strength, blockGroup);
                break;
            }
            default: {
                ModBlock.Stone.register(name, harvestLevel, strength, blockGroup);
                break;
            }
        }

        generateBlock(name, base, template, colors, mode, templateShading);

        DataPackBuilder.LootTable.block(Main.MOD_ID + ":" + name);
    }

    static void registerFromConfig(String name, Config config, Groups itemGroup, Groups toolGroup, Groups blockGroup) {
        Main.LOG.info("Generating new resource:" + name);

        ConfigGroup ore = config.getSubgroup("Ore");
        ConfigGroup tool = config.getSubgroup("ToolMaterial");
        
        String oreName = ore.getStringValue("ore_name");
        Integer harvestLevel = ore.getIntegerValue("harvest_level");
        Float strength = ore.getDoubleValue("strength").floatValue();

        Integer level = tool.getIntegerValue("level");
        Integer maxDamage = tool.getIntegerValue("max_damage");
        Boolean immuneToFire = tool.getFlagValue("immune_to_fire");
        Boolean noRepair = tool.getFlagValue("no_repair");

        String rarityStr = tool.getStringValue("rarity");
        String resourceTypeStr = tool.getStringValue("resource_type");

        Rarity rarity = Rarity.COMMON;
        switch (rarityStr) {
            case "uncommon": {
                rarity = Rarity.UNCOMMON;
                break;
            }
            case "rare": {
                rarity = Rarity.RARE;
                break;
            }
            case "epic": {
                rarity = Rarity.EPIC;
                break;
            }
        }

        OreBase oreBlock = new OreBase(harvestLevel, strength);

        // Here is where a check for "gen_tools, armour, ore..." would be made.
        Main.LOG.info("Registering from config, type: " + name + " -> " + resourceTypeStr);

        if (resourceTypeStr.equals("gem")) {
            ToolMaterial.type resourceType = ToolMaterial.type.gem;

            ToolMaterial material = new ToolMaterial(level, maxDamage, immuneToFire, rarity, noRepair, resourceType);

            ModBlock.Ore.registerGem(name, oreName, oreBlock, material, itemGroup, toolGroup, blockGroup);

            DataPackBuilder.LootTable.block(Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name);
            DataPackBuilder.Recipe.Smelting.run(name, Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name, 1);

            String[] oreColors = config.getSubgroup("Colors").getStringListValue("ore_color").toArray(new String[0]);
            String[] colors = config.getSubgroup("Colors").getStringListValue("resource_color").toArray(new String[0]);

            Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

            String oreMode = config.getSubgroup("Colors").getStringValue("ore_blend_mode");
            String mode = config.getSubgroup("Colors").getStringValue("resource_blend_mode");

            String[] tools = {"pickaxe", "axe", "shovel", "hoe"};

            if (colors.length >= 1) {
                generateBlock(oreName, "ore_gem_base", "ore_gem", oreColors, oreMode, templateShading);

                for (String t : tools) {
                    AssetPackBuilder.Model.Item.item(name + "_" + t, t + "_base", t, colors, mode, templateShading);
                    AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
                }
            }
            else {
                generateBlock(oreName);

                for (String t : tools) {
                    AssetPackBuilder.Model.Item.item(name + "_" + t);
                    AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
                }
            }
        }
        else if (resourceTypeStr.equals("ingot")) {
            ToolMaterial.type resourceType = ToolMaterial.type.ingot;
            ToolMaterial material = new ToolMaterial(level, maxDamage, immuneToFire, rarity, noRepair, resourceType);

            ModBlock.Ore.registerMetal(name, oreName, oreBlock, material, itemGroup, toolGroup, blockGroup);
            
            DataPackBuilder.LootTable.block(Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name);
            DataPackBuilder.Recipe.Smelting.run(name, Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name, 1);

            String[] oreColors = config.getSubgroup("Colors").getStringListValue("ore_color").toArray(new String[0]);
            String[] colors = config.getSubgroup("Colors").getStringListValue("resource_color").toArray(new String[0]);

            Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

            String oreMode = config.getSubgroup("Colors").getStringValue("ore_blend_mode");
            String mode = config.getSubgroup("Colors").getStringValue("resource_blend_mode");

            String[] tools = {"pickaxe", "axe", "shovel", "hoe"};

            if (colors.length >= 1) {
                generateBlock(oreName, "ore_base", "ore", oreColors, oreMode, templateShading);

                for (String t : tools) {
                    AssetPackBuilder.Model.Item.item(name + "_" + t, t + "_base", t, colors, mode, templateShading);
                    AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
                }
            }
            else {
                generateBlock(oreName);

                for (String t : tools) {
                    AssetPackBuilder.Model.Item.item(name + "_" + t);
                    AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
                }
            }
        }
        else {
            ModBlock.Ore.register(oreName, oreBlock, blockGroup);
            AssetPackBuilder.Blockstate.block(oreName);
            AssetPackBuilder.Model.Block.block(oreName);
            AssetPackBuilder.Lang.addBlock(oreName, StringUtil.wordCaps_repl(oreName));
        }
    }

    public static void Init() {
        Groups itemGroup = Groups.createGroup("item_group", ModRegister.registerItem("item_group_icon", new Item.Properties()));
        Groups toolGroup = Groups.createGroup("tool_group", ModRegister.registerItem("tool_group_icon", new Item.Properties()));
        Groups blockGroup = Groups.createGroup("ore_group", ModRegister.registerItem("ore_group_icon", new Item.Properties()));
        Groups cropGroup = Groups.createGroup("crop_group", ModRegister.registerItem("crop_group_icon", new Item.Properties()));
        
        //NEXT UP:
        /*
            Add swords to the lib
            And armour

            have a full block of the main resource generated

            The lib should generate a new resource pack for the user containing basic JSON stuff (?) and the translation file (?)
            Have a notice show to the user on login about the resource pack?
        */

        Pattern p = Pattern.compile("(\\w+)\\[(\\d+)-(\\d+)\\]");

        Main.config.gemConfigs.forEach((String name, Config config) -> {
            registerFromConfig(name, config, itemGroup, toolGroup, blockGroup);
        });

        Main.config.metalConfigs.forEach((String name, Config config) -> {
            registerFromConfig(name, config, itemGroup, toolGroup, blockGroup);
        });

        Main.config.plantConfigs.forEach((String name, Config config) -> {
            Matcher m = p.matcher(name);

            if (m.find()) {
                int start = Integer.parseInt(m.group(2));
                int end = Integer.parseInt(m.group(3));

                for (int i = start; i <= end; i++) {
                    if (i > 0)
                        registerCropFromConfig(m.group(1) + "_" + i, config, cropGroup);
                    else
                        registerCropFromConfig(m.group(1), config, cropGroup);
                }
            }
            else
                registerCropFromConfig(name, config, cropGroup);
        });

        Main.config.blockConfigs.forEach((String name, Config config) -> {
            registerBlockFromConfig(name, config, blockGroup);
        });

        AssetPackBuilder.Lang.write();
    }
}