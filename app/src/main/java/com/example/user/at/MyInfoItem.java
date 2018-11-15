package com.example.user.at;

public class MyInfoItem {

    //게시판flag0 ,내가쓴글=flag1
    int flag = 0;
    public String num, category, times, titles, writers, feedbacks, recommends;

    //관심작품
    public MyInfoItem(String postId, String time, String title, String writer, String feed, String cate) {//내가 쓴 글
        num=postId;
        if (cate.equals("0")) {
            category = "글 ";
        } else if (cate.equals("1")) {
            category = "그림 ";
        } else if (cate.equals("2")) {
            category = "음악 ";
        } else {
            category = cate;
        }
        times = time;
        titles = "제목: " + title;
        writers = "작성자: " + writer;
        feedbacks = "피드백(" + feed + ")";
    }

    public MyInfoItem(int fl, String idnum, String cate, String time, String title, String writer, String feed, String recommend) { //게시판
        flag = fl;
        switch (flag) {
            case 0: //게시판
                num=idnum;
                times = time;
                titles = title;
                writers = "작성자: " + writer;
                feedbacks = "피드백(" + feed + ")";
                recommends = "추천수(" + recommend + ")";
                break;
            case 1: //내가쓴 게시물
                num = idnum;
                if (cate.equals("0")) {
                    category = "글 ";
                } else if (cate.equals("1")) {
                    category = "그림 ";
                } else if (cate.equals("2")) {
                    category = "음악 ";
                } else {
                    category = cate;
                }
                times = time;
                titles = title;
                feedbacks = "피드백(" + feed + ")";
                recommends = "추천수(" + recommend + ")";
                break;
            case 2: //내가쓴 피드백
                num = idnum;
                times = time;
                titles = title;
                recommends = "추천수(" + recommend + ")";
                break;
            case 3: //알림
                num = feed;
                times = time;
                category=cate;
                if (cate.equals("1")) {//쪽지받았을 때
                    titles = title;
                } else if (cate.equals("2")) {//추천받았을 때
                    titles = title;
                } else if (cate.equals("3")) {//피드백 써줬을 때
                    titles = title;
                }
                break;
        }

    }

    public String getCategory() {
        return category;
    }

    public String getTimes() {
        return times;
    }

    public String getTitles() {
        return titles;
    }

    public String getWriters() {
        return writers;
    }

    public String getFeedbacks() {
        return feedbacks;
    }

    public String getRecommends() {
        return recommends;
    }

}
