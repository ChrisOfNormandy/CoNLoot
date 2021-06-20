package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;
import com.github.chrisofnormandy.conloot.asset_builder.AssetBuilder;

public class StairsResource {
    /**
     * 
     * @param name
     * @param variants
     * @param half
     * @param shape
     */
    private static void blockstateVariants(String name, JsonObject variants, String half, String shape) {
        String name_ = Main.MOD_ID + ":block/" + name
                + (shape.equals("outer_right") || shape.equals("outer_left") ? "_outer"
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
            } else
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
     * @param name
     * @param path
     * @param builder
     * @return
     */
    public static JsonObject blockstate(String name, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        JsonObject variants = json.addObject("variants");

        variants.addObject("facing=east,half=bottom,shape=straight").set("model", Main.MOD_ID + ":block/" + name);

        String[] halves = { "bottom", "top" };
        String[] shapes = { "straight", "outer_right", "outer_left", "inner_right", "inner_left" };

        for (String h : halves) {
            for (String s : shapes) {
                blockstateVariants(name, variants, h, s);
            }
        }

        return json;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel(String name, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/stairs");
        JsonObject a = def.addObject("textures");
        a.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
        a.set("top", Main.MOD_ID + ":block/" + name + "_top");
        a.set("side", Main.MOD_ID + ":block/" + name + "_side");

        return def;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_inner(String name, JsonBuilder builder) {
        JsonObject inner = builder.createJsonObject();
        inner.set("parent", "minecraft:block/inner_stairs");
        JsonObject b = inner.addObject("textures");
        b.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
        b.set("top", Main.MOD_ID + ":block/" + name + "_top");
        b.set("side", Main.MOD_ID + ":block/" + name + "_side");

        return inner;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_outer(String name, JsonBuilder builder) {
        JsonObject outer = builder.createJsonObject();
        outer.set("parent", "minecraft:block/outer_stairs");
        JsonObject c = outer.addObject("textures");
        c.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
        c.set("top", Main.MOD_ID + ":block/" + name + "_top");
        c.set("side", Main.MOD_ID + ":block/" + name + "_side");

        return outer;
    }

    /**
     * 
     * @param name
     * @param textures
     * @param builder
     * @return
     */
    public static JsonObject blockModel(String name, String[] textures, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/stairs");
        JsonObject a = def.addObject("textures");

        if (textures.length >= 3) {
            if (Patterns.modID.matcher(textures[0]).find()) {
                a.set("bottom", textures[0]);
                a.set("top", textures[1]);
                a.set("side", textures[2]);
            } else {
                a.set("bottom", Main.MOD_ID + ":block/" + textures[0]);
                a.set("top", Main.MOD_ID + ":block/" + textures[1]);
                a.set("side", Main.MOD_ID + ":block/" + textures[2]);
            }
        } else {
            if (Patterns.modID.matcher(textures[0]).find()) {
                a.set("bottom", textures[0]);
                a.set("top", textures[1]);
                a.set("side", textures[2]);
            } else {
                String tex = Main.MOD_ID + ":block/" + textures[0];
                a.set("bottom", tex);
                a.set("top", tex);
                a.set("side", tex);
            }
        }

        return def;
    }

    /**
     * 
     * @param name
     * @param textures
     * @param builder
     * @return
     */
    public static JsonObject blockModel_inner(String name, String[] textures, JsonBuilder builder) {
        JsonObject inner = builder.createJsonObject();
        inner.set("parent", "minecraft:block/inner_stairs");
        JsonObject a = inner.addObject("textures");

        if (textures.length >= 3) {
            if (Patterns.modID.matcher(textures[0]).find()) {
                a.set("bottom", textures[0]);
                a.set("top", textures[1]);
                a.set("side", textures[2]);
            } else {
                a.set("bottom", Main.MOD_ID + ":block/" + textures[0]);
                a.set("top", Main.MOD_ID + ":block/" + textures[1]);
                a.set("side", Main.MOD_ID + ":block/" + textures[2]);
            }
        } else {
            if (Patterns.modID.matcher(textures[0]).find()) {
                a.set("bottom", textures[0]);
                a.set("top", textures[1]);
                a.set("side", textures[2]);
            } else {
                String tex = Main.MOD_ID + ":block/" + textures[0];
                a.set("bottom", tex);
                a.set("top", tex);
                a.set("side", tex);
            }
        }

        return inner;
    }

    /**
     * 
     * @param name
     * @param textures
     * @param builder
     * @return
     */
    public static JsonObject blockModel_outer(String name, String[] textures, JsonBuilder builder) {
        JsonObject outer = builder.createJsonObject();
        outer.set("parent", "minecraft:block/outer_stairs");
        JsonObject a = outer.addObject("textures");

        if (textures.length >= 3) {
            if (Patterns.modID.matcher(textures[0]).find()) {
                a.set("bottom", textures[0]);
                a.set("top", textures[1]);
                a.set("side", textures[2]);
            } else {
                a.set("bottom", Main.MOD_ID + ":block/" + textures[0]);
                a.set("top", Main.MOD_ID + ":block/" + textures[1]);
                a.set("side", Main.MOD_ID + ":block/" + textures[2]);
            }
        } else {
            if (Patterns.modID.matcher(textures[0]).find()) {
                a.set("bottom", textures[0]);
                a.set("top", textures[1]);
                a.set("side", textures[2]);
            } else {
                String tex = Main.MOD_ID + ":block/" + textures[0];
                a.set("bottom", tex);
                a.set("top", tex);
                a.set("side", tex);
            }
        }

        return outer;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject itemModel(String name, JsonBuilder builder) {
        return builder.createJsonObject().set("parent", Main.MOD_ID + ":block/" + name);
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
    public static void texture(String name, String path, String[] bases, String[] templates, String[] colors,
            String mode, Boolean templateShading) {
        Main.LOG.info("Generating default asset for " + name + " using " + bases.length + " base textures and + "
                + templates.length + " template textures.");

        if (bases.length > templates.length || (bases.length > 1 && bases.length < templates.length)) {
            Main.LOG.error("Cannot create assets for " + name
                    + " based on provided inputs. Check base template and overlay template counts.");
            return;
        }

        if (bases.length <= 1 && templates.length % 3 > 0) {
            Main.LOG.error("Cannot create assets for " + name + " because " + templates.length
                    + " is not evenly divisible by 3.");
            return;
        }

        AssetBuilder.createImage(path, name + "_bottom", new String[] { templates[0] }, new String[] { bases[0] },
                colors, mode, templateShading);
        AssetBuilder.createImage(path, name + "_top", new String[] { templates[1] }, new String[] { bases[1] }, colors,
                mode, templateShading);
        AssetBuilder.createImage(path, name + "_side", new String[] { templates[2] }, new String[] { bases[2] }, colors,
                mode, templateShading);
    }
}
