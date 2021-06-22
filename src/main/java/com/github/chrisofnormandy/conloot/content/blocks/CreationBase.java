package com.github.chrisofnormandy.conloot.content.blocks;

import java.util.List;
import java.util.function.ToIntFunction;

import com.github.chrisofnormandy.conlib.block.ModBlock;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class CreationBase {
    public static void registerBlockFromConfig(String name, Config config, Groups blockGroup) {
        Main.LOG.info("Generating new block:" + name);

        // Establish block properties
        Properties properties = Properties.of(Material.STONE); // Just for instantiation.

        try {
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
                    properties.harvestTool(ToolType.PICKAXE);
                    break;
                }
            }

            Float friction = config.getDoubleValue("friction").floatValue();
            if (friction > 1 || friction < 0)
                throw new Exception("Friction should be a decimal value between 0 and 1.");
            properties.friction(friction);

            Float speedFactor = config.getDoubleValue("speed_factor").floatValue();
            if (speedFactor > 1 || speedFactor < 0)
                throw new Exception("Speed factor should be a decimal value between 0 and 1.");
            properties.speedFactor(speedFactor);

            Float jumpFactor = config.getDoubleValue("jump_factor").floatValue();
            if (jumpFactor > 1 || jumpFactor < 0)
                throw new Exception("Jump factor should be a decimal value between 0 and 1.");
            properties.jumpFactor(jumpFactor);

            // Sound

            Integer lightLevel = config.getIntegerValue("light_level");
            if (lightLevel < 0)
                throw new Exception("Light level should be greater or equal to 0.");
            properties.lightLevel(new ToIntFunction<BlockState>() {
                @Override
                public int applyAsInt(BlockState value) {
                    return lightLevel;
                }
            });

            Float destroyTime = config.getDoubleValue("destroy_time").floatValue();
            if (destroyTime < 0)
                throw new Exception("Destroy time should be greater or equal to 0.");

            Float explosionResistance = config.getDoubleValue("explosion_resistance").floatValue();
            if (explosionResistance < 0)
                throw new Exception("Explosion resistance should be greater or equal to 0.");
            properties.strength(destroyTime, explosionResistance);

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

            // Material

            // Material color

            // Additional functions based on block model setting.
        } catch (Exception err) {
            Main.LOG.error("Failed to create block properties from config. Check values.");
            Main.LOG.error(err.getStackTrace());

            return; // Escape without creating block or assets.
        }

        // Block assets
        String[] colors = config.getSubgroup("Colors").getStringListValue("color").toArray(new String[0]);
        String mode = config.getSubgroup("Colors").getStringValue("blend_mode");

        String[] templates = config.getSubgroup("Assets").getStringListValue("template").toArray(new String[0]);
        String[] bases = config.getSubgroup("Assets").getStringListValue("base").toArray(new String[0]);

        Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

        switch (config.getStringValue("block_model")) {
            case "block": {
                Main.LOG.info("Registering new block from config.");

                List<String> parents = config.getSubgroup("Assets").getStringListValue("parents");

                if (templates.length == 0 && parents.size() == 0)
                    CustomBlock.generateBlock(name);
                else {
                    if (templates.length > 1)
                        CustomBlock.generateBlock(name, bases, templates, colors, mode, templateShading,
                                templates.length, config.getSubgroup("Animation").getIntegerValue("frametime"),
                                config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]));
                    else {
                        if (parents.size() > 0)
                            CustomBlock.generateBlock(name, parents.toArray(new String[0]));
                        else
                            CustomBlock.generateBlock(name, bases, templates, colors, mode, templateShading);
                    }
                }

                ModBlock.Generic.create(name, properties, blockGroup);
                break;
            }
            case "slab": {
                Main.LOG.info("Registering new slab from config.");

                List<String> parents = config.getSubgroup("Assets").getStringListValue("parents");

                if (templates.length == 0 && parents.size() == 0)
                    CustomSlab.generateBlock(name);
                else {
                    if (templates.length > 1)
                        CustomSlab.generateBlock(name, bases, templates, colors, mode, templateShading,
                                templates.length, config.getSubgroup("Animation").getIntegerValue("frametime"),
                                config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]));
                    else {
                        if (parents.size() > 0)
                            CustomSlab.generateBlock(name, parents.toArray(new String[0]));
                        else
                            CustomSlab.generateBlock(name, bases, templates, colors, mode, templateShading);
                    }
                }

                ModBlock.Generic.createSlab(name, new Block(properties), blockGroup);
                break;
            }
            case "stairs": {
                Main.LOG.info("Registering new stairs from config.");

                List<String> parents = config.getSubgroup("Assets").getStringListValue("parents");

                if (templates.length == 0 && parents.size() == 0)
                    CustomStairs.generateBlock(name);
                else {
                    if (templates.length > 1)
                        CustomStairs.generateBlock(name, bases, templates, colors, mode, templateShading,
                                templates.length, config.getSubgroup("Animation").getIntegerValue("frametime"),
                                config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]));
                    else {
                        if (parents.size() > 0)
                            CustomStairs.generateBlock(name, parents.toArray(new String[0]));
                        else
                            CustomStairs.generateBlock(name, bases, templates, colors, mode, templateShading);
                    }
                }

                ModBlock.Generic.createStairs(name, new Block(properties), blockGroup);
                break;
            }
            case "wall": {
                Main.LOG.info("Registering new wall from config.");

                List<String> parents = config.getSubgroup("Assets").getStringListValue("parents");

                if (templates.length == 0 && parents.size() == 0)
                    CustomWall.generateBlock(name);
                else {
                    if (templates.length > 1)
                        CustomWall.generateBlock(name, bases, templates, colors, mode, templateShading,
                                templates.length, config.getSubgroup("Animation").getIntegerValue("frametime"),
                                config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]));
                    else {
                        if (parents.size() > 0)
                            CustomWall.generateBlock(name, parents.toArray(new String[0]));
                        else
                            CustomWall.generateBlock(name, bases, templates, colors, mode, templateShading);
                    }
                }

                ModBlock.Generic.createWall(name, new Block(properties), blockGroup);
                break;
            }
            case "fence": {
                Main.LOG.info("Registering new fence from config.");

                List<String> parents = config.getSubgroup("Assets").getStringListValue("parents");

                if (templates.length == 0 && parents.size() == 0)
                    CustomFence.generateBlock(name);
                else {
                    if (templates.length > 1)
                        CustomFence.generateBlock(name, bases, templates, colors, mode, templateShading,
                                templates.length, config.getSubgroup("Animation").getIntegerValue("frametime"),
                                config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]));
                    else {
                        if (parents.size() > 0)
                            CustomFence.generateBlock(name, parents.toArray(new String[0]));
                        else
                            CustomFence.generateBlock(name, bases, templates, colors, mode, templateShading);
                    }
                }

                ModBlock.Generic.createFence(name, new Block(properties), blockGroup);
                break;
            }
            case "fence_gate": {
                Main.LOG.info("Registering new fence gate from config.");

                List<String> parents = config.getSubgroup("Assets").getStringListValue("parents");

                if (templates.length == 0 && parents.size() == 0)
                    CustomFenceGate.generateBlock(name);
                else {
                    if (templates.length > 1)
                        CustomFenceGate.generateBlock(name, bases, templates, colors, mode, templateShading,
                                templates.length, config.getSubgroup("Animation").getIntegerValue("frametime"),
                                config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]));
                    else {
                        if (parents.size() > 0)
                            CustomFenceGate.generateBlock(name, parents.toArray(new String[0]));
                        else
                            CustomFenceGate.generateBlock(name, bases, templates, colors, mode, templateShading);
                    }
                }

                ModBlock.Generic.createFenceGate(name, new Block(properties), blockGroup);
                break;
            }
            case "door": {
                Main.LOG.info("Registering new door from config.");

                List<String> parents = config.getSubgroup("Assets").getStringListValue("parents");

                if (templates.length == 0 && parents.size() == 0)
                    CustomDoor.generateBlock(name);
                else {
                    if (templates.length > 1)
                        CustomDoor.generateBlock(name, bases, templates, colors, mode, templateShading,
                                templates.length, config.getSubgroup("Animation").getIntegerValue("frametime"),
                                config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]));
                    else {
                        if (parents.size() > 0)
                            CustomDoor.generateBlock(name, parents.toArray(new String[0]));
                        else
                            CustomDoor.generateBlock(name, bases, templates, colors, mode, templateShading);
                    }
                }

                ModBlock.Generic.createDoor(name, new Block(properties), blockGroup);
                break;
            }
            case "trapdoor": {
                Main.LOG.info("Registering new door from config.");

                List<String> parents = config.getSubgroup("Assets").getStringListValue("parents");

                if (templates.length == 0 && parents.size() == 0)
                    CustomTrapdoor.generateBlock(name);
                else {
                    if (templates.length > 1)
                        CustomTrapdoor.generateBlock(name, bases, templates, colors, mode, templateShading,
                                templates.length, config.getSubgroup("Animation").getIntegerValue("frametime"),
                                config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]));
                    else {
                        if (parents.size() > 0)
                            CustomTrapdoor.generateBlock(name, parents.toArray(new String[0]));
                        else
                            CustomTrapdoor.generateBlock(name, bases, templates, colors, mode, templateShading);
                    }
                }

                ModBlock.Generic.createTrapdoor(name, new Block(properties), blockGroup);
                break;
            }
        }

        DataPackBuilder.LootTable.block(Main.MOD_ID + ":" + name);
    }
}
