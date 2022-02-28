package net.music.bot.commandutlis.music;

import lavalink.client.io.filters.Timescale;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.music.bot.lavalink.AudioManagerController;
import net.music.bot.lavalink.GuildAudioManager;

public class Speed {

    private GuildAudioManager manager;

    public Speed(SlashCommandEvent event){
        Guild guild = event.getGuild();
        GuildVoiceState vc = event.getMember().getVoiceState();
        TextChannel tc = event.getTextChannel();
        manager = AudioManagerController.getGuildAudioManager(event.getGuild());

        if(!vc.inVoiceChannel()){
            event.reply("Please Join Voice Channel Fist!!").queue();
            return;
        }
        if(vc.getChannel() != guild.getSelfMember().getVoiceState().getChannel() && guild.getSelfMember().getVoiceState().inVoiceChannel() == true) {
            return;
        }
        Timescale time = new Timescale();
        time.setSpeed(2.0F);

        manager.getPlayer().getFilters().setTimescale(new Timescale());
        manager.getPlayer().getFilters().commit();
    }
}
