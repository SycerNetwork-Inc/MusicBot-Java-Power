package net.music.bot.utils.event;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.music.bot.lavalink.AudioManagerController;
import net.music.bot.lavalink.GuildAudioManager;
import org.jetbrains.annotations.NotNull;

public class AudioEvent extends ListenerAdapter {
    private GuildAudioManager manager;

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        if(!event.getGuild().getSelfMember().getVoiceState().inVoiceChannel()){
            return;
        }
        if(event.getChannelLeft() != event.getGuild().getSelfMember().getVoiceState().getChannel()){
            return;
        }
       if (event.getChannelLeft().getMembers().size() == 1) {
           manager = AudioManagerController.getGuildAudioManager(event.getGuild());
           manager.destroy();
       }
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        if(!event.getGuild().getSelfMember().getVoiceState().inVoiceChannel()){
            return;
        }
        if(event.getChannelLeft() != event.getGuild().getSelfMember().getVoiceState().getChannel()){
            return;
        }
        if (event.getChannelLeft().getMembers().size() == 1) {
            manager = AudioManagerController.getGuildAudioManager(event.getGuild());
            manager.destroy();
        }
    }
}
