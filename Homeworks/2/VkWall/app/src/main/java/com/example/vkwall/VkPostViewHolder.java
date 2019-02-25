package com.example.vkwall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public final class VkPostViewHolder extends RecyclerView.ViewHolder {
    private TextView postAuthorTw;
    private TextView postTextTw;
    private TextView postDate;
    private ImageView postImage;
    private TextView likesCount;
    private TextView commentsCount;
    private TextView sharesCount;
    private ImageView heartImage;
    private ImageView avatar;
    private Context context;
    private LinearLayout heartPanel;

    public VkPostViewHolder(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.avatar);
        postAuthorTw = itemView.findViewById(R.id.post_author);
        postTextTw = itemView.findViewById(R.id.post_text);
        postDate = itemView.findViewById(R.id.post_date);
        postImage = itemView.findViewById(R.id.post_img);
        likesCount = itemView.findViewById(R.id.likes_count);
        commentsCount = itemView.findViewById(R.id.comments_count);
        sharesCount = itemView.findViewById(R.id.shares_count);
        heartImage = itemView.findViewById(R.id.heart);
        heartPanel = itemView.findViewById(R.id.likesArea);

        context = itemView.getContext();
    }

    public View GetHeartPanel(){
        return heartPanel;
    }

    public View GetTextView(){
        return postTextTw;
    }

    public View GetIngView(){
        return postImage;
    }

    private static CircleTransform CircleTransform = new CircleTransform();
    public void SetData(VkPost post) {
        Picasso.get().load(post.getAvatarUrl()).transform(VkPostViewHolder.CircleTransform).into(avatar);
        postAuthorTw.setText(post.getUserName());
        postDate.setText(post.getDate());

        if (post.hasText()) {
            postTextTw.setVisibility(View.VISIBLE);
            postTextTw.setText(post.getText());
        } else {
            postTextTw.setVisibility(View.GONE);
        }

        if (post.hasImage()) {
            postImage.setVisibility(View.VISIBLE);
            Picasso.get().load(post.getImgUrl()).into(postImage);
        } else {
            postImage.setVisibility(View.GONE);
        }

        likesCount.setText(post.getLikesCount());
        commentsCount.setText(post.getCommentsCount());
        sharesCount.setText(post.getSharesCount());

        if (post.isUserLike()) {
            heartImage.setColorFilter(context.getResources().getColor(R.color.selfLiked));
        } else {
            heartImage.setColorFilter(context.getResources().getColor(R.color.standard));
        }
    }
}
