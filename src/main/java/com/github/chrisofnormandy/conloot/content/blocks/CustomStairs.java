package com.github.chrisofnormandy.conloot.content.blocks;

import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

public class CustomStairs {
    public static void generateBlock(String name) {
        Main.LOG.info("Generating Stairs without textures.");
        AssetPackBuilder.Stairs.getBlockstate(name);
        AssetPackBuilder.Stairs.getBlockModels(name);
        AssetPackBuilder.Stairs.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String[] textures) {
        Main.LOG.info("Generating Stairs with " + textures.length + " textures.");
        AssetPackBuilder.Stairs.getBlockstate(name);
        AssetPackBuilder.Stairs.getBlockModels(name, textures);
        AssetPackBuilder.Stairs.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading) {
        Main.LOG.info("Generating Stairs and creating new textures.");
        AssetPackBuilder.Stairs.getBlockstate(name);
        AssetPackBuilder.Stairs.getBlockModels(name);
        AssetPackBuilder.Stairs.getItemModel(name);
        AssetPackBuilder.Stairs.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading, Integer frameCount, Integer frameTime, String[] frameSettings) {
        Main.LOG.info("Generating Stairs and creating new animated textures.");
        AssetPackBuilder.Stairs.getBlockstate(name);
        AssetPackBuilder.Stairs.getBlockModels(name);
        AssetPackBuilder.Stairs.getItemModel(name);
        AssetPackBuilder.Stairs.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Textures.animationController(name, frameCount, frameTime, frameSettings);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }
}
