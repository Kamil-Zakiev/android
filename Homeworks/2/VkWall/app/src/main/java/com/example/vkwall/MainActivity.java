package com.example.vkwall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static int REQUEST_CODE = 1;

    private VkPostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView vkWallRv = findViewById(R.id.vk_wall_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        vkWallRv.setLayoutManager(layoutManager);

        adapter = new VkPostsAdapter(getPostsFromJson(), this);
        vkWallRv.setAdapter(adapter);
    }

    public void startSecondActivity(VkPost post, int i){
        Intent intent = DetailedPost.prepareIntent(this, post, i);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode != REQUEST_CODE) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        boolean heartWasClicked = data.getBooleanExtra(DetailedPost.POST_HEART_WAS_CLICKED, false);
        if (!heartWasClicked) {
            return;
        }

        int index = data.getIntExtra(DetailedPost.POST_INDEX, -1);
        adapter.LikePostAtIndex(index);
    }

    private VkPost[] getPostsFromJson(){
        String recentDateEnding = getResources().getString(R.string.img_desc);
        VkPost[] posts = null;
        try {
            InputStream is = this.getAssets().open("vk_posts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonStr = new String(buffer, "UTF-8");
            JSONArray jsonarray = new JSONArray(jsonStr);
            posts = new VkPost[jsonarray.length()];
            for (int i = 0; i < jsonarray.length(); i++) {
                posts[i] = VkPost.ByJson(jsonarray.getJSONObject(i), recentDateEnding);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }
}
