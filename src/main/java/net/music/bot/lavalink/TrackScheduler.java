package net.music.bot.lavalink;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lavalink.client.player.IPlayer;
import lavalink.client.player.LavalinkPlayer;
import lavalink.client.player.event.PlayerEventListenerAdapter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.music.bot.Main;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TrackScheduler extends PlayerEventListenerAdapter {

    private final Guild guild;
    private AudioTrack lastTrack = null;
    private boolean looping = false;
    private final LavalinkPlayer player;
    public final Queue<AudioTrack> queue;
    private ScheduledFuture timeout;
    private TextChannel musicChannel;
    private GuildAudioManager manager;


    TrackScheduler(Guild guild, LavalinkPlayer player) {
        this.guild = guild;
        this.player = player;
        this.queue = new LinkedList<>();
    }

    public boolean hasNextTrack() {
        return (queue.peek() != null);
    }

    public void queue(AudioTrack track) {
        if(player.getPlayingTrack() == null) {
            queue.add(track);
            nextTrack();
            return;
        }

        queue.offer(track);
    }

    public void clearQueue() {
        queue.clear();
    }



    public void nextTrack() {
        try {
            AudioTrack track = queue.poll();

//            LOGGER.error(queue.toString());

            if(track == null) {
                if(player.getPlayingTrack() != null) {
                    player.stopTrack();
                }

                //TODO: fix in-discord logs
//                msg.sendWebhookMsg("Queue empty | The bot will leave in 1 minute from `" + guild.toString() + "`", "https://discordapp.com/api/webhooks/733077861639913580/t_5H_mh3Onjgkki24CFTTrfNniPiXCEM-zUH7LFNDarmroQYb9zjLeH78RHSgumbD-p1");
//                LOGGER.info("Queue empty | The bot will leave in 1 minute from " + guild.toString());

                try {
                    GuildMessageReceivedEvent evt = (GuildMessageReceivedEvent) lastTrack.getUserData();
                    TextChannel tc = evt.getChannel();

                    if(musicChannel == null) { musicChannel = tc; }

                    if(player.getPlayingTrack() == null && queue.isEmpty()) {

                    }
                } catch(Exception ignored) { }

                return;
            }

            if(timeout != null) {
                timeout.cancel(true);
                timeout = null;
                //TODO: fix in-discord logs
//                msg.sendWebhookMsg("Queue not empty | Leave job cancelled for `" + guild.toString() + "`", "https://discordapp.com/api/webhooks/733077861639913580/t_5H_mh3Onjgkki24CFTTrfNniPiXCEM-zUH7LFNDarmroQYb9zjLeH78RHSgumbD-p1");
//                LOGGER.info("Queue not empty | Leave job cancelled for " + guild.toString());
            }

            player.playTrack(track);




        } catch(Exception ex) { ex.printStackTrace(); }
    }

    @Override
    public void onTrackStart(IPlayer player, AudioTrack track) {
        super.onTrackStart(player, track);
    }

    @Override
    public void onTrackException(IPlayer player, AudioTrack track, Exception exception) {
        super.onTrackException(player, track, exception);
    }

    @Override
    public void onTrackStuck(IPlayer player, AudioTrack track, long thresholdMs) {
        super.onTrackStuck(player, track, thresholdMs);
    }

    @Override
    public void onTrackEnd(IPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        this.lastTrack = track;
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if(endReason.mayStartNext) {
            if(looping) {
                queue.add(track);
                nextTrack();
                return;
            }

            nextTrack();
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(player.getPlayingTrack() == null){
                manager = AudioManagerController.getGuildAudioManager(guild);
                manager.destroy();
            }
        }




    }

    public boolean isLooping() { return looping; }
    public void setLooping(boolean looping) { this.looping = looping; }
    public AudioTrack getLastTrack() { return lastTrack; }
    public void shuffle() { Collections.shuffle((List<?>) queue); }
    public ScheduledFuture getTimeout() {
        return timeout;
    }
    public void setTimeout(ScheduledFuture timeout) {
        this.timeout = timeout;
    }

}
