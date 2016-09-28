package com.klm.testcase.Tasks;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.klm.testcase.Objects.News;
import com.klm.testcase.Objects.Weather;
import com.klm.testcase.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Heitor Zanetti
 * 11/03/2016
 */
public class GetDestinationNews {
    @BindString(R.string.news_error) String REQUEST_ERROR;

    private final String ALCHEMY_BASE_URL = "https://access.alchemyapi.com/calls/data/GetNews?apikey=";
    private final String ALCHEMY_PART1_URL = "&return=enriched.url.title,enriched.url.url,enriched.url.author&start=1457049600&end=1457737200&q.enriched.url.cleanedTitle=";
    private final String ALCHEMY_PART2_URL = "&q.enriched.url.enrichedTitle.docSentiment.type=positive&q.enriched.url.enrichedTitle.taxonomy.taxonomy_.label=art%20and%20entertainment&count=25&outputMode=json";
    private final String ALCHEMY_API_KEY = "94a39a29a15bf9a1604200617ad61389b19eda11";

    Activity c;
    String destination;

    LoadingDestinationNews listener;

    public GetDestinationNews(Activity c, String destination) {
        this.c = c;
        this.destination = destination.substring(0, destination.indexOf(","));
        listener = (LoadingDestinationNews)  c;
        ButterKnife.bind(this, c);
    }

    public interface LoadingDestinationNews {
        void onLoadNewsCompleted(ArrayList<News> news);
    }

    public void request(){

        ServerHelper.get(ALCHEMY_BASE_URL + ALCHEMY_API_KEY + ALCHEMY_PART1_URL + destination + ALCHEMY_PART2_URL, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONObject result = response.getJSONObject("result");
                    JSONArray docs = result.getJSONArray("docs");

                    ArrayList<News> news = new ArrayList<>();
                    String title;
                    String author;
                    String url;

                    for (int i = 0; i < docs.length(); i++){
                        JSONObject jsonurl = docs.getJSONObject(i).getJSONObject("source")
                                                              .getJSONObject("enriched")
                                                              .getJSONObject("url");

                        title = jsonurl.getString("title");
                        author = jsonurl.getString("author");
                        url = jsonurl.getString("url");

                        news.add(new News(title, author, url));
                        Log.d("News", news.get(i).getTitle());

                    }

                    if (listener != null){

                        listener.onLoadNewsCompleted(news);

                    }

                } catch (JSONException e) {
                    Log.d("News", e.getMessage());
                    Log.d("News", response.toString());
                    Toast.makeText(c, REQUEST_ERROR, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("News Fail", throwable.getMessage());
                Log.d("News Fail", errorResponse.toString());
                Toast.makeText(c, REQUEST_ERROR, Toast.LENGTH_SHORT).show();
            }

        });
    }
}
