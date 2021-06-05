package com.github.chrisofnormandy.conloot.asset_builder;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonArray;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conlib.common.Files;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.content.world_gen.BiomeBuilder;

public class DataPackBuilder {
    private static JsonBuilder builder = new JsonBuilder();

    public static String getPath(String path) {
        return "resources/" + Main.MOD_ID + "_datapack/data/" + path;
    }

    static {
        JsonObject mcmeta = builder.createJsonObject();
        JsonObject a = mcmeta.addObject("pack");
        a.set("pack_format", 6);
        a.set("description", "Generated data pack from " + Main.MOD_ID + ".");

        Files.write("resources/" + Main.MOD_ID + "_datapack", "pack", builder.stringify(mcmeta), ".mcmeta");
    }

    public static class WorldGen {
        public static class Biome {
            public static com.github.chrisofnormandy.conlib.world.data.Biome create() {
                return new com.github.chrisofnormandy.conlib.world.data.Biome();
            }

            public static void write(BiomeBuilder builder) {
                Main.LOG.info("Writing data pack info for WorldGen.Biome:");
                
                builder.biomes.forEach((String biomeName, com.github.chrisofnormandy.conlib.world.data.Biome biome) -> {
                    Main.LOG.info("Writing data pack info for WorldGen.Biome:" + biomeName);

                    biome.getFeatures().forEach((String featureName, JsonObject feature) -> {
                        Main.LOG.info("Writing data pack info for WorldGen.Biome -> ConfiguredFeature:" + biomeName + ":" + featureName);

                        Files.write(getPath(Main.MOD_ID + "/worldgen/configured_feature"), featureName, feature.toString(), ".json");
                    });

                    Files.write(getPath("minecraft/worldgen/biome"), biomeName, biome.build().toString(), ".json");
                });
            }
        }

        public static class ConfiguredFeature {
            public static com.github.chrisofnormandy.conlib.world.data.ConfiguredFeature create() {
                return new com.github.chrisofnormandy.conlib.world.data.ConfiguredFeature();
            }
        }
    }

    public static class LootTable {
        /**
         * @param name Should be formatted as full registry name, like minecraft:dirt.
         * @return
         */
        public static JsonObject block(String name) {
            JsonObject json = builder.createJsonObject();

            json.set("type", "minecraft:block");
            JsonArray pools = json.addArray("pools");
            JsonObject a = pools.addObject();

            a.set("rolls", 1);
            JsonArray entries = a.addArray("entries");
            JsonObject b = entries.addObject();

            b.set("type", "minecraft:alternatives");
            JsonArray children = b.addArray("children");
            JsonObject c = children.addObject();
            JsonObject f = children.addObject();

            c.set("type", "minecraft:item");
            c.set("name", name);
            JsonArray conditions = c.addArray("conditions");
            JsonObject d = conditions.addObject();

            d.set("condition", "minecraft:match_tool");
            JsonObject e = d.addObject("predicate").addArray("enchantments").addObject();
            e.set("enchantment", "minecraft:silk_touch");
            e.addObject("levels").set("min", 1);

            f.set("type", "minecraft:item");
            f.set("name",name);
            JsonArray functions = f.addArray("functions");
            JsonObject g = functions.addObject();
            g.set("function", "minecraft:set_count");
            g.set("count", 1);
            JsonObject h = functions.addObject();
            h.set("function", "minecraft:explosion_decay");

            builder.write(getPath(Main.MOD_ID + "/" + "loot_tables/blocks"), name.replaceAll("\\w+:", ""), json);

            return json;
        }

        /**
         * @param name Should be formatted as full registry name, like minecraft:dirt.
         * @return
         */
        public static JsonObject block(String name, String drop) {
            JsonObject json = builder.createJsonObject();

            json.set("type", "minecraft:block");
            JsonArray pools = json.addArray("pools");
            JsonObject a = pools.addObject();

            a.set("rolls", 1);
            JsonArray entries = a.addArray("entries");
            JsonObject b = entries.addObject();

            b.set("type", "minecraft:alternatives");
            JsonArray children = b.addArray("children");
            JsonObject c = children.addObject();
            JsonObject f = children.addObject();

            c.set("type", "minecraft:item");
            c.set("name", name);
            JsonArray conditions = c.addArray("conditions");
            JsonObject d = conditions.addObject();

            d.set("condition", "minecraft:match_tool");
            JsonObject e = d.addObject("predicate").addArray("enchantments").addObject();
            e.set("enchantment", "minecraft:silk_touch");
            e.addObject("levels").set("min", 1);

            f.set("type", "minecraft:item");
            f.set("name", drop);
            JsonArray functions = f.addArray("functions");
            JsonObject g = functions.addObject();
            g.set("function", "minecraft:set_count");
            g.set("count", 1);
            JsonObject h = functions.addObject();
            h.set("function", "minecraft:explosion_decay");

            builder.write(getPath(Main.MOD_ID + "/" + "loot_tables/blocks"), name.replaceAll("\\w+:", ""), json);

            return json;
        }
    }

