package net.music.bot.commandutlis.music;

import com.github.natanbc.lavadsp.karaoke.KaraokePcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.filter.PcmFilterFactory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.music.bot.lavalink.AudioManagerController;
import net.music.bot.lavalink.GuildAudioManager;

import java.awt.*;
import java.util.Collections;
import java.util.Date;

public class Karaoke {
    private GuildAudioManager manager;
    public Karaoke(SlashCommandEvent event){
        Guild guild = event.getGuild();
        GuildVoiceState vc = event.getMember().getVoiceState();
        TextChannel tc = event.getTextChannel();
        manager = AudioManagerController.getGuildAudioManager(event.getGuild());
        Boolean r = event.getOption("karaoke").getAsBoolean();

        if(!vc.inVoiceChannel()){
            event.reply("Please Join Voice Channel Fist!!").queue();
            return;
        }
        if(vc.getChannel() != guild.getSelfMember().getVoiceState().getChannel() && guild.getSelfMember().getVoiceState().inVoiceChannel() == true) {
            return;
        }

        if(r) {
            manager.getPlayer().getFilters().setKaraoke(new lavalink.client.io.filters.Karaoke());
            manager.getPlayer().getFilters().commit();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("Music Command","http://devkimkung.trueddns.com:39410/",event.getGuild().getSelfMember().getUser().getAvatarUrl());
            embed.setTitle("**Added Filters Karaoke : " + "TRUE" + "**");
            embed.setDescription("**Karaoke at : **<#" + event.getMember().getVoiceState().getChannel().getId() +">" +
                    "\n**Used by : **<@!"+event.getMember().getId()+">");

            embed.setColor(Color.magenta);
            embed.setFooter("Develop by hachi",event.getGuild().getSelfMember().getUser().getAvatarUrl());
            embed.setTimestamp(new Date().toInstant());

            event.replyEmbeds(embed.build()).queue();
        }
        else {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("Music Command","http://devkimkung.trueddns.com:39410/",event.getGuild().getSelfMember().getUser().getAvatarUrl());
            embed.setTitle("**Added Filters Karaoke : " + "FALSE" + "**");
            embed.setDescription("**Karaoke at : **<#" + event.getMember().getVoiceState().getChannel().getId() +">" +
                    "\n**Used by : **<@!"+event.getMember().getId()+">");
            embed.setColor(Color.magenta);
            embed.setFooter("Develop by hachi",event.getGuild().getSelfMember().getUser().getAvatarUrl());
            embed.setTimestamp(new Date().toInstant());
            event.replyEmbeds(embed.build()).queue();
            manager.getPlayer().getFilters().setKaraoke(null);
            manager.getPlayer().getFilters().commit();
        }
    }
}
