package com.github.chrisofnormandy.conloot.configs.items;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.ConfigBase;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;

public class GenericWearableConfig {
    public static Config create(String name, Config config) {
        return create(name, config, new ConfigOptions());
    }

    public static Config create(String name, Config cfg, ConfigOptions options) {
        ConfigBase.create(name, cfg, options);

        return cfg;
    }
}
