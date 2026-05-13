package com.example.sandbox.response;

public class BaseMetaData {

    private int code = 200;
    private boolean success = true;
    private String errorMessage;

    public BaseMetaData() {
    }

    public BaseMetaData(int code, boolean success) {
        this.code = code;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}