package net.music.bot.commandutlis;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.music.bot.commandutlis.music.*;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Music extends ListenerAdapter {
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
       System.out.println( new Date().toInstant() + " | SlashCommand(Music) | "+ event.getName() + " | BY " + event.getMember().getId() + "("+ event.getMember().getUser().getName() + ")" + " AT " + event.getTextChannel().getId() + "("+ event.getGuild().getName() + ")");
       if (event.getGuild() == null){
           return;
       }
        switch (event.getName()) {
            case "play" -> new Play(event);
            case "stop" -> new Stop(event);
            case "skip" -> new Skip(event);
            case "bassboost" -> new bassboost(event);
            case "karaoke" -> new Karaoke(event);
            case "volume" -> new Volume(event);
        }

    }

}