    public static class Recipe {
        private static JsonObject r(String type, String[] pattern, HashMap<String, String> keys, String product,
                Integer count) {
            JsonObject json = builder.createJsonObject();

            json.set("type", type);

            JsonArray pat = json.addArray("pattern");
            for (String p : pattern)
                pat.add(p);

            JsonObject key_obj = json.addObject("key");
            keys.forEach((String key, String value) -> {
                key_obj.addObject(key).set("item", value);
            });

            JsonObject result = json.addObject("result");
            result.set("item", product);
            result.set("count", count);

            return json;
        }

        public static class Crafting {
            /**
             * 
             * @param name The recipe name.
             * @param pattern An array of strings, such as [ "XX", "XX" ].
             * @param keys A map of key-value pairs that relate to the pattern, such as X: minecraft:dirt.
             * @param product Should be formatted as full registry name, like minecraft:dirt.
             * @param count 
             * @return
             */
            public static JsonObject shaped(String name, String[] pattern, HashMap<String, String> keys, String product,
                    Integer count) {
                JsonObject json = r("minecraft:crafting_shaped", pattern, keys, product, count);

                builder.write(getPath(Main.MOD_ID + "/" + "recipes/crafting"), name, json);

                return json;
            }

            /**
             * 
             * @param name The recipe name.
             * @param pattern An array of strings, such as [ "XX", "XX" ].
             * @param keys A map of key-value pairs that relate to the pattern, such as X: minecraft:dirt.
             * @param product Should be formatted as full registry name, like minecraft:dirt.
             * @param count 
             * @return
             */
            public static JsonObject shapeless(String name, String[] pattern, HashMap<String, String> keys, String product,
                    Integer count) {
                JsonObject json = r("minecraft:crafting_shapeless", pattern, keys, product, count);
                
                builder.write(getPath(Main.MOD_ID + "/" + "recipes/crafting"), name, json);

                return json;
            }
        }

        public static class Smelting {
            /**
             * 
             * @param name The recipe name.
             * @param ingredient Should be formatted as full registry name, like minecraft:dirt.
             * @param product Should be formatted as full registry name, like minecraft:dirt.
             * @param count
             * @return
             */
            public static JsonObject run(String name, String ingredient, String product, Integer count) {
                JsonObject json = builder.createJsonObject();

                json.set("type", "minecraft:smelting");

                json.addObject("ingredient").set("item", product);

                JsonObject result = json.addObject("result");
                result.set("item", product);
                result.set("count", count);

                builder.write(getPath(Main.MOD_ID + "/" + "recipes/smelting"), name, json);

                return json;
            }
        }

        public static class Stonecutting {
            /**
             * 
             * @param name The recipe name.
             * @param ingredient Should be formatted as full registry name, like minecraft:dirt.
             * @param product Should be formatted as full registry name, like minecraft:dirt.
             * @param count
             * @return
             */
            public static JsonObject block(String name, String ingredient, String product, Integer count) {
                JsonObject json = builder.createJsonObject();

                json.set("type", "minecraft:stonecutting");

                json.addObject("ingredient").set("item", product);

                JsonObject result = json.addObject("result");
                result.set("item", product);
                result.set("count", count);

                builder.write(getPath(Main.MOD_ID + "/" + "recipes/stonecutting"), name, json);

                return json;
            }

            /**
             * 
             * @param ingredient Should be formatted as full registry name, like minecraft:dirt.
             * @param product Should be formatted as full registry name, like minecraft:dirt.
             * @param count
             * @return
             */
            public static JsonObject slab(String name, String ingredient, String product) {
                return block(name, ingredient, product, 2);
            }

            /**
             * 
             * @param ingredient Should be formatted as full registry name, like minecraft:dirt.
             * @param product Should be formatted as full registry name, like minecraft:dirt.
             * @param count
             * @return
             */
            public static JsonObject stairs(String name, String ingredient, String product) {
                return block(name, ingredient, product, 1);
            }

            /**
             * 
             * @param ingredient Should be formatted as full registry name, like minecraft:dirt.
             * @param product Should be formatted as full registry name, like minecraft:dirt.
             * @param count
             * @return
             */
            public static JsonObject wall(String name, String ingredient, String product) {
                return block(name, ingredient, product, 1);
            }
        }
    }

    public static class Tags {
        static JsonObject slabs = builder.createJsonObject();
        static JsonObject stairs = builder.createJsonObject();
        static JsonObject walls = builder.createJsonObject();

        static {
            slabs.set("replace", false);
            stairs.set("replace", false);
            walls.set("replace", false);

            slabs.addArray("values");
            stairs.addArray("values");
            walls.addArray("values");
        }

        public static void addSlab(String name) {
            slabs.getArray("values").add(Main.MOD_ID + ":" + name);
        }

        public static void addStairs(String name) {
            stairs.getArray("values").add(Main.MOD_ID + ":" + name);
        }

        public static void addWall(String name) {
            walls.getArray("values").add(Main.MOD_ID + ":" + name);
        }

        public static void write() {
            builder.write(getPath("minecraft" + "/" + "tags/blocks"), "slabs", slabs);
            builder.write(getPath("minecraft" + "/" + "tags/blocks"), "stairs", stairs);
            builder.write(getPath("minecraft" + "/" + "tags/blocks"), "walls", walls);

            builder.write(getPath("minecraft" + "/" + "tags/items"), "slabs", slabs);
            builder.write(getPath("minecraft" + "/" + "tags/items"), "stairs", stairs);
            builder.write(getPath("minecraft" + "/" + "tags/items"), "walls", walls);
        }
    }
}
