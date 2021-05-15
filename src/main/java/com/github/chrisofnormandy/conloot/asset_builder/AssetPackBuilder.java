package com.github.chrisofnormandy.conloot.asset_builder;

import java.nio.file.Path;
import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonArray;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conlib.common.Files;
import com.github.chrisofnormandy.conloot.Main;

import net.minecraftforge.fml.loading.FMLPaths;

public class AssetPackBuilder {
    private static JsonBuilder builder = new JsonBuilder();

    public static String getPath(String path) {
        return "resourcepacks/" + Main.MOD_ID + "_resources/assets/" + path;
    }

    static {
        JsonObject mcmeta = builder.createJsonObject();
        JsonObject a = mcmeta.addObject("pack");
        a.set("pack_format", 6);
        a.set("description", "Generated resource pack from " + Main.MOD_ID + ".");

        Files.write("resourcepacks/" + Main.MOD_ID + "_resources/", "pack", builder.stringify(mcmeta), ".mcmeta");
    }

    public static class Blockstate {
        /**
         * 
         * @param name Should be formatted as partial registry name, like dirt.
         * @return
         */
        public static JsonObject block(String name) {
            JsonObject json = builder.createJsonObject();

            json.addObject("variants").addObject("").set("model", Main.MOD_ID + ":block/" + name);

            builder.write(getPath(Main.MOD_ID + "/" + "blockstates"), name, json);

            return json;
        }

        /**
         * @param name Should be formatted as partial registry name, like stone_slab.
         * @return
         */
        public static JsonObject slab(String name) {
            JsonObject json = builder.createJsonObject();
            JsonObject variants = json.addObject("variants");

            variants.addObject("type=bottom").set("model", Main.MOD_ID + ":block/" + name);
            variants.addObject("type=top").set("model", Main.MOD_ID + ":block/" + name.replace("_slab", "") + "_top");
            variants.addObject("type=double").set("model", Main.MOD_ID + ":block/" + name.replace("_slab", ""));

            builder.write(getPath(Main.MOD_ID + "/" + "blockstates"), name, json);

            return json;
        }

        private static void stair_variants(String name, JsonObject variants, String half, String shape) {
            String name_ = Main.MOD_ID + ":block/" + name + (shape.equals("outer_right") || shape.equals("outer_left") ? "_outer"
            : (shape.equals("inner_right") || shape.equals("inner_left") ? "_inner" : ""));

            if (half == "top") {
                // East
                JsonObject e = variants.addObject("facing=east,half=" + half + ",shape=" + shape).set("model", name_);
                e.set("uvlock", true);
                e.set("x", 180);
                if (shape.contains("right"))
                    e.set("y", 90);

                // West
                JsonObject w = variants.addObject("facing=west,half=" + half + ",shape=" + shape).set("model", name_);
                w.set("uvlock", true);
                w.set("x", 180);
                if (!shape.contains("right"))
                    w.set("y", 180);
                else
                    w.set("y", 270);

                // South
                JsonObject s = variants.addObject("facing=south,half=" + half + ",shape=" + shape).set("model", name_);
                s.set("uvlock", true);
                s.set("x", 180);
                if (!shape.contains("right"))
                    s.set("y", 90);
                else
                    s.set("y", 180);

                // North
                JsonObject n = variants.addObject("facing=north,half=" + half + ",shape=" + shape).set("model", name_);
                n.set("uvlock", true);
                n.set("x", 180);
                if (!shape.contains("right"))
                    n.set("y", 270);
            } else {
                // East
                JsonObject e = variants.addObject("facing=east,half=" + half + ",shape=" + shape).set("model", name_);
                if (shape.contains("left")) {
                    e.set("y", 270);
                    e.set("uvlock", true);
                }
                else
                    e.set("y", 0);

                // West
                JsonObject w = variants.addObject("facing=west,half=" + half + ",shape=" + shape).set("model", name_);
                w.set("uvlock", true);
                if (shape.contains("left"))
                    w.set("y", 90);
                else
                    w.set("y", 180);

                // South
                JsonObject s = variants.addObject("facing=south,half=" + half + ",shape=" + shape).set("model", name_);
                if (shape.contains("left"))
                    s.set("y", 0);
                else {
                    s.set("y", 90);
                    s.set("uvlock", true);
                }

                // North
                JsonObject n = variants.addObject("facing=north,half=" + half + ",shape=" + shape).set("model", name_);
                n.set("uvlock", true);
                if (shape.contains("left"))
                    n.set("y", 180);
                else
                    n.set("y", 270);
            }
        }

