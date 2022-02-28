package net.music.bot.commandutlis.info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.util.Date;

public class Help {
    public Help(SlashCommandEvent event){
        if (event.getMember() != null && event.getGuild() != null) {
            EmbedBuilder main = new EmbedBuilder();
            main.setAuthor("Bot info"
                    ,"http://devkimkung.trueddns.com:39410/"
                    ,event.getGuild().getSelfMember().getUser().getAvatarUrl());
            main.setTitle("**Help Menu : Main Menu**");
            main.setColor(Color.blue);
            main.setDescription("**Hatsune Miku Bot Develop by hachi \nPlay Music and more Future \nUse button for views slash commands\n(send message in bot dm for contact developer)**");
            main.setFooter("Develop by hachi",event.getGuild().getSelfMember().getUser().getAvatarUrl());
            main.setTimestamp(new Date().toInstant());
            event.replyEmbeds(main.build()).addActionRow(Button.success("miku_main", "🌍"), Button.secondary("miku_general", "🏠"), Button.secondary("miku_music", "🎶")).queue();
    }
    }
}
