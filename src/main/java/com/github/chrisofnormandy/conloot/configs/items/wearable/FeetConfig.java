package com.github.chrisofnormandy.conloot.configs.items.wearable;

import java.util.ArrayList;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.configs.items.GenericWearableConfig;

public class FeetConfig {
    public static Config create(String name, Config config) {
        return GenericWearableConfig.create(name, config);
    }

    public static Config create(String name, Config config, ArrayList<String> colorList, ArrayList<String> textureList,
            ArrayList<String> overlayList) {
        return GenericWearableConfig.create(name, config, colorList, textureList, overlayList);
    }
}
