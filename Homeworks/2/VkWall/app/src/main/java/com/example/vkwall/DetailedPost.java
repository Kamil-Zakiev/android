package com.example.vkwall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

public class DetailedPost extends AppCompatActivity {

    public static void start(Context context, VkPost post) {
        Intent starter = new Intent(context, DetailedPost.class);
        starter.putExtra(DetailedPost.class.getCanonicalName(), post);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);

        VkPost post = getIntent().getParcelableExtra(DetailedPost.class.getCanonicalName());
        VkPostViewHolder hackHolder = new VkPostViewHolder(findViewById(R.id.det_container));
        hackHolder.SetData(post);
    }
}
