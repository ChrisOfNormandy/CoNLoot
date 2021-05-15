package com.github.chrisofnormandy.conloot.content;

import com.github.chrisofnormandy.conlib.config.Config;
import com.github.chrisofnormandy.conlib.crop.CropBase;
import com.github.chrisofnormandy.conlib.crop.SeedBase;
import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conlib.registry.ModRegister;
import com.github.chrisofnormandy.conloot.Main;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;

public class CustomCrop {
    public static void registerFromConfig(String name, Config config, Groups cropGroup) {
        Main.LOG.info("Generating new crop:" + name);
        RenderType transparentRenderType = RenderType.cutoutMipped();

        Block crop = ModRegister.registerCrop(new CropBase(), name + "_crop");
        RenderTypeLookup.setRenderLayer(crop, transparentRenderType);
        
        ModRegister.registerFood(name, 1, 1, cropGroup);
        ModRegister.registerItem(name + "_seeds", new SeedBase(crop, new Item.Properties().tab(cropGroup)));
    }
}
