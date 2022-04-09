package com.github.chrisofnormandy.conloot.configs.items.tools;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;
import com.github.chrisofnormandy.conloot.configs.items.GenericItemConfig;

public class BucketConfig {
    public static Config create(String name, Config config) {
        return GenericItemConfig.create(name, config, new ConfigOptions());
    }

    public static Config create(String name, Config config, ConfigOptions options) {
        return GenericItemConfig.create(name, config, options);
    }
}
