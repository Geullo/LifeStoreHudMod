package com.jonghyun.hud.networking;

public enum HudPacketList {
    GET( "00"),
    QUIZ_VISIBLE("01"),
    ;
    public String recogCode;
    HudPacketList(String recogCode){
        this.recogCode = recogCode;
    }
}
