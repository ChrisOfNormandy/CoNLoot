package com.github.chrisofnormandy.conloot.configs.items;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conloot.configs.ConfigBase;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;

public class GenericHandheldConfig {
    public static Config create(String name, Config config) {
        return create(name, config, new ConfigOptions());
    }

    public static Config create(String name, Config cfg, ConfigOptions options) {
        ConfigBase.create(name, cfg, options);

        ConfigGroup toolMaterial = new ConfigGroup();

        toolMaterial.addInteger("level", 1, "Tool material level.");
        toolMaterial.addInteger("attack_damage", 1, "Attack damage.");
        toolMaterial.addInteger("max_damage", 100, "Max damage.");

        toolMaterial.addDouble("attack_speed", 1.0, "Attack speed.");

        toolMaterial.addFlag("immune_to_fire", false, "Immune to fire.");
        toolMaterial.addFlag("no_repair", false, "No repair.");

        toolMaterial.addString("rarity", "common", "Item rarity.");
        toolMaterial.addString("item_tier", "wood", "Item tier.");

        cfg.addSubgroup("Tool Material", toolMaterial);

        return cfg;
    }
}
