package com.example.user.at;

public class LetterItem {
    public String sentUser, receivedUser, letterTitle, letterTime, letterId,letterContent;
    public int flag;

    public LetterItem(int flag, String user, String letterTitle, String letterTime, String letterId,String content) {
        this.flag=flag;
        this.sentUser = user;
        this.letterTitle = letterTitle;
        this.letterTime = letterTime;
        this.letterId = letterId;
        this.letterContent=content;
    }

    String getSentUser() {
        return sentUser;
    }

    String getReceivedUser() {
        return receivedUser;
    }

    String getLetterTitle(){
        return letterTitle;
    }

    String getLetterTime() {
        return letterTime;
    }

    String getLetterId() {
        return letterId;
    }

    String getLetterContent(){return letterContent;}
}
