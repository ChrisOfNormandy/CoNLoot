package com.github.chrisofnormandy.conloot.asset_builder.items;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;

public class ItemResource {

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject itemModel(String name, String texture, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        json.set("parent", "minecraft:item/generated");

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
        json.set("parent", "minecraft:item/generated");

        JsonObject layers = json.addObject("textures");
        for (int i = 0; i < textures.length; i++)
            layers.set("layer" + i, textures[i]);

        return json;
    }
}
