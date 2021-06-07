package com.github.cybooo.dbl4j;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class DiscordBotsListAPI {

    private final String key;
    private final OkHttpClient client;

    /**
     * Create an instance of DiscordBotsListAPI
     * @param key Your API key
     */
    public DiscordBotsListAPI(String key) {
        this.key = key;
        client = new OkHttpClient.Builder()
                .build();
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
        var json = new JSONObject();
        json.put("serverCount", serverCount);
        json.put("shardCount", shardCount);
        var body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        var request = new Request.Builder()
                .url("https://api.discordbotslist.co/v1/public/bot/" + botId + "/stats")
                .addHeader("Authorization", key)
                .post(body)
                .build();
        try (var response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
     * Success:
     * Status Code: 200
     * Return: { error: false, response: {Response} }
     *
     * Error:
     * Status Code: 404
     * Return: { error: true, response: 'API key does not match a bot.' }
     *
     * @param botId ID of your Bot
     * @return JSONObject containing your bot information
     */
    public JSONObject getBotInformation(String botId) {
        return readJSON("https://api.discordbotslist.co/v1/public/bot/" + botId);
    }

    /**
     *
     * This allows you to get the reviews submitted about your bot.
     *
     * Response:
     * id | string
     * botId | string
     * reviewerId | string
     * positive | boolean
     * review | string
     * reply | string
     *
     * Success:
     * Status Code: 200
     * Return: { error: false, response: {Response[]} }
     *
     * Error:
     * Status Code: 404
     * Return: { error: true, response: 'API key does not match a bot.' }
     *
     * @param botId
     * @return
     */

    public JSONObject getReviews(String botId) {
        return readJSON("https://api.discordbotslist.co/v1/public/bot/" + botId + "/reviews");
    }

    private JSONObject readJSON(String url) {
        var request = new Request.Builder()
                .url(url)
                .header("Authorization", key)
                .get()
                .build();
        try (var response = client.newCall(request).execute()) {
            return new JSONObject(response.body().string());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
