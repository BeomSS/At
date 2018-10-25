package com.example.user.at;

public class LetterItem {
    public String sentUser, receivedUser, letterTitle, letterTime, letterId,letterContent;

    public LetterItem(String sentUser, String receivedUser, String letterTitle, String letterTime, String letterId,String content) {
        this.sentUser = sentUser;
        this.receivedUser = receivedUser;
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
