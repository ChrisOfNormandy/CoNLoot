package com.github.chrisofnormandy.conloot;

import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;
import com.github.chrisofnormandy.conloot.content.CustomResource;
import com.github.chrisofnormandy.conlib.world.data.Biome;
import com.github.chrisofnormandy.conlib.config.Config;

public class ModWorldGen {
    private static Biome createBiomeModifier(String name, Config config) {
        return CustomResource.registerBiomeFromConfig(name, config);
    }

    private static void biomeSetup(String name, Config config) {
        createBiomeModifier(name, config);
    }

    public static void Init() {
        Main.config.biomeConfigs.forEach((String name, Config config) -> biomeSetup(name, config));

        DataPackBuilder.WorldGen.Biome.write(CustomResource.biomeBuilder);
    }
}
