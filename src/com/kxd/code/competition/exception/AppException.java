package com.kxd.code.competition.exception;

/**
 * Created by huangnx on 2017/12/26.
 */
public class AppException extends RuntimeException {

    private String    code;

    private String    msg;

    private Exception orgException;

    public AppException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AppException(String code, String msg, Exception e) {
        this.code = code;
        this.msg = msg;
        this.orgException = e;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Exception getOrgException() {
        return orgException;
    }

    public void setOrgException(Exception orgException) {
        this.orgException = orgException;
    }
}
