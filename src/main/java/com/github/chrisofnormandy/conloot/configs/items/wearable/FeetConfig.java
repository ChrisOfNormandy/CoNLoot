package com.github.chrisofnormandy.conloot.configs.items.wearable;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;
import com.github.chrisofnormandy.conloot.configs.items.GenericWearableConfig;

public class FeetConfig {
    public static Config create(String name, Config config) {
        return GenericWearableConfig.create(name, config);
    }

    public static Config create(String name, Config config, ConfigOptions options) {
        return GenericWearableConfig.create(name, config, options);
    }
}
