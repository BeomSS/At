package com.example.user.at;

public class FeedbackItem {

    public String feedbackId, feedbacktime, writer, contents, likes;
    public Boolean f_liked;

    public FeedbackItem(String getId, String getWriter, String getContents, String getTime, String getLikes,Boolean feedbackliked) {
        feedbackId = getId;
        feedbacktime = getTime;
        writer = getWriter;
        contents = getContents;
        likes = getLikes;
        f_liked=feedbackliked;
    }
}
