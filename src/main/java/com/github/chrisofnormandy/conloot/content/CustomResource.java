package com.github.chrisofnormandy.conloot.content;

import com.github.chrisofnormandy.conlib.block.ModBlock;
import com.github.chrisofnormandy.conlib.block.types.OreBase;
// import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conlib.tool.ToolMaterial;
import com.github.chrisofnormandy.conloot.Main;
// import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;
import com.github.chrisofnormandy.conloot.content.world_gen.CustomBiome;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class CustomResource {
    public static void registerFromConfig(String name, Config config, ItemGroup itemGroup, ItemGroup toolGroup, ItemGroup blockGroup) {
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

            Block oreBlock_reg = ModBlock.Ore.registerGem(name, oreName, oreBlock, material, itemGroup, toolGroup, blockGroup);

            DataPackBuilder.LootTable.block(Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name);
            DataPackBuilder.Recipe.Smelting.run(name, Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name, 1);

            // String[] oreColors = config.getSubgroup("Colors").getStringListValue("ore_color").toArray(new String[0]);
            // String[] colors = config.getSubgroup("Colors").getStringListValue("resource_color").toArray(new String[0]);

            // Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

            // String oreMode = config.getSubgroup("Colors").getStringValue("ore_blend_mode");
            // String mode = config.getSubgroup("Colors").getStringValue("resource_blend_mode");

            // String[] tools = {"pickaxe", "axe", "shovel", "hoe"};

            // if (colors.length >= 1) {
            //     CustomBlock.generateBlock(oreName, new String[]{"ore_gem_base"}, new String[]{"ore_gem"}, oreColors, oreMode, templateShading);

            //     for (String t : tools) {
            //         AssetPackBuilder.Item.getItemModel(name + "_" + t);
            //         AssetPackBuilder.Item.createTexture(name + "_" + t, new String[]{t + "_base"}, new String[]{t}, colors, mode, templateShading);
            //         AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
            //     }
            // }
            // else {
            //     CustomBlock.generateBlock(oreName);

            //     for (String t : tools) {
            //         AssetPackBuilder.Item.getItemModel(name + "_" + t);
            //         AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
            //     }
            // }

            CustomBiome.biomeBuilder.biomes.get("beach").addOreFeature(oreName, oreBlock_reg, 8);
        }
        else if (resourceTypeStr.equals("ingot")) {
            ToolMaterial.type resourceType = ToolMaterial.type.ingot;
            ToolMaterial material = new ToolMaterial(level, maxDamage, immuneToFire, rarity, noRepair, resourceType);

            Block oreBlock_reg = ModBlock.Ore.registerMetal(name, oreName, oreBlock, material, itemGroup, toolGroup, blockGroup);
            
            DataPackBuilder.LootTable.block(Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name);
            DataPackBuilder.Recipe.Smelting.run(name, Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name, 1);

            // String[] oreColors = config.getSubgroup("Colors").getStringListValue("ore_color").toArray(new String[0]);
            // String[] colors = config.getSubgroup("Colors").getStringListValue("resource_color").toArray(new String[0]);

            // Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

            // String oreMode = config.getSubgroup("Colors").getStringValue("ore_blend_mode");
            // String mode = config.getSubgroup("Colors").getStringValue("resource_blend_mode");

            // String[] tools = {"pickaxe", "axe", "shovel", "hoe"};

            // if (colors.length >= 1) {
            //     CustomBlock.generateBlock(oreName, new String[]{"ore_base"}, new String[]{"ore"}, oreColors, oreMode, templateShading);

            //     for (String t : tools) {
            //         AssetPackBuilder.Item.getItemModel(name + "_" + t);
            //         AssetPackBuilder.Item.createTexture(name + "_" + t, new String[] { t + "_base" }, new String[] { t },
            //                 colors, mode, templateShading);
            //         AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
            //     }
            // }
            // else {
            //     CustomBlock.generateBlock(oreName);

            //     for (String t : tools) {
            //         AssetPackBuilder.Item.getItemModel(name + "_" + t);
            //         AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
            //     }
            // }

            CustomBiome.biomeBuilder.biomes.get("beach").addOreFeature(oreName, oreBlock_reg, 8);
        }
        else {
            Block oreBlock_reg = ModBlock.Ore.register(oreName, oreBlock, blockGroup);

            // AssetPackBuilder.Block.getBlockstate(oreName);
            // AssetPackBuilder.Block.getBlockModels(oreName);
            // AssetPackBuilder.Lang.addBlock(oreName, StringUtil.wordCaps_repl(oreName));

            CustomBiome.biomeBuilder.biomes.get("beach").addOreFeature(oreName, oreBlock_reg, 8);
        }
    }
}
