package com.example.vkwall;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public final class VkPostsAdapter extends RecyclerView.Adapter<VkPostViewHolder> {
    private VkPost[] posts;
    private MainActivity mainActivity;

    public VkPostsAdapter(VkPost[] posts, MainActivity mainActivity) {
        this.posts = posts;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public VkPostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vk_post_layout, viewGroup, false);
        return new VkPostViewHolder(view);
    }

    public void LikePostAtIndex(int i){
        posts[i].Like();
        this.notifyItemChanged(i);
    }

    @Override
    public void onBindViewHolder(@NonNull VkPostViewHolder vkPostViewHolder, int position) {
        vkPostViewHolder.SetData(posts[position]);

        final int i = position;
        vkPostViewHolder.GetHeartPanel().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LikePostAtIndex(i);
                    }
                });

        vkPostViewHolder.GetTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startSecondActivity(posts[i], i);
            }
        });

        vkPostViewHolder.GetIngView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startSecondActivity(posts[i], i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.length;
    }
}
