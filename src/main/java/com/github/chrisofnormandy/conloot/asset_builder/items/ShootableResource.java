package com.github.chrisofnormandy.conloot.asset_builder.items;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conloot.Main;

public class ShootableResource {

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject itemModel(String name, String texture, JsonBuilder builder) {
        Main.LOG.info(">>>>>>>>>>>>>>>>>>>>>>" + texture);
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
        Main.LOG.info("<<<<<<<<<<<<<<<<<" + String.join(", ", textures));
        JsonObject json = builder.createJsonObject();
        json.set("parent", "minecraft:item/generated");

        JsonObject layers = json.addObject("textures");
        for (int i = 0; i < textures.length; i++)
            layers.set("layer" + i, textures[i]);

        return json;
    }
}
