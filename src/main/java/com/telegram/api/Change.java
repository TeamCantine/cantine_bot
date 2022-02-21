package com.telegram.api;

public class Change {

private String idChange = "";
private String taskId = "";
private String fieldCode = "";
private String oldValue = "";
private String newValue = "";

public Change(){}


    public Change(String idChange, String taskId, String fieldCode, String oldValue, String newValue) {
        this.idChange = idChange;
        this.taskId = taskId;
        this.fieldCode = fieldCode;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getIdChange() {
        return idChange;
    }

    public void setIdChange(String idChange) {
        this.idChange = idChange;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
