package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;

public class PressurePlateResource {
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

        variants.addObject("powered=true").set("model", Main.MOD_ID + ":block/" + name + "_down");
        variants.addObject("powered=false").set("model", Main.MOD_ID + ":block/" + name);

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
                put(name, blockModel_(name, "up", builder));
                put(name + "_down", blockModel_(name, "down", builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    private static JsonObject blockModel_(String name, String model, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/pressure_plate_" + model);
        JsonObject a = def.addObject("textures");
        a.set("texture", Main.MOD_ID + ":block/" + name);

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
                put(name, blockModel_(name, "up", texture, builder));
                put(name + "_down", blockModel_(name, "down", texture, builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    private static JsonObject blockModel_(String name, String model, String texture, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/pressure_plate_" + model);
        JsonObject a = def.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            a.set("texture", texture);
        else
            a.set("texture", Main.MOD_ID + ":block/" + texture);

        return def;
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
