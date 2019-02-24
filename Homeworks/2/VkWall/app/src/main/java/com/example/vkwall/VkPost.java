package com.example.vkwall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class VkPost {
    private long _id;
    private String _avatarUrl;
    private String _userName;

    public static VkPost ByJson(JSONObject jsonobject) {
        try {
            long id = jsonobject.getLong("id");
            String avatarUrl = jsonobject.getString("avatar_url");
            String username = jsonobject.getString("username");
            long postDate = jsonobject.getLong("post_date")*1000;
            String text = jsonobject.getString("post_text");
            String img = jsonobject.getString("post_image");
            boolean isUserLike = jsonobject.getBoolean("is_user_like");
            int likesCount = jsonobject.getInt("likes_count");
            int commentsCount = jsonobject.getInt("comments_count");
            int sharesCount = jsonobject.getInt("shares_count");

            return new VkPost(id, avatarUrl, username, new Date(postDate), text, img, isUserLike, likesCount, commentsCount, sharesCount);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAvatarUrl() {
        return _avatarUrl;
    }

    public String getUserName() {
        return _userName;
    }

    public Date getDate() {
        return _date;
    }

    public String getText() {
        return _text;
    }

    public String getImgUrl() {
        return _imgUrl;
    }

    public boolean isUserLike() {
        return _isUserLike;
    }

    public int getLikesCount() {
        return _likesCount;
    }

    public int getCommentsCount() {
        return _commentsCount;
    }

    public int getSharesCount() {
        return _sharesCount;
    }

    public void Like(){
        if (isUserLike()){
            _likesCount--;
        } else {
            _likesCount++;
        }

        _isUserLike = !isUserLike();
    }

    private Date _date;
    private String _text;
    private String _imgUrl;
    private boolean _isUserLike;
    private int _likesCount;
    private int _commentsCount;
    private int _sharesCount;

    public VkPost(long id, String avatarUrl, String userName, Date date, String text, String imgUrl, boolean isUserLike, int likesCount, int commentsCount, int sharesCount){
        _id = id;
        _avatarUrl = avatarUrl;
        _userName = userName;
        _date = date;
        _imgUrl = imgUrl;
        _isUserLike = isUserLike;
        _likesCount = likesCount;
        _commentsCount = commentsCount;
        _sharesCount = sharesCount;
        _text = text;
    }
}
