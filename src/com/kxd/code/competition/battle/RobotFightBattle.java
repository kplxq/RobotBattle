package com.kxd.code.competition.battle;

import com.kxd.code.competition.constants.CommonConstant;
import com.kxd.code.competition.constants.ElementTypeEnum;
import com.kxd.code.competition.constants.ErrorCodeDefine;
import com.kxd.code.competition.draw.jpanel.AbstractRobotBattleDrawJpanel;
import com.kxd.code.competition.entity.AttractRecord;
import com.kxd.code.competition.entity.ElementList;
import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.action.CommonMoveAction;
import com.kxd.code.competition.entity.element.AbstractElement;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.element.FightElementsData;
import com.kxd.code.competition.entity.element.child.BloodBag;
import com.kxd.code.competition.entity.element.child.Landmine;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.entity.gameconfig.FightGameControlConfig;
import com.kxd.code.competition.entity.mapinfo.FightMapInfo;
import com.kxd.code.competition.entity.robotsee.FightRobotSeeEntity;
import com.kxd.code.competition.exception.RobotException;
import com.kxd.code.competition.map.AbstractMap;
import com.kxd.code.competition.robot.AbstractRobot;
import com.kxd.code.competition.robot.entity.AbstractRobotBaseInfo;
import com.kxd.code.competition.robot.entity.FightRobotBaseInfo;
import com.kxd.code.competition.utils.CommonUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * Created by huangnx on 2017/12/21.
 */
public class RobotFightBattle extends AbstractRobotBattle {

    private final FightMapInfo fightMapInfo;

    private ElementList[][]    elementListsArray;

    private Boolean[][]        mapLatticeHasElement;

    public RobotFightBattle(AbstractMap map, AbstractElementsData fightElementData,
            AbstractRobotBattleDrawJpanel robotBattleDrawJpanel, AbstractGameControlConfig gameControlConfig) {
        super(map, fightElementData, robotBattleDrawJpanel, gameControlConfig);
        this.fightMapInfo = new FightMapInfo(this.map.getSize());
    }

