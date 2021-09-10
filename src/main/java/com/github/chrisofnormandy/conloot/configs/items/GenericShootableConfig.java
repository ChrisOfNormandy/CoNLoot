package com.github.chrisofnormandy.conloot.configs.items;

import java.util.ArrayList;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;

public class GenericShootableConfig {
    public static Config create(String name, Config config) {
        // Colors
        ConfigGroup colors = new ConfigGroup();

        colors.addStringList("color", new ArrayList<String>(),
                "RGB value for default generated assets. Used for overlay texture.");
        colors.addString("blend_mode", "sharp",
                "How colors are distributed using the overlay template | gradient: a curve blend of colors; sharp: no blending between colors; spotted: uses the first color as a base and applies the rest as spots.");
        colors.addFlag("template_shading", true,
                "Should the template be registered as a brightness value when applying colors? False will use base layer instead.");
        config.addSubgroup("Colors", colors);

        // Templates
        ConfigGroup assets = new ConfigGroup();
        assets.addStringList("textures", new ArrayList<String>(),
                "Texture names. Formatted with mod ID will use existing assets and will not combine.");
        assets.addStringList("overlays", new ArrayList<String>(),
                "Texture names. If the base texture is not a referenced asset, will combine to make single asset texture.");
        config.addSubgroup("Assets", assets);

        // Animation
        ConfigGroup animation = new ConfigGroup();
        animation.addStringList("frames", new ArrayList<String>(),
                "Manual frame determinations. Use the format '0:30' for 'index: 0, time: 30'. Not providing a time will use default. Leaving empty will use order of templates.");
        animation.addInteger("frametime", 0, "Ticks per animation frame. 20 = 1 second. 0 to disable.");
        config.addSubgroup("Animation", animation);

        return config;
    }
}
