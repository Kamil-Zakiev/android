package com.example.vkwall;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class VkPost  implements Parcelable {
    private long _id;
    private String _avatarUrl;
    private String _userName;

    private VkPost(Parcel in) {
        _id = in.readLong();
        _avatarUrl = in.readString();
        _userName = in.readString();
        _date = in.readString();
        _text = in.readString();
        _imgUrl = in.readString();
        _isUserLike = in.readByte() != 0;
        _likesCount = in.readInt();
        _commentsCount = in.readInt();
        _sharesCount = in.readInt();
    }

    public static final Creator<VkPost> CREATOR = new Creator<VkPost>() {
        @Override
        public VkPost createFromParcel(Parcel in) {
            return new VkPost(in);
        }

        @Override
        public VkPost[] newArray(int size) {
            return new VkPost[size];
        }
    };

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

            return new VkPost(id, avatarUrl, username, new Date(postDate), text.equals("null") ? null : text, img.equals("null") ? null : img, isUserLike, likesCount, commentsCount, sharesCount);
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

    public String getDate() {

        return _date;
    }

    public String getText() {
        return _text;
    }

    public boolean hasText(){
        return _text != null;
    }

    public String getImgUrl() {
        return _imgUrl;
    }

    public boolean hasImage(){
        return _imgUrl != null;
    }

    public boolean isUserLike() {
        return _isUserLike;
    }

    public String getLikesCount() {
        return String.valueOf(_likesCount);
    }

    public String getCommentsCount() {
        return String.valueOf(_commentsCount);
    }

    public String getSharesCount() {
        return String.valueOf(_sharesCount);
    }

    public void Like(){
        if (isUserLike()){
            _likesCount--;
        } else {
            _likesCount++;
        }

        _isUserLike = !isUserLike();
    }

    private String _date;
    private String _text;
    private String _imgUrl;
    private boolean _isUserLike;
    private int _likesCount;
    private int _commentsCount;
    private int _sharesCount;

    private static java.text.SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    public VkPost(long id, String avatarUrl, String userName, Date date, String text, String imgUrl, boolean isUserLike, int likesCount, int commentsCount, int sharesCount){
        _id = id;
        _avatarUrl = avatarUrl;
        _userName = userName;

        long daysDiff = TimeUnit.DAYS.convert((new Date().getTime()- date.getTime()), TimeUnit.MILLISECONDS);
        if(daysDiff > 7){
            _date = SimpleDateFormat.format(date);
        } else {
            // todo: move to resources...
            _date = daysDiff + " дней назад";
        }

        _imgUrl = imgUrl;
        _isUserLike = isUserLike;
        _likesCount = likesCount;
        _commentsCount = commentsCount;
        _sharesCount = sharesCount;
        _text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(_avatarUrl);
        dest.writeString(_userName);
        dest.writeString(_date);
        dest.writeString(_text);
        dest.writeString(_imgUrl);
        dest.writeByte((byte) (_isUserLike ? 1 : 0));
        dest.writeInt(_likesCount);
        dest.writeInt(_commentsCount);
        dest.writeInt(_sharesCount);
    }
}
