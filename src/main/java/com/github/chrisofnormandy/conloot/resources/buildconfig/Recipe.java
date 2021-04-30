package com.github.chrisofnormandy.conloot.resources.buildconfig;

import com.github.chrisofnormandy.conlib.Main;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;

public class Recipe {
    static JsonBuilder jsonBuilder  = new JsonBuilder();

    public static JsonObject pickaxe(String item, String result) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("options", 0);
        json.set("pattern", "xxx; s ; s ");
        json.set("result", result.contains("minecraft:") ? result : Main.MOD_ID + ":" + result);

        JsonObject key = json.addObject("key");
        key.addObject("x").set("item", item.contains("minecraft:") ? item : Main.MOD_ID + ":" + item);
        key.addObject("s").set("item", "minecraft:stick");

        return json;
    }

    public static JsonObject pickaxe(String item) {
        return pickaxe(item, item + "_pickaxe");
    }

    public static JsonObject axe(String item, String result) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("options", 0);
        json.set("pattern", "xx;xs; s");
        json.set("result", result.contains("minecraft:") ? result : Main.MOD_ID + ":" + result);

        JsonObject key = json.addObject("key");
        key.addObject("x").set("item", item.contains("minecraft:") ? item : Main.MOD_ID + ":" + item);
        key.addObject("s").set("item", "minecraft:stick");

        return json;
    }

    public JsonObject axe(String item) {
        return axe(item, item + "_axe");
    }

    public static JsonObject shovel(String item, String result) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("options", 0);
        json.set("pattern", "x;s;s");
        json.set("result", result.contains("minecraft:") ? result : Main.MOD_ID + ":" + result);

        JsonObject key = json.addObject("key");
        key.addObject("x").set("item", item.contains("minecraft:") ? item : Main.MOD_ID + ":" + item);
        key.addObject("s").set("item", "minecraft:stick");

        return json;
    }

    public static JsonObject shovel(String item) {
        return shovel(item, item + "_shovel");
    }

    public static JsonObject hoe(String item, String result) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("options", 0);
        json.set("pattern", "xx; s; s");
        json.set("result", result.contains("minecraft:") ? result : Main.MOD_ID + ":" + result);

        JsonObject key = json.addObject("key");
        key.addObject("x").set("item", item.contains("minecraft:") ? item : Main.MOD_ID + ":" + item);
        key.addObject("s").set("item", "minecraft:stick");

        return json;
    }

    public static JsonObject hoe(String item) {
        return hoe(item, item + "_hoe");
    }

    public static JsonObject sword(String item, String result) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("options", 0);
        json.set("pattern", "x;x;s");
        json.set("result", result.contains("minecraft:") ? result : Main.MOD_ID + ":" + result);

        JsonObject key = json.addObject("key");
        key.addObject("x").set("item", item.contains("minecraft:") ? item : Main.MOD_ID + ":" + item);
        key.addObject("s").set("item", "minecraft:stick");

        return json;
    }

    public static JsonObject sword(String item) {
        return sword(item, item + "_sword");
    }

    public static JsonObject smelt(String oreName, String item, Integer amount, Float exp, Integer cookingTime) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("options", 2);
        json.set("pattern", oreName);
        json.set("result", item + "*" + amount);
        json.set("experience", exp);
        json.set("cookingTime", cookingTime);
        return json;
    }

    public static JsonObject smelt(String oreName, String item, Integer amount) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("options", 2);
        json.set("pattern", oreName);
        json.set("result", item + "*" + amount);
        json.set("experience", 0.3f);
        json.set("cookingTime", 200);
        return json;
    }

    public static JsonObject smelt(String oreName, String item) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("options", 2);
        json.set("pattern", oreName);
        json.set("result", item);
        json.set("experience", 0.3f);
        json.set("cookingTime", 200);
        return json;
    }

    public static JsonObject nugget(String item) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("options", 0);
        json.set("type", "shapeless");
        json.set("pattern", "x");
        json.set("result", item + "_nugget");

        JsonObject key = json.addObject("key");
        key.addObject("x").set("item", item);
        return json;
    }

    // public static JsonObject fullBlock(String item) {
    //     JsonObject json = jsonBuilder.createJsonObject();
    //     json.set("options", 0);
    //     json.set("type", "shapeless");
    //     json.set("pattern", "x");
    //     JsonObject key = json.addObject("key");
    //     key.addObject("x").set("item", item);
    //     json.set("result", item + "_nugget");
    //     return json;
    // }
}
