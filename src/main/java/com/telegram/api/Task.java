package com.telegram.api;

public class Task {



    private String id = "";
    private String msg = "";
    private String status = "";

    public Task(){
    }

    public Task(String id, String msg, String status) {
        this.id = id;
        this.msg = msg;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
