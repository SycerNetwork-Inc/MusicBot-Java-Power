package net.music.bot.lavalink;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavalink.client.io.jda.JdaLink;
import lavalink.client.player.LavalinkPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.music.bot.Main;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class GuildAudioManager {
    private final Guild guild;
    private final JdaLink link;
    private final LavalinkPlayer player;
    private final TrackScheduler scheduler;

    public GuildAudioManager(Guild guild) {
        this.guild = guild;
        this.link = Main.lavalink.getLink(guild);
        this.player = this.link.getPlayer();
        this.scheduler = new TrackScheduler(guild, this.player);
        this.player.addListener(this.scheduler);
    }

    public void openConnection(VoiceChannel channel, TextChannel tc) {
        try {
            this.link.connect(channel);
        } catch (Exception ex) {

        }
    }

    public void destroy() {
        link.destroy();
        AudioManagerController.removeGuildAudioManager(guild);
    }

    public void loadAndPlay(@NotNull String searchTerm , SlashCommandEvent event) {


        final String trackUrl = searchTerm.startsWith("<") && searchTerm.endsWith(">") ? searchTerm.substring(1, searchTerm.length() - 1) : searchTerm;

        AudioManagerController.getPlayerManager().loadItemOrdered(this, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                getScheduler().queue(track);
                String urlfix = track.getInfo().uri.replace("https://www.youtube.com/watch?v=","");
                String cover = "https://img.youtube.com/vi/" + urlfix + "/maxresdefault.jpg";

                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor("Playing Music","http://devkimkung.trueddns.com:39410/",event.getGuild().getSelfMember().getUser().getAvatarUrl());
                embed.setTitle(track.getInfo().title, track.getInfo().uri);
                embed.setColor(Color.PINK);
                embed.setDescription("**Author : **``"+track.getInfo().author+"``"+"\n**Time : **``"+formatTime(track.getInfo().length)+"``"+"\n**Voice Room : **" + "<#"+event.getMember().getVoiceState().getChannel().getId()+">"+"\n**Used By : **" + "<@!"+event.getMember().getId()+">");
                embed.setFooter("Develop by hachi",event.getGuild().getSelfMember().getUser().getAvatarUrl());
                embed.setTimestamp(new Date().toInstant());
                embed.setThumbnail(cover);
              //  embed.setDescription("[Video Link]("+track.getInfo().uri+")");
                embed.setColor(Color.GREEN);
                event.replyEmbeds(embed.build()).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor("Playing Music","http://devkimkung.trueddns.com:39410/",event.getGuild().getSelfMember().getUser().getAvatarUrl());
                embed.setTitle("**Added Playlist**");
                for (int i = 0; i < 10; i++) {
                    embed.appendDescription("\n**"+playlist.getTracks().get(i).getInfo().title + "[Video Link]("+playlist.getTracks().get(i).getInfo().uri+")**");
                }
                embed.setColor(Color.PINK);
                embed.setFooter("Develop by hachi",event.getGuild().getSelfMember().getUser().getAvatarUrl());
                embed.setTimestamp(new Date().toInstant());

                event.replyEmbeds(embed.build()).queue();

                for(AudioTrack track : playlist.getTracks()) {
                    getScheduler().queue(track);
                }

            }

            @Override public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {


            }

        });

    }

    public void closeConnection() {
        link.disconnect();
    }
    public TrackScheduler getScheduler() {
        return scheduler;
    }
    public JdaLink getLink() {
        return link;
    }
    public LavalinkPlayer getPlayer() {
        return player;
    }
    public void resetPlayer(Guild guild) {
        link.resetPlayer();
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
