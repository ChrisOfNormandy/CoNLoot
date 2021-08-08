package com.github.chrisofnormandy.conloot.asset_builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conlib.common.Files;
import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.BlockResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.ButtonResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.DoorResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.FenceGateResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.FenceResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.PressurePlateResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.SlabResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.StairsResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.TrapdoorResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.WallResource;
import com.github.chrisofnormandy.conloot.asset_builder.items.HandheldResource;
import com.github.chrisofnormandy.conloot.asset_builder.items.ItemResource;

public class AssetPackBuilder {
    private static JsonBuilder builder = new JsonBuilder();
    private static List<String> assetList = new ArrayList<String>();

    /**
     *
     * @param path
     * @return
     */
    public static String getPath(String path) {
        return "resourcepacks/" + Main.MOD_ID + "_resources/assets/" + path;
    }

    /**
     * 
     * @param path
     * @return
     */
    public static String getModPath(String path) {
        return "resourcepacks/" + Main.MOD_ID + "_resources/assets/" + Main.MOD_ID + "/" + path;
    }

    static {
        JsonObject mcmeta = builder.createJsonObject();
        JsonObject a = mcmeta.addObject("pack");
        a.set("pack_format", 6);
        a.set("description", "Generated resource pack from " + Main.MOD_ID + ".");

        Files.write("resourcepacks/" + Main.MOD_ID + "_resources/", "pack", builder.stringify(mcmeta), ".mcmeta");
    }

    /**
     * 
     * @param name
     * @param type
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @return
     */
    private static String[] fetchAsset(String name, String type, String[] textures, String[] overlays, String[] colors,
            String mode, Boolean templateShading) {
        if (textures.length == 0 && overlays.length == 0)
            return new String[] { "minecraft:block/debug" };

        List<String> assets = new ArrayList<String>();

        String path = getModPath("textures/" + type);

        Pattern replPattern = Pattern.compile("[\\w\\d]+>[\\w\\d]+");

        for (int i = 0; i < textures.length; i++) {
            String[] overlayList;

            if (overlays.length == textures.length)
                overlayList = new String[] { overlays[i] };
            else if (overlays.length >= 1)
                overlayList = new String[] { overlays[0] };
            else
                overlayList = new String[0];

            String tex = textures[i].replace("@", Main.MOD_ID).replace("%", name);
            Matcher p1 = Pattern.compile("&(\\d+)").matcher(tex);

            if (p1.find()) {
                int index = Integer.parseInt(p1.group(1));
                Main.LOG.debug(">>>>>>>>>>>>>>>>>>>>> " + index + " ___ " + name.split("_")[index]);
                tex = tex.replaceAll("&(\\d+)", name.split("_")[index]);
            } else
                tex = tex.replace("&", name.split("_")[0]);

            if (replPattern.matcher(tex).find()) {
                String[] s = tex.split(">");
                String assetName = s[1];

                if (!assetList.contains(assetName)) {
                    String asset = AssetBuilder.createImage(path, assetName, new String[] { s[0] }, overlayList, colors,
                            mode, templateShading);

                    assets.add(asset);
                    assetList.add(asset);
                } else
                    assets.add(Main.MOD_ID + ":block/" + assetName);
            } else {
                if (Patterns.modID.matcher(tex).find())
                    assets.add(tex);
                else {
                    if (!assetList.contains(name)) {
                        String asset = AssetBuilder.createImage(path, name, new String[] { tex }, overlayList, colors,
                                mode, templateShading);

                        assets.add(asset);
                        assetList.add(asset);
                    } else
                        assets.add(Main.MOD_ID + ":block/" + name);
                }
            }
        }

        return assets.toArray(new String[0]);
    }

    /**
     * 
     * @param name
     * @param blockstate
     * @param blockModels
     * @param itemModel
     */
    private static void write(String name, JsonObject blockstate, HashMap<String, JsonObject> blockModels,
            JsonObject itemModel) {
        builder.write(getModPath("blockstates"), name, blockstate);

        blockModels.forEach(
                (String modelName, JsonObject model) -> builder.write(getModPath("models/block"), modelName, model));

        builder.write(getModPath("models/item"), name, itemModel);
    }

    /**
     * 
     * @param name
     * @param itemModel
     */
    private static void write(String name, JsonObject itemModel) {
        builder.write(getModPath("models/item"), name, itemModel);
    }

