package com.github.chrisofnormandy.conloot.configs.worldgen;

import java.util.ArrayList;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;

public class BiomeConfig {
    public static void create(String name, Config cfg) {
        ConfigGroup effects = new ConfigGroup();
        ConfigGroup carvers = new ConfigGroup();
        ConfigGroup features = new ConfigGroup();
        ConfigGroup starts = new ConfigGroup();
        ConfigGroup spawners = new ConfigGroup();

        cfg.addDouble("scale", 0.05, "Scale.");
        cfg.addString("surface_builder", "minecraft:grass", "Surface builder.");
        cfg.addFlag("player_spawn_friendly", true, "Can the player spawn in this biome?");
        cfg.addString("precipitation", "rain", "Rain type.");
        cfg.addDouble("temperature", 0.8, "Temperature.");
        cfg.addDouble("downfall", 0.4, "Downfall.");
        cfg.addString("category", "plains", "Category.");
        cfg.addDouble("depth", 0.125, "Depth");

        cfg.addSubgroup("Effects", effects);
        cfg.addSubgroup("Carvers", carvers);
        cfg.addSubgroup("Features", features);
        cfg.addSubgroup("Starts", starts);
        cfg.addSubgroup("Spawners", spawners);

        ConfigGroup mood_sound = new ConfigGroup();
        mood_sound.addFlag("enable", false, "Enable mood sound.");
        mood_sound.addString("sound", "minecraft:ambient.cave", "Sound.");
        mood_sound.addInteger("tick_delay", 6000, "Tick delay.");
        mood_sound.addInteger("block_search_extent", 8, "Block search extent");
        mood_sound.addDouble("offset", 2.0, "Offset.");
        effects.addSubgroup("mood_sound", mood_sound);

        ConfigGroup additions_sound = new ConfigGroup();
        additions_sound.addFlag("enable", false, "Enable additions sound.");
        additions_sound.addString("sound", "none", "Ambient sound.");
        additions_sound.addDouble("tick_chance", 0.0, "Tick chance.");
        effects.addSubgroup("additions_sound", additions_sound);

        ConfigGroup music = new ConfigGroup();
        music.addFlag("enable", false, "Enable music.");
        music.addString("sound", "none", "Sound.");
        music.addInteger("min_delay", 12000, "Min delay.");
        music.addInteger("max_delay", 24000, "Max delay.");
        music.addFlag("replace_current_music", false, "Replace current music.");
        effects.addSubgroup("music", music);

        ConfigGroup ambient_sound = new ConfigGroup();
        ambient_sound.addFlag("enable", false, "Enable.");
        ambient_sound.addString("sound", "none", "Sound.");
        effects.addSubgroup("ambient_sound", ambient_sound);

        effects.addInteger("sky_color", 7907327, "Sky color.");
        effects.addInteger("fog_color", 12638463, "Fog color.");
        effects.addInteger("water_color", 4159204, "Water color.");
        effects.addInteger("water_fog_color", 329011, "Water fog color.");

        carvers.addStringList("air", new ArrayList<String>(), "Air carvers.");
        carvers.addStringList("liquid", new ArrayList<String>(), "Liquid carvers.");

        features.addStringList("raw_generation", new ArrayList<String>(), "Raw generation.");
        features.addStringList("lakes", new ArrayList<String>(), "Lakes.");
        features.addStringList("local_modifications", new ArrayList<String>(), "Local modifications.");
        features.addStringList("underground_structures", new ArrayList<String>(), "Underground structures.");
        features.addStringList("surface_structures", new ArrayList<String>(), "Surface structures.");
        features.addStringList("strongholds", new ArrayList<String>(), "Strongholds.");
        features.addStringList("underground_ores", new ArrayList<String>(), "Underground ores.");
        features.addStringList("underground_decoration", new ArrayList<String>(), "Underground decoration.");
        features.addStringList("vegetal_decoration", new ArrayList<String>(), "Vegetal decoration.");
        features.addStringList("top_layer_modification", new ArrayList<String>(), "Top layer modification");

        starts.addStringList("starts", new ArrayList<String>(), "Starts.");

        spawners.addStringList("monster", new ArrayList<String>(), "Monsters.");
        spawners.addStringList("creature", new ArrayList<String>(), "Creatures.");
        spawners.addStringList("ambient", new ArrayList<String>(), "Ambient creatures.");
        spawners.addStringList("water_creature", new ArrayList<String>(), "Water creatures.");
        spawners.addStringList("water_ambient", new ArrayList<String>(), "Ambient water creatures.");
        spawners.addStringList("misc", new ArrayList<String>(), "Misc.");
    }
}
