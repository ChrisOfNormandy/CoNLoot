package com.github.chrisofnormandy.conloot.content;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.crop.CropBase;
import com.github.chrisofnormandy.conlib.crop.SeedBase;
import com.github.chrisofnormandy.conlib.registry.FoodRegistry;
import com.github.chrisofnormandy.conlib.registry.ItemRegistry;
import com.github.chrisofnormandy.conlib.registry.PlantRegistry;
import com.github.chrisofnormandy.conloot.Main;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class CustomCrop {
    public static void registerFromConfig(String name, Config config, ItemGroup cropGroup) {
        Main.LOG.info("Generating new crop:" + name);
        RenderType transparentRenderType = RenderType.cutoutMipped();

        Block crop = PlantRegistry.registerCrop(name + "_crop", new CropBase());
        RenderTypeLookup.setRenderLayer(crop, transparentRenderType);

        FoodRegistry.registerFood(name, 1, 1f, cropGroup);
        ItemRegistry.register(name + "_seeds", new SeedBase(crop, new Item.Properties().tab(cropGroup)));
    }
}
