package com.kxd.code.competition.entity.gameconfig;

/**
 * Created by huangnx on 2017/12/21.
 */
public class FightGameControlConfig extends AbstractGameControlConfig {

    public final Integer robotInitBloodNum;

    public final Integer bloodBagBloodNum;

    public final Integer landmineBloodNum;

    public final Integer turnRoundNum;

    public FightGameControlConfig(Integer roundNum, Integer turnRoundNum, Long gameThreadSleepTime,
            Integer robotInitBloodNum, Integer bloodBagBloodNum, Integer landmineBloodNum) {
        super("开普勒星球杯-机器人大作战", roundNum, gameThreadSleepTime);
        this.robotInitBloodNum = robotInitBloodNum;
        this.turnRoundNum = turnRoundNum;
        this.bloodBagBloodNum = bloodBagBloodNum;
        this.landmineBloodNum = landmineBloodNum;
    }
}
