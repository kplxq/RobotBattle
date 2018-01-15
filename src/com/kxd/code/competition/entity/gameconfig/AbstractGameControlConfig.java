package com.kxd.code.competition.entity.gameconfig;

/**
 * Created by huangnx on 2017/12/20.
 */
public abstract class AbstractGameControlConfig {

    public final String  gameTitle;

    public final Integer roundNum;

    public Integer       currentRoundNum  = 0;

    public boolean       gameStart        = false;

    public boolean       currentGameFinal = false;

    public Long          gameThreadSleepTime;

    public AbstractGameControlConfig(String gameTitle, Integer roundNum, Long gameThreadSleepTime) {
        this.gameTitle = gameTitle;
        this.roundNum = roundNum;
        this.gameThreadSleepTime = gameThreadSleepTime;
    }

}
