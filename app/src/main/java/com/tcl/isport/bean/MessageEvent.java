package com.tcl.isport.bean;

/**
 * Created by lishui.lin on 17-9-25 14:15
 */

public class MessageEvent {

    private String message;

    public MessageEvent(String message) {
         this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
