package com.github.chrisofnormandy.conloot.configs;

import java.util.ArrayList;
import java.util.List;

public class ConfigOptions {

    public ConfigOptions() {
    }

    private List<String> textures = new ArrayList<String>();

    public ConfigOptions Texture(String value) {
        this.textures.add(value);
        return this;
    }

    public String[] Textures() {
        return this.textures.size() == 0
                ? new String[] { "minecraft:block/debug" }
                : this.textures.toArray(new String[0]);
    }

    public List<String> TextureList() {
        if (this.textures.size() > 0)
            return this.textures;

        List<String> list = new ArrayList<String>();
        list.add("minecraft:block/debug");
        return list;
    }

    private List<String> overlays = new ArrayList<String>();

    public ConfigOptions Overlay(String value) {
        this.overlays.add(value);
        return this;
    }

    public String[] Overlays() {
        return this.overlays.toArray(new String[0]);
    }

    public List<String> OverlayList() {
        return this.overlays;
    }

    private List<String> colors = new ArrayList<String>();

    public ConfigOptions Color(String value) {
        this.colors.add(value);
        return this;
    }

    public String[] Colors() {
        return this.colors.size() == 0
                ? new String[] { "255, 255, 255" }
                : this.colors.toArray(new String[0]);
    }

    public List<String> ColorList() {
        if (this.colors.size() > 0)
            return this.colors;

        List<String> list = new ArrayList<String>();
        list.add("255, 255, 255");
        return list;
    }

    public ConfigOptions ColorSet(String... values) {
        this.colors.clear();
        for (String value : values)
            this.colors.add(value);
        return this;
    }

    public String material = "stone";

    public ConfigOptions Material(String value) {
        this.material = value;
        return this;
    }

    public String blockModel = "block";

    public ConfigOptions Model(String value) {
        this.blockModel = value;
        return this;
    }

    public String harvestTool = "pickaxe";

    public ConfigOptions Tool(String value) {
        this.harvestTool = value;
        return this;
    }

    public String sound = "stone";

    public ConfigOptions Sound(String value) {
        this.sound = value;
        return this;
    }

    public String renderModel = "all";

    public ConfigOptions RenderModel(String value) {
        this.renderModel = value;
        return this;
    }

    public String itemTexture = null;

    public ConfigOptions ItemTexture(String value) {
        this.itemTexture = value;
        return this;
    }

    public String doubleSlabTexture = null;

    public ConfigOptions DoubleSlabTexture(String value) {
        this.doubleSlabTexture = value;
        return this;
    }

    public List<String> assetNames = new ArrayList<String>();

    public ConfigOptions AssetName(String value) {
        this.assetNames.add(value);
        return this;
    }

    public String predicateKey = null;

    public ConfigOptions PredicateKey(String value) {
        this.predicateKey = value;
        return this;
    }

    public Integer predicateValue = 0;

    public ConfigOptions PredicateValue(Integer value) {
        this.predicateValue = value;
        return this;
    }

    public Boolean templateShading = true;

    public ConfigOptions TemplateShading(Boolean value) {
        this.templateShading = value;
        return this;
    }

    public String colorMode = "sharp";

    public ConfigOptions ColorMode(String value) {
        this.colorMode = value;
        return this;
    }

    public Integer frameTime = 0;

    public ConfigOptions FrameTime(Integer value) {
        this.frameTime = value;
        return this;
    }

    public List<String> frameSettings = new ArrayList<String>();

    public ConfigOptions FrameSettings(String value) {
        this.frameSettings.add(value);
        return this;
    }

    public Boolean opens = false;

    public ConfigOptions Opens() {
        this.opens = true;
        return this;
    }

    public Boolean rotates = false;

    public ConfigOptions Rotates() {
        this.rotates = true;
        return this;
    }

    public String itemGroup = "minecraft:misc";

    public ConfigOptions ItemGroup(String value) {
        this.itemGroup = value;
        return this;
    }
}
