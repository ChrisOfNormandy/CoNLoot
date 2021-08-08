package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;

public class DoorResource {
    /**
     * 
     * @param name
     * @param variants
     * @param half
     * @param shape
     */
    private static void blockstateVariants(String name, JsonObject variants, Boolean half, Boolean hinge,
            Boolean open) {
        String[] directions = new String[] { "east", "north", "south", "west" };

        for (int i = 0; i < directions.length; i++) {
            String facing = directions[i];

            JsonObject var = variants.addObject("facing=" + facing + ",half=" + (half ? "lower" : "upper") + ",hinge="
                    + (hinge ? "left" : "right") + ",open=" + open);

            switch (i) {
                case 0: {
                    if (hinge && open)
                        var.set("y", 90);
                    if (!hinge && open)
                        var.set("y", 270);
                    break;
                }
                case 1: {
                    if (!hinge && open)
                        var.set("y", 180);
                    if (!open)
                        var.set("y", 270);
                    break;
                }
                case 2: {
                    if (!open)
                        var.set("y", 90);
                    if (hinge && open)
                        var.set("y", 180);
                    break;
                }
                case 3: {
                    if (!hinge && open)
                        var.set("y", 90);
                    if (!open)
                        var.set("y", 180);
                    if (hinge && open)
                        var.set("y", 270);
                    break;
                }
            }

            String model = Main.MOD_ID + ":block/" + name + (half ? "_bottom" : "_top")
                    + ((hinge && open) || (!hinge && !open) ? "_hinge" : "");

            var.set("model", model);
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

        for (int half = 0; half < 2; half++) {
            for (int hinge = 0; hinge < 2; hinge++) {
                for (int open = 0; open < 2; open++)
                    blockstateVariants(name, variants, half == 0, hinge == 0, open == 0);
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
                put(name + "_top", blockModel_top(name, builder));
                put(name + "_top_hinge", blockModel_top_hinge(name, builder));
                put(name + "_bottom", blockModel_bottom(name, builder));
                put(name + "_bottom_hinge", blockModel_bottom_hinge(name, builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_top(String name, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/door_top");
        def.addObject("textures").set("top", Main.MOD_ID + ":block/" + name + "_top").set("bottom",
                Main.MOD_ID + ":block/" + name + "_bottom");

        return def;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_top_hinge(String name, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/door_top_rh");
        def.addObject("textures").set("top", Main.MOD_ID + ":block/" + name + "_top").set("bottom",
                Main.MOD_ID + ":block/" + name + "_bottom");

        return def;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_bottom(String name, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/door_bottom");
        def.addObject("textures").set("top", Main.MOD_ID + ":block/" + name + "_top").set("bottom",
                Main.MOD_ID + ":block/" + name + "_bottom");

        return def;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_bottom_hinge(String name, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/door_bottom_rh");
        def.addObject("textures").set("top", Main.MOD_ID + ":block/" + name + "_top").set("bottom",
                Main.MOD_ID + ":block/" + name + "_bottom");

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
        String[] texture;

        if (textures.length < 2) {
            if (textures.length == 0)
                texture = new String[] { "minecraft:block/debug", "minecraft:block/debug" };
            else
                texture = new String[] { textures[0], textures[0] };
        } else
            texture = textures;

        return new HashMap<String, JsonObject>() {
            {
                put(name + "_top", blockModel_top(name, texture, builder));
                put(name + "_top_hinge", blockModel_top_hinge(name, texture, builder));
                put(name + "_bottom", blockModel_bottom(name, texture, builder));
                put(name + "_bottom_hinge", blockModel_bottom_hinge(name, texture, builder));
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
    public static JsonObject blockModel_top(String name, String[] textures, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/door_top");
        JsonObject tex = def.addObject("textures");

        if (Patterns.modID.matcher(textures[0]).find())
            tex.set("top", textures[0]);
        else
            tex.set("top", Main.MOD_ID + ":block/" + textures[0]);

        if (Patterns.modID.matcher(textures[1]).find())
            tex.set("bottom", textures[1]);
        else
            tex.set("bottom", Main.MOD_ID + ":block/" + textures[1]);

        return def;
    }

    /**
     * 
     * @param name
     * @param textures
     * @param builder
     * @return
     */
    public static JsonObject blockModel_top_hinge(String name, String[] textures, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/door_top_rh");
        JsonObject tex = def.addObject("textures");

        if (Patterns.modID.matcher(textures[0]).find())
            tex.set("top", textures[0]);
        else
            tex.set("top", Main.MOD_ID + ":block/" + textures[0]);

        if (Patterns.modID.matcher(textures[1]).find())
            tex.set("bottom", textures[1]);
        else
            tex.set("bottom", Main.MOD_ID + ":block/" + textures[1]);

        return def;
    }

    /**
     * 
     * @param name
     * @param textures
     * @param builder
     * @return
     */
    public static JsonObject blockModel_bottom(String name, String[] textures, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/door_bottom");
        JsonObject tex = def.addObject("textures");

        if (Patterns.modID.matcher(textures[0]).find())
            tex.set("top", textures[0]);
        else
            tex.set("top", Main.MOD_ID + ":block/" + textures[0]);

        if (Patterns.modID.matcher(textures[1]).find())
            tex.set("bottom", textures[1]);
        else
            tex.set("bottom", Main.MOD_ID + ":block/" + textures[1]);

        return def;
    }

    /**
     * 
     * @param name
     * @param textures
     * @param builder
     * @return
     */
    public static JsonObject blockModel_bottom_hinge(String name, String[] textures, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/door_bottom_rh");
        JsonObject tex = def.addObject("textures");

        if (Patterns.modID.matcher(textures[0]).find())
            tex.set("top", textures[0]);
        else
            tex.set("top", Main.MOD_ID + ":block/" + textures[0]);

        if (Patterns.modID.matcher(textures[1]).find())
            tex.set("bottom", textures[1]);
        else
            tex.set("bottom", Main.MOD_ID + ":block/" + textures[1]);

        return def;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject itemModel(String name, JsonBuilder builder) {
        JsonObject item = builder.createJsonObject();
        item.set("parent", "minecraft:item/generated");
        item.addObject("textures").set("layer0", Main.MOD_ID + ":item/" + name);

        return item;
    }


    /**
     * 
     * @param name
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject itemModel(String name, String itemTexture, JsonBuilder builder) {
        JsonObject item = builder.createJsonObject();
        item.set("parent", "minecraft:item/generated");
        item.addObject("textures").set("layer0", itemTexture);

        return item;
    }
}
