package com.kxd.code.competition.robot.entity;

import com.kxd.code.competition.constants.ElementTypeEnum;
import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.element.AbstractElement;

import java.awt.*;

/**
 * Created by huangnx on 2017/12/22.
 */
public abstract class AbstractRobotBaseInfo extends AbstractElement {

    /**
     * 名字
     */
    public final String name;

    public Color        color;

    public Integer      currentCanStepNum = 1;

    /**
     * 机器人所在位置
     */
    public Location     currentLocation;

    /**
     * 异常数量
     */
    public Integer      exceptionNum      = 0;

    public AbstractRobotBaseInfo(String name, Color color, Location currentLocation) {
        super(ElementTypeEnum.ROBOT_INFO);
        this.name = name;
        this.color = color;
        this.currentLocation = currentLocation;
    }

    public void occurException() {
        this.exceptionNum++;
    }

}
