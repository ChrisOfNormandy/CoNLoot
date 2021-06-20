package com.github.chrisofnormandy.conloot;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conlib.registry.Items;
import com.github.chrisofnormandy.conlib.registry.Tools;
import com.github.chrisofnormandy.conlib.tool.ToolMaterial;
import com.github.chrisofnormandy.conlib.tool.ToolMaterial.type;

import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.ToolType;

public class ModItems {
    public static JsonBuilder jsonBuilder = new JsonBuilder();

    private static void plainItemSetup(String name, Config config, Groups itemGroup) {
        Items.register(name, new Item.Properties(), itemGroup);
    }

    private static void pickaxeSetup(String name, Config config, Groups toolGroup) {
        Tools.register(name, new ToolMaterial(1, 100, false, Rarity.COMMON, false, type.material), ToolType.PICKAXE, 1,
                toolGroup);
    }

    private static void axdSetup(String name, Config config, Groups toolGroup) {
        Tools.register(name, new ToolMaterial(1, 100, false, Rarity.COMMON, false, type.material), ToolType.AXE, 1,
                toolGroup);
    }

    private static void shovelSetup(String name, Config config, Groups toolGroup) {
        Tools.register(name, new ToolMaterial(1, 100, false, Rarity.COMMON, false, type.material), ToolType.SHOVEL, 1,
                toolGroup);
    }

    private static void hoeSetup(String name, Config config, Groups toolGroup) {
        Tools.register(name, new ToolMaterial(1, 100, false, Rarity.COMMON, false, type.material), ToolType.HOE, 1,
                toolGroup);
    }

    public static void Init() {
        Main.config.plainItemConfigs
                .forEach((String name, Config config) -> plainItemSetup(name, config, ModGroups.itemGroup));

        Main.config.pickaxeConfigs
                .forEach((String name, Config config) -> pickaxeSetup(name, config, ModGroups.toolGroup));
        Main.config.axeConfigs.forEach((String name, Config config) -> axdSetup(name, config, ModGroups.toolGroup));
        Main.config.shovelConfigs
                .forEach((String name, Config config) -> shovelSetup(name, config, ModGroups.toolGroup));
        Main.config.hoeConfigs.forEach((String name, Config config) -> hoeSetup(name, config, ModGroups.toolGroup));
    }
}
