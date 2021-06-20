package com.github.chrisofnormandy.conloot.content.blocks;

import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

public class CustomWall {
    public static void generateBlock(String name) {
        Main.LOG.info("Generating Wall without textures.");
        AssetPackBuilder.Wall.getBlockstate(name);
        AssetPackBuilder.Wall.getBlockModels(name);
        AssetPackBuilder.Wall.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String[] textures) {
        Main.LOG.info("Generating Wall with " + textures.length + " textures.");
        AssetPackBuilder.Wall.getBlockstate(name);
        AssetPackBuilder.Wall.getBlockModels(name, textures);
        AssetPackBuilder.Wall.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading) {
        Main.LOG.info("Generating Wall and creating new textures.");
        AssetPackBuilder.Wall.getBlockstate(name);
        AssetPackBuilder.Wall.getBlockModels(name);
        AssetPackBuilder.Wall.getItemModel(name);
        AssetPackBuilder.Wall.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading, Integer frameCount, Integer frameTime, String[] frameSettings) {
        Main.LOG.info("Generating Wall and creating new animated textures.");
        AssetPackBuilder.Wall.getBlockstate(name);
        AssetPackBuilder.Wall.getBlockModels(name);
        AssetPackBuilder.Wall.getItemModel(name);
        AssetPackBuilder.Wall.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Textures.animationController(name, frameCount, frameTime, frameSettings);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }
}
