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
     * @param texture
     * @param overlay
     * @param colors
     * @param mode
     * @param templateShading
     * @return
     */
    private static String fetchAsset(String name, String type, String texture, String overlay, String[] colors,
            String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {
        if (texture.equals("") && overlay.equals(""))
            return "minecraft:block/debug";

        String path = getModPath("textures/" + type);

        Pattern replPattern = Pattern.compile("[\\w\\d]+>[\\w\\d]+");

        String imageName;

        String tex = texture.replace("@", Main.MOD_ID).replace("%", name);
        Matcher p1 = Pattern.compile("&(\\d+)").matcher(tex);

        if (p1.find()) {
            int index = Integer.parseInt(p1.group(1));
            tex = tex.replaceAll("&(\\d+)", name.split("_")[index]);
        } else
            tex = tex.replace("&", name.split("_")[0]);

        if (replPattern.matcher(tex).find()) { // The texture name provided has an alias.
            String[] s = tex.split(">");
            String assetName = s[1];

            if (!assetList.contains(assetName)) { // If the image has not been created already.
                if (s[0].indexOf(";") > -1)
                    imageName = AssetBuilder.createAnimatedImage(path, name, s[0].split(";"), frameTime, frameSettings);
                else
                    imageName = AssetBuilder.createImage(path, assetName, s[0], overlay, colors, mode, templateShading);

                assetList.add(imageName);
            } else
                imageName = Main.MOD_ID + ":block/" + assetName; // If the image already exists.
        } else { // The texture name provided is the image name.
            if (Patterns.modID.matcher(tex).find()) // If the texture references a mod ID.
                imageName = tex;
            else { // The texture name provided is the image name itself.
                if (!assetList.contains(name)) {
                    if (tex.indexOf(";") > -1)
                        imageName = AssetBuilder.createAnimatedImage(path, name, tex.split(";"), frameTime,
                                frameSettings);
                    else
                        imageName = AssetBuilder.createImage(path, name, tex, overlay, colors, mode, templateShading);

                    assetList.add(imageName);
                } else
                    imageName = Main.MOD_ID + ":block/" + name; // If the image already exists.
            }
        }

        return imageName;
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
    private static String[] fetchAssets(String name, String type, String textures[], String overlays[], String[] colors,
            String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {
        List<String> assets = new ArrayList<String>();

        for (int i = 0; i < textures.length; i++) {
            assets.add(fetchAsset(name, type, textures[i], (i < overlays.length ? overlays[i] : ""), colors, mode,
                    templateShading, frameTime, frameSettings));
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

                blockModels.put(name, BlockResource.blockModel(name, "cube_column", fetchAssets(name, "block", textures,
                        overlays, colors, mode, templateShading, frameTime, frameSettings), builder));
                blockModels
                        .put(name + "_horizontal",
                                BlockResource.blockModel(name, "cube_column_horizontal", fetchAssets(name, "block",
                                        textures, overlays, colors, mode, templateShading, frameTime, frameSettings),
                                        builder));

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

                blockModels.put(name, BlockResource.blockModel(name, fetchAssets(name, "block", textures, overlays,
                        colors, mode, templateShading, frameTime, frameSettings), builder));
                break;
            }
        }

        write(name, blockstate, blockModels, BlockResource.itemModel(name, builder));
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
                fetchAssets(name, "block", textures, overlays, colors, mode, templateShading, frameTime, frameSettings),
                builder);

        write(name, blockstate, blockModels, SlabResource.itemModel(name, builder));
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

        write(name, StairsResource.blockstate(name, builder), StairsResource.blockModel(name,
                fetchAssets(name, "block", textures, overlays, colors, mode, templateShading, frameTime, frameSettings),
                builder), StairsResource.itemModel(name, builder));
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

        write(name, WallResource.blockstate(name, builder), WallResource.blockModel(name,
                fetchAssets(name, "block", textures, overlays, colors, mode, templateShading, frameTime, frameSettings),
                builder), WallResource.itemModel(name, builder));
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

        write(name, FenceResource.blockstate(name, builder), FenceResource.blockModel(name,
                fetchAssets(name, "block", textures, overlays, colors, mode, templateShading, frameTime, frameSettings),
                builder), FenceResource.itemModel(name, builder));
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
                FenceGateResource.blockModel(name, fetchAssets(name, "block", textures, overlays, colors, mode,
                        templateShading, frameTime, frameSettings), builder),
                FenceGateResource.itemModel(name, builder));
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
                        fetchAssets(name, "block", textures, overlays, colors, mode, templateShading, frameTime,
                                frameSettings),
                        builder),
                DoorResource.itemModel(name,
                        fetchAsset(name, "item", itemTexture, (overlays.length > 0 ? overlays[0] : ""), colors, mode,
                                templateShading, frameTime, frameSettings),
                        builder));
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
                TrapdoorResource.blockModel(name, fetchAssets(name, "block", textures, overlays, colors, mode,
                        templateShading, frameTime, frameSettings), builder),
                TrapdoorResource.itemModel(name, builder));

    }

    public static void createPressurePlateBlock(String name, String textures[], String[] overlays, String[] colors,
            String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createPressurePlateBlock --> " + name + " | Animation: " + (frameTime > 0));

        write(name, PressurePlateResource.blockstate(name, builder),
                PressurePlateResource.blockModel(name, fetchAssets(name, "block", textures, overlays, colors, mode,
                        templateShading, frameTime, frameSettings), builder),
                PressurePlateResource.itemModel(name, builder));
    }

    public static void createButtonBlock(String name, String textures[], String[] overlays, String[] colors,
            String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

        Main.LOG.debug("AssetPackBuilder.createButtonBlock --> " + name + " | Animation: " + (frameTime > 0));

        write(name, ButtonResource.blockstate(name, builder), ButtonResource.blockModel(name,
                fetchAssets(name, "block", textures, overlays, colors, mode, templateShading, frameTime, frameSettings),
                builder), ButtonResource.itemModel(name, builder));
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
                fetchAssets(name, "item", textures, overlays, colors, mode, templateShading, frameTime, frameSettings),
                builder));

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
                fetchAssets(name, "item", textures, overlays, colors, mode, templateShading, frameTime, frameSettings),
                builder));

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
