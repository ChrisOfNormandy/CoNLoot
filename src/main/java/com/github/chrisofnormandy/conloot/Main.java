package com.github.chrisofnormandy.conloot;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conlib.registry.ModRegister;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("conloot")
public class Main
{
    // Directly reference a log4j logger.
    public static final Logger LOG = LogManager.getLogger();
    public static String MOD_ID = "conloot";
    public static Mod_Config config = new Mod_Config();

    public Main() {
        config.Init();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {      
        // some preinit code
        // LOG.info("HELLO FROM PREINIT");
        // LOG.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        // try {
        //     LOG.info("SENDING INFO.");
        //     Client.Send("TEST STRING.");
        // }
        // catch (Exception e) {
        //     LOG.warn("################");
        //     LOG.warn(e);
        // }
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        // LOG.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        // InterModComms.sendTo("conloot", "helloworld", () -> { LOG.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        // LOG.info("Got IMC {}", event.getIMCStream().
        //         map(m->m.getMessageSupplier().get()).
        //         collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        // LOG.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            ModRegister.Init(MOD_ID);
            
            JsonBuilder jsonBuilder = new JsonBuilder();
            JsonObject cfg = jsonBuilder.createJsonObject();
            cfg.set("mod_id", MOD_ID);
            cfg.set("mod_name", MOD_ID);
            cfg.set("author", "");
            cfg.set("path", "./resourcepacks");
            cfg.set("build_path", "./defaultconfigs");

            String configPath = FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).toString();

            jsonBuilder.write(configPath + "/config", cfg.toString());

            ModBlocks.Init();
            ModItems.Init();
        }
    }
}
