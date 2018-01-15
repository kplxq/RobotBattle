package com.kxd.code.competition.entity.robotsee;

import com.kxd.code.competition.constants.MoveActionCommandEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangnx on 2017/12/19.
 */
public class RobotSeeMazeEntity extends AbstractRobotSeeEntity {

    public Boolean                             isFinal;

    public Map<MoveActionCommandEnum, Integer> canMoveStepSituation = new HashMap<>();

}
