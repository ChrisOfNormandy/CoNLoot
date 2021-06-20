package com.github.chrisofnormandy.conloot.content.blocks;

import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

public class CustomFence {
    public static void generateBlock(String name) {
        Main.LOG.info("Generating Fence without textures.");
        AssetPackBuilder.Fence.getBlockstate(name);
        AssetPackBuilder.Fence.getBlockModels(name);
        AssetPackBuilder.Fence.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String[] textures) {
        Main.LOG.info("Generating Fence with " + textures.length + " textures.");
        AssetPackBuilder.Fence.getBlockstate(name);
        AssetPackBuilder.Fence.getBlockModels(name, textures);
        AssetPackBuilder.Fence.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading) {
        Main.LOG.info("Generating Fence and creating new textures.");
        AssetPackBuilder.Fence.getBlockstate(name);
        AssetPackBuilder.Fence.getBlockModels(name);
        AssetPackBuilder.Fence.getItemModel(name);
        AssetPackBuilder.Fence.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading, Integer frameCount, Integer frameTime, String[] frameSettings) {
        Main.LOG.info("Generating Fence and creating new animated textures.");
        AssetPackBuilder.Fence.getBlockstate(name);
        AssetPackBuilder.Fence.getBlockModels(name);
        AssetPackBuilder.Fence.getItemModel(name);
        AssetPackBuilder.Fence.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Textures.animationController(name, frameCount, frameTime, frameSettings);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }
}
