package com.github.cybooo.dbl4j;

import okhttp3.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class DiscordBotsListAPI {

    private final String key;

    /**
     * Create an instance of DiscordBotsListAPI
     * @param key Your API key
     */
    public DiscordBotsListAPI(String key) {
        this.key = key;
    }

    /**
     * Posts your Server count and Shard count to the API
     *
     * Rate Limit: 1 Request / 5 minutes
     *
     * @param botId ID of your Bot
     * @param serverCount Server count
     * @param shardCount Shard count
     * @return response
     */
    public String postStats(String botId, int serverCount, int shardCount) {
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject();
        json.put("serverCount", serverCount);
        json.put("shardCount", shardCount);
        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url("https://api.discordbotslist.co/v1/public/bot/" + botId + "/stats").addHeader("Authorization", key).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.message();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This allows you to get most of the information about your bot.
     *
     * Response:
     * id | string
     * owners | string[]
     * votes | number
     * prefix | string
     * library | string
     * tags | string[]
     * description | string
     * overview | string
     * discordInvite | string | null
     * website | string | null
     * certified | boolean
     * promoted | boolean
     * vanity | string | null
     * github | string | null
     * donateUrl | string | null
     * serverCount | number | null
     * shardCount | number | null
     *
     * Error:
     * Status Code: 404
     *
     * Return: { error: true, response: 'API key does not match a bot.' }
     *
     * @param botId ID of your Bot
     * @return JSONObject containing your bot information
     * @throws IOException If you reach the rate-limit, or other things.
     */
    private JSONObject getBotInformation(String botId) throws IOException {
        URLConnection connection = new URL("https://api.discordbotslist.co/v1/public/bot/" + botId).openConnection();
        connection.setRequestProperty("Authorization", key);
        connection.setUseCaches(false);
        connection.connect();
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String jsonText = readAll(rd);
        return new JSONObject(jsonText);
    }

    private String readAll(Reader rd) throws IOException {
        var sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
