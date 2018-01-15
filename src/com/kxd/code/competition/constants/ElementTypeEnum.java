package com.kxd.code.competition.constants;

/**
 * Created by huangnx on 2017/12/21.
 */
public enum ElementTypeEnum implements CodeEnum {

    LANDMINE("02"), BLOOD_BAG("03"), ROBOT_INFO("04");

    private String code;

    ElementTypeEnum(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
