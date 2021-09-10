package com.github.chrisofnormandy.conloot.configs;

import java.util.ArrayList;

public class DefaultConfigValues {
    public static final ArrayList<String> defaultColorList = new ArrayList<String>() {
        {
            add("255, 255, 255");
        }
    };

    public static class Textures {
        // Blocks
        public static final ArrayList<String> block_planks = new ArrayList<String>() {
            {
                add("planks>%_planks");
            }
        };
        public static final ArrayList<String> block_stone = new ArrayList<String>() {
            {
                add("stone>%");
            }
        };
        public static final ArrayList<String> block_log = new ArrayList<String>() {
            {
                add("log_top>%_top");
                add("log>%");
            }
        };
        public static final ArrayList<String> stripped_block_log = new ArrayList<String>() {
            {
                add("stripped_log_top>%_top");
                add("stripped_log>%");
            }
        };
        public static final ArrayList<String> block_wood = new ArrayList<String>() {
            {
                add("log>&0_log");
            }
        };
        public static final ArrayList<String> block_stripped_wood = new ArrayList<String>() {
            {
                add("stripped_log>stripped_&1_log");
            }
        };
        public static final ArrayList<String> block_fence = new ArrayList<String>() {
            {
                add("@:block/&0_planks");
            }
        };
        public static final ArrayList<String> block_fence_gate = new ArrayList<String>() {
            {
                add("@:block/&0_planks");
            }
        };
        public static final ArrayList<String> block_wall = new ArrayList<String>() {
            {
                add("@:block/&0");
            }
        };
        public static final ArrayList<String> block_slab = new ArrayList<String>() {
            {
                add("@:block/&0");
            }
        };
        public static final ArrayList<String> block_slab_wood = new ArrayList<String>() {
            {
                add("@:block/&0_planks");
            }
        };
        public static final ArrayList<String> block_stairs = new ArrayList<String>() {
            {
                add("@:block/&0");
            }
        };
        public static final ArrayList<String> block_stairs_wood = new ArrayList<String>() {
            {
                add("@:block/&0_planks");
            }
        };
        public static final ArrayList<String> block_button_wood = new ArrayList<String>() {
            {
                add("@:block/&0_planks");
            }
        };
        public static final ArrayList<String> block_button_stone = new ArrayList<String>() {
            {
                add("@:block/&0");
            }
        };
        public static final ArrayList<String> block_pressure_plate_wood = new ArrayList<String>() {
            {
                add("@:block/&0_planks");
            }
        };
        public static final ArrayList<String> block_pressure_plate_stone = new ArrayList<String>() {
            {
                add("@:block/&0");
            }
        };
        public static final ArrayList<String> block_trapdoor = new ArrayList<String>() {
            {
                add("trapdoor>%");
            }
        };
        public static final ArrayList<String> block_door = new ArrayList<String>() {
            {
                add("metal_door_top>%_top");
                add("metal_door_bottom>%_bottom");
            }
        };
        public static final String block_door_item = "metal_door_item>%";

        // Items

        // Tools
        public static final ArrayList<String> tool_pickaxe = new ArrayList<String>() {
            {
                add("pickaxe_head>%");
            }
        };
        public static final ArrayList<String> tool_axe = new ArrayList<String>() {
            {
                add("axe_head>%");
            }
        };
        public static final ArrayList<String> tool_shovel = new ArrayList<String>() {
            {
                add("shovel_head>%");
            }
        };
        public static final ArrayList<String> tool_hoe = new ArrayList<String>() {
            {
                add("hoe_head>%");
            }
        };
        public static final ArrayList<String> tool_flint_and_steel = new ArrayList<String>() {
            {
                add("fas_steel>%");
            }
        };
        public static final ArrayList<String> tool_fishing_rod = new ArrayList<String>() {
            {
                add("fishing_rod_handle");
                add("fishing_rod_handle");
            }
        };
        public static final ArrayList<String> tool_shears = new ArrayList<String>() {
            {
                add("shears_blades>%");
            }
        };

