package net.music.bot.utils;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class SlazCommandRegistry {

    public SlazCommandRegistry(CommandListUpdateAction commands) {
        commands.addCommands(
                new CommandData("play", "Music | Play the Music")
                        .addOptions(new OptionData(OptionType.STRING, "music", "Music URL")
                                .setRequired(true))
        );
        commands.addCommands(
                new CommandData("stop","Music | Stop the Music")
        );
        commands.addCommands(
                new CommandData("skip","Music | Skip the Music")
        );
        commands.addCommands(
                new CommandData("volume","Music | Set Music volume").addOptions(new OptionData(OptionType.INTEGER,"volume","set a bot volume").setRequired(true))
        );
        commands.addCommands(
                new CommandData("bassboost","Music | Use Bass boot").addOptions(new OptionData(OptionType.INTEGER,"bassboost","Enter percentage").setRequired(true))
        );
        commands.addCommands(
                new CommandData("karaoke","Music | Use Karaoke mode").addOptions(new OptionData(OptionType.BOOLEAN,"karaoke","set true or flase").setRequired(true))
        );
        commands.addCommands(
                new CommandData("queue","Music | Music Queue")
        );
        commands.addCommands(
                new CommandData("nowplay","Music | Music Now Play")
        );
        commands.addCommands(
                new CommandData("speed","Music | Music Play Speed").addOptions(new OptionData(OptionType.INTEGER,"speed","set Music speed").setRequired(true))
        );
        commands.addCommands(
                new CommandData("help","Normal | About Bot commands")
        );
        commands.addCommands(
                new CommandData("stat","Dev info | Get bot performance")
        );
        commands.queue();
        System.out.println("Registry Command!!");

    }
}