        /**
         * 
         * @param name Should be formatted as partial registry name, like stone_stairs.
         * @return
         */
        public static JsonObject stairs(String name) {
            JsonObject json = builder.createJsonObject();
            JsonObject variants = json.addObject("variants");

            variants.addObject("facing=east,half=bottom,shape=straight").set("model", Main.MOD_ID + ":block/" + name);

            String[] halves = { "bottom", "top" };
            String[] shapes = { "straight", "outer_right", "outer_left", "inner_right", "inner_left" };

            for (String h : halves) {
                for (String s : shapes) {
                    stair_variants(name, variants, h, s);
                }
            }

            builder.write(getPath(Main.MOD_ID + "/" + "blockstates"), name, json);

            return json;
        }

        private static void wall_variants(String name, JsonArray multipart, String when) {
            JsonObject objN = multipart.addObject();
            JsonObject objE = multipart.addObject();
            JsonObject objS = multipart.addObject();
            JsonObject objW = multipart.addObject();

            objN.addObject("when").set("north", when);
            objE.addObject("when").set("east", when);
            objS.addObject("when").set("south", when);
            objW.addObject("when").set("west", when);

            objN.addObject("apply").set("model", name + "_side" + (when.equals("tall") ? "_tall" : "")).set("uvlock", true);
            objE.addObject("apply").set("model", name + "_side" + (when.equals("tall") ? "_tall" : "")).set("uvlock", true).set("y", 90);;
            objS.addObject("apply").set("model", name + "_side" + (when.equals("tall") ? "_tall" : "")).set("uvlock", true).set("y", 180);;
            objW.addObject("apply").set("model", name + "_side" + (when.equals("tall") ? "_tall" : "")).set("uvlock", true).set("y", 270);;
        }

        /**
         * 
         * @param name Should be formatted as partial registry name, like stone_wall.
         * @return
         */
        public static JsonObject wall(String name) {
            JsonObject json = builder.createJsonObject();
            JsonArray multipart = json.addArray("multipart");

            String name_ = Main.MOD_ID + ":block/" + name;

            JsonObject a = multipart.addObject();
            a.addObject("when").set("up", "true");
            a.addObject("apply").set("model", name_ + "_post");

            String[] whens = { "low", "tall" };
            for (String w : whens)
                wall_variants(name_, multipart, w);

            builder.write(getPath(Main.MOD_ID + "/" + "blockstates"), name, json);

            return json;
        }
    }

    public static class Model {
        public static class Block {
            private static void write(String name, JsonObject json) {
                Main.LOG.info("Creating new model for " + name);
                Path p = FMLPaths.GAMEDIR.get().resolve(getPath(Main.MOD_ID + "/" + "models/block"));

                if (!p.resolve(name + ".json").toFile().exists())
                    builder.write(getPath(Main.MOD_ID + "/" + "models/block"), name, json);
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt.
             * @return
             */
            public static JsonObject block(String name) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", "minecraft:block/cube_all");
                json.addObject("textures").set("all", Main.MOD_ID + ":block/" + name);

                write(name, json);

                return json;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt.
             * @return
             */
            public static JsonObject block(String name, String base, String template, String[] colors, String mode,
                    Boolean templateShading) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", "minecraft:block/cube_all");
                json.addObject("textures").set("all", Main.MOD_ID + ":block/" + name);

                write(name, json);

                Main.LOG.info("Generating default asset for " + name + " using " + base + " + " + template);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/block"), name, template, base, colors, mode,
                        templateShading);

