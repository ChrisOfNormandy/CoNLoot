package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonArray;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;
import com.github.chrisofnormandy.conloot.asset_builder.AssetBuilder;

public class WallResource {
    /**
     * 
     * @param name
     * @param multipart
     * @param when
     */
    private static void blockstateVariants(String name, JsonArray multipart, String when) {
        JsonObject objN = multipart.addObject();
        JsonObject objE = multipart.addObject();
        JsonObject objS = multipart.addObject();
        JsonObject objW = multipart.addObject();

        objN.addObject("when").set("north", when);
        objE.addObject("when").set("east", when);
        objS.addObject("when").set("south", when);
        objW.addObject("when").set("west", when);

        objN.addObject("apply").set("model", name + "_side" + (when.equals("tall") ? "_tall" : "")).set("uvlock", true);
        objE.addObject("apply").set("model", name + "_side" + (when.equals("tall") ? "_tall" : "")).set("uvlock", true)
                .set("y", 90);
        objS.addObject("apply").set("model", name + "_side" + (when.equals("tall") ? "_tall" : "")).set("uvlock", true)
                .set("y", 180);
        objW.addObject("apply").set("model", name + "_side" + (when.equals("tall") ? "_tall" : "")).set("uvlock", true)
                .set("y", 270);
    }

    /**
     * 
     * @param name Should be formatted as partial registry name, like stone_wall.
     * @return
     */
    public static JsonObject blockstate(String name, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        JsonArray multipart = json.addArray("multipart");

        String name_ = Main.MOD_ID + ":block/" + name;

        JsonObject a = multipart.addObject();
        a.addObject("when").set("up", "true");
        a.addObject("apply").set("model", name_ + "_post");

        String[] whens = { "low", "tall" };
        for (String w : whens)
            blockstateVariants(name_, multipart, w);

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
                put(name + "_side_tall", blockModel_sideTall(name, builder));
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
        side.set("parent", "minecraft:block/template_wall_side");
        side.addObject("textures").set("wall", Main.MOD_ID + ":block/" + name);

        return side;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_sideTall(String name, JsonBuilder builder) {
        JsonObject sideTall = builder.createJsonObject();
        sideTall.set("parent", "minecraft:block/template_wall_side_tall");
        sideTall.addObject("textures").set("wall", Main.MOD_ID + ":block/" + name);

        return sideTall;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_post(String name, JsonBuilder builder) {
        JsonObject post = builder.createJsonObject();
        post.set("parent", "minecraft:block/template_wall_post");
        post.addObject("textures").set("wall", Main.MOD_ID + ":block/" + name);

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
        inv.set("parent", "minecraft:block/wall_inventory");
        inv.addObject("textures").set("wall", Main.MOD_ID + ":block/" + name);

        return inv;
    }

    /**
     * 
     * @param name
     * @param textures
     * @param builder
     * @return
     */
    public static HashMap<String, JsonObject> blockModel(String name, String[] textures, JsonBuilder builder) {
        String texture = textures.length == 0
            ? "minecraft:block/debug"
            : textures[0];

        return new HashMap<String, JsonObject>() {
            {
                put(name + "_side", blockModel_side(name, texture, builder));
                put(name + "_post", blockModel_post(name, texture, builder));
                put(name + "_side_tall", blockModel_sideTall(name, texture, builder));
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
        side.set("parent", "minecraft:block/template_wall_side");
        JsonObject tex = side.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("wall", texture);
        else
            tex.set("wall", Main.MOD_ID + ":block/" + texture);

        return side;
    }

    /**
     * 
     * @param name
     * @param texture
     * @param builder
     * @return
     */
    public static JsonObject blockModel_sideTall(String name, String texture, JsonBuilder builder) {
        JsonObject sideTall = builder.createJsonObject();
        sideTall.set("parent", "minecraft:block/template_wall_side_tall");
        JsonObject tex = sideTall.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("wall", texture);
        else
            tex.set("wall", Main.MOD_ID + ":block/" + texture);

        return sideTall;
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
        post.set("parent", "minecraft:block/template_wall_post");
        JsonObject tex = post.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("wall", texture);
        else
            tex.set("wall", Main.MOD_ID + ":block/" + texture);

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
        inv.set("parent", "minecraft:block/wall_inventory");
        JsonObject tex = inv.addObject("textures");

        if (Patterns.modID.matcher(texture).find())
            tex.set("wall", texture);
        else
            tex.set("wall", Main.MOD_ID + ":block/" + texture);

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

    /**
     * 
     * @param name
     * @param path
     * @param bases
     * @param templates
     * @param colors
     * @param mode
     * @param templateShading
     */
    public static void texture(String name, String path, String bases[], String templates[], String[] colors,
            String mode, Boolean templateShading) {
        Main.LOG.info("Generating default asset for " + name + " using " + bases.length + " + " + templates.length);

        AssetBuilder.createImage(path, name, templates, bases, colors, mode, templateShading);
    }
}
