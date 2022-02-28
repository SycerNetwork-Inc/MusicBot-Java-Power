package net.music.bot.commandutlis.music;

import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavalink.client.io.Link;
import lavalink.client.io.jda.JdaLink;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.music.bot.Main;
import net.music.bot.lavalink.AudioManagerController;
import net.music.bot.lavalink.GuildAudioManager;
import net.music.bot.lavalink.YouTubeSearchHandler;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Play {
    private GuildAudioManager manager;

    public Play(SlashCommandEvent event){

        Guild guild = event.getGuild();
        GuildVoiceState vc = event.getMember().getVoiceState();
        TextChannel tc = event.getTextChannel();
        manager = AudioManagerController.getGuildAudioManager(event.getGuild());
        String s = event.getOption("music").getAsString();

        if(!vc.inVoiceChannel()){
            event.reply("Please Join Voice Channel Fist!!").queue();
        return;
        }
        if(vc.getChannel() != guild.getSelfMember().getVoiceState().getChannel() && guild.getSelfMember().getVoiceState().inVoiceChannel() == true) {
            event.reply("Has other member use bot now!");
            return;
        }
        manager.openConnection(vc.getChannel(),tc);
        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.setSelfDeafened(true);
        if (isURI(s)) {
            manager.loadAndPlay(s,event);

        }else {
            String Link = "https://www.youtube.com/watch?v=" + YouTubeSearchHandler.search(s, 1).get(0).toString().replace("{\"id\":{\"videoId\":\"", "").replace("\"}}", "");
            manager.loadAndPlay(Link, event);

        }
    }

    final static Set<String> protocols, protocolsWithHost;

    static {
        protocolsWithHost = new HashSet<String>(
                Arrays.asList( new String[]{ "file", "ftp", "http", "https" } )
        );
        protocols = new HashSet<String>(
                Arrays.asList( new String[]{ "mailto", "news", "urn" } )
        );
        protocols.addAll(protocolsWithHost);
    }

    public static boolean isURI(String str) {
        int colon = str.indexOf(':');
        if (colon < 3)                      return false;

        String proto = str.substring(0, colon).toLowerCase();
        if (!protocols.contains(proto))     return false;

        try {
            URI uri = new URI(str);
            if (protocolsWithHost.contains(proto)) {
                if (uri.getHost() == null)      return false;

                String path = uri.getPath();
                if (path != null) {
                    for (int i=path.length()-1; i >= 0; i--) {
                        if ("?<>:*|\"".indexOf( path.charAt(i) ) > -1)
                            return false;
                    }
                }
            }

            return true;
        } catch ( Exception ex ) {}

        return false;
    }
}
