package com.kxd.code.competition.battle;

import com.kxd.code.competition.constants.CommonConstant;
import com.kxd.code.competition.draw.jpanel.AbstractRobotBattleDrawJpanel;
import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.action.CommonMoveAction;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.entity.lattice.MazeLattice;
import com.kxd.code.competition.entity.mapinfo.MazeInfo;
import com.kxd.code.competition.entity.robotsee.RobotSeeMazeEntity;
import com.kxd.code.competition.map.AbstractMap;
import com.kxd.code.competition.map.Maze;
import com.kxd.code.competition.robot.AbstractRobot;
import com.kxd.code.competition.robot.entity.MazeRobotBaseInfo;
import com.kxd.code.competition.utils.CommonUtil;

import java.awt.*;

/**
 * Created by huangnx on 2017/12/18.
 */
public class RobotMazeBattle extends AbstractRobotBattle {

    private MazeInfo mazeInfo;

    public RobotMazeBattle(AbstractMap map, AbstractElementsData mazeElementData,
            AbstractRobotBattleDrawJpanel robotBattleDrawJpanel, AbstractGameControlConfig gameControlConfig) {
        super(map, mazeElementData, robotBattleDrawJpanel, gameControlConfig);
        mazeInfo = new MazeInfo(map.getSize(), new Location(map.getSize() - 1, map.getSize() - 1));
    }

    @Override
    public void reInit() {
        // 初始化地图
        Maze maze = (Maze) this.map;
        maze.initMap();

        // 初始化机器人位置、颜色
        for (AbstractRobot robot : elementData.robots) {
            Integer[] rgb = CommonUtil.getRandColorCode();
            elementData.robotBaseInfoMap.put(robot.getName(),
                    new MazeRobotBaseInfo(robot.getName(), new Color(rgb[0], rgb[1], rgb[2]), new Location(0, 0)));

            RobotSeeMazeEntity robotSeeMazeEntity = new RobotSeeMazeEntity();
            robotSeeMazeEntity.robotBaseInfo = (MazeRobotBaseInfo) elementData.robotBaseInfoMap.get(robot.getName());
            robotSeeMazeEntity.mapInfo = mazeInfo.clone();

            robot.noticeRobot(robotSeeMazeEntity);
        }
    }

    /**
     * 构造机器人当前可移动的情况
     * @param robot
     * @return
     */
    @Override
    protected RobotSeeMazeEntity constructRobotSeeMazeSituation(AbstractRobot robot) {
        MazeLattice[][] map = (MazeLattice[][]) this.map.getMap();

        RobotSeeMazeEntity robotSeeMazeSituationEntity = new RobotSeeMazeEntity();
        robotSeeMazeSituationEntity.mapInfo = mazeInfo;
        robotSeeMazeSituationEntity.robotBaseInfo = (MazeRobotBaseInfo) elementData.robotBaseInfoMap
                .get(robot.getName()).clone();

        int currentX = elementData.robotBaseInfoMap.get(robot.getName()).currentLocation.x;
        int currentY = elementData.robotBaseInfoMap.get(robot.getName()).currentLocation.y;
        if (currentX == map.length - 1 && currentY == map.length - 1) {
            robotSeeMazeSituationEntity.isFinal = true;
        }

        // 计算机器人当前可移动情况、可见范围内终点位置
        for (int i = 0; i <= 3; i++) {
            int xt = currentX, yt = currentY;
            int moveStep = 0;
            while (true) {
                int xo = xt, yo = yt;

                xt += CommonConstant.directXY.get(i);
                yt += CommonConstant.directXY.get(i + 1);

                if (!CommonUtil.judgeIsOutOfArray(map, xt, yt)) {
                    MazeLattice currentLattice = (MazeLattice) this.map.getMap()[xo][yo];
                    MazeLattice targetLattice = (MazeLattice) this.map.getMap()[xt][yt];
                    if ((currentLattice.getFather() == targetLattice || targetLattice.getFather() == currentLattice)) {
                        moveStep++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            robotSeeMazeSituationEntity.canMoveStepSituation.put(CommonConstant.directCodes.get(i), moveStep);
        }

        return robotSeeMazeSituationEntity;
    }

    @Override
    protected void doBeforeRound(Integer currentRound) {

    }

    /**
     * 执行机器人的指令
     * @param mazeRobot
     * @param action
     */
    @Override
    protected void doMoveAction(AbstractRobot mazeRobot, CommonMoveAction action) {
        if (null != action && null != action.getActionCommand()) {
            RobotSeeMazeEntity robotSeeMazeSituationEntity = constructRobotSeeMazeSituation(mazeRobot);
            Integer stepNum = robotSeeMazeSituationEntity.canMoveStepSituation.get(action.getActionCommand());
            if (stepNum > 0) {
                Location currentLocation = elementData.robotBaseInfoMap.get(mazeRobot.getName()).currentLocation;
                int index = CommonConstant.directCodes.indexOf(action.getActionCommand());

                elementData.robotBaseInfoMap.get(mazeRobot.getName()).currentLocation = new Location(
                        currentLocation.x + CommonConstant.directXY.get(index),
                        currentLocation.y + +CommonConstant.directXY.get(index + 1));
            }
        } else {
            // TODO 异常处理
        }
    }

    @Override
    protected Boolean isFinish(Integer currentRound) {
        return null;
    }
}
