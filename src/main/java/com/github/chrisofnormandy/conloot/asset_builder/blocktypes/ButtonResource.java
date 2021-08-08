package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;

public class ButtonResource {
    /**
     * 
     * @param name
     * @param builder
     * @return
     */
    public static JsonObject blockstate(String name, JsonBuilder builder) {
        JsonObject json = builder.createJsonObject();
        JsonObject variants = json.addObject("variants");

        String[] facings = new String[] { "north", "east", "south", "west" };
        String[] faces = new String[] { "ceiling", "floor", "wall" };

        for (int facing = 0; facing < 4; facing++) {
            for (int face = 0; face < 3; face++) {
                for (int powered = 0; powered < 2; powered++) {
                    JsonObject v = variants.addObject(
                            "face=" + faces[face] + ",facing=" + facings[facing] + ",powered=" + (powered == 1));

                    v.set("model", Main.MOD_ID + ":block/" + name + (powered == 1 ? "_pressed" : ""));

                    if (faces[face].equals("ceiling")) {
                        v.set("x", 180);
                        if (facing != 3)
                            v.set("y", (180 + 90 * facing) % 360);

                    } else if (faces[face].equals("floor")) {
                        if (facing > 0)
                            v.set("y", 90 * facing);
                    } else {
                        v.set("uvlock", true);
                        v.set("x", 90);
                        if (facing > 0)
                            v.set("y", 90 * facing);
                    }
                }
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
    public static HashMap<String, JsonObject> blockModel(String name, JsonBuilder builder) {
        return new HashMap<String, JsonObject>() {
            {
                put(name, blockModel_(name, "", builder));
                put(name, blockModel_(name, "_pressed", builder));
                put(name, blockModel_(name, "_inventory", builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param model
     * @param builder
     * @return
     */
    private static JsonObject blockModel_(String name, String model, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/button" + model);
        JsonObject a = def.addObject("textures");
        a.set("texture", Main.MOD_ID + ":block/" + name + model);

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
                put(name, blockModel_(name, "", texture, builder));
                put(name + "_pressed", blockModel_(name, "_pressed", texture, builder));
                put(name + "_inventory", blockModel_(name, "_inventory", texture, builder));
            }
        };
    }

    /**
     * 
     * @param name
     * @param model
     * @param texture
     * @param builder
     * @return
     */
    private static JsonObject blockModel_(String name, String model, String texture, JsonBuilder builder) {
        JsonObject def = builder.createJsonObject();
        def.set("parent", "minecraft:block/button" + model);
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
        return builder.createJsonObject().set("parent", Main.MOD_ID + ":block/" + name + "_inventory");
    }
}
