package com.github.chrisofnormandy.conloot;

import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;
import com.github.chrisofnormandy.conloot.content.world_gen.CustomBiome;
import com.github.chrisofnormandy.conlib.world.data.Biome;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.config.Config;

public class ModWorldGen {
    private static Biome createBiomeModifier(String name, Config config) {
        return CustomBiome.registerBiomeFromConfig(name, config);
    }

    private static void biomeSetup(String name, Config config) {
        createBiomeModifier(name, config);
    }

    public static void Init() {
        Main.config.worldGenContent.forEach((String key, HashMap<String, Config> map) -> {
            String[] keyPath = key.split("\\.");

            switch (keyPath[0]) {
                case "biome": {
                    map.forEach((String name, Config config) -> biomeSetup(name, config));
                    break;
                }
            }
        });

        DataPackBuilder.WorldGen.Biome.write(CustomBiome.biomeBuilder);
    }
}
