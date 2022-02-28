package net.music.bot.commandutlis.music;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.music.bot.lavalink.AudioManagerController;
import net.music.bot.lavalink.GuildAudioManager;

import java.awt.*;
import java.util.Date;

public class Volume {
    private GuildAudioManager manager;

    public Volume(SlashCommandEvent event)
    {
        Guild guild = event.getGuild();
        GuildVoiceState vc = event.getMember().getVoiceState();
        TextChannel tc = event.getTextChannel();
        manager = AudioManagerController.getGuildAudioManager(event.getGuild());
        int vol = (int) event.getOption("volume").getAsLong();

        if(!vc.inVoiceChannel()){
            event.reply("Please Join Voice Channel Fist!!").queue();
            return;
        }
        if(vc.getChannel() != guild.getSelfMember().getVoiceState().getChannel() && guild.getSelfMember().getVoiceState().inVoiceChannel() == true) {
            return;
        }

        if(vol > 100 ){
            event.reply("Your can't set volume more than 100%").queue();
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("Music Command","http://devkimkung.trueddns.com:39410/",event.getGuild().getSelfMember().getUser().getAvatarUrl());
        embed.setTitle("**Set Volume : " + vol + "%**");
        embed.setDescription("**Played at : **<#" + event.getMember().getVoiceState().getChannel().getId() +">" +
                "\n**Used by : **<@!"+event.getMember().getId()+">");

        embed.setColor(Color.magenta);
        embed.setFooter("Develop by hachi",event.getGuild().getSelfMember().getUser().getAvatarUrl());
        embed.setTimestamp(new Date().toInstant());

        event.replyEmbeds(embed.build()).queue();

        manager = AudioManagerController.getGuildAudioManager(guild);
        manager.getPlayer().setVolume(vol);


    }
}
