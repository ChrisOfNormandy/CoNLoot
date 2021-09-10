package com.github.chrisofnormandy.conloot.configs.items.tools;

import java.util.ArrayList;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.items.GenericHandheldConfig;

public class PickaxeConfig {
    public static Config create(String name, Config config) {
        return GenericHandheldConfig.create(name, config);
    }

    public static Config create(String name, Config config, ArrayList<String> colorList, ArrayList<String> textureList,
            ArrayList<String> overlayList) {
        return GenericHandheldConfig.create(name, config, colorList, textureList, overlayList);
    }
}