                return json;
            }

            private static HashMap<String, JsonObject> _wall(String name) {
                HashMap<String, JsonObject> map = new HashMap<String, JsonObject>();

                JsonObject side = builder.createJsonObject();
                side.set("parent", "minecraft:block/template_wall_side");
                side.addObject("textures").set("wall", Main.MOD_ID + ":block/" + name);

                JsonObject sideTall = builder.createJsonObject();
                sideTall.set("parent", "minecraft:block/template_wall_side_tall");
                sideTall.addObject("textures").set("wall", Main.MOD_ID + ":block/" + name);

                JsonObject post = builder.createJsonObject();
                post.set("parent", "minecraft:block/template_wall_post");
                post.addObject("textures").set("wall", Main.MOD_ID + ":block/" + name);

                JsonObject inv = builder.createJsonObject();
                inv.set("parent", "minecraft:block/wall_inventory");
                inv.addObject("textures").set("wall", Main.MOD_ID + ":block/" + name);

                map.put("_side", side);
                map.put("_side_tall", sideTall);
                map.put("_post", post);
                map.put("_inventory", inv);

                return map;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt. This is
             *             the parent block name.
             * @return
             */
            public static void wall(String name) {
                _wall(name).forEach((String ext, JsonObject json) -> {
                    builder.write(getPath(Main.MOD_ID + "/" + "models/block"), name + ext, json);
                });
            }

            public static void wall(String name, String base, String template, String[] colors, String mode,
                    Boolean templateShading) {
                _wall(name).forEach((String ext, JsonObject json) -> {
                    builder.write(getPath(Main.MOD_ID + "/" + "models/block"), name + ext, json);
                });

                Main.LOG.info("Generating default asset for " + name + " using " + base + " + " + template);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/block"), name, template, base, colors, mode,
                        templateShading);
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt. This is
             *             the parent block name.
             * @return
             */
            private static HashMap<String, JsonObject> _stairs(String name) {
                HashMap<String, JsonObject> map = new HashMap<String, JsonObject>();

                JsonObject def = builder.createJsonObject();
                def.set("parent", "minecraft:block/stairs");
                JsonObject a = def.addObject("textures");
                a.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
                a.set("top", Main.MOD_ID + ":block/" + name + "_top");
                a.set("side", Main.MOD_ID + ":block/" + name + "_side");

                JsonObject inner = builder.createJsonObject();
                inner.set("parent", "minecraft:block/inner_stairs");
                JsonObject b = inner.addObject("textures");
                b.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
                b.set("top", Main.MOD_ID + ":block/" + name + "_top");
                b.set("side", Main.MOD_ID + ":block/" + name + "_side");

                JsonObject outer = builder.createJsonObject();
                outer.set("parent", "minecraft:block/outer_stairs");
                JsonObject c = outer.addObject("textures");
                c.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
                c.set("top", Main.MOD_ID + ":block/" + name + "_top");
                c.set("side", Main.MOD_ID + ":block/" + name + "_side");

                map.put("", def);
                map.put("_inner", inner);
                map.put("_outer", outer);

                return map;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt. This is
             *             the parent block name.
             * @return
             */
            public static void stairs(String name) {
                _stairs(name).forEach((String ext, JsonObject json) -> {
                    builder.write(getPath(Main.MOD_ID + "/" + "models/block"), name + ext, json);
                });
            }

            /**
             * 
             * @param name
             * @param base            An array of templates. Order is bottom, top, side.
             * @param template        An array of templates. Order is bottom, top, side.
             * @param colors
             * @param mode
             * @param templateShading
             */
            public static void stairs(String name, String[] base, String[] template, String[] colors, String mode,
                    Boolean templateShading) {
                _stairs(name).forEach((String ext, JsonObject json) -> {
                    builder.write(getPath(Main.MOD_ID + "/" + "models/block"), name + ext, json);
                });

                Main.LOG.info("Generating default assets for " + name + " using " + base + " + " + template);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/block"), name + "_bottom", template[0],
                        base[0], colors, mode, templateShading);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/block"), name + "_top", template[1], base[1],
                        colors, mode, templateShading);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/block"), name + "_side", template[2], base[2],
                        colors, mode, templateShading);
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt. This is
             *             the parent block name.
             * @return
             */
            private static HashMap<String, JsonObject> _slab(String name) {
                HashMap<String, JsonObject> map = new HashMap<String, JsonObject>();

                JsonObject def = builder.createJsonObject();
                def.set("parent", "minecraft:block/slab");
                JsonObject a = def.addObject("textures");
                a.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
                a.set("top", Main.MOD_ID + ":block/" + name + "_top");
                a.set("side", Main.MOD_ID + ":block/" + name + "_side");

                JsonObject top = builder.createJsonObject();
                top.set("parent", "minecraft:block/slab_top");
                JsonObject b = top.addObject("textures");
                b.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
                b.set("top", Main.MOD_ID + ":block/" + name + "_top");
                b.set("side", Main.MOD_ID + ":block/" + name + "_side");

                map.put("", def);
                map.put("_top", top);

                return map;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt. This is
             *             the parent block name.
             * @return
             */
            public static void slab(String name) {
                _slab(name).forEach((String ext, JsonObject json) -> {
                    builder.write(getPath(Main.MOD_ID + "/" + "models/block"), name + ext, json);
                });
            }

            /**
             * 
             * @param name
             * @param base            An array of templates. Order is bottom, top, side.
             * @param template        An array of templates. Order is bottom, top, side.
             * @param colors
             * @param mode
             * @param templateShading
             */
            public static void slab(String name, String[] base, String[] template, String[] colors, String mode,
                    Boolean templateShading) {
                _slab(name).forEach((String ext, JsonObject json) -> {
                    write(name + ext, json);
                });

                Main.LOG.info("Generating default assets for " + name + " using " + base + " + " + template);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/block"), name + "_bottom", template[0],
                        base[0], colors, mode, templateShading);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/block"), name + "_top", template[1], base[1],
                        colors, mode, templateShading);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/block"), name + "_side", template[2], base[2],
                        colors, mode, templateShading);
            }
        }

        public static class Item {
            private static void write(String name, JsonObject json) {
                Main.LOG.info("Creating new model for " + name);
                Path p = FMLPaths.GAMEDIR.get().resolve(getPath(Main.MOD_ID + "/" + "models/item"));

                if (!p.resolve(name + ".json").toFile().exists())
                    builder.write(getPath(Main.MOD_ID + "/" + "models/item"), name, json);
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt.
             * @return
             */
            public static JsonObject block(String name) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", Main.MOD_ID + ":block/" + name);

                write(name, json);

                return json;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt.
             * @return
             */
            public static JsonObject wall(String name) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", Main.MOD_ID + ":block/" + name + "_inventory");

                write(name, json);

                return json;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt.
             * @return
             */
            public static JsonObject stairs(String name) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", Main.MOD_ID + ":block/" + name);

                write(name, json);

                return json;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like stone_slab.
             * @return
             */
            public static JsonObject slab(String name) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", Main.MOD_ID + ":block/" + name);

                write(name, json);

                return json;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt.
             * @return
             */
            public static JsonObject item(String name) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", "item/generated");
                json.addObject("textures").set("layer0", Main.MOD_ID + ":item/" + name);

                write(name, json);

                return json;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt.
             * @return
             */
            public static JsonObject item(String name, String base, String template, String[] colors, String mode,
                    Boolean templateShading) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", "item/generated");
                json.addObject("textures").set("layer0", Main.MOD_ID + ":item/" + name);

                write(name, json);

                Main.LOG.info("Generating default asset for " + name + " using " + base + " + " + template);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/item"), name, template, base, colors, mode,
                        templateShading);

                return json;
            }
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
            builder.write(getPath(Main.MOD_ID + "/" + "lang"), "en_us", file);
        }
    }
}
