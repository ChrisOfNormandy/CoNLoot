package com.github.chrisofnormandy.conloot.content.blocks;

import java.util.HashMap;
import java.util.function.ToIntFunction;

import com.github.chrisofnormandy.conlib.block.ModBlock;
import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;

public class CreationBase {
    private static HashMap<String, Material> materials = new HashMap<String, Material>() {{
        put("air", Material.AIR);
        put("bamboo", Material.BAMBOO);
        put("bamboo_sapling", Material.BAMBOO_SAPLING);
        put("barrier", Material.BARRIER);
        put("bubble_column", Material.BUBBLE_COLUMN);
        put("buildable_glass", Material.BUILDABLE_GLASS);
        put("cactus", Material.CACTUS);
        put("cake", Material.CAKE);
        put("clay", Material.CLAY);
        put("cloth_decoration", Material.CLOTH_DECORATION);
        put("coral", Material.CORAL);
        put("decoration", Material.DECORATION);
        put("dirt", Material.DIRT);
        put("egg", Material.EGG);
        put("explosive", Material.EXPLOSIVE);
        put("fire", Material.FIRE);
        put("glass", Material.GLASS);
        put("grass", Material.GRASS);
        put("heavy_metal", Material.HEAVY_METAL);
        put("ice", Material.ICE);
        put("ice_solid", Material.ICE_SOLID);
        put("lava", Material.LAVA);
        put("leaves", Material.LEAVES);
        put("metal", Material.METAL);
        put("nether_wood", Material.NETHER_WOOD);
        put("piston", Material.PISTON);
        put("plant", Material.PLANT);
        put("portal", Material.PORTAL);
        put("replaceable_fireproof_plant", Material.REPLACEABLE_FIREPROOF_PLANT);
        put("replaceable_plant", Material.REPLACEABLE_PLANT);
        put("replaceable_water_plant", Material.REPLACEABLE_WATER_PLANT);
        put("sand", Material.SAND);
        put("shulker_shell", Material.SHULKER_SHELL);
        put("snow", Material.SNOW);
        put("sponge", Material.SPONGE);
        put("stone", Material.STONE);
        put("structural_air", Material.STRUCTURAL_AIR);
        put("top_snow", Material.TOP_SNOW);
        put("vegetable", Material.VEGETABLE);
        put("water", Material.WATER);
        put("water_plant", Material.WATER_PLANT);
        put("web", Material.WEB);
        put("wood", Material.WOOD);
        put("wool", Material.WOOL);
    }};

    private static HashMap<String, SoundType> sounds = new HashMap<String, SoundType>() {{
        put("ancient_debris", SoundType.ANCIENT_DEBRIS);
        put("anvil", SoundType.ANVIL);
        put("bamboo", SoundType.BAMBOO);
        put("bamboo_sapling", SoundType.BAMBOO_SAPLING);
        put("basalt", SoundType.BASALT);
        put("bone_block", SoundType.BONE_BLOCK);
        put("chain", SoundType.CHAIN);
        put("coral_block", SoundType.CORAL_BLOCK);
        put("crop", SoundType.CROP);
        put("fungus", SoundType.FUNGUS);
        put("gilded_blackstone", SoundType.GILDED_BLACKSTONE);
        put("glass", SoundType.GLASS);
        put("grass", SoundType.GRASS);
        put("gravel", SoundType.GRAVEL);
        put("hard_crop", SoundType.HARD_CROP);
        put("honey_block", SoundType.HONEY_BLOCK);
        put("ladder", SoundType.LADDER);
        put("lantern", SoundType.LANTERN);
        put("lily_pad", SoundType.LILY_PAD);
        put("lodestone", SoundType.LODESTONE);
        put("metal", SoundType.METAL);
        put("netherite_block", SoundType.NETHERITE_BLOCK);
        put("netherrack", SoundType.NETHERRACK);
        put("nether_bricks", SoundType.NETHER_BRICKS);
        put("nether_gold_ore", SoundType.NETHER_GOLD_ORE);
        put("nether_ore", SoundType.NETHER_ORE);
        put("nether_sprouts", SoundType.NETHER_SPROUTS);
        put("nether_wart", SoundType.NETHER_WART);
        put("nylium", SoundType.NYLIUM);
        put("roots", SoundType.ROOTS);
        put("sand", SoundType.SAND);
        put("scaffolding", SoundType.SCAFFOLDING);
        put("shroomlight", SoundType.SHROOMLIGHT);
        put("slime_block", SoundType.SLIME_BLOCK);
        put("snow", SoundType.SNOW);
        put("soul_sand", SoundType.SOUL_SAND);
        put("stem", SoundType.STEM);
        put("stone", SoundType.STONE);
        put("sweet_berry_bush", SoundType.SWEET_BERRY_BUSH);
        put("twisting_vines", SoundType.TWISTING_VINES);
        put("vine", SoundType.VINE);
        put("wart_block", SoundType.WART_BLOCK);
        put("weeping_vines", SoundType.WEEPING_VINES);
        put("wet_grass", SoundType.WET_GRASS);
        put("wood", SoundType.WOOD);
        put("wool", SoundType.WOOL);
    }};

