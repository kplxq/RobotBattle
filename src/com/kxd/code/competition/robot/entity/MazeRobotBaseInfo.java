package com.kxd.code.competition.robot.entity;

import com.kxd.code.competition.entity.Location;

import java.awt.*;

/**
 * Created by huangnx on 2017/12/22.
 */
public class MazeRobotBaseInfo extends AbstractRobotBaseInfo {

    public MazeRobotBaseInfo(String name, Color color, Location currentLocation) {
        super(name, color, currentLocation);
    }

    @Override
    public MazeRobotBaseInfo clone() {
        MazeRobotBaseInfo mazeRobotBaseInfo = new MazeRobotBaseInfo(this.name, this.color,
                this.currentLocation.clone());
        return mazeRobotBaseInfo;
    }
}
