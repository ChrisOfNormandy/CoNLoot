package com.github.chrisofnormandy.conloot;

import java.util.HashMap;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.content.ui.CustomItemGroup;

public class ModUIs {

        private static void registerItemGroup(String name, Config config) {
                CustomItemGroup.registerFromConfig(name, config);
        }

        public static void Init() {
                Main.config.uiContent.forEach((String key, HashMap<String, Config> map) -> {
                        if (map.isEmpty()) {
                                Main.LOG.debug("UI map " + key + " is empty.");
                                Main.LOG.debug(map.size());
                                return;
                        }

                        Main.LOG.debug("UIContent: " + key + " -> " + map.size());

                        String[] keyPath = key.split("\\.");

                        Main.LOG.debug("UI content -> " + key + " | " + keyPath.length);

                        switch (keyPath[0]) {
                                case "ui": {
                                        Main.LOG.debug("Running setup for: " + keyPath[1]);

                                        switch (keyPath[1]) {
                                                case "itemGroup": {
                                                        map.forEach((String tab, Config config) -> registerItemGroup(tab, config));
                                                        break;
                                                }
                                        }

                                        break;
                                }
                        }
                });
        }
}
