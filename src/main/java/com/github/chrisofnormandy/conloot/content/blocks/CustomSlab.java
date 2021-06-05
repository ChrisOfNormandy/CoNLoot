package com.github.chrisofnormandy.conloot.content.blocks;

import com.github.chrisofnormandy.conlib.block.ModBlock;
import com.github.chrisofnormandy.conlib.block.subsets.Subsets;
import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conlib.registry.Blocks;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;

public class CustomSlab {
    public static void generateBlock(String name) {
        AssetPackBuilder.Blockstate.slab(name);
        AssetPackBuilder.Model.Block.slab(name);
        AssetPackBuilder.Model.Item.slab(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String[] base, String[] template, String[] colors, String mode, Boolean templateShading) {
        AssetPackBuilder.Blockstate.slab(name);
        AssetPackBuilder.Model.Block.slab(name, base, template, colors, mode, templateShading);
        AssetPackBuilder.Model.Item.slab(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void registerBlockFromConfig(String name, Config config, Groups blockGroup) {
        Main.LOG.info("Generating new slab:" + name);

        Integer harvestLevel = config.getIntegerValue("harvest_level");
        Float strength = config.getDoubleValue("strength").floatValue();
        String type = config.getStringValue("block_type");
        // String model = config.getStringValue("block_model");

        String[] colors = config.getSubgroup("Colors").getStringListValue("color").toArray(new String[0]);
        String mode = config.getSubgroup("Colors").getStringValue("blend_mode");

        String[] template = config.getSubgroup("Assets").getStringListValue("templates").toArray(new String[0]);
        String[] base = config.getSubgroup("Assets").getStringListValue("bases").toArray(new String[0]);

        Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

        switch (type) {
            case "stone": {
                Blocks.register(name, Subsets.create_slab(ModBlock.Stone.create(harvestLevel, strength)), blockGroup);
                break;
            }
            case "wood": {
                Blocks.register(name, Subsets.create_slab(ModBlock.Wood.create(harvestLevel, strength)), blockGroup);
                break;
            }
            case "bricks": {
                Blocks.register(name, Subsets.create_slab(ModBlock.Bricks.create(harvestLevel, strength)), blockGroup);
                break;
            }
            default: {
                Blocks.register(name, Subsets.create_slab(ModBlock.Stone.create(harvestLevel, strength)), blockGroup);
                break;
            }
        }

        generateBlock(name, base, template, colors, mode, templateShading);

        DataPackBuilder.LootTable.block(Main.MOD_ID + ":" + name);
    }
}
