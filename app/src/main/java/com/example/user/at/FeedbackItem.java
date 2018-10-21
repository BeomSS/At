package com.example.user.at;

public class FeedbackItem {

    public String feedbackId, feedbacktime, writer, contents, likes;

    public FeedbackItem(String getId, String getWriter, String getContents, String getTime, String getLikes) {
        feedbackId = getId;
        feedbacktime = getTime;
        writer = getWriter;
        contents = getContents;
        likes = getLikes;
    }
}
