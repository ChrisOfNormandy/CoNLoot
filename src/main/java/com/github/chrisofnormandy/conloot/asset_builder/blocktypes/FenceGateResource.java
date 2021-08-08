package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;

public class FenceGateResource {
    /**
     * 
     * @param name
     * @param variants
     * @param in_wall
     * @param open
     */
    private static void blockstateVariants(String name, JsonObject variants, Boolean in_wall, Boolean open) {
        String[] directions = new String[] { "south", "west", "north", "east" };

        for (int i = 0; i < directions.length; i++) {
            String facing = directions[i];

            JsonObject var = variants.addObject("facing=" + facing + ",in_wall=" + in_wall + ",open=" + open)
                    .set("uvlock", true);

            if (i > 0)
                var.set("y", 90 * i);

            var.set("model", Main.MOD_ID + ":block/" + name + (in_wall ? "_wall" : "") + (open ? "_open" : ""));
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

        for (int in_wall = 0; in_wall < 2; in_wall++) {
            for (int open = 0; open < 2; open++)
                blockstateVariants(name, variants, in_wall == 0, open == 0);
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
                put(name + "_open", blockModel_open(name, builder));
                put(name + "_wall", blockModel_wall(name, builder));
                put(name + "wall_open", blockModel_wallOpen(name, builder));
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
        def.set("parent", "minecraft:block/template_fence_gate");
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
        JsonObject open = builder.createJsonObject();
        open.set("parent", "minecraft:block/template_fence_gate_open");
        open.addObject("textures").set("texture", Main.MOD_ID + ":block/" + name);

        return open;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_wall(String name, JsonBuilder builder) {
        JsonObject wall = builder.createJsonObject();
        wall.set("parent", "minecraft:block/template_fence_gate_wall");
        wall.addObject("textures").set("texture", Main.MOD_ID + ":block/" + name);

        return wall;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_wallOpen(String name, JsonBuilder builder) {
        JsonObject wallOpen = builder.createJsonObject();
        wallOpen.set("parent", "minecraft:block/template_fence_gate_wall_open");
        wallOpen.addObject("textures").set("texture", Main.MOD_ID + ":block/" + name);

        return wallOpen;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static HashMap<String, JsonObject> blockModel(String name, String[] textures, JsonBuilder builder) {
    
        String texture = textures.length == 0
            ? "minecraft:block/debug"
            : textures[0];

        return new HashMap<String, JsonObject>() {
            {
                put(name, blockModel_(name, texture, builder));
                put(name + "_open", blockModel_open(name, texture, builder));
                put(name + "_wall", blockModel_wall(name, texture, builder));
                put(name + "wall_open", blockModel_wallOpen(name, texture, builder));
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
    public static JsonObject blockModel_(String name, String texture, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/template_fence_gate");
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
        JsonObject open = builder.createJsonObject();
        open.set("parent", "minecraft:block/template_fence_gate_open");
        JsonObject tex = open.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("texture", texture);
        else
            tex.set("texture", Main.MOD_ID + ":block/" + texture);

        return open;
    }

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject blockModel_wall(String name, String texture, JsonBuilder builder) {
        JsonObject wall = builder.createJsonObject();
        wall.set("parent", "minecraft:block/template_fence_gate_wall");
        JsonObject tex = wall.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("texture", texture);
        else
            tex.set("texture", Main.MOD_ID + ":block/" + texture);

        return wall;
    }

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject blockModel_wallOpen(String name, String texture, JsonBuilder builder) {
        JsonObject wallOpen = builder.createJsonObject();
        wallOpen.set("parent", "minecraft:block/template_fence_gate_wall_open");
        JsonObject tex = wallOpen.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("texture", texture);
        else
            tex.set("texture", Main.MOD_ID + ":block/" + texture);

        return wallOpen;
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
