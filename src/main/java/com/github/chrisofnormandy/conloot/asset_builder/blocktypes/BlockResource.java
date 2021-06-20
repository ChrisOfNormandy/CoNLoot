package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;
import com.github.chrisofnormandy.conloot.asset_builder.AssetBuilder;

public class BlockResource {
    /**
     * 
     * @param name
     * @param path
     * @param builder
     * @return
     */
    public static JsonObject blockstate(String name, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        json.addObject("variants").addObject("").set("model",
                Main.MOD_ID + ":block/" + name);
        return json;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel(String name, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();

        json.set("parent", "minecraft:block/cube_all");
        json.addObject("textures").set("all", Main.MOD_ID + ":block/" + name);

        return json;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel(String name, String texture, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();

        json.set("parent", "minecraft:block/cube_all");
        JsonObject tex = json.addObject("textures");
        
        if (Patterns.modID.matcher(texture).find())
            tex.set("all", texture);
        else
            tex.set("all", Main.MOD_ID + ":block/" + texture);

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

    /**
     * 
     * @param name
     * @param path
     * @param builder
     * @param bases
     * @param templates
     * @param colors
     * @param mode
     * @param templateShading
     */
    public static void texture(String name, String path, String[] bases, String[] templates, String[] colors,
            String mode, Boolean templateShading) {
        Main.LOG.info("Generating default asset for " + name + " using " + bases.length + " base textures and + "
                + templates.length + " template textures.");

        if (bases.length > templates.length || (bases.length > 1 && bases.length < templates.length)) {
            Main.LOG.error(
                    "Cannot create asset based on provided inputs. Check base template and overlay template counts.");
            return;
        }

        AssetBuilder.createImage(path, name, templates, bases, colors, mode, templateShading);
    }
}
