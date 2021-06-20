package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;
import com.github.chrisofnormandy.conloot.asset_builder.AssetBuilder;

public class SlabResource {
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

        variants.addObject("type=bottom").set("model", Main.MOD_ID + ":block/" + name);
        variants.addObject("type=top").set("model", Main.MOD_ID + ":block/" + name.replace("_slab", "") + "_top");
        variants.addObject("type=double").set("model", Main.MOD_ID + ":block/" + name.replace("_slab", ""));

        return json;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel(String name, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/slab");
        JsonObject a = def.addObject("textures");
        a.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
        a.set("top", Main.MOD_ID + ":block/" + name + "_top");
        a.set("side", Main.MOD_ID + ":block/" + name + "_side");

        return def;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel_top(String name, JsonBuilder builder) {
        JsonObject top = builder.createJsonObject();
        top.set("parent", "minecraft:block/slab_top");
        JsonObject b = top.addObject("textures");
        b.set("bottom", Main.MOD_ID + ":block/" + name + "_bottom");
        b.set("top", Main.MOD_ID + ":block/" + name + "_top");
        b.set("side", Main.MOD_ID + ":block/" + name + "_side");

        return top;
    }

    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockModel(String name, String[] textures, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/slab");
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
        }
        else {
            if (Patterns.modID.matcher(textures[0]).find()) {
                a.set("bottom", textures[0]);
                a.set("top", textures[1]);
                a.set("side", textures[2]);
            }
            else {
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
    public static JsonObject blockModel_top(String name, String[] textures, JsonBuilder builder) {
        JsonObject top = builder.createJsonObject();
        top.set("parent", "minecraft:block/slab_top");
        JsonObject a = top.addObject("textures");
        
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

        return top;
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
            Main.LOG.error("Cannot create assets for " + name
                    + " based on provided inputs. Check base template and overlay template counts.");
            return;
        }

        if (bases.length <= 1 && templates.length % 3 > 0) {
            Main.LOG.error("Cannot create assets for " + name + " because " + templates.length
                    + " is not evenly divisible by 3.");
            return;
        }

        AssetBuilder.createImage(path, name + "_bottom", new String[] { templates[0] }, new String[] { bases[0] },
                colors, mode, templateShading);
        AssetBuilder.createImage(path, name + "_top", new String[] { templates[1] }, new String[] { bases[1] }, colors,
                mode, templateShading);
        AssetBuilder.createImage(path, name + "_side", new String[] { templates[2] }, new String[] { bases[2] }, colors,
                mode, templateShading);
    }
}
