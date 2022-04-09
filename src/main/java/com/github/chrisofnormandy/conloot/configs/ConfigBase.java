package com.github.chrisofnormandy.conloot.configs;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;

public class ConfigBase {
    public static void create(String name, Config cfg, ConfigOptions options) {
        // Creative Tab
        ConfigGroup creative = new ConfigGroup();

        creative.addString("item_group", options.itemGroup, "Name of the creative inventory tab to register the item to. 'minecraft:misc' = the 'Misc' vanilla tab.");

        cfg.addSubgroup("Creative", creative);

        // Colors
        ConfigGroup colors = new ConfigGroup();

        colors.addStringList("color", options.ColorList(), "RGB value for default generated assets. Used for overlay texture.");

        colors.addString("blend_mode", "sharp",
                "How colors are distributed using the overlay template | gradient: a curve blend of colors; sharp: no blending between colors; spotted: uses the first color as a base and applies the rest as spots.");

        colors.addFlag("template_shading", true,
                "Should the template be registered as a brightness value when applying colors? False will use base layer instead.");

        cfg.addSubgroup("Colors", colors);

        // Templates
        ConfigGroup assets = new ConfigGroup();

        assets.addStringList("textures", options.TextureList(),
                "Texture names. Formatted with mod ID will use existing assets and will not combine.");

        assets.addStringList("overlays", options.OverlayList(),
                "Texture names. If the base texture is not a referenced asset, will combine to make single asset texture.");

        cfg.addSubgroup("Assets", assets);

        // Animation
        ConfigGroup animation = new ConfigGroup();

        animation.addStringList("frames", options.frameSettings,
                "Manual frame determinations. Use the format '0:30' for 'index: 0, time: 30'. Not providing a time will use default. Leaving empty will use order of templates.");

        animation.addInteger("frametime", 0, "Ticks per animation frame. 20 = 1 second. 0 to disable.");

        cfg.addSubgroup("Animation", animation);
    }
}
