package com.github.chrisofnormandy.conloot.content.blocks;

import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

public class CustomFenceGate {
    public static void generateBlock(String name) {
        Main.LOG.info("Generating FenceGate without textures.");
        AssetPackBuilder.FenceGate.getBlockstate(name);
        AssetPackBuilder.FenceGate.getBlockModels(name);
        AssetPackBuilder.FenceGate.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String[] textures) {
        Main.LOG.info("Generating FenceGate with " + textures.length + " textures.");
        AssetPackBuilder.FenceGate.getBlockstate(name);
        AssetPackBuilder.FenceGate.getBlockModels(name, textures);
        AssetPackBuilder.FenceGate.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading) {
        Main.LOG.info("Generating FenceGate and creating new textures.");
        AssetPackBuilder.FenceGate.getBlockstate(name);
        AssetPackBuilder.FenceGate.getBlockModels(name);
        AssetPackBuilder.FenceGate.getItemModel(name);
        AssetPackBuilder.FenceGate.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading, Integer frameCount, Integer frameTime, String[] frameSettings) {
        Main.LOG.info("Generating FenceGate and creating new animated textures.");
        AssetPackBuilder.FenceGate.getBlockstate(name);
        AssetPackBuilder.FenceGate.getBlockModels(name);
        AssetPackBuilder.FenceGate.getItemModel(name);
        AssetPackBuilder.FenceGate.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Textures.animationController(name, frameCount, frameTime, frameSettings);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }
}
