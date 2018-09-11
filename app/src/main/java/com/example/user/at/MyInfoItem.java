package com.example.user.at;

public class MyInfoItem {

    public String times,titles,writers,feedbacks,recommends;

    public MyInfoItem(String time,String title,String writer,String feed){//내가 쓴 글
        times=time;
        titles="제목: "+title;
        writers="작성자: "+writer;
        feedbacks="피드백("+feed+")";
    }

    public MyInfoItem(String time,String title,String writer,String feed,String recommend){ //게시판
        times=time;
        titles=title;
        writers="작성자: "+writer;
        feedbacks="피드백("+feed+")";
        recommends="추천수("+recommend+")";
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
    public String getRecommends(){ return recommends; }

}
