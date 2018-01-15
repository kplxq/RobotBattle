package com.kxd.code.competition.draw.element.maze;

import com.kxd.code.competition.draw.element.AbstractMapElementDraw;
import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.robot.AbstractRobot;
import com.kxd.code.competition.robot.entity.MazeRobotBaseInfo;

/**
 * Created by huangnx on 2017/12/19.
 */
public class MazeMapElementDraw extends AbstractMapElementDraw {

    public MazeMapElementDraw(DrawArea drawArea, int width, AbstractElementsData elementData) {
        super(drawArea, width, elementData);
    }

    /**
     * 画机器人所在位置
     */
    @Override
    public void paint() {
        for (AbstractRobot abstractMazeRobot : elementData.robots) {
            MazeRobotBaseInfo mazeRobotBaseInfo = (MazeRobotBaseInfo) elementData.robotBaseInfoMap
                    .get(abstractMazeRobot.getName());

            g.setColor(mazeRobotBaseInfo.color);
            g.fillOval(getCenterX(mazeRobotBaseInfo.currentLocation.y) - width / 3,
                    getCenterY(mazeRobotBaseInfo.currentLocation.x) - width / 3, width / 2, width / 2);
        }
    }

}
