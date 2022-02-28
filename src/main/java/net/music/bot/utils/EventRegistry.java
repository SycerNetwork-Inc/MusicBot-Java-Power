package net.music.bot.utils;

import net.dv8tion.jda.api.JDABuilder;
import net.music.bot.commandutlis.Info;
import net.music.bot.commandutlis.Music;
import net.music.bot.utils.event.ActivityEvent;
import net.music.bot.utils.event.AudioEvent;

public class EventRegistry {
    public EventRegistry(JDABuilder jda){
        jda.addEventListeners(new Music());
        jda.addEventListeners(new AudioEvent());
        jda.addEventListeners(new ActivityEvent());
        jda.addEventListeners(new Info());
        System.out.println("Registry Event!!");
    }
}
