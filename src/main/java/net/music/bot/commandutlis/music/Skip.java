package net.music.bot.commandutlis.music;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.music.bot.lavalink.AudioManagerController;
import net.music.bot.lavalink.GuildAudioManager;

public class Skip {
    private GuildAudioManager manager;

    public Skip(SlashCommandEvent event){
        Guild guild = event.getGuild();
        GuildVoiceState vc = event.getMember().getVoiceState();
        TextChannel tc = event.getTextChannel();
        manager = AudioManagerController.getGuildAudioManager(event.getGuild());

        event.reply("Skipped!!").queue();
        manager = AudioManagerController.getGuildAudioManager(guild);
        manager.getScheduler().nextTrack();
    }
}
