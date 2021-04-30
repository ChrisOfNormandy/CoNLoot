package com.github.chrisofnormandy.conloot;

import com.github.chrisofnormandy.conlib.block.ModBlock;
import com.github.chrisofnormandy.conlib.block.types.OreBase;
import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conlib.registry.ModRegister;
import com.github.chrisofnormandy.conlib.tool.ToolMaterial;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.config.ConfigGroup;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonArray;
import com.github.chrisofnormandy.conlib.common.StringUtil;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class ModBlocks {
    public static JsonBuilder jsonBuilder = new JsonBuilder();
    public static JsonArray blockJson = jsonBuilder.createJsonArray();
    public static JsonArray itemJson = jsonBuilder.createJsonArray();
    public static JsonArray recipeJson = jsonBuilder.createJsonArray();
    public static JsonArray groupJson = jsonBuilder.createJsonArray();

    static void registerFromConfig(String name, Config config, Groups itemGroup, Groups toolGroup, Groups blockGroup) {
        Main.LOG.info("Generating new resource:" + name);

        ConfigGroup ore = config.getSubgroup("Ore");
        ConfigGroup tool = config.getSubgroup("ToolMaterial");
        
        String oreName = ore.getStringValue("ore_name");
        Integer minXP = ore.getIntegerValue("min_xp");
        Integer maxXP = ore.getIntegerValue("max_xp");
        Integer harvestLevel = ore.getIntegerValue("harvest_level");
        Float hardness = ore.getFloatValue("hardness");

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

        OreBase oreBlock = new OreBase(minXP, maxXP, harvestLevel, hardness);

        // Here is where a check for "gen_tools, armour, ore..." would be made.
        Main.LOG.info("Registering from config, type: " + name + " -> " + resourceTypeStr);

        if (resourceTypeStr.equals("gem")) {
            ToolMaterial.type resourceType = ToolMaterial.type.gem;

            ToolMaterial material = new ToolMaterial(level, maxDamage, immuneToFire, rarity, noRepair, resourceType);

            ModBlock.Ore.registerGem(name, oreName, oreBlock, material, itemGroup, toolGroup, blockGroup);
        
            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")));

            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")) + " Pickaxe");

            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")) + " Axe");

            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")) + " Shovel");

            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")) + " Hoe");
        }
        else if (resourceTypeStr.equals("ingot")) {
            ToolMaterial.type resourceType = ToolMaterial.type.ingot;
            ToolMaterial material = new ToolMaterial(level, maxDamage, immuneToFire, rarity, noRepair, resourceType);

            ModBlock.Ore.registerMetal(name, oreName, oreBlock, material, itemGroup, toolGroup, blockGroup);
        
            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")) + " Nugget");
            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")) + " Ingot");

            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")) + " Pickaxe");

            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")) + " Axe");

            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")) + " Shovel");

            itemJson.add(StringUtil.wordCaps(name.replace("_", " ")) + " Hoe");
        }
        else {
            ModBlock.Ore.register(oreName, oreBlock, blockGroup);
        }

        blockJson.addObject().set("name", StringUtil.wordCaps(oreName.replace("_", " "))).set("options", 1);
    }

    public static void Init() {
        Groups itemGroup = Groups.createGroup("item_group", ModRegister.registerItem("item_group_icon", new Item.Properties()));
        Groups toolGroup = Groups.createGroup("tool_group", ModRegister.registerItem("tool_group_icon", new Item.Properties()));
        Groups blockGroup = Groups.createGroup("ore_group", ModRegister.registerItem("ore_group_icon", new Item.Properties()));

        groupJson.addObject().set("registryName", "itemGroup.{mod_id}_items").set("name", "CoNLoot | Items");
        groupJson.addObject().set("registryName", "itemGroup.{mod_id}_tools").set("name", "CoNLoot | Tools");
        groupJson.addObject().set("registryName", "itemGroup.{mod_id}_ores").set("name", "CoNLoot | Ores");
        
        //NEXT UP:
        /*
            Add swords to the lib
            And armour

            have a full block of the main resource generated

            The lib should generate a new resource pack for the user containing basic JSON stuff (?) and the translation file (?)
            Have a notice show to the user on login about the resource pack?
        */

        Main.config.gemConfigs.forEach((String name, Config config) -> {
            registerFromConfig(name, config, itemGroup, toolGroup, blockGroup);
        });

        Main.config.metalConfigs.forEach((String name, Config config) -> {
            registerFromConfig(name, config, itemGroup, toolGroup, blockGroup);
        });

        String configPath = FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).toString();
        
        jsonBuilder.write(configPath + "/blocks", jsonBuilder.stringify(blockJson));
    }
}