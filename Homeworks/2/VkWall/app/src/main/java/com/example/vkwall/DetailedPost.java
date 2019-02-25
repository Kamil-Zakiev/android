package com.example.vkwall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DetailedPost extends AppCompatActivity {
    private static String POST_KEY = "com.example.vkwall.post";
    public static String POST_INDEX = "com.example.vkwall.index";
    public static String POST_HEART_WAS_CLICKED = "com.example.vkwall.heartWasClicked";

    public static Intent prepareIntent(Context context, VkPost post, int i){
        Intent intent = new Intent(context, DetailedPost.class);
        intent.putExtra(POST_KEY, post);
        intent.putExtra(POST_INDEX, i);
        return intent;
    }

    private int index;
    private boolean heartWasClicked = false;
    VkPost post;
    VkPostViewHolder hackHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);

        post = getIntent().getParcelableExtra(POST_KEY);
        hackHolder = new VkPostViewHolder(findViewById(R.id.det_container));
        hackHolder.SetData(post);

        hackHolder.GetHeartPanel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.Like();
                heartWasClicked = !heartWasClicked;
                hackHolder.SetData(post);
            }
        });

        index = getIntent().getIntExtra(POST_INDEX, -1);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra(POST_HEART_WAS_CLICKED, heartWasClicked);
        intent.putExtra(POST_INDEX, index);
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }
}
