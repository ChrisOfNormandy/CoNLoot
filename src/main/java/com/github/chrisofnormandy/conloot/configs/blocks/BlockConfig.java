package com.github.chrisofnormandy.conloot.configs.blocks;

import java.util.ArrayList;
import java.util.List;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conloot.Patterns;

public class BlockConfig {
        public static void create(String name, Config cfg, String blockType, String defaultColor) {
                // Block properties
                cfg.addFlag("no_collission", false, "When true, sets collision and occlusion to false.");
                cfg.addFlag("no_occlusion", false, "When true, sets occlusion to false.");

                cfg.addInteger("harvest_level", 1,
                                "The block hardness level. Gold: 0; Wood: 0; Stone: 1; Iron: 2; Diamond: 3; Netherite: 4");
                cfg.addString("harvest_tool", "pickaxe", "String representing a ToolType value.");

                cfg.addDouble("friction", 1.0, "Block friction, like ice. Should be between 0 and 1.");
                cfg.addDouble("speed_factor", 1.0, "Block speed factor, like soul sand.");
                cfg.addDouble("jump_factor", 1.0, "Block jump factor, like slime blocks.");

                cfg.addString("sound", "none", "String representing a SoundType value.");

                cfg.addInteger("light_level", 0,
                                "Integer value for light level. Converts to ToIntFunction<BlockState>.");

                cfg.addDouble("destroy_time", 1.0, "Block break time. Setting to 0 means insta-break.");
                cfg.addDouble("explosion_resistance", 1.0, "Block explosion resistance.");

                cfg.addFlag("random_ticks", false, "Does the block tick randomly, like plant growth or decay?");

                cfg.addFlag("dynamic_shape", false, "Does the block have a dynamic shape?");

                cfg.addFlag("no_drops", false, "Does the block drop nothing? Will override loot table.");

                cfg.addFlag("is_air", false, "Is this block air?");

                cfg.addStringList("valid_spawn", new ArrayList<String>(), "Entities that may spawn on this block.");

                cfg.addFlag("redstone_conductor", true, "Does this block allow passing redstone signals?");

                cfg.addFlag("suffocating", true,
                                "Will entities take suffocation damage if they are inside this block?");

                cfg.addFlag("opaque", true, "Does this block block viewing? false = transparent.");

                cfg.addFlag("post_process", false, "Does this block use post processing?");

                cfg.addFlag("emissive_rendering", false, "Does this block use emissive rendering?");

                cfg.addFlag("require_correct_tool", true,
                                "When broken, does this block require the correct tool to be used to drop an item?");

                cfg.addString("material", "none", "String representing a Material value.");

                cfg.addString("material_color", "none",
                                "String representing a MaterialColor value. Converts to Function<BlockState, MaterialColor>.");

                cfg.addString("block_model", blockType,
                                "What model should the block adapt? Options: block, slab, stairs, wall, fence, fence_gate");

                // Asset generation settings

                // Colors
                ConfigGroup colors = new ConfigGroup();
                List<String> colorList = new ArrayList<String>();
                colorList.add(Patterns.colorMap.get(defaultColor));

                colors.addStringList("color", colorList,
                                "RGB value for default generated assets. Used for overlay texture.");
                colors.addString("blend_mode", "sharp",
                                "How colors are distributed using the overlay template | gradient: a curve blend of colors; sharp: no blending between colors; spotted: uses the first color as a base and applies the rest as spots.");
                colors.addFlag("template_shading", true,
                                "Should the template be registered as a brightness value when applying colors? False will use base layer instead.");
                cfg.addSubgroup("Colors", colors);

                // Templates
                ConfigGroup assets = new ConfigGroup();
                List<String> templates = new ArrayList<String>();
                List<String> bases = new ArrayList<String>();
                List<String> parents = new ArrayList<String>();

                templates.add("none");

                assets.addStringList("template", templates,
                                "The PNG name(s) to use as the asset template(s). Should be a cutout of the colored / custom portion.");
                assets.addStringList("base", bases,
                                "The PNG name(s) to use as the asset base(s). Should be a full block texture, such as stone, or a texture with the colored / custom portion removed.");
                assets.addStringList("parents", parents,
                                "The block texture to use as a parent texture. Can only be custom (at the moment).");
                cfg.addSubgroup("Assets", assets);

                // Animation
                ConfigGroup animation = new ConfigGroup();
                List<String> frames = new ArrayList<String>();
                animation.addStringList("frames", frames, "Manual frame determinations. Use the format '0:30' for 'index: 0, time: 30'. Not providing a time will use default. Leaving empty will use order of templates.");
                animation.addInteger("frametime", 1, "Ticks per animation frame. 20 = 1 second.");
                cfg.addSubgroup("Animation", animation);
        }
}
