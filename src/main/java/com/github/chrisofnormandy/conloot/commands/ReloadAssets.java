package com.github.chrisofnormandy.conloot.commands;

import com.github.chrisofnormandy.conloot.ModBlocks;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;

public class ReloadAssets {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> command = Commands.literal("con_rl")
                .requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("message", MessageArgument.message()).executes(ReloadAssets::reload));

        dispatcher.register(command);
    }

    static int reload(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
        ModBlocks.Init();
        return 1;
    }
}
