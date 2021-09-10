package com.github.chrisofnormandy.conloot.configs.items.weapons;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.items.GenericShootableConfig;

public class BowConfig {
    public static Config create(String name, Config config) {
        return GenericShootableConfig.create(name, config);
    }
}
