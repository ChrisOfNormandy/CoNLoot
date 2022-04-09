package com.github.chrisofnormandy.conloot.configs.items;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.ConfigBase;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;

public class GenericItemConfig {
    public static Config create(String name, Config cfg, ConfigOptions options) {
        ConfigBase.create(name, cfg, options);

        return cfg;
    }
}
