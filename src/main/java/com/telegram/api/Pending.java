package com.telegram.api;

public class Pending {

    private String idTel = "";
    private String taskId = "";
    private String operation = "";
    private String oldValue = "";

    public Pending(){
    }

    public Pending(String idTel, String taskId, String operation, String oldValue) {
        this.idTel = idTel;
        this.taskId = taskId;
        this.operation = operation;
        this.oldValue = oldValue;
    }


    public String getIdTel() {
        return idTel;
    }

    public void setIdTel(String idTel) {
        this.idTel = idTel;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
}
