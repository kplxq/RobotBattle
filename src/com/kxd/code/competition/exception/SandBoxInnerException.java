package com.kxd.code.competition.exception;

/**
 * Created by huangnx on 2017/12/26.
 */
public class SandBoxInnerException extends AppException {

    public SandBoxInnerException(String code, String msg) {
        super(code, msg);
    }

    public SandBoxInnerException(String code, String msg, Exception e) {
        super(code, msg, e);
    }
}
