package com.github.chrisofnormandy.conloot.configs.items.weapons;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;
import com.github.chrisofnormandy.conloot.configs.items.GenericShootableConfig;

public class CrossbowConfig {
    public static Config create(String name, Config cfg, ConfigOptions options) {
        return GenericShootableConfig.create(name, cfg, options);
    }
}