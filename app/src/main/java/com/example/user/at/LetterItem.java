package com.example.user.at;

public class LetterItem {
    public String sentUser, receivedUser, letterTitle, letterTime, letterId;

    public LetterItem(String sentUser, String receivedUser, String letterTitle, String letterTime, String letterId) {
        this.sentUser = sentUser;
        this.receivedUser = receivedUser;
        this.letterTitle = letterTitle;
        this.letterTime = letterTime;
        this.letterId = letterId;
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
}
