package com.kxd.code.competition.constants;

/**
 * Created by huangnx on 2017/12/19.
 */
public enum MoveActionCommandEnum implements CodeEnum {
    MOVE_TOP("1"), MOVE_RIGHT("2"), MOVE_DOWN("3"), MOVE_LEFT("4");

    private String code;

    MoveActionCommandEnum(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
