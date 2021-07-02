package com.github.chrisofnormandy.conloot.content.world_gen;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conlib.world.data.Biome;
import com.github.chrisofnormandy.conloot.Main;

public class CustomBiome {
    public static final BiomeBuilder biomeBuilder = new BiomeBuilder();

    public static Biome addBiome(String name) {
        return biomeBuilder.create(name);
    }
    
    public static Biome registerBiomeFromConfig(String name, Config config) {
        Biome biome = addBiome(name);

        biome.root.setScale(config.getDoubleValue("scale").floatValue());
        biome.root.setSurfaceBuilder(config.getStringValue("surface_builder"));
        if (!config.getFlagValue("player_spawn_friendly"))
            biome.root.denyPlayerSpawn();
        biome.root.setPrecipitation(config.getStringValue("precipitation"));
        biome.root.setTemperature(config.getDoubleValue("downfall").floatValue());
        biome.root.setCategory(config.getStringValue("category"));
        biome.root.setDepth(config.getDoubleValue("depth").floatValue());

        ConfigGroup effects = config.getSubgroup("Effects");
        if (effects.getSubgroup("mood_sound").getFlagValue("enable"))
            biome.root.effects.setMoodSound(effects.getSubgroup("mood_sound").getStringValue("sound"));

        if (effects.getSubgroup("additions_sound").getFlagValue("enable"))
            biome.root.effects.setAdditionsSound(effects.getSubgroup("additions_sound").getStringValue("sound"));

        if (effects.getSubgroup("music").getFlagValue("enable"))
            biome.root.effects.setMusic(effects.getSubgroup("music").getStringValue("sound"));

        if (effects.getSubgroup("ambient_sound").getFlagValue("enable"))
            biome.root.effects.setAmbientSound(effects.getSubgroup("ambient_sound").getStringValue("sound"));

        biome.root.effects.setSkyColor(effects.getIntegerValue("sky_color"));
        biome.root.effects.setFogColor(effects.getIntegerValue("fog_color"));
        biome.root.effects.setWaterColor(effects.getIntegerValue("water_color"));
        biome.root.effects.setWaterFogColor(effects.getIntegerValue("water_fog_color"));

        ConfigGroup carvers = config.getSubgroup("Carvers");
        carvers.getStringListValue("air").forEach((String value) -> biome.root.carvers.addAirCarver(value));
        carvers.getStringListValue("liquid").forEach((String value) -> biome.root.carvers.addLiquidCarver(value));

        ConfigGroup features = config.getSubgroup("Features");
        features.getStringListValue("raw_generation")
                .forEach((String value) -> biome.root.features.addRawGeneration(value));
        features.getStringListValue("lakes").forEach((String value) -> biome.root.features.addLake(value));
        features.getStringListValue("local_modifications")
                .forEach((String value) -> biome.root.features.addLocalModification(value));
        features.getStringListValue("underground_structures")
                .forEach((String value) -> biome.root.features.addUndergroundStructure(value));
        features.getStringListValue("surface_structures")
                .forEach((String value) -> biome.root.features.addSurfaceStructure(value));
        features.getStringListValue("strongholds").forEach((String value) -> biome.root.features.addStronghold(value));
        features.getStringListValue("underground_ores")
                .forEach((String value) -> biome.root.features.addOreSpawn(value));
        features.getStringListValue("underground_decoration")
                .forEach((String value) -> biome.root.features.addUndergroundDecoration(value));
        features.getStringListValue("vegetal_decoration")
                .forEach((String value) -> biome.root.features.addVegetation(value));
        features.getStringListValue("top_layer_modification")
                .forEach((String value) -> biome.root.features.addTopLayerModification(value));

        config.getSubgroup("Starts").getStringListValue("starts").forEach((String value) -> biome.root.addStart(value));

        ConfigGroup spawners = config.getSubgroup("Spawners");
        spawners.getStringListValue("monster").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addMonster(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]),
                            Integer.parseInt(v[3]));
                } catch (Exception err) {
                    Main.LOG.warn("Could not add monster spawn for " + name + " using: " + value
                            + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            } else
                Main.LOG.warn("Could not add monster spawn for " + name + " using: " + value
                        + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });
        spawners.getStringListValue("creature").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addCreature(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]),
                            Integer.parseInt(v[3]));
                } catch (Exception err) {
                    Main.LOG.warn("Could not add creature spawn for " + name + " using: " + value
                            + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            } else
                Main.LOG.warn("Could not add creature spawn for " + name + " using: " + value
                        + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });
        spawners.getStringListValue("ambient").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addAmbient(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]),
                            Integer.parseInt(v[3]));
                } catch (Exception err) {
                    Main.LOG.warn("Could not add ambient spawn for " + name + " using: " + value
                            + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            } else
                Main.LOG.warn("Could not add ambient spawn for " + name + " using: " + value
                        + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });
        spawners.getStringListValue("water_creature").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addWaterCreature(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]),
                            Integer.parseInt(v[3]));
                } catch (Exception err) {
                    Main.LOG.warn("Could not add water creature spawn for " + name + " using: " + value
                            + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            } else
                Main.LOG.warn("Could not add water creature spawn for " + name + " using: " + value
                        + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });
        spawners.getStringListValue("water_ambient").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addWaterAmbient(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]),
                            Integer.parseInt(v[3]));
                } catch (Exception err) {
                    Main.LOG.warn("Could not add ambient water creature spawn for " + name + " using: " + value
                            + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            } else
                Main.LOG.warn("Could not add ambient water creature spawn for " + name + " using: " + value
                        + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });
        spawners.getStringListValue("misc").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addMisc(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]),
                            Integer.parseInt(v[3]));
                } catch (Exception err) {
                    Main.LOG.warn("Could not add misc spawn for " + name + " using: " + value
                            + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            } else
                Main.LOG.warn("Could not add misc spawn for " + name + " using: " + value
                        + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });

        return biome;
    }
}
