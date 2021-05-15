package com.github.chrisofnormandy.conloot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conlib.registry.ModRegister;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;
import com.github.chrisofnormandy.conloot.content.CustomCrop;
import com.github.chrisofnormandy.conloot.content.CustomResource;
import com.github.chrisofnormandy.conloot.content.blocks.CustomBlock;
import com.github.chrisofnormandy.conloot.content.blocks.CustomSlab;
import com.github.chrisofnormandy.conloot.content.blocks.CustomStairs;
import com.github.chrisofnormandy.conloot.content.blocks.CustomWall;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder;

import net.minecraft.item.Item;

public class ModBlocks {
    public static JsonBuilder jsonBuilder = new JsonBuilder();

    static void registerCrop(String name, Config config, Groups cropGroup) {
        CustomCrop.registerFromConfig(name, config, cropGroup);
    }

    static void registerBlock(String name, Config config, Groups blockGroup) {
        CustomBlock.registerBlockFromConfig(name, config, blockGroup);
    }

    static void registerSlab(String name, Config config, Groups blockGroup) {
        CustomSlab.registerBlockFromConfig(name, config, blockGroup);
        DataPackBuilder.Tags.addSlab(name);
    }

    static void registerStairs(String name, Config config, Groups blockGroup) {
        CustomStairs.registerBlockFromConfig(name, config, blockGroup);
        DataPackBuilder.Tags.addStairs(name);
    }

    static void registerWall(String name, Config config, Groups blockGroup) {
        CustomWall.registerBlockFromConfig(name, config, blockGroup);
        DataPackBuilder.Tags.addWall(name);
    }

    static void registerResource(String name, Config config, Groups itemGroup, Groups toolGroup, Groups blockGroup) {
        CustomResource.registerFromConfig(name, config, itemGroup, toolGroup, blockGroup);
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
            registerResource(name, config, itemGroup, toolGroup, blockGroup);
        });

        Main.config.metalConfigs.forEach((String name, Config config) -> {
            registerResource(name, config, itemGroup, toolGroup, blockGroup);
        });

        Main.config.plantConfigs.forEach((String name, Config config) -> {
            Matcher m = p.matcher(name);

            if (m.find()) {
                int start = Integer.parseInt(m.group(2));
                int end = Integer.parseInt(m.group(3));

                for (int i = start; i <= end; i++) {
                    if (i > 0)
                        registerCrop(m.group(1) + "_" + i, config, cropGroup);
                    else
                        registerCrop(m.group(1), config, cropGroup);
                }
            }
            else
                registerCrop(name, config, cropGroup);
        });

        Main.config.blockConfigs.forEach((String name, Config config) -> {
            if (name.contains("_slab"))
                registerSlab(name, config, blockGroup);
            else if (name.contains("_stairs"))
                registerStairs(name, config, blockGroup);
            else if (name.contains("_wall"))
                registerWall(name, config, blockGroup);
            else
                registerBlock(name, config, blockGroup);
        });

        AssetPackBuilder.Lang.write();
        DataPackBuilder.Tags.write();
    }
}