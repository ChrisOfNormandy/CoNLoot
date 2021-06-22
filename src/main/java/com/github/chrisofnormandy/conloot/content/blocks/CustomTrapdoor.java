package com.github.chrisofnormandy.conloot.content.blocks;

import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

public class CustomTrapdoor {
    public static void generateBlock(String name) {
        Main.LOG.info("Generating Trapdoor without textures.");
        AssetPackBuilder.Trapdoor.getBlockstate(name);
        AssetPackBuilder.Trapdoor.getBlockModels(name);
        AssetPackBuilder.Trapdoor.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String[] textures) {
        Main.LOG.info("Generating Trapdoor with " + textures.length + " textures.");
        AssetPackBuilder.Trapdoor.getBlockstate(name);
        AssetPackBuilder.Trapdoor.getBlockModels(name, textures);
        AssetPackBuilder.Trapdoor.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading) {
        Main.LOG.info("Generating Trapdoor and creating new textures.");
        AssetPackBuilder.Trapdoor.getBlockstate(name);
        AssetPackBuilder.Trapdoor.getBlockModels(name);
        AssetPackBuilder.Trapdoor.getItemModel(name);
        AssetPackBuilder.Trapdoor.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading, Integer frameCount, Integer frameTime, String[] frameSettings) {
        Main.LOG.info("Generating Trapdoor and creating new animated textures.");
        AssetPackBuilder.Trapdoor.getBlockstate(name);
        AssetPackBuilder.Trapdoor.getBlockModels(name);
        AssetPackBuilder.Trapdoor.getItemModel(name);
        AssetPackBuilder.Trapdoor.createTexture(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Textures.animationController(name, frameCount, frameTime, frameSettings);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }
}
