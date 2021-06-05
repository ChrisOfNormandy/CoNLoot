package com.github.chrisofnormandy.conloot.content;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.crop.CropBase;
import com.github.chrisofnormandy.conlib.crop.SeedBase;
import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conlib.registry.Foods;
import com.github.chrisofnormandy.conlib.registry.Items;
import com.github.chrisofnormandy.conlib.registry.Plants;
import com.github.chrisofnormandy.conloot.Main;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;

public class CustomCrop {
    public static void registerFromConfig(String name, Config config, Groups cropGroup) {
        Main.LOG.info("Generating new crop:" + name);
        RenderType transparentRenderType = RenderType.cutoutMipped();

        Block crop = Plants.registerCrop(name + "_crop", new CropBase());
        RenderTypeLookup.setRenderLayer(crop, transparentRenderType);
        
        Foods.registerFood(name, 1, 1f, cropGroup);
        Items.register(name + "_seeds", new SeedBase(crop, new Item.Properties().tab(cropGroup)));
    }
}
