package com.github.chrisofnormandy.conloot.configs.blocks;

import com.github.chrisofnormandy.conlib.config.Config;

public class CropConfig {
    public static void create(String name, Config cfg) {
        cfg.addFlag("generate_wild", true, "Should this crop be generated in the wild?");
    }
}
