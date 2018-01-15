package com.kxd.code.competition.robot.fight;

import com.kxd.code.competition.constants.CommonConstant;
import com.kxd.code.competition.constants.ElementTypeEnum;
import com.kxd.code.competition.constants.MoveActionCommandEnum;
import com.kxd.code.competition.entity.ElementList;
import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.action.CommonMoveAction;
import com.kxd.code.competition.entity.element.AbstractElement;
import com.kxd.code.competition.entity.element.child.BloodBag;
import com.kxd.code.competition.entity.mapinfo.AbstractMapInfo;
import com.kxd.code.competition.entity.robotsee.FightRobotSeeEntity;
import com.kxd.code.competition.robot.AbstractFightRobot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by huangnx on 2017/12/21.
 */
public class DemoFightRobot extends AbstractFightRobot {

    public DemoFightRobot(String name) {
        super(name);
    }

    private BloodBag lastBloodBag = null;

    @Override
    public CommonMoveAction getNextAction() {
        Random random = new Random(System.currentTimeMillis());

        FightRobotSeeEntity fightRobotSeeEntity = (FightRobotSeeEntity) currentSeeMazeSituation;

        ElementList[][] elementLists = fightRobotSeeEntity.robotGetElementList;
        AbstractElement targetElement = null;

        // 当前所见的血包存到list
        List<AbstractElement> bloodBags = new ArrayList<>();
        for (int i = 0; i < elementLists.length; i++) {
            for (int j = 0; j < elementLists.length; j++) {
                ElementList elementList = elementLists[i][j];
                for (AbstractElement element : elementList.elements) {
                    if (ElementTypeEnum.BLOOD_BAG == element.elementType) {
                        bloodBags.add(element);
                    }
                }
            }
        }

        // 当前地图上有血包
        if (!bloodBags.isEmpty()) {
            if (null == lastBloodBag) {
                // 没有追踪的血包
                targetElement = bloodBags.get(Math.abs(random.nextInt()) % bloodBags.size());
                lastBloodBag = (BloodBag) targetElement;
            } else {
                // 有追踪的血包
                BloodBag temp = null;
                for (AbstractElement element : bloodBags) {
                    BloodBag bloodBag = (BloodBag) element;
                    if (bloodBag.location.equals(lastBloodBag.location)) {
                        temp = bloodBag;
                        break;
                    }
                }

                if (temp == null) {
                    // 追踪的血包不见了
                    targetElement = bloodBags.get(Math.abs(random.nextInt()) % bloodBags.size());
                    lastBloodBag = (BloodBag) targetElement;
                } else {
                    // 追踪的血包还存在
                    targetElement = lastBloodBag;
                }
            }
        }

        MoveActionCommandEnum moveActionCommand = null;
        if (targetElement != null) {
            BloodBag bloodBag = (BloodBag) targetElement;

            int targetX = bloodBag.location.x;
            int targetY = bloodBag.location.y;

            List<MoveActionCommandEnum> moveActionCommands = new ArrayList<>();

            if (targetX < fightRobotSeeEntity.robotBaseInfo.currentLocation.x) {
                moveActionCommands.add(MoveActionCommandEnum.MOVE_TOP);
            } else if (targetX > fightRobotSeeEntity.robotBaseInfo.currentLocation.x) {
                moveActionCommands.add(MoveActionCommandEnum.MOVE_DOWN);
            }
            if (targetY < fightRobotSeeEntity.robotBaseInfo.currentLocation.y) {
                moveActionCommands.add(MoveActionCommandEnum.MOVE_LEFT);
            } else {
                moveActionCommands.add(MoveActionCommandEnum.MOVE_RIGHT);
            }

            moveActionCommand = moveActionCommands.get(0);
        } else {
            moveActionCommand = generateNoLandmineAction();
        }

        CommonMoveAction actionEntity = new CommonMoveAction(moveActionCommand);
        return actionEntity;
    }

    private Boolean isHasLandmine(ElementList[][] elementLists, Location location, AbstractMapInfo fightMapInfo) {
        Boolean isHasLandmine = false;
        if (location.x >= fightMapInfo.size || location.y >= fightMapInfo.size || location.x < 0 || location.y < 0) {
            return true;
        }

        for (AbstractElement element : elementLists[location.x][location.y].elements) {
            if (ElementTypeEnum.LANDMINE == element.elementType) {
                isHasLandmine = true;
                break;
            }
        }
        return isHasLandmine;
    }

    private MoveActionCommandEnum generateNoLandmineAction() {
        FightRobotSeeEntity fightRobotSeeEntity = (FightRobotSeeEntity) currentSeeMazeSituation;
        ElementList[][] elementLists = fightRobotSeeEntity.robotGetElementList;
        Location currentLocation = fightRobotSeeEntity.robotBaseInfo.currentLocation;

        Random random = new Random(System.currentTimeMillis());

        MoveActionCommandEnum moveActionCommand = MoveActionCommandEnum.MOVE_DOWN;

        int tryNum = 0;
        while (true) {
            tryNum++;

            int index = Math.abs(random.nextInt()) % 4;
            moveActionCommand = CommonConstant.directCodes.get(index);

            Integer nextX = currentLocation.x + CommonConstant.directXY.get(index);
            Integer nextY = currentLocation.y + CommonConstant.directXY.get(index + 1);
            if (!isHasLandmine(elementLists, new Location(nextX, nextY), fightRobotSeeEntity.mapInfo) || tryNum == 10) {
                break;
            }
        }

        return moveActionCommand;
    }
}
