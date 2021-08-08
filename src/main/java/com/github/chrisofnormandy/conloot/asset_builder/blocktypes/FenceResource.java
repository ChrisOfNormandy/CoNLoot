package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonArray;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;

public class FenceResource {
    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockstate(String name, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        JsonArray multipart = json.addArray("multipart");

        multipart.addObject().addObject("apply").set("model", Main.MOD_ID + ":block/" + name + "_post");

        JsonObject objN = multipart.addObject();
        JsonObject objE = multipart.addObject();
        JsonObject objS = multipart.addObject();
        JsonObject objW = multipart.addObject();

        objN.addObject("when").set("north", "true");
        objE.addObject("when").set("east", "true");
        objS.addObject("when").set("south", "true");
        objW.addObject("when").set("west", "true");

        objN.addObject("apply").set("model", Main.MOD_ID + ":block/" + name + "_side").set("uvlock", true);
        objE.addObject("apply").set("model", Main.MOD_ID + ":block/" + name + "_side").set("uvlock", true).set("y", 90);
        objS.addObject("apply").set("model", Main.MOD_ID + ":block/" + name + "_side").set("uvlock", true).set("y",
                180);
        objW.addObject("apply").set("model", Main.MOD_ID + ":block/" + name + "_side").set("uvlock", true).set("y",
                270);

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
                put(name + "_side", blockModel_side(name, builder));
                put(name + "_post", blockModel_post(name, builder));
                put(name + "_inventory", blockModel_inventory(name, builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_side(String name, JsonBuilder builder) {
        JsonObject side = builder.createJsonObject();
        side.set("parent", "minecraft:block/fence_side");
        side.addObject("textures").set("texture", Main.MOD_ID + ":block/" + name);

        return side;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_post(String name, JsonBuilder builder) {
        JsonObject post = builder.createJsonObject();
        post.set("parent", "minecraft:block/fence_post");
        post.addObject("textures").set("texture", Main.MOD_ID + ":block/" + name);

        return post;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_inventory(String name, JsonBuilder builder) {
        JsonObject inv = builder.createJsonObject();
        inv.set("parent", "minecraft:block/fence_inventory");
        inv.addObject("textures").set("texture", Main.MOD_ID + ":block/" + name);

        return inv;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static HashMap<String, JsonObject> blockModel(String name, String[] textures, JsonBuilder builder) {
        String texture = textures.length == 0 ? "minecraft:block/debug" : textures[0];

        return new HashMap<String, JsonObject>() {
            {
                put(name + "_side", blockModel_side(name, texture, builder));
                put(name + "_post", blockModel_post(name, texture, builder));
                put(name + "_inventory", blockModel_inventory(name, texture, builder));
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
    public static JsonObject blockModel_side(String name, String texture, JsonBuilder builder) {
        JsonObject side = builder.createJsonObject();
        side.set("parent", "minecraft:block/fence_side");
        JsonObject tex = side.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("texture", texture);
        else
            tex.set("texture", Main.MOD_ID + ":block/" + texture);

        return side;
    }

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject blockModel_post(String name, String texture, JsonBuilder builder) {
        JsonObject post = builder.createJsonObject();
        post.set("parent", "minecraft:block/fence_post");
        JsonObject tex = post.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("texture", texture);
        else
            tex.set("texture", Main.MOD_ID + ":block/" + texture);
        return post;
    }

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject blockModel_inventory(String name, String texture, JsonBuilder builder) {
        JsonObject inv = builder.createJsonObject();
        inv.set("parent", "minecraft:block/fence_inventory");
        JsonObject tex = inv.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("texture", texture);
        else
            tex.set("texture", Main.MOD_ID + ":block/" + texture);

        return inv;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject itemModel(String name, JsonBuilder builder) {
        return builder.createJsonObject().set("parent", Main.MOD_ID + ":block/" + name + "_inventory");
    }
}
