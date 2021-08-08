package com.github.chrisofnormandy.conloot;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.chrisofnormandy.conlib.registry.ModRegister;

@Mod("conloot")
public class Main {
    public static final Logger LOG = LogManager.getLogger();
    public static String MOD_ID = "conloot";
    public static ModConfigs config = new ModConfigs();

    public Main() {
        ModRegister.Init(MOD_ID);

        config.Init();

        ModUIs.Init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            ModBlocks.Init();
        }

        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            ModItems.Init();
        }

        @SubscribeEvent
        public static void onBiomeRegistry(final RegistryEvent.Register<Biome> itemRegistryEvent) {
            ModWorldGen.Init();
        }

        @SubscribeEvent
        public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
            MinecraftForge.EVENT_BUS.register(ModCommands.class);
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ModRegister.transparentBlocks
                    .forEach((String name, Block block) -> RenderTypeLookup.setRenderLayer(block, RenderType.cutout()));
        }
    }
}
