package com.github.chrisofnormandy.conloot.content.items;

import com.github.chrisofnormandy.conlib.collections.Quartet;
import com.github.chrisofnormandy.conlib.common.ArrayUtil;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conlib.registry.ToolRegistry;
import com.github.chrisofnormandy.conlib.registry.WeaponRegistry;
import com.github.chrisofnormandy.conlib.registry.WearableRegistry;
import com.github.chrisofnormandy.conloot.Main;
import com.github.chrisofnormandy.conloot.asset_builder.AssetPackBuilder;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Rarity;
import net.minecraft.item.Item.Properties;

public class CreationBase {
    public static void registerItemFromConfig(String name, Config config) {
        Main.LOG.info("Generating new item:" + name);

        // Item assets
        String[] colors = config.getSubgroup("Colors").getStringListValue("color").toArray(new String[0]);
        String mode = config.getSubgroup("Colors").getStringValue("blend_mode");

        String[] textures = config.getSubgroup("Assets").getStringListValue("textures").toArray(new String[0]);
        String[] overlays = config.getSubgroup("Assets").getStringListValue("overlays").toArray(new String[0]);

        Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

        Integer frameTime = config.getSubgroup("Animation").getIntegerValue("frametime");
        String[] frames = config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]);

        AssetPackBuilder.createItem(name, textures, overlays, colors, mode, templateShading, frameTime, frames);
    }

    public static void registerHandheldItemFromConfig(String name, Config config) {
        Main.LOG.info("Generating new item:" + name);

        // Item assets
        String[] colors = config.getSubgroup("Colors").getStringListValue("color").toArray(new String[0]);
        String mode = config.getSubgroup("Colors").getStringValue("blend_mode");

        String[] textures = config.getSubgroup("Assets").getStringListValue("textures").toArray(new String[0]);
        String[] overlays = config.getSubgroup("Assets").getStringListValue("overlays").toArray(new String[0]);

        Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

        Integer frameTime = config.getSubgroup("Animation").getIntegerValue("frametime");
        String[] frames = config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]);

        AssetPackBuilder.createHandheldItem(name, textures, overlays, colors, mode, templateShading, frameTime, frames);
    }

    public static void registerShootableItemFromConfig(String name, Config config) {
        Main.LOG.info("Generating new item:" + name);

        // Item assets
        String[] colors = config.getSubgroup("Colors").getStringListValue("color").toArray(new String[0]);
        String mode = config.getSubgroup("Colors").getStringValue("blend_mode");

        String[] textures = config.getSubgroup("Assets").getStringListValue("textures").toArray(new String[0]);
        String[] overlays = config.getSubgroup("Assets").getStringListValue("overlays").toArray(new String[0]);

        Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

        Integer frameTime = config.getSubgroup("Animation").getIntegerValue("frametime");
        String[] frames = config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]);

        AssetPackBuilder.createShootableItem(name, textures, overlays, colors, mode, templateShading, frameTime,
                frames);
    }

    public static void registerTwoStageHandheldItemFromConfig(String name1, String name2, Config config) {
        Main.LOG.info("Generating new item:" + name1 + " + " + name2);

        // Item assets
        String[] colors = config.getSubgroup("Colors").getStringListValue("color").toArray(new String[0]);
        String mode = config.getSubgroup("Colors").getStringValue("blend_mode");

        String[] textures = config.getSubgroup("Assets").getStringListValue("textures").toArray(new String[0]);
        String[] overlays = config.getSubgroup("Assets").getStringListValue("overlays").toArray(new String[0]);

        Boolean templateShading = config.getSubgroup("Colors").getFlagValue("template_shading");

        Integer frameTime = config.getSubgroup("Animation").getIntegerValue("frametime");
        String[] frames = config.getSubgroup("Animation").getStringListValue("frames").toArray(new String[0]);

        String[][] textureList = ArrayUtil.split(textures, 2).toArray(new String[0][]);
        String[][] overlayList = ArrayUtil.split(overlays, 2).toArray(new String[0][]);

        try {
            AssetPackBuilder.createTwoStepHandheldItem(name1, name2, "item/handheld_rod", "cast", 1, textureList,
                    overlayList, colors, mode, templateShading, frameTime, frames);
        } catch (ArrayIndexOutOfBoundsException e) {
            Main.LOG.error(e);
        }
    }

    private static Quartet<ItemTier, Integer, Float, Properties> getToolValues(String name, Config config) {
        Properties properties = new Properties();
        ConfigGroup toolMaterial = config.getSubgroup("Tool Material");

        properties.durability(toolMaterial.getIntegerValue("max_damage"));
        // Integer level = toolMaterial.getIntegerValue("level");

        ItemTier tier;
        switch (toolMaterial.getStringValue("item_tier")) {
            case "stone": {
                tier = ItemTier.STONE;
                break;
            }
            case "iron": {
                tier = ItemTier.IRON;
                break;
            }
            case "diamond": {
                tier = ItemTier.DIAMOND;
                break;
            }
            case "gold": {
                tier = ItemTier.GOLD;
                break;
            }
            case "netherite": {
                tier = ItemTier.NETHERITE;
                break;
            }
            default: {
                tier = ItemTier.WOOD;
                break;
            }
        }

        if (toolMaterial.getFlagValue("immune_to_fire"))
            properties.fireResistant();

        switch (toolMaterial.getStringValue("rarity")) {
            case "uncommon": {
                properties.rarity(Rarity.UNCOMMON);
                break;
            }
            case "rare": {
                properties.rarity(Rarity.RARE);
                break;
            }
            case "epic": {
                properties.rarity(Rarity.EPIC);
                break;
            }
            default: {
                properties.rarity(Rarity.COMMON);
                break;
            }
        }

        if (toolMaterial.getFlagValue("no_repair"))
            properties.setNoRepair();

        Integer attackDamage = toolMaterial.getIntegerValue("attack_damage");
        Float attackSpeed = toolMaterial.getDoubleValue("attack_speed").floatValue();

        return new Quartet<ItemTier, Integer, Float, Properties>(tier, attackDamage, attackSpeed, properties);

    }

    // Tools
    public static void registerPickaxe(String name, Config config, ItemGroup toolGroup) {
        Quartet<ItemTier, Integer, Float, Properties> v = getToolValues(name, config);
        ToolRegistry.registerPickaxe(name, v.w, v.x, v.y, v.z, toolGroup);
        registerHandheldItemFromConfig(name, config);
    }

    public static void registerShovel(String name, Config config, ItemGroup toolGroup) {
        Quartet<ItemTier, Integer, Float, Properties> v = getToolValues(name, config);
        ToolRegistry.registerShovel(name, v.w, v.x, v.y, v.z, toolGroup);
        registerHandheldItemFromConfig(name, config);
    }

    public static void registerAxe(String name, Config config, ItemGroup toolGroup) {
        Quartet<ItemTier, Integer, Float, Properties> v = getToolValues(name, config);
        ToolRegistry.registerAxe(name, v.w, v.x, v.y, v.z, toolGroup);
        registerHandheldItemFromConfig(name, config);
    }

    public static void registerHoe(String name, Config config, ItemGroup toolGroup) {
        Quartet<ItemTier, Integer, Float, Properties> v = getToolValues(name, config);
        ToolRegistry.registerHoe(name, v.w, v.x, v.y, v.z, toolGroup);
        registerHandheldItemFromConfig(name, config);
    }

    public static void registerFlintAndSteel(String name, Config config, ItemGroup toolGroup) {
        ToolRegistry.registerFlintAndSteel(name, new Properties(), toolGroup);
        registerHandheldItemFromConfig(name, config);
    }

    public static void registerFishingRod(String name, Config config, ItemGroup toolGroup) {
        ToolRegistry.registerFishingRod(name, new Properties(), toolGroup);
        registerTwoStageHandheldItemFromConfig(name, name + "_cast", config);
    }

    public static void registerShears(String name, Config config, ItemGroup toolGroup) {
        ToolRegistry.registerShearsItem(name, new Properties(), toolGroup);
        registerHandheldItemFromConfig(name, config);
    }

    // Weapons
    public static void registerSword(String name, Config config, ItemGroup weaponGroup) {
        Quartet<ItemTier, Integer, Float, Properties> v = getToolValues(name, config);
        WeaponRegistry.registerSword(name, v.w, v.x, v.y, v.z, weaponGroup);
        registerHandheldItemFromConfig(name, config);
    }

    public static void registerShield(String name, Config config, ItemGroup weaponGroup) {
        WeaponRegistry.registerShield(name, new Properties(), weaponGroup);
        registerItemFromConfig(name, config);
    }

    public static void registerBow(String name, Config config, ItemGroup weaponGroup) {
        WeaponRegistry.registerBow(name, new Properties(), weaponGroup);
        registerShootableItemFromConfig(name, config);
    }

    public static void registerCrossbow(String name, Config config, ItemGroup weaponGroup) {
        WeaponRegistry.registerCrossbow(name, new Properties(), weaponGroup);
        registerShootableItemFromConfig(name, config);
    }

    public static void registerArrow(String name, Config config, ItemGroup weaponGroup) {
        WeaponRegistry.registerArrow(name, new Properties(), weaponGroup);
        registerItemFromConfig(name, config);
    }

    // Armour
    public static void registerHelmet(String name, Config config, ItemGroup armourGroup) {
        WearableRegistry.registerHead(name, new Properties(), armourGroup);
        registerItemFromConfig(name, config);
    }

    public static void registerChestplate(String name, Config config, ItemGroup armourGroup) {
        WearableRegistry.registerChest(name, new Properties(), armourGroup);
        registerItemFromConfig(name, config);
    }

    public static void registerLeggings(String name, Config config, ItemGroup armourGroup) {
        WearableRegistry.registerLegs(name, new Properties(), armourGroup);
        registerItemFromConfig(name, config);
    }

    public static void registerBoots(String name, Config config, ItemGroup armourGroup) {
        WearableRegistry.registerFeet(name, new Properties(), armourGroup);
        registerItemFromConfig(name, config);
    }
}
