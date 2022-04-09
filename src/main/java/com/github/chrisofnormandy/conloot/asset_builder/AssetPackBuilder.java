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
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.*;
import com.github.chrisofnormandy.conloot.asset_builder.items.*;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;

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

        String imageName;

        String tex = texture.replace("@", Main.MOD_ID).replace("%", name);
        Matcher p1 = Pattern.compile("&(\\d+)").matcher(tex);

        if (p1.find()) {
            int index = Integer.parseInt(p1.group(1));
            tex = tex.replaceAll("&(\\d+)", name.split("_")[index]);
        }
        else
            tex = tex.replace("&", name.split("_")[0]);

        if (Patterns.replPattern.matcher(tex).find()) { // The texture name provided has an alias.
            String[] s = tex.split(">");
            String assetName = s[1];

            if (!assetList.contains(assetName)) { // If the image has not been created already.
                if (s[0].indexOf(";") > -1)
                    imageName = AssetBuilder.createAnimatedImage(path, name, s[0].split(";"), frameTime, frameSettings);
                else
                    imageName = AssetBuilder.createImage(path, assetName, s[0], overlay, colors, mode, templateShading);

                assetList.add(imageName);
            }
            else
                imageName = Main.MOD_ID + ":block/" + assetName; // If the image already exists.
        }
        else { // The texture name provided is the image name.
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
                }
                else
                    imageName = Main.MOD_ID + ":block/" + name; // If the image already exists.
            }
        }

        return imageName;
    }

    /**
     * 
     * @param name
     * @param type
     * @param options
     * @return
     */
    private static String[] fetchAssets(String name, String type, ConfigOptions options) {
        List<String> assets = new ArrayList<String>();

        String[] textures = options.Textures();
        String[] overlays = options.Overlays();
        String[] colors = options.Colors();
        String[] frameSettings = options.frameSettings.toArray(new String[0]);

        for (int i = 0; i < textures.length; i++) {
            assets.add(fetchAsset(name, type, textures[i], (i < overlays.length ? overlays[i] : ""),
                    colors, options.colorMode,
                    options.templateShading, options.frameTime, frameSettings));
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

        if (blockstate == null)
            Main.LOG.error("Attempted to write null blockstate for: " + name);
        else
            builder.write(getModPath("blockstates"), name, blockstate);

        blockModels.forEach(
                (String modelName, JsonObject model) -> {
                    if (model == null)
                        Main.LOG.error("Attempted to write null model for: " + name + ", " + modelName);
                    else
                        builder.write(getModPath("models/block"), modelName, model);
                });

        if (itemModel == null)
            Main.LOG.error("Attempted to write null item model for: " + name);
        else
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

    public static void createBlock(String name, ConfigOptions options) {
        Main.LOG.debug("AssetPackBuilder.createBlock --> " + name + " | Animation: " + (options.frameTime > 0) + " | Textures: "
                + String.join("; ", options.Textures()) + " | Render Model:" + options.renderModel);

        HashMap<String, JsonObject> blockModels = new HashMap<String, JsonObject>();

        JsonObject blockstate = BlockResource.blockstate(name, options, builder);

        switch (options.renderModel) {
            case "column": { // Used for blocks like pillars and logs
                blockModels.put(name, BlockResource.blockModel(name, "cube_column", fetchAssets(name, "block", options), builder));

                blockModels.put(name + "_horizontal",
                        BlockResource.blockModel(name, "cube_column_horizontal", fetchAssets(name, "block", options), builder));

                break;
            }
            case "bottom_top": { // Used for blocks like barrels that have different end textures
                blockModels.put(name, BlockResource.blockModel(name, "cube_bottom_top", fetchAssets(name, "block", options), builder));

                if (options.opens)
                    blockModels.put(name + "_open", BlockResource.blockModel(name + "_open", "cube_bottom_top", fetchAssets(name, "block", options), builder));

                break;
            }
            default: {
                blockModels.put(name, BlockResource.blockModel(name, fetchAssets(name, "block", options), builder));

                break;
            }
        }

        write(name, blockstate, blockModels, BlockResource.itemModel(name, builder));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createSlabBlock(String name, ConfigOptions options) {

        Main.LOG.debug("AssetPackBuilder.createSlabBlock --> " + name + " | Animation: " + (options.frameTime > 0));

        JsonObject blockstate = SlabResource.blockstate(name, options.doubleSlabTexture, builder);
        HashMap<String, JsonObject> blockModels;

        blockModels = SlabResource.blockModel(name,
                fetchAssets(name, "block", options),
                builder);

        write(name, blockstate, blockModels, SlabResource.itemModel(name, builder));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createStairBlock(String name, ConfigOptions options) {

        Main.LOG.debug("AssetPackBuilder.createStairBlock --> " + name + " | Animation: " + (options.frameTime > 0));

        write(name, StairsResource.blockstate(name, builder), StairsResource.blockModel(name,
                fetchAssets(name, "block", options),
                builder), StairsResource.itemModel(name, builder));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createWallBlock(String name, ConfigOptions options) {

        Main.LOG.debug("AssetPackBuilder.createWallBlock --> " + name + " | Animation: " + (options.frameTime > 0));

        write(name, WallResource.blockstate(name, builder), WallResource.blockModel(name,
                fetchAssets(name, "block", options),
                builder), WallResource.itemModel(name, builder));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createFenceBlock(String name, ConfigOptions options) {

        Main.LOG.debug("AssetPackBuilder.createFenceBlock --> " + name + " | Animation: " + (options.frameTime > 0));

        write(name, FenceResource.blockstate(name, builder), FenceResource.blockModel(name,
                fetchAssets(name, "block", options),
                builder), FenceResource.itemModel(name, builder));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createFenceGateBlock(String name, ConfigOptions options) {

        Main.LOG.debug("AssetPackBuilder.createFenceGateBlock --> " + name + " | Animation: " + (options.frameTime > 0));

        write(name, FenceGateResource.blockstate(name, builder),
                FenceGateResource.blockModel(name, fetchAssets(name, "block", options), builder),
                FenceGateResource.itemModel(name, builder));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createDoorBlock(String name, ConfigOptions options) {

        Main.LOG.debug("AssetPackBuilder.createDoorBlock --> " + name + " | Animation: " + (options.frameTime > 0));

        String[] overlays = options.Overlays();
        String[] colors = options.Colors();
        String[] frameSettings = options.frameSettings.toArray(new String[0]);

        write(name, DoorResource.blockstate(name, builder),
                DoorResource.blockModel(name,
                        fetchAssets(name, "block", options),
                        builder),
                DoorResource.itemModel(name,
                        fetchAsset(name, "item", options.itemTexture, (overlays.length > 0 ? overlays[0] : ""),
                                colors, options.colorMode,
                                options.templateShading,
                                options.frameTime, frameSettings),
                        builder));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createTrapdoorBlock(String name, ConfigOptions options) {

        Main.LOG.debug("AssetPackBuilder.createTrapdoorBlock --> " + name + " | Animation: " + (options.frameTime > 0));

        write(name, TrapdoorResource.blockstate(name, builder),
                TrapdoorResource.blockModel(name, fetchAssets(name, "block", options), builder),
                TrapdoorResource.itemModel(name, builder));

    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createPressurePlateBlock(String name, ConfigOptions options) {

        Main.LOG.debug("AssetPackBuilder.createPressurePlateBlock --> " + name + " | Animation: " + (options.frameTime > 0));

        write(name, PressurePlateResource.blockstate(name, builder),
                PressurePlateResource.blockModel(name, fetchAssets(name, "block", options), builder),
                PressurePlateResource.itemModel(name, builder));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createButtonBlock(String name, ConfigOptions options) {

        Main.LOG.debug("AssetPackBuilder.createButtonBlock --> " + name + " | Animation: " + (options.frameTime > 0));

        write(name,
                ButtonResource.blockstate(name, builder),
                ButtonResource.blockModel(name, fetchAssets(name, "block", options), builder),
                ButtonResource.itemModel(name, builder));
    }

    /**
     * 
     * @param name
     */
    public static void createBlockItem(String name) {
        builder.write(getModPath("models/item"), name, builder.createJsonObject().set("parent", Main.MOD_ID + ":item/" + name));

        Lang.addItem(name, StringUtil.wordCaps_repl(name));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createItem(String name, ConfigOptions options) {
        Main.LOG.debug("AssetPackBuilder.createItem --> " + name + " | Animation: " + (options.frameTime > 0));

        write(name, ItemResource.itemModel(name, fetchAssets(name, "item", options), builder));

        Lang.addItem(name, StringUtil.wordCaps_repl(name));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createHandheldItem(String name, ConfigOptions options) {
        Main.LOG.debug("AssetPackBuilder.createHandheldItem --> " + name + " | Animation: " + (options.frameTime > 0));

        write(name, HandheldResource.itemModel(name,
                fetchAssets(name, "item", options),
                builder));

        Lang.addItem(name, StringUtil.wordCaps_repl(name));
    }

    /**
     * 
     * @param options
     */
    public static void createTwoStepHandheldItem(ConfigOptions options) {

        // Additional work needed...
        return;

        // String[] names = options.assetNames.toArray(new String[0]);
        // if (names.length < 2) {
        // if (names.length < 1) {
        // Main.LOG.error("No names provided for two step handheld item AssetPackBuilder
        // function.");
        // return;
        // }
        // names[1] = names[0] + "_1";
        // }

        // Main.LOG.debug("AssetPackBuilder.createTwoStepHandheldItem --> " + names[0] +
        // " + " + names[1] + " | Animation: "
        // + (options.frameTime > 0));

        // try {
        // String[] textureList1 = fetchAssets(names[0], "item", options);

        // String[] textureList2 = fetchAssets(names[1], "item", options);

        // write(names[0], HandheldResource.itemModel_twoStep(names[0], names[1],
        // options.blockModel, options.predicateKey, options.predicateValue,
        // textureList1, builder));

        // write(names[1], HandheldResource.itemModel(names[1], Main.MOD_ID + ":item/" +
        // names[0], textureList2, builder));
        // }
        // catch (ArrayIndexOutOfBoundsException e) {
        // Main.LOG.error(e);
        // return;
        // }

        // Lang.addItem(names[0], StringUtil.wordCaps_repl(names[0]));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createShootableItem(String name, ConfigOptions options) {

        // Additional work needed...
        return;

        // Main.LOG.debug("AssetPackBuilder.createShootableItem --> " + name + " |
        // Animation: " + (frameTime > 0));

        // write(name, ShootableResource.itemModel(name,
        // fetchAssets(name, "item", textures, overlays, colors, mode, templateShading,
        // frameTime, frameSettings),
        // builder));

        // Lang.addItem(name, StringUtil.wordCaps_repl(name));
    }

    /**
     * 
     * @param name
     * @param options
     */
    public static void createWearableItem(String name, ConfigOptions options) {
        Main.LOG.debug("AssetPackBuilder.createWearableItem --> " + name + " | Animation: " + (options.frameTime > 0));

        write(name, WearableResource.itemModel(name,
                fetchAssets(name, "item", options),
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
         * @param registryName
         *            Should be formatted as partial registry name, like
         *            my_block.
         * @param name
         *            Should be the translated text, like My Block.
         */
        public static void addBlock(String registryName, String name) {
            file.set("block." + Main.MOD_ID + "." + registryName, name);
        }

        /**
         *
         * @param registryName
         *            Should be formatted as partial registry name, like
         *            my_item.
         * @param name
         *            Should be the translated text, like My Item.
         */
        public static void addItem(String registryName, String name) {
            file.set("item." + Main.MOD_ID + "." + registryName, name);
        }

        /**
         *
         * @param registryName
         *            Should be formatted as partial registry name, like
         *            my_group.
         * @param name
         *            Should be the translated text, like My Group.
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
         * @param name
         *            Should be formatted as partial registry name, like dirt.
         * @param frameCount
         *            How many frames per animation cycle
         * @param frameTime
         *            How many ticks per frame
         * @param frameSettings
         *            Additional frame settings. Optional.
         */
        public static void animationController(String name, Integer frameCount, Integer frameTime,
                String[] frameSettings) {
            AssetBuilder.createAnimationController(getModPath("/textures/block"), name, frameCount, frameTime,
                    frameSettings);
        }
    }
}
