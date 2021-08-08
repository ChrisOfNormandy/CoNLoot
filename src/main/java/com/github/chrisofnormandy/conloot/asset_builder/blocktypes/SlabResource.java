package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;

public class SlabResource {
    /**
     * 
     * @param name
     * @param path
     * @param builder
     * @return
     */
    public static JsonObject blockstate(String name, String doubleName, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        JsonObject variants = json.addObject("variants");

        variants.addObject("type=bottom").set("model", Main.MOD_ID + ":block/" + name);
        variants.addObject("type=top").set("model", Main.MOD_ID + ":block/" + name + "_top");
        variants.addObject("type=double").set("model", Main.MOD_ID + ":block/" + doubleName);

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
                put(name, blockModel_(name, "slab", builder));
                put(name + "_top", blockModel_(name, "slab_top", builder));
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
        def.set("parent", "minecraft:block/" + model);
        JsonObject a = def.addObject("textures");
        a.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
        a.set("top", Main.MOD_ID + ":block/" + name + "_top");
        a.set("side", Main.MOD_ID + ":block/" + name + "_side");

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
        if (textures.length < 3) {
            if (textures.length == 0)
                texture = new String[] { "minecraft:block/debug", "minecraft:block/debug", "minecraft:block/debug" };
            else
                texture = new String[] { textures[0], textures[0], textures[0] };
        } else
            texture = textures;

        return new HashMap<String, JsonObject>() {
            {
                put(name, blockModel_(name, "slab", texture, builder));
                put(name + "_top", blockModel_(name, "slab_top", texture, builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    private static JsonObject blockModel_(String name, String model, String[] textures, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/" + model);
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
     * @param builder
     * @return
     */
    public static JsonObject itemModel(String name, JsonBuilder builder) {
        return builder.createJsonObject().set("parent", Main.MOD_ID + ":block/" + name);
    }
}
