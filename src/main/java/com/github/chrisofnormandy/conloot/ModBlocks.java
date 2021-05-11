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

    static void registerFromConfig(String name, Config config, Groups itemGroup, Groups toolGroup, Groups blockGroup) {
        Main.LOG.info("Generating new resource:" + name);

        ConfigGroup ore = config.getSubgroup("Ore");
        ConfigGroup tool = config.getSubgroup("ToolMaterial");
        
        String oreName = ore.getStringValue("ore_name");
        Integer harvestLevel = ore.getIntegerValue("harvest_level");

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

        OreBase oreBlock = new OreBase(harvestLevel);

        // Here is where a check for "gen_tools, armour, ore..." would be made.
        Main.LOG.info("Registering from config, type: " + name + " -> " + resourceTypeStr);

        if (resourceTypeStr.equals("gem")) {
            ToolMaterial.type resourceType = ToolMaterial.type.gem;

            ToolMaterial material = new ToolMaterial(level, maxDamage, immuneToFire, rarity, noRepair, resourceType);

            ModBlock.Ore.registerGem(name, oreName, oreBlock, material, itemGroup, toolGroup, blockGroup);
            AssetPackBuilder.Blockstate.block(oreName);
            AssetPackBuilder.Model.Block.block(oreName);
            AssetPackBuilder.Model.Item.block(oreName);
            AssetPackBuilder.Lang.addBlock(oreName, StringUtil.wordCaps_repl(oreName));
            DataPackBuilder.LootTable.block(Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name);
            DataPackBuilder.Recipe.Smelting.run(name, Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name, 1);
        }
        else if (resourceTypeStr.equals("ingot")) {
            ToolMaterial.type resourceType = ToolMaterial.type.ingot;
            ToolMaterial material = new ToolMaterial(level, maxDamage, immuneToFire, rarity, noRepair, resourceType);

            ModBlock.Ore.registerMetal(name, oreName, oreBlock, material, itemGroup, toolGroup, blockGroup);
            AssetPackBuilder.Blockstate.block(oreName);
            AssetPackBuilder.Model.Block.block(oreName);
            AssetPackBuilder.Lang.addBlock(oreName, StringUtil.wordCaps_repl(oreName));
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
            }
}