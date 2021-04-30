package com.github.chrisofnormandy.conloot.resources;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonArray;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;

public class Recipe {
    static JsonBuilder jsonBuilder = new JsonBuilder();

    public JsonObject shaped(String[] pattern, JsonObject key, JsonObject result) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("type", "minecraft:crafting_shaped");

        JsonArray arr = json.addArray("pattern");
        for (int i = 0; i < pattern.length; i++)
            arr.add(pattern[i]);

        json.set("key", key);
        json.set("result", result);

        return json;
    }

    public JsonObject shaped(String pattern, JsonObject key, String result, Integer count) {
        return shaped(pattern.split(";"), key, jsonBuilder.createJsonObject().set("result", result).set("count", count));
    }

    public JsonObject shaped(String pattern, JsonObject key, String result) {
        String[] s = result.split("*");
        return shaped(pattern.split(";"), key, jsonBuilder.createJsonObject().set("result", s[0]).set("count", s.length > 1 ? Integer.parseInt(s[1]) : 1));
    }

    public JsonObject shapeless(String[] pattern, JsonObject key, JsonObject result) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("type", "minecraft:crafting_shapeless");

        JsonArray arr = json.addArray("pattern");
        for (int i = 0; i < pattern.length; i++)
            arr.add(pattern[i]);

        json.set("key", key);
        json.set("result", result);

        return json;
    }

    public JsonObject shapeless(String pattern, JsonObject key, String result, Integer count) {
        return shapeless(pattern.split(";"), key, jsonBuilder.createJsonObject().set("result", result).set("count", count));
    }

    public JsonObject shapeless(String pattern, JsonObject key, String result) {
        String[] s = result.split("*");
        return shapeless(pattern.split(";"), key, jsonBuilder.createJsonObject().set("result", s[0]).set("count", s.length > 1 ? Integer.parseInt(s[1]) : 1));
    }

    public JsonObject furnace(String ingredient, String result, Float experience, Integer cookingTime) {
        JsonObject json = jsonBuilder.createJsonObject();

        json.set("type", "minecraft:smelting");
        json.addObject("ingredient").set("item", ingredient);
        json.set("result", result);
        json.set("experience", experience);
        json.set("cookingtime", cookingTime);

        return json;
    }

    public JsonObject furnace(String ingredient, String result) {
        return furnace(ingredient, result, 0.3f, 200);
    }

    public JsonObject stonecutter(JsonObject json) {
        return json;
    }
}
