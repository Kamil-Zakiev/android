package com.example.vkwall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VkPost[] posts = null;
        try {
            String jsonStr = loadJSONFromAsset();
            JSONArray jsonarray = new JSONArray(jsonStr);
            posts = new VkPost[jsonarray.length()];
            for (int i = 0; i < jsonarray.length(); i++) {
                posts[i] = VkPost.ByJson(jsonarray.getJSONObject(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView vkWallRv = findViewById(R.id.vk_wall_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        vkWallRv.setLayoutManager(layoutManager);

        VkPostsAdapter adapter = new VkPostsAdapter(posts);
        vkWallRv.setAdapter(adapter);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("vk_posts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
