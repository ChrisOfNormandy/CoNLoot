package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;

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
    public static HashMap<String, JsonObject> blockModel(String name, JsonBuilder builder) {
        return new HashMap<String, JsonObject>() {
            {
                put(name, blockModel_(name, builder));
                put(name + "_inner", blockModel_inner(name, builder));
                put(name + "_outer", blockModel_outer(name, builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_(String name, JsonBuilder builder) {
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
    public static HashMap<String, JsonObject> blockModel(String name, String[] textures, JsonBuilder builder) {
        String[] texture;

        if (textures.length < 3) {
            if (textures.length == 0)
                texture = new String[] { "minecraft:block/debug", "minecraft:block/debug", "minecraft:block/debug" };
            else
                texture = new String[] { textures[0], textures[0], textures[0] };
        } else
            texture = textures;

        return new HashMap<String, JsonObject>() {
            {
                put(name, blockModel_(name, texture, builder));
                put(name + "_inner", blockModel_inner(name, texture, builder));
                put(name + "_outer", blockModel_outer(name, texture, builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param textures
     * @param builder
     * @return
     */
    public static JsonObject blockModel_(String name, String[] textures, JsonBuilder builder) {
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
}
