package com.github.chrisofnormandy.conloot;

import com.github.chrisofnormandy.conloot.commands.ReloadAssets;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModCommands {
    @SubscribeEvent
    public static void onCommandRegistry(final RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
        ReloadAssets.register(commandDispatcher);
    }
}
