package com.example.vkwall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public final class VkPostsAdapter extends RecyclerView.Adapter<VkPostViewHolder> {
    private VkPost[] posts;

    public VkPostsAdapter(VkPost[] posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public VkPostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vk_post_layout, viewGroup, false);
        return new VkPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VkPostViewHolder vkPostViewHolder, int position) {
        vkPostViewHolder.SetData(posts[position]);

        final VkPostsAdapter adapter = this;
        final int i = position;
        vkPostViewHolder.GetHeartPanel()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        posts[i].Like();
                        adapter.notifyItemChanged(i);
                    }
                });

        final Context context = vkPostViewHolder.GetContext();
        vkPostViewHolder.GetTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailedPost.start(context, posts[i]);
            }
        });

        vkPostViewHolder.GetIngView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailedPost.start(context, posts[i]);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.length;
    }
}
