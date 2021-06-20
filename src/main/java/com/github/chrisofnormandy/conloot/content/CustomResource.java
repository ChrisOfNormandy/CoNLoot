package com.github.chrisofnormandy.conloot.content;

import com.github.chrisofnormandy.conlib.block.ModBlock;
import com.github.chrisofnormandy.conlib.block.types.OreBase;
import com.github.chrisofnormandy.conlib.common.StringUtil;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conlib.tool.ToolMaterial;
import com.github.chrisofnormandy.conlib.world.data.Biome;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;
import com.github.chrisofnormandy.conloot.asset_builder.DataPackBuilder;
import com.github.chrisofnormandy.conloot.content.blocks.CustomBlock;
import com.github.chrisofnormandy.conloot.content.world_gen.BiomeBuilder;

import net.minecraft.block.Block;
import net.minecraft.item.Rarity;

public class CustomResource {
    public static final BiomeBuilder biomeBuilder = new BiomeBuilder();

    public static Biome addBiome(String name) {
        return biomeBuilder.create(name);
    }

    public static Biome registerBiomeFromConfig(String name, Config config) {
        Biome biome = addBiome(name);

        biome.root.setScale(config.getDoubleValue("scale").floatValue());
        biome.root.setSurfaceBuilder(config.getStringValue("surface_builder"));
        if (!config.getFlagValue("player_spawn_friendly"))
            biome.root.denyPlayerSpawn();
        biome.root.setPrecipitation(config.getStringValue("precipitation"));
        biome.root.setTemperature(config.getDoubleValue("downfall").floatValue());
        biome.root.setCategory(config.getStringValue("category"));
        biome.root.setDepth(config.getDoubleValue("depth").floatValue());

        ConfigGroup effects = config.getSubgroup("Effects");
        if (effects.getSubgroup("mood_sound").getFlagValue("enable"))
            biome.root.effects.setMoodSound(effects.getSubgroup("mood_sound").getStringValue("sound"));

        if (effects.getSubgroup("additions_sound").getFlagValue("enable"))
            biome.root.effects.setAdditionsSound(effects.getSubgroup("additions_sound").getStringValue("sound"));
        
        if (effects.getSubgroup("music").getFlagValue("enable"))
            biome.root.effects.setMusic(effects.getSubgroup("music").getStringValue("sound"));

        if (effects.getSubgroup("ambient_sound").getFlagValue("enable"))
            biome.root.effects.setAmbientSound(effects.getSubgroup("ambient_sound").getStringValue("sound"));

        biome.root.effects.setSkyColor(effects.getIntegerValue("sky_color"));
        biome.root.effects.setFogColor(effects.getIntegerValue("fog_color"));
        biome.root.effects.setWaterColor(effects.getIntegerValue("water_color"));
        biome.root.effects.setWaterFogColor(effects.getIntegerValue("water_fog_color"));

        ConfigGroup carvers = config.getSubgroup("Carvers");
        carvers.getStringListValue("air").forEach((String value) -> biome.root.carvers.addAirCarver(value));
        carvers.getStringListValue("liquid").forEach((String value) -> biome.root.carvers.addLiquidCarver(value));

        ConfigGroup features = config.getSubgroup("Features");
        features.getStringListValue("raw_generation").forEach((String value) -> biome.root.features.addRawGeneration(value));
        features.getStringListValue("lakes").forEach((String value) -> biome.root.features.addLake(value));
        features.getStringListValue("local_modifications").forEach((String value) -> biome.root.features.addLocalModification(value));
        features.getStringListValue("underground_structures").forEach((String value) -> biome.root.features.addUndergroundStructure(value));
        features.getStringListValue("surface_structures").forEach((String value) -> biome.root.features.addSurfaceStructure(value));
        features.getStringListValue("strongholds").forEach((String value) -> biome.root.features.addStronghold(value));
        features.getStringListValue("underground_ores").forEach((String value) -> biome.root.features.addOreSpawn(value));
        features.getStringListValue("underground_decoration").forEach((String value) -> biome.root.features.addUndergroundDecoration(value));
        features.getStringListValue("vegetal_decoration").forEach((String value) -> biome.root.features.addVegetation(value));
        features.getStringListValue("top_layer_modification").forEach((String value) -> biome.root.features.addTopLayerModification(value));

        config.getSubgroup("Starts").getStringListValue("starts").forEach((String value) -> biome.root.addStart(value));

        ConfigGroup spawners = config.getSubgroup("Spawners");
        spawners.getStringListValue("monster").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addMonster(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]), Integer.parseInt(v[3]));
                }
                catch (Exception err) {
                    Main.LOG.warn("Could not add monster spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            }
            else
                Main.LOG.warn("Could not add monster spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });
        spawners.getStringListValue("creature").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addCreature(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]), Integer.parseInt(v[3]));
                }
                catch (Exception err) {
                    Main.LOG.warn("Could not add creature spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            }
            else
                Main.LOG.warn("Could not add creature spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });
        spawners.getStringListValue("ambient").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addAmbient(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]), Integer.parseInt(v[3]));
                }
                catch (Exception err) {
                    Main.LOG.warn("Could not add ambient spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            }
            else
                Main.LOG.warn("Could not add ambient spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });
        spawners.getStringListValue("water_creature").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addWaterCreature(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]), Integer.parseInt(v[3]));
                }
                catch (Exception err) {
                    Main.LOG.warn("Could not add water creature spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            }
            else
                Main.LOG.warn("Could not add water creature spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });
        spawners.getStringListValue("water_ambient").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addWaterAmbient(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]), Integer.parseInt(v[3]));
                }
                catch (Exception err) {
                    Main.LOG.warn("Could not add ambient water creature spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            }
            else
                Main.LOG.warn("Could not add ambient water creature spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });
        spawners.getStringListValue("misc").forEach((String value) -> {
            String[] v = value.split(";");
            if (v.length == 4) {
                try {
                    biome.root.spawners.addMisc(v[0], Integer.parseInt(v[1]), Integer.parseInt(v[2]), Integer.parseInt(v[3]));
                }
                catch (Exception err) {
                    Main.LOG.warn("Could not add misc spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");

                    Main.LOG.error(err);
                }
            }
            else
                Main.LOG.warn("Could not add misc spawn for " + name + " using: " + value + ". Should be formatted as \"type;weight;minCount;maxCount\".");
        });

        return biome;
    }

    public static void registerFromConfig(String name, Config config, Groups itemGroup, Groups toolGroup, Groups blockGroup) {
        Main.LOG.info("Generating new resource:" + name);

        ConfigGroup ore = config.getSubgroup("Ore");
        ConfigGroup tool = config.getSubgroup("ToolMaterial");
        
        String oreName = ore.getStringValue("ore_name");
        Integer harvestLevel = ore.getIntegerValue("harvest_level");
        Float strength = ore.getDoubleValue("strength").floatValue();

        Integer level = tool.getIntegerValue("level");
        Integer maxDamage = tool.getIntegerValue("max_damage");
        Boolean immuneToFire = tool.getFlagValue("immune_to_fire");
        Boolean noRepair = tool.getFlagValue("no_repair");

        String rarityStr = tool.getStringValue("rarity");
        String resourceTypeStr = tool.getStringValue("resource_type");

        Rarity rarity = Rarity.COMMON;
        switch (rarityStr) {
            case "uncommon": {
                rarity = Rarity.UNCOMMON;
                break;
            }
            case "rare": {
                rarity = Rarity.RARE;
                break;
            }
            case "epic": {
                rarity = Rarity.EPIC;
                break;
            }
        }

        OreBase oreBlock = new OreBase(harvestLevel, strength);

        // Here is where a check for "gen_tools, armour, ore..." would be made.
        Main.LOG.info("Registering from config, type: " + name + " -> " + resourceTypeStr);

        if (resourceTypeStr.equals("gem")) {
            ToolMaterial.type resourceType = ToolMaterial.type.gem;

            ToolMaterial material = new ToolMaterial(level, maxDamage, immuneToFire, rarity, noRepair, resourceType);

            Block oreBlock_reg = ModBlock.Ore.registerGem(name, oreName, oreBlock, material, itemGroup, toolGroup, blockGroup);

            DataPackBuilder.LootTable.block(Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name);
            DataPackBuilder.Recipe.Smelting.run(name, Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name, 1);

            String[] oreColors = config.getSubgroup("Colors").getStringListValue("ore_color").toArray(new String[0]);
            String[] colors = config.getSubgroup("Colors").getStringListValue("resource_color").toArray(new String[0]);

            Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

            String oreMode = config.getSubgroup("Colors").getStringValue("ore_blend_mode");
            String mode = config.getSubgroup("Colors").getStringValue("resource_blend_mode");

            String[] tools = {"pickaxe", "axe", "shovel", "hoe"};

            if (colors.length >= 1) {
                CustomBlock.generateBlock(oreName, new String[]{"ore_gem_base"}, new String[]{"ore_gem"}, oreColors, oreMode, templateShading);

                for (String t : tools) {
                    AssetPackBuilder.Item.getItemModel(name + "_" + t);
                    AssetPackBuilder.Item.createTexture(name + "_" + t, new String[]{t + "_base"}, new String[]{t}, colors, mode, templateShading);
                    AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
                }
            }
            else {
                CustomBlock.generateBlock(oreName);

                for (String t : tools) {
                    AssetPackBuilder.Item.getItemModel(name + "_" + t);
                    AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
                }
            }

            biomeBuilder.biomes.get("beach").addOreFeature(oreName, oreBlock_reg, 8);
        }
        else if (resourceTypeStr.equals("ingot")) {
            ToolMaterial.type resourceType = ToolMaterial.type.ingot;
            ToolMaterial material = new ToolMaterial(level, maxDamage, immuneToFire, rarity, noRepair, resourceType);

            Block oreBlock_reg = ModBlock.Ore.registerMetal(name, oreName, oreBlock, material, itemGroup, toolGroup, blockGroup);
            
            DataPackBuilder.LootTable.block(Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name);
            DataPackBuilder.Recipe.Smelting.run(name, Main.MOD_ID + ":" + oreName, Main.MOD_ID + ":" + name, 1);

            String[] oreColors = config.getSubgroup("Colors").getStringListValue("ore_color").toArray(new String[0]);
            String[] colors = config.getSubgroup("Colors").getStringListValue("resource_color").toArray(new String[0]);

            Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

            String oreMode = config.getSubgroup("Colors").getStringValue("ore_blend_mode");
            String mode = config.getSubgroup("Colors").getStringValue("resource_blend_mode");

            String[] tools = {"pickaxe", "axe", "shovel", "hoe"};

            if (colors.length >= 1) {
                CustomBlock.generateBlock(oreName, new String[]{"ore_base"}, new String[]{"ore"}, oreColors, oreMode, templateShading);

                for (String t : tools) {
                    AssetPackBuilder.Item.getItemModel(name + "_" + t);
                    AssetPackBuilder.Item.createTexture(name + "_" + t, new String[] { t + "_base" }, new String[] { t },
                            colors, mode, templateShading);
                    AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
                }
            }
            else {
                CustomBlock.generateBlock(oreName);

                for (String t : tools) {
                    AssetPackBuilder.Item.getItemModel(name + "_" + t);
                    AssetPackBuilder.Lang.addItem(name + "_" + t, StringUtil.wordCaps_repl(name + "_" + t));
                }
            }

            biomeBuilder.biomes.get("beach").addOreFeature(oreName, oreBlock_reg, 8);
        }
        else {
            Block oreBlock_reg = ModBlock.Ore.register(oreName, oreBlock, blockGroup);

            AssetPackBuilder.Block.getBlockstate(oreName);
            AssetPackBuilder.Block.getBlockModel(oreName);
            AssetPackBuilder.Lang.addBlock(oreName, StringUtil.wordCaps_repl(oreName));

            biomeBuilder.biomes.get("beach").addOreFeature(oreName, oreBlock_reg, 8);
        }
    }
}
