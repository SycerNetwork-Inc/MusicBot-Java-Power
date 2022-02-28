package net.music.bot.lavalink;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

public class YouTubeSearchHandler {
    private static final HashMap<String, SearchListResponse> searchCache = new HashMap<>();

    /**
     * Searches youtube using e.getParameters() and returns the first result.
     * @return youtube video url.
     */
    public static List<SearchResult> search(String searchString, long maxResults) {
        try {
            SearchListResponse searchResponse = searchCache.getOrDefault(searchString, null);

            if(searchResponse == null) {
                YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new GsonFactory(), request -> {
                }).setApplicationName("Miku Bot").build();

                YouTube.Search.List search = youtube.search().list("snippet")
                        .setKey("AIzaSyAluStC1dnU10o7GC-TG5dzJYJUQtXm2go")
                        .setQ(searchString)
                        .setType("video")
                        .setFields("items(id/videoId)")
                        .setMaxResults(maxResults);

                searchResponse = search.execute();
                searchCache.put(searchString, searchResponse);
            }

            return searchResponse.getItems();

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static HashMap<String, SearchListResponse> getSearchCache() {
        return searchCache;
    }

    public static void clearSearchCache() {
        searchCache.clear();
    }
}
