package com.kxd.code.competition.robot.maze;

import com.kxd.code.competition.constants.MoveActionCommandEnum;
import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.action.CommonMoveAction;
import com.kxd.code.competition.entity.robotsee.RobotSeeMazeEntity;
import com.kxd.code.competition.robot.AbstractMazeRobot;

import java.util.*;

/**
 * Created by huangnx on 2017/12/18.
 */
public class DemoMazeRobot extends AbstractMazeRobot {

    public DemoMazeRobot() {
        super("demoMazeRobot");
    }

    private Stack<MoveActionCommandEnum> historyMoveActionCommands = new Stack<>();

    @Override
    public CommonMoveAction getNextAction() {

        RobotSeeMazeEntity robotSeeMazeEntity = (RobotSeeMazeEntity) currentSeeMazeSituation;
        Location currentLocation = robotSeeMazeEntity.robotBaseInfo.currentLocation;

        MoveActionCommandEnum moveActionCommand = null;
        MoveActionCommandEnum hisCommand = null;
        if (historyMoveActionCommands.size() > 0
                && robotSeeMazeEntity.canMoveStepSituation.get(historyMoveActionCommands.peek()) > 0) {
            hisCommand = historyMoveActionCommands.peek();
        }

        Set<MoveActionCommandEnum> keySet = robotSeeMazeEntity.canMoveStepSituation.keySet();
        List<Object> keyList = Arrays.asList(keySet.toArray());

        Random random = new Random();
        int ran = Math.abs(random.nextInt() % keySet.size());
        for (int i = 0; i < keySet.size(); i++) {
            ran++;
            ran %= keySet.size();

            MoveActionCommandEnum key = (MoveActionCommandEnum) keyList.get(ran);
            if (robotSeeMazeEntity.canMoveStepSituation.get(key) > 0) {
                moveActionCommand = key;
                break;
            }

            if (hisCommand != null && moveActionCommand != null) {
                int temp = Math.abs(random.nextInt() % 5);
                if (temp == 0) {
                    moveActionCommand = moveActionCommand;
                } else {
                    moveActionCommand = hisCommand;
                }
            }
        }

        historyMoveActionCommands.add(moveActionCommand);

        CommonMoveAction commonMoveAction = new CommonMoveAction(moveActionCommand);

        return commonMoveAction;
    }
}
