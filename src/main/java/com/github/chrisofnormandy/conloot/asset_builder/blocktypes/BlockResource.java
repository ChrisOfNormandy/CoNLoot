package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conloot.Main;

public class BlockResource {
    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockstate(String name, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();

        json.addObject("variants").addObject("").set("model", Main.MOD_ID + ":block/" + name);

        return json;
    }

    /**
     * 
     * @param name
     * @param subType
     * @param builder
     * @return
     */
    public static JsonObject blockstate(String name, String subType, JsonBuilder builder) {
        switch (subType) {
            case "column":
                return blockstate_column(name, builder);
        }
        return null;
    }

    private static JsonObject blockstate_column(String name, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        JsonObject vars = json.addObject("variants");

        vars.addObject("axis=x").set("model", Main.MOD_ID + ":block/" + name + "_horizontal").set("x", 90).set("y", 90);
        vars.addObject("axis=y").set("model", Main.MOD_ID + ":block/" + name);
        vars.addObject("axis=z").set("model", Main.MOD_ID + ":block/" + name + "_horizontal").set("x", 90);

        return json;
    }

    /**
     * 
     * @param name
     * @param model
     * @param builder
     * @return
     */
    public static JsonObject blockModel(String name, String model, JsonBuilder builder) {
        return blockModel_(name, model, new String[] { Main.MOD_ID + ":block/" + name }, builder);
    }

    /**
     * 
     * @param name
     * @param model
     * @param textures
     * @param builder
     * @return
     */
    public static JsonObject blockModel(String name, String model, String[] textures, JsonBuilder builder) {
        return blockModel_(name, model, textures, builder);
    }

    /**
     * 
     * @param name
     * @param textures
     * @param builder
     * @return
     */
    public static JsonObject blockModel(String name, String[] textures, JsonBuilder builder) {
        return blockModel_(name, "cube_all", textures, builder);
    }

    private static JsonObject blockModel_(String name, String model, String[] textures, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();

        JsonObject tex = json.addObject("textures");

        String[] texture = textures.length == 0
            ? new String[] {"minecraft:block/debug"}
            : textures;

        json.set("parent", "minecraft:block/" + (texture.length == 1 ? "cube_all" : model));

        switch (texture.length) {
            case 1: {
                tex.set("all", texture[0]);
                break;
            }
            case 2: {
                tex.set("end", texture[0]);
                tex.set("side", texture[1]);
                break;
            }
            case 3: {
                tex.set("top", texture[0]);
                tex.set("bottom", texture[1]);
                tex.set("side", texture[2]);
                break;
            }
            case 4: {
                tex.set("top", texture[0]);
                tex.set("bottom", texture[1]);
                tex.set("side", texture[2]);
                tex.set("front", texture[3]);
                break;
            }
            case 5: {
                tex.set("north", texture[0]);
                tex.set("east", texture[1]);
                tex.set("south", texture[2]);
                tex.set("west", texture[3]);
                tex.set("end", texture[4]);
                break;
            }
            default: {
                tex.set("particle", texture[0]);
                tex.set("north", texture[0]);
                tex.set("south", texture[1]);
                tex.set("east", texture[2]);
                tex.set("west", texture[3]);
                tex.set("up", texture[4]);
                tex.set("down", texture[5]);
                break;
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
    public static JsonObject itemModel(String name, JsonBuilder builder) {
        return builder.createJsonObject().set("parent", Main.MOD_ID + ":block/" + name);
    }
}