    /**
     * 
     * @param name
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @param frameTime
     * @param frameSettings
     * @param subType
     */
    public static void createBlock(String name, String textures[], String[] overlays, String[] colors, String mode,
            Boolean templateShading, Integer frameTime, String[] frameSettings, String subType) {

        Main.LOG.debug("AssetPackBuilder.createBlock --> " + name + " | Animation: " + (frameTime > 0) + " | Textures: "
                + String.join("; ", textures));

        JsonObject blockstate;
        HashMap<String, JsonObject> blockModels = new HashMap<String, JsonObject>();

        switch (subType) {
        case "column": {
            blockstate = BlockResource.blockstate(name, subType, builder);

            blockModels.put(name, BlockResource.blockModel(name, "cube_column",
                    fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder));
            blockModels.put(name + "_horizontal", BlockResource.blockModel(name, "cube_column_horizontal",
                    fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder));

            break;
        }
        case "bottom_top": {

        }
        case "bottom_top_rotate": {

        }
        case "bottom_top_rotate_2_states": {

        }
        default: {
            blockstate = BlockResource.blockstate(name, builder);

            blockModels.put(name, BlockResource.blockModel(name,
                    fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder));
            break;
        }
        }

        write(name, blockstate, blockModels, BlockResource.itemModel(name, builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);
    }

    /**
     * 
     * @param name
     * @param doubleName
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @param frameTime
     * @param frameSettings
     */
    public static void createSlabBlock(String name, String doubleName, String textures[], String[] overlays,
            String[] colors, String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createSlabBlock --> " + name + " | Animation: " + (frameTime > 0));

        JsonObject blockstate = SlabResource.blockstate(name, doubleName, builder);
        HashMap<String, JsonObject> blockModels;

        blockModels = SlabResource.blockModel(name,
                fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder);

        write(name, blockstate, blockModels, SlabResource.itemModel(name, builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);
    }

    /**
     * 
     * @param name
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @param frameTime
     * @param frameSettings
     */
    public static void createStairBlock(String name, String textures[], String[] overlays, String[] colors, String mode,
            Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createStairBlock --> " + name + " | Animation: " + (frameTime > 0));

        write(name, StairsResource.blockstate(name, builder),
                StairsResource.blockModel(name,
                        fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder),
                StairsResource.itemModel(name, builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);
    }

    /**
     * 
     * @param name
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @param frameTime
     * @param frameSettings
     */
    public static void createWallBlock(String name, String textures[], String[] overlays, String[] colors, String mode,
            Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createWallBlock --> " + name + " | Animation: " + (frameTime > 0));

        write(name, WallResource.blockstate(name, builder),
                WallResource.blockModel(name,
                        fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder),
                WallResource.itemModel(name, builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);
    }

    /**
     * 
     * @param name
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @param frameTime
     * @param frameSettings
     */
    public static void createFenceBlock(String name, String textures[], String[] overlays, String[] colors, String mode,
            Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createFenceBlock --> " + name + " | Animation: " + (frameTime > 0));

        write(name, FenceResource.blockstate(name, builder),
                FenceResource.blockModel(name,
                        fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder),
                FenceResource.itemModel(name, builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);
    }

    /**
     * 
     * @param name
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @param frameTime
     * @param frameSettings
     */
    public static void createFenceGateBlock(String name, String textures[], String[] overlays, String[] colors,
            String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createFenceGateBlock --> " + name + " | Animation: " + (frameTime > 0));

        write(name, FenceGateResource.blockstate(name, builder),
                FenceGateResource.blockModel(name,
                        fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder),
                FenceGateResource.itemModel(name, builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);
    }

    /**
     * 
     * @param name
     * @param itemTexture
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @param frameTime
     * @param frameSettings
     */
    public static void createDoorBlock(String name, String itemTexture, String textures[], String[] overlays,
            String[] colors, String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createDoorBlock --> " + name + " | Animation: " + (frameTime > 0));

        write(name, DoorResource.blockstate(name, builder),
                DoorResource.blockModel(name,
                        fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder),
                DoorResource.itemModel(name, fetchAsset(name, "item", new String[] { itemTexture }, overlays, colors,
                        mode, templateShading)[0], builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);
    }

    /**
     * 
     * @param name
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @param frameTime
     * @param frameSettings
     */
    public static void createTrapdoorBlock(String name, String textures[], String[] overlays, String[] colors,
            String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createTrapdoorBlock --> " + name + " | Animation: " + (frameTime > 0));

        write(name, TrapdoorResource.blockstate(name, builder),
                TrapdoorResource.blockModel(name,
                        fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder),
                TrapdoorResource.itemModel(name, builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);
    }

    public static void createPressurePlateBlock(String name, String textures[], String[] overlays, String[] colors,
            String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createPressurePlateBlock --> " + name + " | Animation: " + (frameTime > 0));

        write(name, PressurePlateResource.blockstate(name, builder),
                PressurePlateResource.blockModel(name,
                        fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder),
                PressurePlateResource.itemModel(name, builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);
    }

    public static void createButtonBlock(String name, String textures[], String[] overlays, String[] colors,
            String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createButtonBlock --> " + name + " | Animation: " + (frameTime > 0));

        write(name, ButtonResource.blockstate(name, builder),
                ButtonResource.blockModel(name,
                        fetchAsset(name, "block", textures, overlays, colors, mode, templateShading), builder),
                ButtonResource.itemModel(name, builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);
    }

    /**
     * 
     * @param name
     */
    public static void createBlockItem(String name) {
        builder.write(getModPath("models/item"), name,
                builder.createJsonObject().set("parent", Main.MOD_ID + ":item/" + name));

        Lang.addItem(name, StringUtil.wordCaps_repl(name));
    }

    /**
     * 
     * @param name
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @param frameTime
     * @param frameSettings
     */
    public static void createItem(String name, String textures[], String[] overlays, String[] colors, String mode,
            Boolean templateShading, Integer frameTime, String[] frameSettings) {
        Main.LOG.debug("AssetPackBuilder.createItem --> " + name + " | Animation: " + (frameTime > 0));

        write(name, ItemResource.itemModel(name,
                fetchAsset(name, "item", textures, overlays, colors, mode, templateShading), builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);

        Lang.addItem(name, StringUtil.wordCaps_repl(name));
    }

    /**
     * 
     * @param name
     * @param textures
     * @param overlays
     * @param colors
     * @param mode
     * @param templateShading
     * @param frameTime
     * @param frameSettings
     */
    public static void createHandheldItem(String name, String textures[], String[] overlays, String[] colors,
            String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {
        Main.LOG.debug("AssetPackBuilder.createHandheldItem --> " + name + " | Animation: " + (frameTime > 0));

        write(name, HandheldResource.itemModel(name,
                fetchAsset(name, "item", textures, overlays, colors, mode, templateShading), builder));

        if (frameTime > 0)
            Textures.animationController(name, textures.length, frameTime, frameSettings);

        Lang.addItem(name, StringUtil.wordCaps_repl(name));
    }

    /**
     * 
     * @param name
     */
    public static void createGroup(String name) {
        Lang.addGroup(Main.MOD_ID + ":" + name, StringUtil.wordCaps(name));
    }

    public static class Lang {
        public static JsonObject file = builder.createJsonObject();

        /**
         *
         * @param registryName Should be formatted as partial registry name, like
         *                     my_block.
         * @param name         Should be the translated text, like My Block.
         */
        public static void addBlock(String registryName, String name) {
            file.set("block." + Main.MOD_ID + "." + registryName, name);
        }

        /**
         *
         * @param registryName Should be formatted as partial registry name, like
         *                     my_item.
         * @param name         Should be the translated text, like My Item.
         */
        public static void addItem(String registryName, String name) {
            file.set("item." + Main.MOD_ID + "." + registryName, name);
        }

        /**
         *
         * @param registryName Should be formatted as partial registry name, like
         *                     my_group.
         * @param name         Should be the translated text, like My Group.
         */
        public static void addGroup(String registryName, String name) {
            file.set("itemGroup." + registryName, name);
        }

        public static void write() {
            builder.write(getModPath("/lang"), "en_us", file);
        }
    }

    public static class Textures {
        /**
         *
         * @param name          Should be formatted as partial registry name, like dirt.
         * @param frameCount    How many frames per animation cycle
         * @param frameTime     How many ticks per frame
         * @param frameSettings Additional frame settings. Optional.
         */
        public static void animationController(String name, Integer frameCount, Integer frameTime,
                String[] frameSettings) {
            AssetBuilder.createAnimationController(getModPath("/textures/block"), name, frameCount, frameTime,
                    frameSettings);
        }
    }
}
