package com.kxd.code.competition.robot;

import com.kxd.code.competition.entity.action.CommonMoveAction;
import com.kxd.code.competition.entity.robotsee.AbstractRobotSeeEntity;

/**
 * Created by huangnx on 2017/12/18.
 */
public abstract class AbstractRobot {

    protected final String           name;

    /**
     * 机器人当前可见情况
     */
    protected AbstractRobotSeeEntity currentSeeMazeSituation;

    /**
     * 通知robot当前它的可见情况
     */
    public final void noticeRobot(final AbstractRobotSeeEntity currentSeeMazeSituation) {
        this.currentSeeMazeSituation = currentSeeMazeSituation;
    }

    public AbstractRobot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 获取下一个指令
     * @return
     */
    public abstract CommonMoveAction getNextAction();

}
