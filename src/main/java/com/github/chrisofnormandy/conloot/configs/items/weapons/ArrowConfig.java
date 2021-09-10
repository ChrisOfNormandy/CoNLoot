package com.github.chrisofnormandy.conloot.configs.items.weapons;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.items.GenericItemConfig;

public class ArrowConfig {
    public static Config create(String name, Config config) {
        return GenericItemConfig.create(name, config);
    }
}
