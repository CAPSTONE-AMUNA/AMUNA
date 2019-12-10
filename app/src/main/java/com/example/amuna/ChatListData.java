package com.example.amuna;

public class ChatListData{
    private String member_RoomKey;
    private long member_Date;
    private String member_Lastmessage;

    public ChatListData(){}

    public long getMember_Date() {
        return member_Date;
    }

    public void setMember_Date(long member_Date) {
        this.member_Date = member_Date;
    }

    public String getMember_Lastmessage() {
        return member_Lastmessage;
    }

    public void setMember_Lastmessage(String member_Lastmessage) {
        this.member_Lastmessage = member_Lastmessage;
    }




    public String getMember_RoomKey() {
        return member_RoomKey;
    }

    public void setMember_RoomKey(String member_RoomKey) {
        this.member_RoomKey = member_RoomKey;
    }



    public ChatListData(String member_RoomKey, String member_Lastmessage, long member_Date) {
        this.member_RoomKey = member_RoomKey;
        this.member_Lastmessage = member_Lastmessage;
        this.member_Date=member_Date;
    }

}
