package com.github.chrisofnormandy.conloot.configs.blocks;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.ConfigBase;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;

public class CropConfig {
    public static void create(String name, Config cfg, ConfigOptions options) {
        ConfigBase.create(name, cfg, options);

        cfg.addFlag("generate_wild", true, "Should this crop be generated in the wild?");
    }
}
