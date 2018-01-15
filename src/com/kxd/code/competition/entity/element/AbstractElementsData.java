package com.kxd.code.competition.entity.element;

import com.kxd.code.competition.entity.AttractRecord;
import com.kxd.code.competition.robot.AbstractRobot;
import com.kxd.code.competition.robot.entity.AbstractRobotBaseInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by huangnx on 2017/12/21.
 */
public abstract class AbstractElementsData {

    public List<AbstractRobot>                robots;

    public Map<String, AbstractRobotBaseInfo> robotBaseInfoMap;

    public List<AttractRecord>                attractRecords = new ArrayList<>();

    public void setRobotDatas(List<AbstractRobot> robots, Map<String, AbstractRobotBaseInfo> robotBaseInfoMap) {
        this.robots = new CopyOnWriteArrayList(robots);
        this.robotBaseInfoMap = Collections.synchronizedMap(robotBaseInfoMap);
    }
}
