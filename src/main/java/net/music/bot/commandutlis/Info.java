package net.music.bot.commandutlis;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.music.bot.commandutlis.info.Botinfo;
import net.music.bot.commandutlis.info.Help;
import net.music.bot.commandutlis.music.*;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Info extends ListenerAdapter {
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        System.out.println( new Date().toInstant() + " | SlashCommand(Info) | "+ event.getName() + " | BY " + event.getMember().getId() + "("+ event.getMember().getUser().getName() + ")" + " AT " + event.getTextChannel().getId() + "("+ event.getGuild().getName() + ")");
        if (event.getGuild() == null){
            return;
        }
        switch (event.getName()) {
            case "help" -> new Help(event);
            case "stat" -> new Botinfo(event);
        }

    }
}