    public static void registerBlockFromConfig(String name, Config config, ItemGroup blockGroup) {
        Main.LOG.info("Generating new block:" + name);

        Properties properties = Properties.of(Material.AIR);

        try {
            String material = config.getStringValue("material");

            if (materials.containsKey(material))
                properties = Properties.of(materials.get(material));

            if (config.getFlagValue("no_collission"))
                properties.noCollission();

            if (config.getFlagValue("no_occlusion"))
                properties.noOcclusion();

            properties.harvestLevel(config.getIntegerValue("harvest_level"));

            String tooltype = config.getStringValue("harvest_tool");
            switch (tooltype) {
                case "pickaxe": {
                    properties.harvestTool(ToolType.PICKAXE);
                    break;
                }
                case "axe": {
                    properties.harvestTool(ToolType.AXE);
                    break;
                }
                case "shovel": {
                    properties.harvestTool(ToolType.SHOVEL);
                    break;
                }
                case "hoe": {
                    properties.harvestTool(ToolType.HOE);
                    break;
                }
                default: {
                    Main.LOG.info("Failed to find tooltype associated with " + tooltype
                            + ". Defaulting to ToolType.PICKAXE.");
                    break;
                }
            }

            Float friction = config.getDoubleValue("friction").floatValue();
            if (friction > 1)
                throw new Exception("Friction should be a decimal value between 0 and 1.");
            if (friction >= 0)
                properties.friction(friction);

            Float speedFactor = config.getDoubleValue("speed_factor").floatValue();
            if (speedFactor > 1)
                throw new Exception("Speed factor should be a decimal value between 0 and 1.");
            if (speedFactor >= 0)
                properties.speedFactor(speedFactor);

            Float jumpFactor = config.getDoubleValue("jump_factor").floatValue();
            if (jumpFactor > 1)
                throw new Exception("Jump factor should be a decimal value between 0 and 1.");
            if (jumpFactor >= 0)
                properties.jumpFactor(jumpFactor);

            String sound = config.getStringValue("sound");
            if (sounds.containsKey(sound))
                properties.sound(sounds.get(sound));

            Integer lightLevel = config.getIntegerValue("light_level");
            if (lightLevel >= 0)
                properties.lightLevel(new ToIntFunction<BlockState>() {
                    @Override
                    public int applyAsInt(BlockState value) {
                        return lightLevel;
                    }
                });

            Float destroyTime = config.getDoubleValue("destroy_time").floatValue();
            Float explosionResistance = config.getDoubleValue("explosion_resistance").floatValue();

            if (destroyTime >= 0) {
                if (explosionResistance >= 0)
                    properties.strength(destroyTime, explosionResistance);
                else
                    properties.strength(destroyTime);
            }

            if (config.getFlagValue("random_ticks"))
                properties.randomTicks();

            if (config.getFlagValue("dynamic_shape"))
                properties.dynamicShape();

            if (config.getFlagValue("no_drops"))
                properties.noDrops();

            if (config.getFlagValue("is_air"))
                properties.air();

            // Valid spawn

            // Redstone conductor

            // Suffocating

            // View blocking

            // Post processing

            if (config.getFlagValue("require_correct_tool"))
                properties.requiresCorrectToolForDrops();

            // Material color

            // Additional functions based on block model setting.
        } catch (Exception err) {
            Main.LOG.error("Failed to create block properties from config. Check values.");
            err.printStackTrace();

            return; // Escape without creating block or assets.
        }

        // Block assets
        String[] colors = config.getSubgroup("Colors").getStringListValue("color").toArray(new String[0]);
        String mode = config.getSubgroup("Colors").getStringValue("blend_mode");

        String[] textures = config.getSubgroup("Assets").getStringListValue("textures").toArray(new String[0]);
        String[] overlays = config.getSubgroup("Assets").getStringListValue("overlays").toArray(new String[0]);

        Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

        Integer frameTime = config.getSubgroup("Animation").getIntegerValue("frametime");
        String[] frames = config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]);

        switch (config.getStringValue("block_model")) {
            case "block": {
                String subType = config.getStringValue("block_model_subtype");

                Main.LOG.info("Registering new block from config.");
                Main.LOG.debug("Creating asset pack for " + name + " using: [" + textures.length + "] -> " + String.join(", ", textures));

                AssetPackBuilder.createBlock(name, textures, overlays, colors, mode, templateShading, frameTime,
                        frames, subType);

                Main.LOG.debug("Registering " + name);

                switch (subType) {
                    case "column": {
                        Main.LOG.debug("Subtype: " + subType);
                        ModBlock.Generic.createColumn(name, properties, blockGroup);
                        break;
                    }
                    default: {
                        ModBlock.Generic.create(name, properties, blockGroup);
                        break;
                    }
                }

                break;
            }
            case "slab": {
                Main.LOG.info("Registering new slab from config.");
                AssetPackBuilder.createSlabBlock(name, config.getSubgroup("Settings").getStringValue("double_stack_textures"), textures, overlays, colors, mode, templateShading, frameTime,
                        frames);
                ModBlock.Generic.createSlab(name, new Block(properties), blockGroup);

                break;
            }
            case "stairs": {
                Main.LOG.info("Registering new stairs from config.");
                AssetPackBuilder.createStairBlock(name, textures, overlays, colors, mode, templateShading, frameTime,
                        frames);
                ModBlock.Generic.createStairs(name, new Block(properties), blockGroup);

                break;
            }
            case "wall": {
                Main.LOG.info("Registering new wall from config.");
                AssetPackBuilder.createWallBlock(name, textures, overlays, colors, mode, templateShading, frameTime,
                        frames);
                ModBlock.Generic.createWall(name, new Block(properties), blockGroup);

                break;
            }
            case "fence": {
                Main.LOG.info("Registering new fence from config.");
                AssetPackBuilder.createFenceBlock(name, textures, overlays, colors, mode, templateShading, frameTime,
                        frames);
                ModBlock.Generic.createFence(name, new Block(properties), blockGroup);

                break;
            }
            case "fence_gate": {
                Main.LOG.info("Registering new fence gate from config.");
                AssetPackBuilder.createFenceGateBlock(name, textures, overlays, colors, mode, templateShading,
                        frameTime, frames);
                ModBlock.Generic.createFenceGate(name, new Block(properties), blockGroup);

                break;
            }
            case "door": {
                Main.LOG.info("Registering new door from config.");
                AssetPackBuilder.createDoorBlock(name, config.getSubgroup("Settings").getStringValue("item_textures"), textures, overlays, colors, mode, templateShading, frameTime,
                        frames);
                ModBlock.Generic.createDoor(name, new Block(properties), blockGroup);

                break;
            }
            case "trapdoor": {
                Main.LOG.info("Registering new door from config.");
                AssetPackBuilder.createTrapdoorBlock(name, textures, overlays, colors, mode, templateShading, frameTime,
                        frames);
                ModBlock.Generic.createTrapdoor(name, new Block(properties), blockGroup);

                break;
            }
            case "barrel": {
                Main.LOG.info("Registering new barrel from config.");
                AssetPackBuilder.createBlock(name, textures, overlays, colors, mode, templateShading, frameTime,
                        frames, config.getStringValue("block_model_subtype"));
                ModBlock.Interactive.createBarrel(name, new Block(properties), blockGroup);

                break;
            }
            case "shulker": {
                Main.LOG.info("Registering new shulker from config.");
                AssetPackBuilder.createBlock(name, textures, overlays, colors, mode, templateShading, frameTime,
                        frames, config.getStringValue("block_model_subtype"));
                ModBlock.Interactive.createShulker(name, DyeColor.BLACK, new Block(properties), blockGroup);

                break;
            }
        }

        AssetPackBuilder.Lang.addBlock(name, StringUtil.wordCaps_repl(name));
        DataPackBuilder.LootTable.addBlock(Main.MOD_ID + ":" + name);
    }
}
