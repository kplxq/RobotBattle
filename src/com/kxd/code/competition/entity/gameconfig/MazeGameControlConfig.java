package com.kxd.code.competition.entity.gameconfig;

/**
 * Created by huangnx on 2017/12/20.
 */
public class MazeGameControlConfig extends AbstractGameControlConfig {

    public MazeGameControlConfig(Integer roundNum, Long threadSleepTime) {
        super("迷宫大逃亡（空格：开启关闭最优路径；S：start；R：stop）", roundNum, threadSleepTime);
    }

    private boolean drawPath = false;

    public boolean isDrawPath() {
        return drawPath;
    }

    public synchronized void setDrawPath(boolean drawPath) {
        this.drawPath = drawPath;
    }

}
