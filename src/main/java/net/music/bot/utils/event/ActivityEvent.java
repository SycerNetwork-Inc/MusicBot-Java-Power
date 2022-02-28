package net.music.bot.utils.event;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ActivityEvent extends ListenerAdapter {


    private int currentIndex = 0;
    private ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();
    @Override
    public void onReady(@NotNull ReadyEvent event) {

        threadPool.scheduleWithFixedDelay(() -> {
            String[] messages = { "起きろ","今日は良い一日を","こんいちわ","Members|"+getAllMember(event.getJDA()),"Server|" + event.getJDA().getGuilds().size()};
            event.getJDA().getPresence().setActivity(Activity.playing(messages[currentIndex]));
            currentIndex = (currentIndex + 1) % messages.length;
        }, 0, 15, TimeUnit.SECONDS);
    }
    public int getAllMember(JDA jda) {
        int[] i = {0};
        for (Guild g : jda.getGuilds())
            i[0] += g.getMemberCount();
        return i[0];
    }
}
