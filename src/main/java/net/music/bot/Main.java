package net.music.bot;

import lavalink.client.io.LavalinkSocket;
import lavalink.client.io.jda.JdaLavalink;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.music.bot.lavalink.AudioManagerController;
import net.music.bot.utils.EventRegistry;
import net.music.bot.utils.SlazCommandRegistry;

import javax.security.auth.login.LoginException;
import java.net.URI;
import java.util.function.Function;

public class Main {
    public static String Token = "TOKEN";
    public static JDA PublicJDA = null;

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(Token,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_EMOJIS);

        builder.addEventListeners(lavalink).setVoiceDispatchInterceptor(lavalink.getVoiceInterceptor());
        lavalink.setAutoReconnect(true);

        // Lavalink Connection Local+Port, Password
        lavalink.addNode(URI.create(""),"");
        lavalink.addNode(URI.create(""),"");

        new EventRegistry(builder);
        JDA jda = builder.build();
        PublicJDA = jda;
        CommandListUpdateAction commands = jda.updateCommands();
        new SlazCommandRegistry(commands);
        new AudioManagerController();
        for (LavalinkSocket a : lavalink.getNodes()) {
            System.out.println(a.getName() + ": " + (a.isAvailable() ? "OK" : "Mai OK"));
        }
    }

   public static JdaLavalink lavalink = new JdaLavalink(
           "894495806948778036",
           2,
           new Function<Integer, JDA>() {
               @Override
               public JDA apply(Integer integer) {
                   return PublicJDA;
               }
           }
   );

}
