package com.github.chrisofnormandy.conloot.content.world_gen;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.world.data.Biome;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;

public class BiomeBuilder {
    public final HashMap<String, Biome> biomes = new HashMap<String, Biome>();

    public BiomeBuilder() {}

    public Biome create(String name) {
        Biome biome = DataPackBuilder.WorldGen.Biome.create();

        // Generation
        biome.root.setCategory("plains");
        biome.root.setDepth(0.125f);
        biome.root.setTemperature(0.8f);
        biome.root.setDownfall(0.4f);

        // Client
        biome.root.effects.setSkyColor(7254527).setFogColor(6840176).setWaterColor(4159204).setWaterFogColor(4341314);
        biome.root.effects.setMoodSound("minecraft:ambient.cave");

        // Features
        biome.root.carvers.addAirCarver("minecraft:cave").addAirCarver("minecraft:canyon");
        biome.root.features.addLake("minecraft:lake_water").addLake("minecraft:lake_lava");
        biome.root.features.addUndergroundStructure("minecraft:monster_room");

        biomes.put(name, biome);

        return biome;
    }
}