    @Override
    protected FightRobotSeeEntity constructRobotSeeMazeSituation(AbstractRobot robot) {
        FightRobotSeeEntity fightRobotSeeEntity = new FightRobotSeeEntity();
        fightRobotSeeEntity.robotBaseInfo = (FightRobotBaseInfo) elementData.robotBaseInfoMap.get(robot.getName())
                .clone();
        fightRobotSeeEntity.mapInfo = fightMapInfo;

        ElementList[][] robotGetElementList = new ElementList[map.getSize()][map.getSize()];
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                robotGetElementList[i][j] = new ElementList();
                for (AbstractElement element : elementListsArray[i][j].elements) {
                    if (element.elementType == ElementTypeEnum.ROBOT_INFO) {
                        FightRobotBaseInfo fightRobotBaseInfo = (FightRobotBaseInfo) element;
                        if (fightRobotBaseInfo.bloodNum > 0) {
                            robotGetElementList[i][j].elements.add(fightRobotBaseInfo.clone());
                        }
                    } else {
                        robotGetElementList[i][j].elements.add(element.clone());
                    }
                }
            }
        }

        fightRobotSeeEntity.robotGetElementList = robotGetElementList;

        return fightRobotSeeEntity;
    }

    @Override
    protected void doBeforeRound(Integer currentRound) {
        FightGameControlConfig fightGameControlConfig = (FightGameControlConfig) gameControlConfig;
        if (currentRound % ((FightGameControlConfig) gameControlConfig).turnRoundNum == 0) {
            int turn = currentRound / ((FightGameControlConfig) gameControlConfig).turnRoundNum;

            FightElementsData fightElementData = (FightElementsData) elementData;

            // 增加地图元素：地雷、血包
            addMapElement(turn);

            constructElementListsArray();

            robotBattleDrawJpanel.repaint();

            try {
                Thread.sleep(500L);
            } catch (Exception e) {
                // TODO 处理异常
            }
        }
    }

    @Override
    protected Boolean isFinish(Integer currentRound) {
        FightElementsData fightElementsData = (FightElementsData) elementData;

        Integer onBattleRobotNum = 0;
        Set<String> keySet = fightElementsData.robotBaseInfoMap.keySet();
        for (String key : keySet) {
            FightRobotBaseInfo fightRobotBaseInfo = (FightRobotBaseInfo) fightElementsData.robotBaseInfoMap.get(key);
            if (fightRobotBaseInfo.bloodNum > 0) {
                onBattleRobotNum++;
            }
        }
        return onBattleRobotNum <= 1;
    }

    @Override
    protected void doMoveAction(AbstractRobot robot, CommonMoveAction action) {
        if (null != action && null != action.getActionCommand()) {
            FightElementsData fightElementsData = (FightElementsData) elementData;
            Location currentLocation = elementData.robotBaseInfoMap.get(robot.getName()).currentLocation;
            FightRobotBaseInfo fightRobotBaseInfo = (FightRobotBaseInfo) elementData.robotBaseInfoMap
                    .get(robot.getName());

            // 如果机器人已经阵亡，不在执行它的指令
            if (fightRobotBaseInfo.bloodNum <= 0) {
                return;
            }

            int index = CommonConstant.directCodes.indexOf(action.getActionCommand());
            int finalX = currentLocation.x + CommonConstant.directXY.get(index);
            int finalY = currentLocation.y + CommonConstant.directXY.get(index + 1);

            // 机器人的移动指令在地图边界内则执行；不在边界内则报异常
            if (!CommonUtil.judgeIsOutOfArray(map.getMap(), finalX, finalY)) {
                fightRobotBaseInfo.currentLocation = new Location(finalX, finalY);
            } else {
                System.out.println(finalX + "," + finalY);
                throw new RobotException(ErrorCodeDefine.OUT_BORDER_CODE, ErrorCodeDefine.OUT_BORDER_MSG,
                        fightRobotBaseInfo.name);
            }

            // 机器人移动后，触发情况判断，加 or 扣血
            ElementList elementList = elementListsArray[finalX][finalY];
            for (AbstractElement element : elementList.elements) {
                if (ElementTypeEnum.LANDMINE == element.elementType) {
                    fightRobotBaseInfo.stepOnLandmine((Landmine) element, gameControlConfig.currentRoundNum);
                    fightElementsData.landmines.remove(element);
                } else if (ElementTypeEnum.BLOOD_BAG == element.elementType) {
                    fightRobotBaseInfo.getBloodBag((BloodBag) element);
                    fightElementsData.bloodBags.remove(element);
                } else if (ElementTypeEnum.ROBOT_INFO == element.elementType) {
                    FightRobotBaseInfo targetRobotInfo = (FightRobotBaseInfo) element;
                    if (targetRobotInfo.bloodNum > 0) {
                        AttractRecord attractRecord = fightRobotBaseInfo.attractRobot(targetRobotInfo,
                                gameControlConfig.currentRoundNum);
                        fightElementsData.attractRecords.add(attractRecord);
                    }
                }
            }

            // 地图位置统计数据的刷新
            mapLatticeHasElement[finalX][finalY] = true;
            constructElementListsArray();
            if (elementListsArray[currentLocation.x][currentLocation.y].elements.isEmpty()) {
                mapLatticeHasElement[currentLocation.x][currentLocation.y] = false;
            }
        } else {
            throw new RobotException(ErrorCodeDefine.ROBOT_NO_ACTION_ERROR_CODE,
                    ErrorCodeDefine.ROBOT_NO_ACTION_ERROR_MSG, robot.getName());
        }
    }

    @Override
    public void reInit() {
        FightGameControlConfig fightGameControlConfig = (FightGameControlConfig) gameControlConfig;
        FightElementsData fightElementsData = (FightElementsData) elementData;

        // 初始化战役元素及位置统计
        fightElementsData.bloodBags.clear();
        fightElementsData.landmines.clear();
        fightElementsData.robotBaseInfoMap.clear();
        fightElementsData.attractRecords.clear();
        this.elementListsArray = new ElementList[map.getSize()][map.getSize()];
        this.mapLatticeHasElement = new Boolean[map.getSize()][map.getSize()];
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                elementListsArray[i][j] = new ElementList();
                mapLatticeHasElement[i][j] = false;
            }
        }

        // 初始化机器人位置、颜色
        for (AbstractRobot robot : elementData.robots) {
            Integer[] rgb = CommonUtil.getRandColorCode();
            fightElementsData.robotBaseInfoMap.put(robot.getName(),
                    new FightRobotBaseInfo(robot.getName(), new Color(rgb[0], rgb[1], rgb[2]), generateNoUseLocation(),
                            fightGameControlConfig.robotInitBloodNum));

            FightRobotSeeEntity robotSeeMazeEntity = new FightRobotSeeEntity();
            robotSeeMazeEntity.robotBaseInfo = (AbstractRobotBaseInfo) fightElementsData.robotBaseInfoMap
                    .get(robot.getName()).clone();
            robot.noticeRobot(robotSeeMazeEntity);
        }

        // 初始化地雷、血包
        addMapElement(1);

        constructElementListsArray();
    }

    /**
     * 生成未被使用的坐标，并将该坐标标记为已使用
     * @return
     */
    private Location generateNoUseLocation() {
        java.util.List<Location> notHasLocations = new ArrayList<>();
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (!mapLatticeHasElement[i][j]) {
                    notHasLocations.add(new Location(i, j));
                }
            }
        }

        if (notHasLocations.isEmpty()) {
            return null;
        }

        Random random = new Random(System.currentTimeMillis());
        Location location = notHasLocations.get(Math.abs(random.nextInt()) % notHasLocations.size());
        mapLatticeHasElement[location.x][location.y] = true;

        return location;
    }

    /**
     * 构造位置元素统计数组
     */
    private void constructElementListsArray() {
        // 重置统计数组
        for (int i = 0; i < elementListsArray.length; i++) {
            for (int j = 0; j < elementListsArray.length; j++) {
                elementListsArray[i][j].elements.clear();
            }
        }

        FightElementsData fightElementData = (FightElementsData) elementData;
        for (AbstractRobot fightRobot : fightElementData.robots) {
            FightRobotBaseInfo fightRobotBaseInfo = (FightRobotBaseInfo) elementData.robotBaseInfoMap
                    .get(fightRobot.getName());
            if (fightRobotBaseInfo.bloodNum > 0) {
                elementListsArray[fightRobotBaseInfo.currentLocation.x][fightRobotBaseInfo.currentLocation.y].elements
                        .add(fightRobotBaseInfo);
            }
        }
        for (BloodBag bloodBag : fightElementData.bloodBags) {
            elementListsArray[bloodBag.location.x][bloodBag.location.y].elements.add(bloodBag);
        }
        for (Landmine landmine : fightElementData.landmines) {
            elementListsArray[landmine.location.x][landmine.location.y].elements.add(landmine);
        }
    }

    /**
     * 增加地图元素：地雷、沙盒
     */
    private void addMapElement(Integer turn) {
        FightGameControlConfig fightGameControlConfig = (FightGameControlConfig) gameControlConfig;

        // 初始化地雷
        FightElementsData fightElementData = (FightElementsData) elementData;

        int landmineNum = this.map.getSize() / 4;
        landmineNum = landmineNum >= 5 ? landmineNum : 5;

        for (int i = 0; i < this.map.getSize() / 4; i++) {
            Location location = generateNoUseLocation();
            if (null != location) {
                fightElementData.landmines.add(new Landmine(location, fightGameControlConfig.landmineBloodNum));
            }
        }

        // 初始化血包
        for (int i = 0; i < ((FightElementsData) elementData).getCurrentSurvivalRobotNum(); i++) {
            Location location = generateNoUseLocation();
            if (null != location) {
                fightElementData.bloodBags.add(new BloodBag(location, fightGameControlConfig.bloodBagBloodNum));
            }
        }
    }
}
