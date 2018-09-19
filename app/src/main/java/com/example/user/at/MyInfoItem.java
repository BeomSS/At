package com.example.user.at;

public class MyInfoItem {

    //게시판flag0 ,내가쓴글=flag1
    int flag=0;
    public String category,times,titles,writers,feedbacks,recommends;

    //리사이클러 테스트용 생성자
    public MyInfoItem(String time,String title,String writer,String feed){//내가 쓴 글
        times=time;
        titles="제목: "+title;
        writers="작성자: "+writer;
        feedbacks="피드백("+feed+")";
    }

    public MyInfoItem(int fl,String cate,String time,String title,String writer,String feed,String recommend){ //게시판
        flag=fl;
        switch (flag){
            case 0:
                times=time;
                titles=title;
                writers="작성자: "+writer;
                feedbacks="피드백("+feed+")";
                recommends="추천수("+recommend+")";
                break;
            case 1:
                if(cate.equals("0")){
                    category="글   ";
                }else if(cate.equals("1")){
                    category="그림 ";
                }else if(cate.equals("2")){
                    category="음악 ";
                }else{
                    category=cate;
                }
                times=time;
                titles=title;
                feedbacks="피드백("+feed+")";
                recommends="추천수("+recommend+")";
                break;
        }

    }

    public String getCategory(){return category;}
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
