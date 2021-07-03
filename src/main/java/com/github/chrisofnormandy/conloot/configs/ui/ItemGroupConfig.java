package com.github.chrisofnormandy.conloot.configs.ui;

import com.github.chrisofnormandy.conlib.config.Config;

public class ItemGroupConfig {
    public static void create(String name, Config cfg) {
        cfg.addString("tab_name", name, "The name of the creative tab.");
        cfg.addString("item_name", name, "The name of the creative tab icon item.");
    }
}
