package com.example.vkwall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    private static CircleTransform CircleTransform = new CircleTransform();
    private static SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    public void SetData(VkPost post) {

        Picasso.get().load(post.getAvatarUrl()).transform(VkPostViewHolder.CircleTransform).into(avatar);

        postAuthorTw.setText(post.getUserName());

        String date = null;
        long daysDiff = TimeUnit.DAYS.convert((new Date().getTime()- post.getDate().getTime()), TimeUnit.MILLISECONDS);
        if(daysDiff > 7){
            date = SimpleDateFormat.format(post.getDate());
        } else {
            // todo: move to resources...
            date = daysDiff + " дней назад";
        }

        postDate.setText(date);

        String text = post.getText();
        if (text == "null") {
            postTextTw.setVisibility(View.GONE);
        } else {
            postTextTw.setVisibility(View.VISIBLE);
            postTextTw.setText(post.getText());
        }

        String imgUrl = post.getImgUrl();
        if (imgUrl == "null") {
            postImage.setVisibility(View.GONE);
        } else {
            postImage.setVisibility(View.VISIBLE);
            Picasso.get().load(imgUrl).into(postImage);
        }

        likesCount.setText(String.valueOf(post.getLikesCount()));
        commentsCount.setText(String.valueOf(post.getCommentsCount()));
        sharesCount.setText(String.valueOf(post.getSharesCount()));

        if (post.isUserLike()) {
            heartImage.setColorFilter(context.getResources().getColor(R.color.selfLiked));
        } else {
            heartImage.setColorFilter(context.getResources().getColor(R.color.standard));
        }
    }
}
