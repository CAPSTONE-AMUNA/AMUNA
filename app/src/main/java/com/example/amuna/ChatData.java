package com.example.amuna;

import android.graphics.Bitmap;

public class ChatData {
    private String member_MyID;
    private String member_ChattingContent;

    public ChatData(){}


    public String getMember_MyID() {
        return member_MyID;
    }

    public void setMember_MyID(String member_MyID) {
        this.member_MyID = member_MyID;
    }


    public String getMember_ChattingContent() {
        return member_ChattingContent;
    }

    public void setMember_ChattingContent(String member_ChattingContent) {
        this.member_ChattingContent = member_ChattingContent;
    }


    public ChatData(String member_MyID, String member_ChattingContent) {
        this.member_MyID = member_MyID;
        this.member_ChattingContent = member_ChattingContent;
    }
}
