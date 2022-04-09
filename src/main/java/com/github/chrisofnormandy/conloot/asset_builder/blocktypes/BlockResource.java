package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;

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
     * @param options
     * @param builder
     * @return
     */
    public static JsonObject blockstate(String name, ConfigOptions options, JsonBuilder builder) {
        Main.LOG.debug("Blockstate: " + options.renderModel);

        switch (options.renderModel) {
            case "column":
                return blockstate_column(name, builder, options.opens);
            case "bottom_top":
                return blockstate_bottom_top(name, builder, options.rotates, options.opens);
        }

        return null;
    }

    private static JsonObject blockstate_column(String name, JsonBuilder builder, Boolean opens) {
        JsonObject json = builder.createJsonObject();
        JsonObject vars = json.addObject("variants");

        vars.addObject("axis=x").set("model", Main.MOD_ID + ":block/" + name + "_horizontal").set("x", 90).set("y", 90);
        vars.addObject("axis=y").set("model", Main.MOD_ID + ":block/" + name);
        vars.addObject("axis=z").set("model", Main.MOD_ID + ":block/" + name + "_horizontal").set("x", 90);

        return json;
    }

    private static JsonObject blockstate_bottom_top(String name, JsonBuilder builder, Boolean rotates, Boolean opens) {
        JsonObject json = builder.createJsonObject();
        JsonObject vars = json.addObject("variants");

        Main.LOG.debug("Blockstate bottom_top ::: Rotates: " + rotates + ", Opens: " + opens);

        if (rotates) {
            String[] dir = { "north", "east", "south", "west", "up", "down" };

            if (opens) {
                String[] openState = { "true", "false" };

                for (String o : openState) {
                    for (int i = 0; i < dir.length; i++) {
                        JsonObject v = vars.addObject("facing=" + dir[i] + ",open=" + o);

                        if (i == 5) {
                            v.set("x", 180);
                        }
                        else if (i < 4) {
                            v.set("x", 90);

                            if (i > 0)
                                v.set("y", 90 * i);
                        }

                        v.set("model", Main.MOD_ID + ":block/" + name + (o.equals("true") ? "_open" : ""));
                    }
                }
            }
            else {
                for (int i = 0; i < dir.length; i++) {
                    JsonObject v = vars.addObject("facing=" + dir[i]);

                    if (i == 5) {
                        v.set("x", 180);
                    }
                    else if (i < 4) {
                        v.set("x", 90);

                        if (i > 0)
                            v.set("y", 90 * i);
                    }

                    v.set("model", Main.MOD_ID + ":block/" + name);
                }
            }
        }
        else {
            if (opens) {
                String[] openState = { "true", "false" };

                for (String o : openState) {
                    vars.addObject("axis=x,open=" + o).set("model", Main.MOD_ID + ":block/" + name + "_horizontal" + (o.equals("true") ? "_open" : "")).set("x", 90).set("y", 90);
                    vars.addObject("axis=y,open=" + o).set("model", Main.MOD_ID + ":block/" + name + (o.equals("true") ? "_open" : ""));
                    vars.addObject("axis=z,open=" + o).set("model", Main.MOD_ID + ":block/" + name + "_horizontal" + (o.equals("true") ? "_open" : "")).set("x", 90);
                }
            }
            else {
                vars.addObject("axis=x").set("model", Main.MOD_ID + ":block/" + name + "_horizontal").set("x", 90).set("y", 90);
                vars.addObject("axis=y").set("model", Main.MOD_ID + ":block/" + name);
                vars.addObject("axis=z").set("model", Main.MOD_ID + ":block/" + name + "_horizontal").set("x", 90);
            }
        }

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
                ? new String[] { "minecraft:block/debug" }
                : textures;

        json.set("parent", "minecraft:block/" + model);

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
