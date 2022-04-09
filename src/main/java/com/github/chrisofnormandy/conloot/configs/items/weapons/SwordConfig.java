package com.github.chrisofnormandy.conloot.configs.items.weapons;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;
import com.github.chrisofnormandy.conloot.configs.items.GenericHandheldConfig;

public class SwordConfig {
    public static Config create(String name, Config config) {
        return GenericHandheldConfig.create(name, config);
    }

    public static Config create(String name, Config config, ConfigOptions options) {
        return GenericHandheldConfig.create(name, config, options);
    }
}
