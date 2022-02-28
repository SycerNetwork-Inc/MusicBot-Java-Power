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

public class bassboost {
    private GuildAudioManager manager;
    private static final float[] BASS_BOOST = {0.05f, 0.07f, 0.16f, 0.03f, 0.05f, 0.11f};

    public bassboost(SlashCommandEvent event){
        Guild guild = event.getGuild();
        GuildVoiceState vc = event.getMember().getVoiceState();
        TextChannel tc = event.getTextChannel();
        manager = AudioManagerController.getGuildAudioManager(event.getGuild());
        int r = (int) event.getOption("bassboost").getAsLong();

        if(!vc.inVoiceChannel()){
            event.reply("Please Join Voice Channel Fist!!").queue();
            return;
        }
        if(vc.getChannel() != guild.getSelfMember().getVoiceState().getChannel() && guild.getSelfMember().getVoiceState().inVoiceChannel() == true) {
            return;
        }

        if(r < 600) {
            if (r != 0) {
                final float multiplier = r / 100.0f;
                for (int i = 0; i < BASS_BOOST.length; i++) {
                    manager.getPlayer().getFilters().setBand(i, BASS_BOOST[i] * multiplier);
                   // System.out.println("Debug:" + i + "," + BASS_BOOST[i] * multiplier);
                }
                manager.getPlayer().getFilters().commit();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor("Music Command","http://devkimkung.trueddns.com:39410/",event.getGuild().getSelfMember().getUser().getAvatarUrl());
                embed.setTitle("**Added Filters Bass boost : " + r + "%**");
                embed.setDescription("**Bass boost at : **<#" + event.getMember().getVoiceState().getChannel().getId() +">" +
                        "\n**Used by : **<@!"+event.getMember().getId()+">");

                embed.setColor(Color.magenta);
                embed.setFooter("Develop by hachi",event.getGuild().getSelfMember().getUser().getAvatarUrl());
                embed.setTimestamp(new Date().toInstant());

                event.replyEmbeds(embed.build()).queue();
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor("Music Command","http://devkimkung.trueddns.com:39410/",event.getGuild().getSelfMember().getUser().getAvatarUrl());
                embed.setTitle("**Added Filters Bass boost : Disable**");
                embed.setDescription("**Bass boost at : **<#" + event.getMember().getVoiceState().getChannel().getId() +">" +
                        "\n**Used by : **<@!"+event.getMember().getId()+">");

                embed.setColor(Color.magenta);
                embed.setFooter("Develop by hachi",event.getGuild().getSelfMember().getUser().getAvatarUrl());
                embed.setTimestamp(new Date().toInstant());

                event.replyEmbeds(embed.build()).queue();
                manager.getPlayer().getFilters().clear();
                manager.getPlayer().getFilters().commit();
            }
        }else {
           event.reply("Error Number").queue();
        }

}
}
