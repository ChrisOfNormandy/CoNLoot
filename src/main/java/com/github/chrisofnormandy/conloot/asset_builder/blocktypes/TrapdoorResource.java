package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;
import com.github.chrisofnormandy.conloot.asset_builder.AssetBuilder;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;

public class TrapdoorResource {
    /**
     * 
     * @param name
     * @param variants
     * @param half
     * @param open
     */
    private static void blockstateVariants(String name, JsonObject variants, Boolean half, Boolean open) {
        String[] directions = new String[] { "east", "north", "south", "west" };

        for (int i = 0; i < directions.length; i++) {
            String facing = directions[i];

            JsonObject var = variants
                    .addObject("facing=" + facing + ",half=" + (half ? "top" : "bottom") + ",open=" + open);

            switch (i) {
                case 0: {
                    if (half && open)
                        var.set("x", 180).set("y", 270);
                    else
                        var.set("y", 90);
                    break;
                }
                case 1: {
                    if (half && open)
                        var.set("x", 180).set("y", 180);
                    break;
                }
                case 2: {
                    if (half && open)
                        var.set("x", 180).set("y", 0);
                    else
                        var.set("y", 180);
                    break;
                }
                case 3: {
                    if (half && open)
                        var.set("x", 180).set("y", 90);
                    else
                        var.set("y", 270);
                    break;
                }
            }

            String model = Main.MOD_ID + ":block/" + name + (open ? "_open" : (half ? "_top" : "_bottom"));

            var.set("model", model);
        }
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockstate(String name, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        JsonObject variants = json.addObject("variants");

        for (int half = 0; half < 2; half++) {
            for (int open = 0; open < 2; open++)
                blockstateVariants(name, variants, half == 0, open == 0);
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
                put(name + "_bottom", blockModel_bottom(name, builder));
                put(name + "_open", blockModel_open(name, builder));
                put(name + "_top", blockModel_top(name, builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_bottom(String name, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/template_orientable_trapdoor_bottom");
        def.addObject("textures").set("texture", Main.MOD_ID + ":block/" + name);

        return def;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_open(String name, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/template_orientable_trapdoor_open");
        def.addObject("textures").set("texture", Main.MOD_ID + ":block/" + name);

        return def;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_top(String name, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/template_orientable_trapdoor_top");
        def.addObject("textures").set("texture", Main.MOD_ID + ":block/" + name);

        return def;
    }

    /**
     * 
     * @param name
     * @param textures
     * @param builder
     * @return
     */
    public static HashMap<String, JsonObject> blockModel(String name, String[] textures, JsonBuilder builder) {
        String texture = textures.length == 0 ? "minecraft:block/debug" : textures[0];

        return new HashMap<String, JsonObject>() {
            {
                put(name + "_bottom", blockModel_bottom(name, texture, builder));
                put(name + "_open", blockModel_open(name, texture, builder));
                put(name + "_top", blockModel_top(name, texture, builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject blockModel_bottom(String name, String texture, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/template_orientable_trapdoor_bottom");
        JsonObject tex = def.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("texture", texture);
        else
            tex.set("texture", Main.MOD_ID + ":block/" + texture);

        return def;
    }

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject blockModel_open(String name, String texture, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/template_orientable_trapdoor_open");
        JsonObject tex = def.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("texture", texture);
        else
            tex.set("texture", Main.MOD_ID + ":block/" + texture);

        return def;
    }

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject blockModel_top(String name, String texture, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/template_orientable_trapdoor_top");
        JsonObject tex = def.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("texture", texture);
        else
            tex.set("texture", Main.MOD_ID + ":block/" + texture);

        return def;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject itemModel(String name, JsonBuilder builder) {
        return builder.createJsonObject().set("parent", Main.MOD_ID + ":block/" + name + "_bottom");
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
    public static void texture(String name, String path, String bases[], String templates[], String[] colors,
            String mode, Boolean templateShading) {
        Main.LOG.info("Generating default asset for " + name + " using " + bases.length + " + " + templates.length);

        AssetBuilder.createImage(path, name, templates, bases, colors, mode, templateShading);
    }
}
