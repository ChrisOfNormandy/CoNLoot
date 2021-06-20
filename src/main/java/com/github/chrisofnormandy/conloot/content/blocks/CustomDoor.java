package com.github.chrisofnormandy.conloot.content.blocks;

import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

public class CustomDoor {
    public static void generateBlock(String name) {
        Main.LOG.info("Generating Door without textures.");
        AssetPackBuilder.Door.getBlockstate(name);
        AssetPackBuilder.Door.getBlockModels(name);
        AssetPackBuilder.Door.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String[] textures) {
        Main.LOG.info("Generating Door with " + textures.length + " textures.");
        AssetPackBuilder.Door.getBlockstate(name);
        AssetPackBuilder.Door.getBlockModels(name, textures);
        AssetPackBuilder.Door.getItemModel(name);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading) {
        Main.LOG.info("Generating Door and creating new textures.");
        AssetPackBuilder.Door.getBlockstate(name);
        AssetPackBuilder.Door.getBlockModels(name);
        AssetPackBuilder.Door.getItemModel(name);
        AssetPackBuilder.Door.createTexture_top(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Door.createTexture_bottom(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }

    public static void generateBlock(String name, String bases[], String[] templates, String[] colors, String mode,
            Boolean templateShading, Integer frameCount, Integer frameTime, String[] frameSettings) {
        Main.LOG.info("Generating Door and creating new animated textures.");
        AssetPackBuilder.Door.getBlockstate(name);
        AssetPackBuilder.Door.getBlockModels(name);
        AssetPackBuilder.Door.getItemModel(name);
        AssetPackBuilder.Door.createTexture_top(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Door.createTexture_bottom(name, bases, templates, colors, mode, templateShading);
        AssetPackBuilder.Textures.animationController(name, frameCount, frameTime, frameSettings);
        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
    }
}
