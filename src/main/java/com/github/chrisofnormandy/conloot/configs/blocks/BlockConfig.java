package com.github.chrisofnormandy.conloot.configs.blocks;

import java.util.ArrayList;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conloot.configs.ConfigBase;
import com.github.chrisofnormandy.conloot.configs.ConfigOptions;

public class BlockConfig {
    private static String getSound(String material) {
        switch (material) {
            case "wood":
                return "wood";
            case "stone":
                return "stone";
        }
        return "stone";
    }

    private static String getTool(String material) {
        switch (material) {
            case "wood":
                return "axe";
            case "stone":
                return "pickaxe";
        }
        return "pickaxe";
    }

    public static void create(String name, Config cfg, ConfigOptions options) {
        ConfigBase.create(name, cfg, options);

        // Overrides
        ConfigGroup overrides = new ConfigGroup();
        overrides.addFlag("use_vanilla", true,
                "When true, ignores properties of this config and uses vanilla equivalent properties (barrel -> minecraft:barrel).");

        cfg.addSubgroup("Overrides", overrides);

        // Block properties
        cfg.addFlag("no_collission", false, "When true, sets collision and occlusion to false.");

        cfg.addFlag("no_occlusion", false, "When true, sets occlusion to false.");

        cfg.addInteger("harvest_level", 1,
                "The block hardness level. Gold: 0; Wood: 0; Stone: 1; Iron: 2; Diamond: 3; Netherite: 4");

        cfg.addString("harvest_tool", getTool(options.material), "String representing a ToolType value.");

        cfg.addDouble("friction", -1.0, "Block friction, like ice. Should be between 0 and 1.");

        cfg.addDouble("speed_factor", -1.0, "Block speed factor, like soul sand.");

        cfg.addDouble("jump_factor", -1.0, "Block jump factor, like slime blocks.");

        cfg.addString("sound", getSound(options.material), "String representing a SoundType value.");

        cfg.addInteger("light_level", -1, "Integer value for light level. Converts to ToIntFunction<BlockState>.");

        cfg.addDouble("destroy_time", 1.0, "Block break time. Setting to 0 means insta-break.");

        cfg.addDouble("explosion_resistance", 1.0, "Block explosion resistance.");

        cfg.addFlag("random_ticks", false, "Does the block tick randomly, like plant growth or decay?");

        cfg.addFlag("dynamic_shape", false, "Does the block have a dynamic shape?");

        cfg.addFlag("no_drops", false, "Does the block drop nothing? Will override loot table.");

        cfg.addFlag("is_air", false, "Is this block air?");

        cfg.addStringList("valid_spawn", new ArrayList<String>(), "Entities that may spawn on this block.");

        cfg.addFlag("redstone_conductor", true, "Does this block allow passing redstone signals?");

        cfg.addFlag("suffocating", true, "Will entities take suffocation damage if they are inside this block?");

        cfg.addFlag("opaque", true, "Does this block block viewing? false = transparent.");

        cfg.addFlag("post_process", false, "Does this block use post processing?");

        cfg.addFlag("emissive_rendering", false, "Does this block use emissive rendering?");

        cfg.addFlag("require_correct_tool", true,
                "When broken, does this block require the correct tool to be used to drop an item?");

        cfg.addString("material", options.material, "String representing a Material value.");

        cfg.addString("material_color", "",
                "String representing a MaterialColor value. Converts to Function<BlockState, MaterialColor>.");

        cfg.addString("block_model", options.blockModel,
                "What general model should the block adapt? Examples: block, slab, wall.");

        cfg.addString("block_render_model", options.renderModel, "Block model type. Options: column, ...");

        cfg.addFlag("block_render_opens", options.opens, "Block has two states: open and closed.");

        cfg.addFlag("block_render_rotates", options.rotates, "Block can rotate in all 6 directions (NESW + up + down).");

        // Asset generation settings
        ConfigGroup settings = new ConfigGroup();

        switch (options.blockModel) {
            case "slab": {
                settings.addString("double_stack_textures", name.replace("_slab", ""),
                        "Texture names for double stacked slabs. Formatted with mod ID will use existing assets and will not combine.");
                break;
            }
            case "door": {
                settings.addString("item_textures", "minecraft:item/oak_door",
                        "Texture names for door item. Formatted with mod ID will use existing assets and will not combine.");
                break;
            }
        }

        cfg.addSubgroup("Settings", settings);
    }
}
