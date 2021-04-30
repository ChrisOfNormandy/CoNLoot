package com.github.chrisofnormandy.conloot;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonArray;

import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class ModItems {
    static JsonBuilder jsonBuilder = new JsonBuilder();
    public static JsonArray itemJson = jsonBuilder.createJsonArray();
    public static JsonArray groupJson = jsonBuilder.createJsonArray();
    public static JsonArray recipeJson = jsonBuilder.createJsonArray();
        
    public static void Init() {
        JsonArray a = jsonBuilder.concat(itemJson, ModBlocks.itemJson);
        JsonArray b = jsonBuilder.concat(groupJson, ModBlocks.groupJson);
        JsonArray c = jsonBuilder.concat(recipeJson, ModBlocks.recipeJson);

        String configPath = FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).toString();

        jsonBuilder.write(configPath + "/items", a.toString());
        jsonBuilder.write(configPath + "/groups", b.toString());
        jsonBuilder.write(configPath + "/recipes", c.toString());
    }
}