        // Weapons
        public static final ArrayList<String> weapon_sword = new ArrayList<String>() {
            {
                add("sword_head>%");
            }
        };
        public static final ArrayList<String> weapon_shield = new ArrayList<String>();
        public static final ArrayList<String> weapon_bow = new ArrayList<String>() {
            {
                add("minecraft:bow>%");
            }
        };
        public static final ArrayList<String> weapon_crossbow = new ArrayList<String>();
        public static final ArrayList<String> item_arrow = new ArrayList<String>() {
            {
                add("minecraft:arrow>%");
            }
        };

        // Wearable
        public static final ArrayList<String> wearable_head_item = new ArrayList<String>() {
            {
                add("helmet>%");
            }
        };
        public static final ArrayList<String> wearable_chest_item = new ArrayList<String>() {
            {
                add("chestplate>%");
            }
        };
        public static final ArrayList<String> wearable_legs_item = new ArrayList<String>() {
            {
                add("leggings>%");
            }
        };
        public static final ArrayList<String> wearable_feet_item = new ArrayList<String>() {
            {
                add("boots>%");
            }
        };
    }

    public static class Overlays {
        // Blocks
        public static final ArrayList<String> block_planks = new ArrayList<String>();
        public static final ArrayList<String> block_stone = new ArrayList<String>();
        public static final ArrayList<String> block_log = new ArrayList<String>();
        public static final ArrayList<String> stripped_block_log = new ArrayList<String>();
        public static final ArrayList<String> block_wood = new ArrayList<String>();
        public static final ArrayList<String> block_stripped_wood = new ArrayList<String>();
        public static final ArrayList<String> block_fence = new ArrayList<String>();
        public static final ArrayList<String> block_fence_gate = new ArrayList<String>();
        public static final ArrayList<String> block_wall = new ArrayList<String>();
        public static final ArrayList<String> block_slab = new ArrayList<String>();
        public static final ArrayList<String> block_slab_wood = new ArrayList<String>();
        public static final ArrayList<String> block_stairs = new ArrayList<String>();
        public static final ArrayList<String> block_stairs_wood = new ArrayList<String>();
        public static final ArrayList<String> block_button_wood = new ArrayList<String>();
        public static final ArrayList<String> block_button_stone = new ArrayList<String>();
        public static final ArrayList<String> block_pressure_plate_wood = new ArrayList<String>();
        public static final ArrayList<String> block_pressure_plate_stone = new ArrayList<String>();
        public static final ArrayList<String> block_trapdoor = new ArrayList<String>();
        public static final ArrayList<String> block_door = new ArrayList<String>();
        // public static final String block_door_item = "metal_door_item>%";

        // Items

        // Tools
        public static final ArrayList<String> tool_pickaxe = new ArrayList<String>() {
            {
                add("pickaxe_handle");
            }
        };
        public static final ArrayList<String> tool_axe = new ArrayList<String>() {
            {
                add("axe_handle");
            }
        };
        public static final ArrayList<String> tool_shovel = new ArrayList<String>() {
            {
                add("shovel_handle");
            }
        };
        public static final ArrayList<String> tool_hoe = new ArrayList<String>() {
            {
                add("hoe_handle");
            }
        };
        public static final ArrayList<String> tool_flint_and_steel = new ArrayList<String>() {
            {
                add("fas_flint");
            }
        };
        public static final ArrayList<String> tool_fishing_rod = new ArrayList<String>() {
            {
                add("fishing_rod_line");
                add("fishing_rod_cast_line");
            }
        };
        public static final ArrayList<String> tool_shears = new ArrayList<String>() {
            {
                add("shears_handle");
            }
        };

        // Weapons
        public static final ArrayList<String> weapon_sword = new ArrayList<String>() {
            {
                add("sword_handle");
            }
        };
        public static final ArrayList<String> weapon_shield = new ArrayList<String>();
        public static final ArrayList<String> weapon_bow = new ArrayList<String>();
        public static final ArrayList<String> weapon_crossbow = new ArrayList<String>();
        public static final ArrayList<String> item_arrow = new ArrayList<String>();

        // Wearable
        public static final ArrayList<String> wearable_head_item = new ArrayList<String>();
        public static final ArrayList<String> wearable_chest_item = new ArrayList<String>();
        public static final ArrayList<String> wearable_legs_item = new ArrayList<String>();
        public static final ArrayList<String> wearable_feet_item = new ArrayList<String>();
    }
}
