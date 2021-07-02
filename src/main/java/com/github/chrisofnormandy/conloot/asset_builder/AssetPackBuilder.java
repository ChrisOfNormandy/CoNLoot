package com.github.chrisofnormandy.conloot.asset_builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conlib.common.Files;
import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.BlockResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.DoorResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.FenceGateResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.FenceResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.SlabResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.StairsResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.TrapdoorResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.WallResource;

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

        public static String getModPath(String path) {
                return "resourcepacks/" + Main.MOD_ID + "_resources/assets/" + Main.MOD_ID + "/" + path;
        }

        static {
                JsonObject mcmeta = builder.createJsonObject();
                JsonObject a = mcmeta.addObject("pack");
                a.set("pack_format", 6);
                a.set("description", "Generated resource pack from " + Main.MOD_ID + ".");

                Files.write("resourcepacks/" + Main.MOD_ID + "_resources/", "pack", builder.stringify(mcmeta),
                                ".mcmeta");
        }

        private static String[] getAssets(String name, String[] textures, String[] overlays, String[] colors,
                        String mode, Boolean templateShading) {
                String[] assets = new String[textures.length];

                String path = getModPath("textures/block");

                Pattern replPattern = Pattern.compile("\\w+>[\\w%]+");

                for (int i = 0; i < textures.length; i++) {
                        String[] overlayList;

                        if (overlays.length == textures.length)
                                overlayList = new String[] { overlays[i] };
                        else if (overlays.length >= 1)
                                overlayList = new String[] { overlays[0] };
                        else
                                overlayList = new String[0];



                        if (replPattern.matcher(textures[i]).find()) {
                                String[] s = textures[i].split(">");
                                String assetName = s[1].replace("%", name);

                                if (!assetList.contains(assetName)) {
                                        assets[i] = AssetBuilder.createImage(path, assetName,
                                                        new String[] { s[0] }, overlayList, colors, mode, templateShading);

                                        assetList.add(assets[i]);
                                }
                                else
                                        assets[i] = Main.MOD_ID + ":block/" + assetName;
                        } else {
                                if (Patterns.modID.matcher(textures[i]).find())
                                        assets[i] = textures[i];
                                else {
                                        if (!assetList.contains(name)) {
                                                assets[i] = AssetBuilder.createImage(path, name, new String[] { textures[i] },
                                                                overlayList, colors, mode, templateShading);
                                                assetList.add(assets[i]);
                                        }
                                        else
                                                assets[i] = Main.MOD_ID + ":block/" + name;
                                }
                        }
                }

                return assets;
        }

        private static void write(String name, JsonObject blockstate, HashMap<String, JsonObject> blockModels,
                        JsonObject itemModel) {
                builder.write(getModPath("blockstates"), name, blockstate);

                blockModels.forEach((String modelName, JsonObject model) -> builder.write(getModPath("models/block"),
                                modelName, model));

                builder.write(getModPath("models/item"), name, itemModel);

                Lang.addBlock(Main.MOD_ID + ":" + name, StringUtil.wordCaps(name));
        }

        public static void createBlock(String name, String textures[], String[] overlays, String[] colors, String mode,
                        Boolean templateShading, Integer frameTime, String[] frameSettings, String subType) {

                Main.LOG.debug("AssetPackBuilder.createBlock --> " + name + " | Animation: " + (frameTime > 0) + " | Textures: " + String.join("; ", textures));

                JsonObject blockstate;
                HashMap<String, JsonObject> blockModels = new HashMap<String, JsonObject>();

                switch (subType) {
                        case "column": {
                                blockstate = BlockResource.blockstate(name, subType, builder);

                                if (frameTime > 0) {
                                        blockModels.put(name, BlockResource.blockModel(name, "cube_column",
                                                        new String[] { AssetBuilder.createImage(
                                                                        getModPath("textures/block"), name, textures,
                                                                        overlays, colors, mode, templateShading) },
                                                        builder));
                                        blockModels.put(name + "_horizontal", BlockResource.blockModel(name,
                                                        "cube_column_horizontal",
                                                        new String[] { AssetBuilder.createImage(
                                                                        getModPath("textures/block"), name, textures,
                                                                        overlays, colors, mode, templateShading) },
                                                        builder));
                                } else {
                                        blockModels.put(name,
                                                        BlockResource.blockModel(name, "cube_column",
                                                                        getAssets(name, textures, overlays, colors,
                                                                                        mode, templateShading),
                                                                        builder));
                                        blockModels.put(name + "_horizontal",
                                                        BlockResource.blockModel(name, "cube_column_horizontal",
                                                                        getAssets(name, textures, overlays, colors,
                                                                                        mode, templateShading),
                                                                        builder));
                                }

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

                                if (frameTime > 0) {
                                        blockModels.put(name, BlockResource.blockModel(name,
                                                        new String[] { AssetBuilder.createImage(
                                                                        getModPath("textures/block"), name, textures,
                                                                        overlays, colors, mode, templateShading) },
                                                        builder));
                                } else {
                                        blockModels.put(name,
                                                        BlockResource.blockModel(name,
                                                                        getAssets(name, textures, overlays, colors,
                                                                                        mode, templateShading),
                                                                        builder));
                                }

                                break;
                        }
                }

                write(name, blockstate, blockModels, BlockResource.itemModel(name, builder));

                if (frameTime > 0)
                        Textures.animationController(name, textures.length, frameTime, frameSettings);
        }

        public static void createSlabBlock(String name, String doubleName, String textures[], String[] overlays, String[] colors,
                        String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

                Main.LOG.debug("AssetPackBuilder.createBlock --> " + name + " | Animation: " + (frameTime > 0));

                JsonObject blockstate = SlabResource.blockstate(name, doubleName, builder);
                HashMap<String, JsonObject> blockModels;

                if (frameTime > 0) {
                        blockModels = SlabResource.blockModel(
                                        name, new String[] { AssetBuilder.createImage(getModPath("textures/block"),
                                                        name, textures, overlays, colors, mode, templateShading) },
                                        builder);
                } else {
                        blockModels = SlabResource.blockModel(name,
                                        getAssets(name, textures, overlays, colors, mode, templateShading), builder);
                }

                write(name, blockstate, blockModels, SlabResource.itemModel(name, builder));

                if (frameTime > 0)
                        Textures.animationController(name, textures.length, frameTime, frameSettings);
        }

        public static void createStairBlock(String name, String textures[], String[] overlays, String[] colors,
                        String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

                Main.LOG.debug("AssetPackBuilder.createBlock --> " + name + " | Animation: " + (frameTime > 0));

                write(name, StairsResource.blockstate(name, builder), (frameTime > 0 ? StairsResource.blockModel(name,
                                new String[] { AssetBuilder.createImage(getModPath("textures/block"), name, textures,
                                                overlays, colors, mode, templateShading) },
                                builder)
                                : StairsResource.blockModel(name,
                                                getAssets(name, textures, overlays, colors, mode, templateShading),
                                                builder)),
                                StairsResource.itemModel(name, builder));

                if (frameTime > 0)
                        Textures.animationController(name, textures.length, frameTime, frameSettings);
        }

        public static void createWallBlock(String name, String textures[], String[] overlays, String[] colors,
                        String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

                Main.LOG.debug("AssetPackBuilder.createBlock --> " + name + " | Animation: " + (frameTime > 0));

                write(name, WallResource.blockstate(name, builder), (frameTime > 0 ? WallResource.blockModel(name,
                                new String[] { AssetBuilder.createImage(getModPath("textures/block"), name, textures,
                                                overlays, colors, mode, templateShading) },
                                builder)
                                : WallResource.blockModel(name,
                                                getAssets(name, textures, overlays, colors, mode, templateShading),
                                                builder)),
                                WallResource.itemModel(name, builder));

                if (frameTime > 0)
                        Textures.animationController(name, textures.length, frameTime, frameSettings);
        }

        public static void createFenceBlock(String name, String textures[], String[] overlays, String[] colors,
                        String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

                Main.LOG.debug("AssetPackBuilder.createBlock --> " + name + " | Animation: " + (frameTime > 0));

                write(name, FenceResource.blockstate(name, builder), (frameTime > 0 ? FenceResource.blockModel(name,
                                new String[] { AssetBuilder.createImage(getModPath("textures/block"), name, textures,
                                                overlays, colors, mode, templateShading) },
                                builder)
                                : FenceResource.blockModel(name,
                                                getAssets(name, textures, overlays, colors, mode, templateShading),
                                                builder)),
                                FenceResource.itemModel(name, builder));

                if (frameTime > 0)
                        Textures.animationController(name, textures.length, frameTime, frameSettings);
        }

        public static void createFenceGateBlock(String name, String textures[], String[] overlays, String[] colors,
                        String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

                Main.LOG.debug("AssetPackBuilder.createBlock --> " + name + " | Animation: " + (frameTime > 0));

                write(name, FenceGateResource.blockstate(name, builder), (frameTime > 0
                                ? FenceGateResource.blockModel(name,
                                                new String[] { AssetBuilder.createImage(getModPath("textures/block"),
                                                                name, textures, overlays, colors, mode,
                                                                templateShading) },
                                                builder)
                                : FenceGateResource.blockModel(name,
                                                getAssets(name, textures, overlays, colors, mode, templateShading),
                                                builder)),
                                FenceGateResource.itemModel(name, builder));

                if (frameTime > 0)
                        Textures.animationController(name, textures.length, frameTime, frameSettings);
        }

        public static void createDoorBlock(String name, String textures[], String[] overlays, String[] colors,
                        String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

                Main.LOG.debug("AssetPackBuilder.createBlock --> " + name + " | Animation: " + (frameTime > 0));

                write(name, DoorResource.blockstate(name, builder), (frameTime > 0 ? DoorResource.blockModel(name,
                                new String[] { AssetBuilder.createImage(getModPath("textures/block"), name, textures,
                                                overlays, colors, mode, templateShading) },
                                builder)
                                : DoorResource.blockModel(name,
                                                getAssets(name, textures, overlays, colors, mode, templateShading),
                                                builder)),
                                DoorResource.itemModel(name, builder));

                if (frameTime > 0)
                        Textures.animationController(name, textures.length, frameTime, frameSettings);
        }

        public static void createTrapdoorBlock(String name, String textures[], String[] overlays, String[] colors,
                        String mode, Boolean templateShading, Integer frameTime, String[] frameSettings) {

                Main.LOG.debug("AssetPackBuilder.createBlock --> " + name + " | Animation: " + (frameTime > 0));

                write(name, TrapdoorResource.blockstate(name, builder), (frameTime > 0
                                ? TrapdoorResource.blockModel(name,
                                                new String[] { AssetBuilder.createImage(getModPath("textures/block"),
                                                                name, textures, overlays, colors, mode,
                                                                templateShading) },
                                                builder)
                                : TrapdoorResource.blockModel(name,
                                                getAssets(name, textures, overlays, colors, mode, templateShading),
                                                builder)),
                                TrapdoorResource.itemModel(name, builder));

                if (frameTime > 0)
                        Textures.animationController(name, textures.length, frameTime, frameSettings);
        }

        public static void createItem(String name) {
                builder.write(getModPath("models/item"), name,
                                builder.createJsonObject().set("parent", Main.MOD_ID + ":item/" + name));

                Lang.addItem(Main.MOD_ID + ":" + name, StringUtil.wordCaps(name));
        }

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
                        AssetBuilder.createAnimationController(getModPath("/textures/block"), name, frameCount,
                                        frameTime, frameSettings);
                }
        }
}
