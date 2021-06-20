package com.github.chrisofnormandy.conloot.asset_builder;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conlib.common.Files;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.BlockResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.DoorResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.FenceGateResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.FenceResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.SlabResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.StairsResource;
import com.github.chrisofnormandy.conloot.asset_builder.blocktypes.WallResource;

public class AssetPackBuilder {
    private static JsonBuilder builder = new JsonBuilder();

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
     * @param name
     * @param path
     * @param json
     */
    private static void write(String name, String path, JsonObject json) {
        Main.LOG.info("Creating new model for " + name + " at: " + path);
        Main.LOG.debug(builder.stringify(json));
        builder.write(path, name, json);
    }

    static {
        JsonObject mcmeta = builder.createJsonObject();
        JsonObject a = mcmeta.addObject("pack");
        a.set("pack_format", 6);
        a.set("description", "Generated resource pack from " + Main.MOD_ID + ".");

        Files.write("resourcepacks/" + Main.MOD_ID + "_resources/", "pack", builder.stringify(mcmeta), ".mcmeta");
    }

    public static class Block {
        /**
         * 
         * @param name Should be formatted as partial registry name, like dirt.
         * @return
         */
        public static JsonObject getBlockstate(String name) {
            JsonObject blockstate = BlockResource.blockstate(name, builder);
            builder.write(getPath(Main.MOD_ID + "/blockstates"), name, blockstate);
            return blockstate;
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject getBlockModel(String name) {
            JsonObject model = BlockResource.blockModel(name, builder);
            write(name, getPath(Main.MOD_ID + "/models/block"), model);
            return model;
        }

        /**
         * 
         * @param name
         * @param textures
         * @return
         */
        public static JsonObject getBlockModel(String name, String[] textures) {
            Main.LOG.debug("Block textures: " + String.join(", ", textures));

            JsonObject model = BlockResource.blockModel(name, textures[0], builder);
            write(name, getPath(Main.MOD_ID + "/models/block"), model);
            return model;
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject getItemModel(String name) {
            JsonObject model = BlockResource.itemModel(name, builder);
            write(name, getPath(Main.MOD_ID + "/models/item"), model);
            return model;
        }

        /**
         * 
         * @param name
         * @param bases
         * @param templates
         * @param colors
         * @param mode
         * @param templateShading
         */
        public static void createTexture(String name, String[] bases, String[] templates, String[] colors, String mode,
                Boolean templateShading) {
            BlockResource.texture(name, getPath(Main.MOD_ID + "/textures/block"), bases, templates, colors, mode,
                    templateShading);
        }
    }

    public static class Slab {
        /**
         * 
         * @param name Should be formatted as partial registry name, like dirt.
         * @return
         */
        public static JsonObject getBlockstate(String name) {
            JsonObject blockstate = SlabResource.blockstate(name, builder);
            builder.write(getPath(Main.MOD_ID + "/blockstates"), name, blockstate);
            return blockstate;
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject[] getBlockModels(String name) {
            JsonObject model = SlabResource.blockModel(name, builder);
            JsonObject model_top = SlabResource.blockModel(name, builder);

            write(name, getPath(Main.MOD_ID + "/models/block"), model);
            write(name + "_top", getPath(Main.MOD_ID + "/models/block"), model_top);

            return new JsonObject[] { model, model_top };
        }

        /**
         * 
         * @param name
         * @param textures
         * @return
         */
        public static JsonObject[] getBlockModels(String name, String[] textures) {
            Main.LOG.debug("Slab textures: " + String.join(", ", textures));

            JsonObject model = SlabResource.blockModel(name, textures, builder);
            JsonObject model_top = SlabResource.blockModel(name, textures, builder);

            write(name, getPath(Main.MOD_ID + "/models/block"), model);
            write(name + "_top", getPath(Main.MOD_ID + "/models/block"), model_top);

            return new JsonObject[] { model, model_top };
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject getItemModel(String name) {
            JsonObject model = SlabResource.itemModel(name, builder);
            write(name, getPath(Main.MOD_ID + "/models/item"), model);
            return model;
        }

        /**
         * 
         * @param name
         * @param bases
         * @param templates
         * @param colors
         * @param mode
         * @param templateShading
         */
        public static void createTexture(String name, String[] bases, String[] templates, String[] colors, String mode,
                Boolean templateShading) {
            SlabResource.texture(name, getPath(Main.MOD_ID + "/textures/block"), bases, templates, colors, mode,
                    templateShading);
        }
    }

    public static class Stairs {
        /**
         * 
         * @param name Should be formatted as partial registry name, like dirt.
         * @return
         */
        public static JsonObject getBlockstate(String name) {
            JsonObject blockstate = StairsResource.blockstate(name, builder);
            builder.write(getPath(Main.MOD_ID + "/blockstates"), name, blockstate);
            return blockstate;
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject[] getBlockModels(String name) {
            JsonObject model = StairsResource.blockModel(name, builder);
            JsonObject model_inner = StairsResource.blockModel_inner(name, builder);
            JsonObject model_outer = StairsResource.blockModel_outer(name, builder);

            String path = getPath(Main.MOD_ID + "/models/block");
            write(name, path, model);
            write(name + "_inner", path, model_inner);
            write(name + "_outer", path, model_outer);

            return new JsonObject[] { model, model_inner, model_outer };
        }

        public static JsonObject[] getBlockModels(String name, String[] textures) {
            Main.LOG.debug("Stair textures: " + String.join(", ", textures));

            JsonObject model = StairsResource.blockModel(name, textures, builder);
            JsonObject model_inner = StairsResource.blockModel_inner(name, textures, builder);
            JsonObject model_outer = StairsResource.blockModel_outer(name, textures, builder);

            String path = getPath(Main.MOD_ID + "/models/block");
            write(name, path, model);
            write(name + "_inner", path, model_inner);
            write(name + "_outer", path, model_outer);

            return new JsonObject[] { model, model_inner, model_outer };
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject getItemModel(String name) {
            JsonObject model = StairsResource.itemModel(name, builder);
            write(name, getPath(Main.MOD_ID + "/models/item"), model);
            return model;
        }

        /**
         * 
         * @param name
         * @param bases
         * @param templates
         * @param colors
         * @param mode
         * @param templateShading
         */
        public static void createTexture(String name, String[] bases, String[] templates, String[] colors, String mode,
                Boolean templateShading) {
            StairsResource.texture(name, getPath(Main.MOD_ID + "/textures/block"), bases, templates, colors, mode,
                    templateShading);
        }
    }

    public static class Wall {
        /**
         * 
         * @param name Should be formatted as partial registry name, like dirt.
         * @return
         */
        public static JsonObject getBlockstate(String name) {
            JsonObject blockstate = WallResource.blockstate(name, builder);
            builder.write(getPath(Main.MOD_ID + "/blockstates"), name, blockstate);
            return blockstate;
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject[] getBlockModels(String name) {
            JsonObject model_side = WallResource.blockModel_side(name, builder);
            JsonObject model_sideTall = WallResource.blockModel_sideTall(name, builder);
            JsonObject model_post = WallResource.blockModel_post(name, builder);
            JsonObject model_inventory = WallResource.blockModel_inventory(name, builder);

            String path = getPath(Main.MOD_ID + "/models/block");
            write(name + "_side", path, model_side);
            write(name + "_side_tall", path, model_sideTall);
            write(name + "_post", path, model_post);
            write(name + "_inventory", path, model_inventory);

            return new JsonObject[] { model_side, model_sideTall, model_post, model_inventory };
        }

        /**
         * 
         * @param name
         * @param textures
         * @return
         */
        public static JsonObject[] getBlockModels(String name, String[] textures) {
            Main.LOG.debug("Wall textures: " + String.join(", ", textures));

            JsonObject model_side = WallResource.blockModel_side(name, textures[0], builder);
            JsonObject model_sideTall = WallResource.blockModel_sideTall(name,
                    (textures.length >= 4 ? textures[1] : textures[0]), builder);
            JsonObject model_post = WallResource.blockModel_post(name,
                    (textures.length >= 4 ? textures[2] : textures[0]), builder);
            JsonObject model_inventory = WallResource.blockModel_inventory(name,
                    (textures.length >= 4 ? textures[3] : textures[0]), builder);

            String path = getPath(Main.MOD_ID + "/models/block");
            write(name + "_side", path, model_side);
            write(name + "_side_tall", path, model_sideTall);
            write(name + "_post", path, model_post);
            write(name + "_inventory", path, model_inventory);

            return new JsonObject[] { model_side, model_sideTall, model_post, model_inventory };
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject getItemModel(String name) {
            JsonObject model = WallResource.itemModel(name, builder);
            write(name, getPath(Main.MOD_ID + "/models/item"), model);
            return model;
        }

        /**
         * 
         * @param name
         * @param bases
         * @param templates
         * @param colors
         * @param mode
         * @param templateShading
         */
        public static void createTexture(String name, String[] bases, String[] templates, String[] colors, String mode,
                Boolean templateShading) {
            WallResource.texture(name, getPath(Main.MOD_ID + "/textures/block"), bases, templates, colors, mode,
                    templateShading);
        }
    }

    public static class Fence {
        /**
         * 
         * @param name Should be formatted as partial registry name, like dirt.
         * @return
         */
        public static JsonObject getBlockstate(String name) {
            JsonObject blockstate = FenceResource.blockstate(name, builder);
            builder.write(getPath(Main.MOD_ID + "/blockstates"), name, blockstate);
            return blockstate;
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject[] getBlockModels(String name) {
            JsonObject model_side = FenceResource.blockModel_side(name, builder);
            JsonObject model_post = FenceResource.blockModel_post(name, builder);
            JsonObject model_inventory = FenceResource.blockModel_inventory(name, builder);

            String path = getPath(Main.MOD_ID + "/models/block");
            write(name + "_side", path, model_side);
            write(name + "_post", path, model_post);
            write(name + "_inventory", path, model_inventory);

            return new JsonObject[] { model_side, model_post, model_inventory };
        }

        /**
         * 
         * @param name
         * @param textures
         * @return
         */
        public static JsonObject[] getBlockModels(String name, String[] textures) {
            Main.LOG.debug("Fence textures: " + String.join(", ", textures));

            JsonObject model_side = FenceResource.blockModel_side(name, textures[0], builder);
            JsonObject model_post = FenceResource.blockModel_post(name,
                    (textures.length >= 3 ? textures[1] : textures[0]), builder);
            JsonObject model_inventory = FenceResource.blockModel_inventory(name,
                    (textures.length >= 3 ? textures[2] : textures[0]), builder);

            String path = getPath(Main.MOD_ID + "/models/block");
            write(name + "_side", path, model_side);
            write(name + "_post", path, model_post);
            write(name + "_inventory", path, model_inventory);

            return new JsonObject[] { model_side, model_post, model_inventory };
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject getItemModel(String name) {
            JsonObject model = FenceResource.itemModel(name, builder);
            write(name, getPath(Main.MOD_ID + "/models/item"), model);
            return model;
        }

        /**
         * 
         * @param name
         * @param bases
         * @param templates
         * @param colors
         * @param mode
         * @param templateShading
         */
        public static void createTexture(String name, String[] bases, String[] templates, String[] colors, String mode,
                Boolean templateShading) {
            FenceResource.texture(name, getPath(Main.MOD_ID + "/textures/block"), bases, templates, colors, mode,
                    templateShading);
        }
    }

    public static class FenceGate {
        /**
         * 
         * @param name Should be formatted as partial registry name, like dirt.
         * @return
         */
        public static JsonObject getBlockstate(String name) {
            JsonObject blockstate = FenceGateResource.blockstate(name, builder);
            builder.write(getPath(Main.MOD_ID + "/blockstates"), name, blockstate);
            return blockstate;
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject[] getBlockModels(String name) {
            JsonObject model = FenceGateResource.blockModel(name, builder);
            JsonObject model_open = FenceGateResource.blockModel_open(name, builder);
            JsonObject model_wall = FenceGateResource.blockModel_wall(name, builder);
            JsonObject model_wallOpen = FenceGateResource.blockModel_wallOpen(name, builder);

            String path = getPath(Main.MOD_ID + "/models/block");
            write(name + "", path, model);
            write(name + "_open", path, model_open);
            write(name + "_wall", path, model_wall);
            write(name + "_wall_open", path, model_wallOpen);

            return new JsonObject[] { model, model_open, model_wall, model_wallOpen };
        }

        /**
         * 
         * @param name
         * @param textures
         * @return
         */
        public static JsonObject[] getBlockModels(String name, String[] textures) {
            Main.LOG.debug("FenceGate textures: " + String.join(", ", textures));

            JsonObject model = FenceGateResource.blockModel(name, textures[0], builder);
            JsonObject model_open = FenceGateResource.blockModel_open(name,
                    (textures.length >= 3 ? textures[1] : textures[0]), builder);
            JsonObject model_wall = FenceGateResource.blockModel_wall(name,
                    (textures.length >= 3 ? textures[2] : textures[0]), builder);
            JsonObject model_wallOpen = FenceGateResource.blockModel_wallOpen(name,
                    (textures.length >= 3 ? textures[3] : textures[0]), builder);

            String path = getPath(Main.MOD_ID + "/models/block");
            write(name + "", path, model);
            write(name + "_open", path, model_open);
            write(name + "_wall", path, model_wall);
            write(name + "_wall_open", path, model_wallOpen);

            return new JsonObject[] { model, model_open, model_wall, model_wallOpen };
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject getItemModel(String name) {
            JsonObject model = FenceGateResource.itemModel(name, builder);
            write(name, getPath(Main.MOD_ID + "/models/item"), model);
            return model;
        }

        /**
         * 
         * @param name
         * @param bases
         * @param templates
         * @param colors
         * @param mode
         * @param templateShading
         */
        public static void createTexture(String name, String[] bases, String[] templates, String[] colors, String mode,
                Boolean templateShading) {
            FenceGateResource.texture(name, getPath(Main.MOD_ID + "/textures/block"), bases, templates, colors, mode,
                    templateShading);
        }
    }

    public static class Door {
        /**
         * 
         * @param name Should be formatted as partial registry name, like dirt.
         * @return
         */
        public static JsonObject getBlockstate(String name) {
            JsonObject blockstate = DoorResource.blockstate(name, builder);
            builder.write(getPath(Main.MOD_ID + "/blockstates"), name, blockstate);
            return blockstate;
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject[] getBlockModels(String name) {
            JsonObject top = DoorResource.blockModel_top(name, builder);
            JsonObject top_hinge = DoorResource.blockModel_top_hinge(name, builder);
            JsonObject bottom = DoorResource.blockModel_bottom(name, builder);
            JsonObject bottom_hinge = DoorResource.blockModel_bottom_hinge(name, builder);

            String path = getPath(Main.MOD_ID + "/models/block");
            write(name + "_top", path, top);
            write(name + "_top_hinge", path, top_hinge);
            write(name + "_bottom", path, bottom);
            write(name + "_bottom_hinge", path, bottom_hinge);

            return new JsonObject[] { top, top_hinge, bottom, bottom_hinge };
        }

        /**
         * 
         * @param name
         * @param textures
         * @return
         */
        public static JsonObject[] getBlockModels(String name, String[] textures) {
            Main.LOG.debug("Door textures: " + String.join(", ", textures));

            JsonObject top = DoorResource.blockModel_top(name, new String[]{textures[0], textures[1]}, builder);

            JsonObject top_hinge = DoorResource.blockModel_top_hinge(name, new String[] { textures[0], textures[1] },
                    builder);

            JsonObject bottom = DoorResource.blockModel_bottom(name, new String[] { textures[0], textures[1] },
                    builder);

            JsonObject bottom_hinge = DoorResource.blockModel_bottom_hinge(name,
                    new String[] { textures[0], textures[1] }, builder);

            String path = getPath(Main.MOD_ID + "/models/block");
            write(name + "_top", path, top);
            write(name + "_top_hinge", path, top_hinge);
            write(name + "_bottom", path, bottom);
            write(name + "_bottom_hinge", path, bottom_hinge);

            return new JsonObject[] { top, top_hinge, bottom, bottom_hinge };
        }

        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject getItemModel(String name) {
            JsonObject model = DoorResource.itemModel(name, builder);
            write(name, getPath(Main.MOD_ID + "/models/item"), model);
            return model;
        }

        /**
         * 
         * @param name
         * @param bases
         * @param templates
         * @param colors
         * @param mode
         * @param templateShading
         */
        public static void createTexture_top(String name, String[] bases, String[] templates, String[] colors, String mode,
                Boolean templateShading) {
            DoorResource.texture(name + "_top", getPath(Main.MOD_ID + "/textures/block"), bases, templates, colors, mode,
                    templateShading);
        }

        /**
         * 
         * @param name
         * @param bases
         * @param templates
         * @param colors
         * @param mode
         * @param templateShading
         */
        public static void createTexture_bottom(String name, String[] bases, String[] templates, String[] colors, String mode,
                Boolean templateShading) {
            DoorResource.texture(name + "_bottom", getPath(Main.MOD_ID + "/textures/block"), bases, templates, colors, mode,
                    templateShading);
        }
    }

    public static class Item {
        /**
         * 
         * @param name
         * @return
         */
        public static JsonObject getItemModel(String name) {
            JsonObject model = BlockResource.itemModel(name, builder);
            write(name, getPath(Main.MOD_ID + "/models/item"), model);
            return model;
        }

        /**
         * 
         * @param name
         * @param path
         * @param bases
         * @param templates
         * @param colors
         * @param mode
         * @param templateShading
         */
        public static void createTexture(String name, String bases[], String templates[], String[] colors, String mode,
                Boolean templateShading) {
            Main.LOG.info("Generating default asset for " + name + " using " + bases.length + " + " + templates.length);

            AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/item"), name, templates, bases, colors, mode,
                    templateShading);
        }
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
            builder.write(getPath(Main.MOD_ID + "/lang"), "en_us", file);
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
            AssetBuilder.createAnimationController(getPath(Main.MOD_ID + "/textures/block"), name, frameCount,
                    frameTime, frameSettings);
        }
    }
}
