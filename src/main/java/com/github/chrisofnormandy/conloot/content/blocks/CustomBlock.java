package com.github.chrisofnormandy.conloot.content.blocks;

import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

public class CustomBlock {
    public static void generateBlock(String name) {
        Main.LOG.info("Generating Block without textures.");
        AssetPackBuilder.Block.getBlockstate(name);
        AssetPackBuilder.Block.getBlockModel(name);
        AssetPackBuilder.Block.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String[] textures) {
        Main.LOG.info("Generating Block with " + textures.length + " textures.");
        AssetPackBuilder.Block.getBlockstate(name);
        AssetPackBuilder.Block.getBlockModel(name, textures);
        AssetPackBuilder.Block.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading) {
        Main.LOG.info("Generating Block and creating new textures.");
        AssetPackBuilder.Block.getBlockstate(name);
        AssetPackBuilder.Block.getBlockModel(name);
        AssetPackBuilder.Block.getItemModel(name);
        AssetPackBuilder.Block.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading, Integer frameCount, Integer frameTime, String[] frameSettings) {
        Main.LOG.info("Generating Block and creating new animated textures.");
        AssetPackBuilder.Block.getBlockstate(name);
        AssetPackBuilder.Block.getBlockModel(name);
        AssetPackBuilder.Block.getItemModel(name);
        AssetPackBuilder.Block.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Textures.animationController(name, frameCount, frameTime, frameSettings);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }
}
