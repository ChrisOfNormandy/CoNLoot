package com.github.chrisofnormandy.conloot.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.Patterns;
import com.github.chrisofnormandy.conloot.configs.blocks.BlockConfig;
import com.github.chrisofnormandy.conloot.configs.blocks.CropConfig;
import com.github.chrisofnormandy.conloot.configs.blocks.OreConfig;
import com.github.chrisofnormandy.conloot.configs.items.tools.AxeConfig;
import com.github.chrisofnormandy.conloot.configs.items.tools.BucketConfig;
import com.github.chrisofnormandy.conloot.configs.items.tools.FishingRodConfig;
import com.github.chrisofnormandy.conloot.configs.items.tools.FlintAndSteelConfig;
import com.github.chrisofnormandy.conloot.configs.items.tools.HoeConfig;
import com.github.chrisofnormandy.conloot.configs.items.tools.PickaxeConfig;
import com.github.chrisofnormandy.conloot.configs.items.tools.ShearsConfig;
import com.github.chrisofnormandy.conloot.configs.items.tools.ShovelConfig;

public class Generators {
    private static Boolean hasColorTag(String s) {
        return Patterns.dye.matcher(s).find();
    }

    public static class Block {
        public static void loadBlock(HashMap<String, Config> map, ConfigLoader loader) {
            if (hasColorTag(loader.name)) {
                List<ConfigLoader> l = new ArrayList<ConfigLoader>();

                for (String clr : Patterns.colors) {
                    l.add(new ConfigLoader(
                            loader.name.replaceAll(Patterns.dye.pattern(), clr),
                            loader.options.ColorSet(Patterns.colorMap.get(clr))));
                }

                loadBlocks(map, l);
            }
            else {
                try {
                    Config cfg = new Config("conloot/blocks/" + loader.options.blockModel, loader.name);
                    BlockConfig.create(loader.name, cfg, loader.options);
                    map.put(loader.name, cfg.Build());
                }
                catch (Exception err) {
                    Main.LOG.error("Failed to create config for " + loader.name);
                    err.printStackTrace();
                }
            }
        }

        public static void loadBlocks(HashMap<String, Config> map, List<ConfigLoader> list) {
            list.forEach((ConfigLoader loader) -> {
                loadBlock(map, loader);
            });
        }
    }

    public static class Resource {
        public static class Plant {
            public static void loadCrops(HashMap<String, Config> map, List<ConfigLoader> list) {
                list.forEach((ConfigLoader loader) -> {
                    if (hasColorTag(loader.name)) {
                        List<ConfigLoader> l = new ArrayList<ConfigLoader>();

                        for (String clr : Patterns.colors) {
                            l.add(new ConfigLoader(
                                    loader.name.replaceAll(Patterns.dye.pattern(), clr),
                                    loader.options.ColorSet(Patterns.colorMap.get(clr))));
                        }

                        loadCrops(map, l);
                    }
                    else {
                        try {
                            Config cfg = new Config("conloot/plants/crops", loader.name);
                            CropConfig.create(loader.name, cfg, loader.options);
                            map.put(loader.name, cfg.Build());
                        }
                        catch (Exception err) {
                            Main.LOG.error("Failed to create config for " + loader.name);
                            err.printStackTrace();
                        }
                    }
                });
            }
        }

        public static class Ore {
            public static void loadGems(HashMap<String, Config> map, List<ConfigLoader> list) {
                list.forEach((ConfigLoader loader) -> {
                    if (hasColorTag(loader.name)) {
                        List<ConfigLoader> l = new ArrayList<ConfigLoader>();

                        for (String clr : Patterns.colors) {
                            l.add(new ConfigLoader(
                                    loader.name.replaceAll(Patterns.dye.pattern(), clr),
                                    loader.options.ColorSet(Patterns.colorMap.get(clr))));
                        }

                        loadGems(map, l);
                    }
                    else {
                        try {
                            Config cfg = new Config("conloot/ores/gems", loader.name);
                            OreConfig.create(loader.name, cfg, loader.options);
                            map.put(loader.name, cfg.Build());
                        }
                        catch (Exception err) {
                            Main.LOG.error("Failed to create config for " + loader.name);
                            err.printStackTrace();
                        }
                    }
                });
            }

            public static void loadMetals(HashMap<String, Config> map, List<ConfigLoader> list) {
                list.forEach((ConfigLoader loader) -> {
                    if (hasColorTag(loader.name)) {
                        List<ConfigLoader> l = new ArrayList<ConfigLoader>();

                        for (String clr : Patterns.colors) {
                            l.add(new ConfigLoader(
                                    loader.name.replaceAll(Patterns.dye.pattern(), clr),
                                    loader.options.ColorSet(Patterns.colorMap.get(clr))));
                        }

                        loadMetals(map, list);
                    }
                    else {
                        try {
                            Config cfg = new Config("conloot/metals", loader.name);
                            OreConfig.create(loader.name, cfg, new ConfigOptions());
                            map.put(loader.name, cfg.Build());
                        }
                        catch (Exception err) {
                            Main.LOG.error("Failed to create config for " + loader.name);
                            err.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public static class Item {
        public static class Tool {
            private static void loadTool(String tool, HashMap<String, Config> map, ConfigLoader loader) {
                try {
                    Config cfg = new Config("conloot/items/tools/" + tool + "s", loader.name);

                    switch (tool) {
                        case "pickaxe": {
                            PickaxeConfig.create(loader.name, cfg, new ConfigOptions());
                            break;
                        }
                        case "axe": {
                            AxeConfig.create(loader.name, cfg, new ConfigOptions());
                            break;
                        }
                        case "shovel": {
                            ShovelConfig.create(loader.name, cfg, new ConfigOptions());
                            break;
                        }
                        case "hoe": {
                            HoeConfig.create(loader.name, cfg, new ConfigOptions());
                            break;
                        }
                        case "flint_and_steel": {
                            FlintAndSteelConfig.create(loader.name, cfg, new ConfigOptions());
                            break;
                        }
                        case "fishing_rod": {
                            FishingRodConfig.create(loader.name, cfg, new ConfigOptions());
                            break;
                        }
                        case "bucket": {
                            BucketConfig.create(loader.name, cfg, new ConfigOptions());
                            break;
                        }
                        case "shears": {
                            ShearsConfig.create(loader.name, cfg, new ConfigOptions());
                            break;
                        }
                    }

                    map.put(loader.name, cfg.Build());
                }
                catch (Exception err) {
                    Main.LOG.error("Failed to create config for " + loader.name);
                    err.printStackTrace();
                }
            }

            public static void loadPickaxes(HashMap<String, Config> map, List<ConfigLoader> list) {
                list.forEach((ConfigLoader loader) -> {
                    if (hasColorTag(loader.name)) {
                        List<ConfigLoader> l = new ArrayList<ConfigLoader>();

                        for (String clr : Patterns.colors) {
                            l.add(new ConfigLoader(
                                    loader.name.replaceAll(Patterns.dye.pattern(), clr),
                                    loader.options.ColorSet(Patterns.colorMap.get(clr))));
                        }

                        loadPickaxes(map, list);
                    }
                    else {
                        try {
                            Config cfg = new Config("conloot/items/tools/pickaxes", loader.name);
                            PickaxeConfig.create(loader.name, cfg, new ConfigOptions());
                            map.put(loader.name, cfg.Build());
                        }
                        catch (Exception err) {
                            Main.LOG.error("Failed to create config for " + loader.name);
                            err.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
