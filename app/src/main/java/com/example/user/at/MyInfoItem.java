package com.example.user.at;

public class MyInfoItem {

    public String times,titles,writers,feedbacks;

    public MyInfoItem(String time,String title,String writer,String feed){
        times=time;
        titles="제목: "+title;
        writers="작성자: "+writer;
        feedbacks="피드백("+feed+")";
    }

    public String getTimes(){
        return times;
    }
    public String getTitles(){
        return titles;
    }
    public String getWriters(){
        return writers;
    }
    public String getFeedbacks(){
        return feedbacks;
    }

}
