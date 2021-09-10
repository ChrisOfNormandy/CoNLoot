package com.github.chrisofnormandy.conloot.asset_builder.items;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonArray;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conloot.Main;

public class HandheldResource {

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject itemModel(String name, String texture, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        json.set("parent", "minecraft:item/handheld");
        JsonObject layers = json.addObject("textures");
        layers.set("layer0", texture);
        return json;
    }

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject itemModel(String name, String[] textures, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        json.set("parent", "minecraft:item/handheld");

        JsonObject layers = json.addObject("textures");
        for (int i = 0; i < textures.length; i++)
            layers.set("layer" + i, textures[i]);

        return json;
    }

    public static JsonObject itemModel(String name, String parent, String[] textures, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        json.set("parent", parent);

        JsonObject layers = json.addObject("textures");
        for (int i = 0; i < textures.length; i++)
            layers.set("layer" + i, textures[i]);

        return json;
    }

    public static JsonObject itemModel_twoStep(String name1, String name2, String[] textures, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        json.set("parent", "minecraft:item/handheld");

        JsonObject layers = json.addObject("textures");
        for (int i = 0; i < textures.length; i++)
            layers.set("layer" + i, textures[i]);

        return json;
    }

    public static JsonObject itemModel_twoStep(String name1, String name2, String parent, String predicateKey,
            Integer predicateValue, String[] textures, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        json.set("parent", parent);

        JsonObject layers = json.addObject("textures");
        for (int i = 0; i < textures.length; i++)
            layers.set("layer" + i, textures[i]);

        JsonArray overrides = json.addArray("overrides");
        JsonObject ov = overrides.addObject();
        ov.addObject("predicate").set(predicateKey, predicateValue);
        ov.set("model", Main.MOD_ID + ":item/" + name2);

        return json;
    }
}
