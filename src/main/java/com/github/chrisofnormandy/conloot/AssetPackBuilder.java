package com.github.chrisofnormandy.conloot;

import java.nio.file.Path;
import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonArray;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conlib.common.Files;

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

            builder.write(getPath(Main.MOD_ID + "/" +"blockstates"), name, json);

            return json;
        }

        private static void stair_variants(String name, JsonObject variants, String half, String shape) {
            variants.addObject("facing=east,half=" + half + ",shape=" + shape).set("model", Main.MOD_ID + ":block/" + name);

            JsonObject a = variants.addObject("facing=west,half=" + half + ",shape=" + shape);
            a.set("model", Main.MOD_ID + ":block/" + name + (shape.equals("outer_right") || shape.equals("outer_left") ? "_outer" : (shape.equals("inner_right") || shape.equals("inner_left") ? "_inner" : "")));
            a.set("y", 180);
            a.set("uvlock", true);

            JsonObject b = variants.addObject("facing=south,half=" + half + ",shape=" + shape);
            b.set("model", Main.MOD_ID + ":block/" + name + (shape.equals("outer_right") || shape.equals("outer_left") ? "_outer" : (shape.equals("inner_right") || shape.equals("inner_left") ? "_inner" : "")));
            b.set("y", 90);
            b.set("uvlock", true);

            JsonObject c = variants.addObject("facing=north,half=" + half + ",shape=" + shape);
            c.set("model", Main.MOD_ID + ":block/" + name + (shape.equals("outer_right") || shape.equals("outer_left") ? "_outer" : (shape.equals("inner_right") || shape.equals("inner_left") ? "_inner" : "")));
            c.set("y", 270);
            c.set("uvlock", true);
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

            String[] halves = {"bottom", "top"};
            String[] shapes = {"straight", "outer_right", "outer_left", "inner_right", "inner_left"};

            for (String h : halves) {
                for (String s : shapes) {
                    stair_variants(name, variants, h, s);
                }
            }

            builder.write(getPath(Main.MOD_ID + "/" +"blockstates"), name, json);

            return json;
        }

        private static void wall_variants(String name, JsonArray arr, String when) {
            JsonObject b = arr.addObject();
            b.addObject("when").set("north", when);
            JsonObject ba = b.addObject("apply");
            ba.set("model", Main.MOD_ID + ":block/" + name + "_side" + (when.equals("tall") ? "_tall" : ""));
            ba.set("uvlock", true);

            JsonObject c = arr.addObject();
            c.addObject("when").set("east", when);
            JsonObject ca = c.addObject("apply");
            ca.set("model", Main.MOD_ID + ":block/" + name + "_side" + (when.equals("tall") ? "_tall" : ""));
            ca.set("y", 90);
            ca.set("uvlock", true);

            JsonObject d = arr.addObject();
            d.addObject("when").set("south", when);
            JsonObject da = c.addObject("apply");
            da.set("model", Main.MOD_ID + ":block/" + name + "_side" + (when.equals("tall") ? "_tall" : ""));
            da.set("y", 180);
            da.set("uvlock", true);

            JsonObject e = arr.addObject();
            e.addObject("when").set("west", when);
            JsonObject ea = c.addObject("apply");
            ea.set("model", Main.MOD_ID + ":block/" + name + "_side" + (when.equals("tall") ? "_tall" : ""));
            ea.set("y", 270);
            ea.set("uvlock", true);
        }

        /**
         * 
         * @param name Should be formatted as partial registry name, like stone_wall.
         * @return
         */
        public static JsonObject wall(String name) {
            JsonObject json = builder.createJsonObject();
            JsonArray arr = json.addArray("multipart");

            JsonObject a = arr.addObject();
            a.addObject("when").set("up", "true");
            a.addObject("apply").set("model", Main.MOD_ID + ":block/" + name + "_post");

            String[] whens = {"low", "tall"};
            for (String w : whens)
                wall_variants(name, arr, w);

            builder.write(getPath(Main.MOD_ID + "/" +"blockstates"), name, json);

            return json;
        }
    }

    public static class Model {
        public static class Block {
            private static void write(String name, JsonObject json) {
                Main.LOG.info("Creating new model for " + name);
                Path p = FMLPaths.GAMEDIR.get().resolve(getPath(Main.MOD_ID + "/" +"models/block"));
                
                if (!p.resolve(name + ".json").toFile().exists())
                    builder.write(getPath(Main.MOD_ID + "/" +"models/block"), name, json);
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
            public static JsonObject block(String name, String base, String template, String[] colors, String mode, Boolean templateShading) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", "minecraft:block/cube_all");
                json.addObject("textures").set("all", Main.MOD_ID + ":block/" + name);

                write(name, json);

                Main.LOG.info("Generating default asset for " + name + " using " + base + " + " + template);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/block"), name, template, base, colors, mode, templateShading);

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
             * @param name Should be formatted as partial registry name, like dirt. This is the parent block name.
             * @return
             */
            public static void wall(String name) {
                _wall(name).forEach((String ext, JsonObject json) -> {
                    builder.write(getPath(Main.MOD_ID + "/" +"models/block"), name + ext, json);
                });
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt. This is the parent block name.
             * @return
             */
            private static HashMap<String, JsonObject> _stairs(String name) {
                HashMap<String, JsonObject> map = new HashMap<String, JsonObject>();

                JsonObject def = builder.createJsonObject();
                def.set("parent", "minecraft:block/stairs");
                JsonObject a = def.addObject("textures");
                a.set("bottom", Main.MOD_ID + ":block/" + name);
                a.set("top", Main.MOD_ID + ":block/" + name);
                a.set("side", Main.MOD_ID + ":block/" + name);

                JsonObject inner = builder.createJsonObject();
                inner.set("parent", "minecraft:block/inner_stairs");
                JsonObject b = inner.addObject("textures");
                b.set("bottom", Main.MOD_ID + ":block/" + name);
                b.set("top", Main.MOD_ID + ":block/" + name);
                b.set("side", Main.MOD_ID + ":block/" + name);

                JsonObject outer = builder.createJsonObject();
                outer.set("parent", "minecraft:block/outer_stairs");
                JsonObject c = outer.addObject("textures");
                c.set("bottom", Main.MOD_ID + ":block/" + name);
                c.set("top", Main.MOD_ID + ":block/" + name);
                c.set("side", Main.MOD_ID + ":block/" + name);

                map.put("", def);
                map.put("_inner", inner);
                map.put("_outer", outer);

                return map;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt. This is the parent block name.
             * @return
             */
            public static void stairs(String name) {
                _stairs(name).forEach((String ext, JsonObject json) -> {
                    builder.write(getPath(Main.MOD_ID + "/" +"models/block"), name + ext, json);
                });
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt. This is the parent block name.
             * @return
             */
            private static HashMap<String, JsonObject> _slab(String name) {
                HashMap<String, JsonObject> map = new HashMap<String, JsonObject>();

                JsonObject def = builder.createJsonObject();
                def.set("parent", "minecraft:block/slab");
                JsonObject a = def.addObject("textures");
                a.set("bottom", Main.MOD_ID + ":block/" + name);
                a.set("top", Main.MOD_ID + ":block/" + name);
                a.set("side", Main.MOD_ID + ":block/" + name);

                JsonObject top = builder.createJsonObject();
                top.set("parent", "minecraft:block/slab_top");
                JsonObject b = top.addObject("textures");
                b.set("bottom", Main.MOD_ID + ":block/" + name);
                b.set("top", Main.MOD_ID + ":block/" + name);
                b.set("side", Main.MOD_ID + ":block/" + name);

                map.put("", def);
                map.put("_top", top);

                return map;
            }

            /**
             * 
             * @param name Should be formatted as partial registry name, like dirt. This is the parent block name.
             * @return
             */
            public static void slab(String name) {
                _slab(name).forEach((String ext, JsonObject json) -> {
                    builder.write(getPath(Main.MOD_ID + "/" +"models/block"), name + ext, json);
                });
            }
        }

        public static class Item {
            private static void write(String name, JsonObject json) {
                Main.LOG.info("Creating new model for " + name);
                Path p = FMLPaths.GAMEDIR.get().resolve(getPath(Main.MOD_ID + "/" +"models/item"));

                if (!p.resolve(name + ".json").toFile().exists())
                    builder.write(getPath(Main.MOD_ID + "/" +"models/item"), name, json);
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

                json.set("parent", Main.MOD_ID + ":block/" + name + "_wall_inventory");
                
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
             * @param name Should be formatted as partial registry name, like dirt.
             * @return
             */
            public static JsonObject slab(String name) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", Main.MOD_ID + ":block/" + name + "_slab");
                
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
            public static JsonObject item(String name, String base, String template, String[] colors, String mode, Boolean templateShading) {
                JsonObject json = builder.createJsonObject();

                json.set("parent", "item/generated");
                json.addObject("textures").set("layer0", Main.MOD_ID + ":item/" + name);
                
                write(name, json);

                Main.LOG.info("Generating default asset for " + name + " using " + base + " + " + template);
                AssetBuilder.createImage(getPath(Main.MOD_ID + "/textures/item"), name, template, base, colors, mode, templateShading);

                return json;
            }
        }
    }

    public static class Lang {
        public static JsonObject file = builder.createJsonObject();

        /**
         * 
         * @param registryName Should be formatted as partial registry name, like my_block.
         * @param name Should be the translated text, like My Block.
         */
        public static void addBlock(String registryName, String name) {
            file.set("block." + Main.MOD_ID + "." + registryName, name);
        }

        /**
         * 
         * @param registryName Should be formatted as partial registry name, like my_item.
         * @param name Should be the translated text, like My Item.
         */
        public static void addItem(String registryName, String name) {
            file.set("item." + Main.MOD_ID + "." + registryName, name);
        }

        /**
         * 
         * @param registryName Should be formatted as partial registry name, like my_group.
         * @param name Should be the translated text, like My Group.
         */
        public static void addGroup(String registryName, String name) {
            file.set("itemGroup." + registryName, name);
        }

        public static void write() {
            builder.write(getPath(Main.MOD_ID + "/" + "lang"), "en_us", file);
        }
    }
}
