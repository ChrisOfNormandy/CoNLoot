package com.github.chrisofnormandy.conloot.content.blocks;

import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

public class CustomSlab {
    public static void generateBlock(String name) {
        Main.LOG.info("Generating Slab without textures.");
        AssetPackBuilder.Slab.getBlockstate(name);
        AssetPackBuilder.Slab.getBlockModels(name);
        AssetPackBuilder.Slab.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String[] textures) {
        Main.LOG.info("Generating Slab with " + textures.length + " textures.");
        AssetPackBuilder.Slab.getBlockstate(name);
        AssetPackBuilder.Slab.getBlockModels(name, textures);
        AssetPackBuilder.Slab.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading) {
        Main.LOG.info("Generating Slab and creating new textures.");
        AssetPackBuilder.Slab.getBlockstate(name);
        AssetPackBuilder.Slab.getBlockModels(name);
        AssetPackBuilder.Slab.getItemModel(name);
        AssetPackBuilder.Slab.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading, Integer frameCount, Integer frameTime, String[] frameSettings) {
        Main.LOG.info("Generating Slab and creating new animated textures.");
        AssetPackBuilder.Slab.getBlockstate(name);
        AssetPackBuilder.Slab.getBlockModels(name);
        AssetPackBuilder.Slab.getItemModel(name);
        AssetPackBuilder.Slab.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Textures.animationController(name, frameCount, frameTime, frameSettings);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }
}
