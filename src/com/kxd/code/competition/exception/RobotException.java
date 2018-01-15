package com.kxd.code.competition.exception;

/**
 * Created by huangnx on 2017/12/26.
 */
public class RobotException extends AppException {
    private String robotName;

    public RobotException(String code, String msg, String robotName) {
        super(code, msg);
        this.robotName = robotName;
    }

    public RobotException(String code, String msg, Exception e, String robotName) {
        super(code, msg, e);
        this.robotName = robotName;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    @Override
    public String toString() {
        if (this.getOrgException() != null) {
            return "机器人：" + this.getRobotName() + ",发生异常。异常code：" + this.getCode() + "，异常msg：" + this.getMsg()
                    + "，原始异常信息:" + this.getOrgException().toString();
        } else {
            return "机器人：" + this.getRobotName() + ",发生异常。异常code：" + this.getCode() + "，异常msg：" + this.getMsg();
        }
    }
}
