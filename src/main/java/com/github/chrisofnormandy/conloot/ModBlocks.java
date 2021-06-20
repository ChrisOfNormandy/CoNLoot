package com.github.chrisofnormandy.conloot;

import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;
import com.github.chrisofnormandy.conloot.content.CustomCrop;
import com.github.chrisofnormandy.conloot.content.CustomResource;
import com.github.chrisofnormandy.conloot.content.blocks.CreationBase;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder;

public class ModBlocks {
    public static JsonBuilder jsonBuilder = new JsonBuilder();

    private static void registerCrop(String name, Config config, Groups cropGroup) {
        CustomCrop.registerFromConfig(name, config, cropGroup);
    }

    private static void registerBlock(String name, Config config, Groups blockGroup) {
        CreationBase.registerBlockFromConfig(name, config, blockGroup);
    }

    private static void registerSlab(String name, Config config, Groups blockGroup) {
        CreationBase.registerBlockFromConfig(name, config, blockGroup);
        DataPackBuilder.Tags.addSlab(name);
    }

    private static void registerStairs(String name, Config config, Groups blockGroup) {
        CreationBase.registerBlockFromConfig(name, config, blockGroup);
        DataPackBuilder.Tags.addStairs(name);
    }

    private static void registerWall(String name, Config config, Groups blockGroup) {
        CreationBase.registerBlockFromConfig(name, config, blockGroup);
        DataPackBuilder.Tags.addWall(name);
    }

    private static void registerFence(String name, Config config, Groups blockGroup) {
        CreationBase.registerBlockFromConfig(name, config, blockGroup);
        DataPackBuilder.Tags.addFence(name);
    }

    private static void registerFenceGate(String name, Config config, Groups blockGroup) {
        CreationBase.registerBlockFromConfig(name, config, blockGroup);
        DataPackBuilder.Tags.addFenceGate(name);
    }

    private static void registerDoor(String name, Config config, Groups blockGroup) {
        CreationBase.registerBlockFromConfig(name, config, blockGroup);
        DataPackBuilder.Tags.addDoor(name);
    }

    private static void registerResource(String name, Config config, Groups itemGroup, Groups toolGroup, Groups blockGroup) {
        CustomResource.registerFromConfig(name, config, itemGroup, toolGroup, blockGroup);
    }

    private static void resourceSetup(String name, Config config, Groups itemGroup, Groups toolGroup, Groups blockGroup) {
        registerResource(name, config, itemGroup, toolGroup, blockGroup);
    }

    private static void cropSetup(String name, Config config, Groups cropGroup) {
        registerCrop(name, config, cropGroup);
    }

    private static void blockSetup(String name, Config config, Groups blockGroup) {
        // Replace this with regex later.
        if (name.contains("_slab"))
            registerSlab(name, config, blockGroup);
        else if (name.contains("_stairs"))
            registerStairs(name, config, blockGroup);
        else if (name.contains("_wall"))
            registerWall(name, config, blockGroup);
        else if (name.contains("_fence"))
            registerFence(name, config, blockGroup);
        else if (name.contains("_fence_gate"))
            registerFenceGate(name, config, blockGroup);
        else if (name.contains("_door"))
            registerDoor(name, config, blockGroup);
        else
            registerBlock(name, config, blockGroup);
    }

    public static void Init() {
        Main.config.gemConfigs.forEach((String name, Config config) -> resourceSetup(name, config, ModGroups.itemGroup,
                ModGroups.toolGroup, ModGroups.blockGroup));
        Main.config.metalConfigs.forEach((String name, Config config) -> resourceSetup(name, config,
                ModGroups.itemGroup, ModGroups.toolGroup, ModGroups.blockGroup));

        Main.config.cropConfigs.forEach((String name, Config config) -> cropSetup(name, config, ModGroups.cropGroup));
        
        Main.config.blockConfigs
                .forEach((String name, Config config) -> blockSetup(name, config, ModGroups.blockGroup));

        AssetPackBuilder.Lang.write();
        DataPackBuilder.Tags.write();
    }
}