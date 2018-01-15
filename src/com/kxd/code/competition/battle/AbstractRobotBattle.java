package com.kxd.code.competition.battle;

import com.kxd.code.competition.constants.ErrorCodeDefine;
import com.kxd.code.competition.draw.jpanel.AbstractRobotBattleDrawJpanel;
import com.kxd.code.competition.entity.action.CommonMoveAction;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.entity.robotsee.AbstractRobotSeeEntity;
import com.kxd.code.competition.exception.AppException;
import com.kxd.code.competition.exception.RobotException;
import com.kxd.code.competition.exception.SandBoxInnerException;
import com.kxd.code.competition.map.AbstractMap;
import com.kxd.code.competition.robot.AbstractRobot;
import com.kxd.code.competition.robot.entity.FightRobotBaseInfo;

/**
 * Created by huangnx on 2017/12/20.
 */
public abstract class AbstractRobotBattle implements Runnable {

    public AbstractMap                   map;

    public AbstractElementsData          elementData;

    public AbstractRobotBattleDrawJpanel robotBattleDrawJpanel;

    public AbstractGameControlConfig     gameControlConfig;

    public AbstractRobotBattle(AbstractMap map, AbstractElementsData elementData,
            AbstractRobotBattleDrawJpanel robotBattleDrawJpanel, AbstractGameControlConfig gameControlConfig) {
        this.map = map;
        this.elementData = elementData;
        this.robotBattleDrawJpanel = robotBattleDrawJpanel;
        this.gameControlConfig = gameControlConfig;
    }

    @Override
    public void run() {
        this.doBattle();
    }

    /**
     * 战斗dispatch
     */
    public void doBattle() {
        for (int i = 1; i <= gameControlConfig.roundNum; i++) {
            try {
                // 判断游戏是否关闭。关闭则终止
                if (!gameControlConfig.gameStart) {
                    break;
                }

                gameControlConfig.currentRoundNum = i;

                // 每回合开始检查游戏结束条件
                if (isFinish(i)) {
                    gameControlConfig.currentGameFinal = true;
                    break;
                }

                // 回合正式开始前的沙盒处理
                doBeforeRound(i);

                // 执行：所有机器人一回合的行为
                for (AbstractRobot robot : elementData.robots) {
                    FightRobotBaseInfo fightRobotBaseInfo = (FightRobotBaseInfo) elementData.robotBaseInfoMap
                            .get(robot.getName());
                    if (fightRobotBaseInfo.bloodNum > 0) {
                        // 默认机器人可执行步数为1步，子battle可以自定义步数规则
                        for (int num = 0; num < elementData.robotBaseInfoMap
                                .get(robot.getName()).currentCanStepNum; num++) {
                            // 行动前先睡眠：保证可视界面效果
                            Thread.sleep(gameControlConfig.gameThreadSleepTime);
                            doRobotRound(robot, i);
                        }
                        elementData.robotBaseInfoMap.get(robot.getName()).currentCanStepNum = 1;
                    }
                }
            } catch (Exception e) {
                if (e instanceof AppException) {
                    SandBoxInnerException sandBoxInnerException = (SandBoxInnerException) e;
                    System.out.println("沙盒：发生异常。异常code：" + sandBoxInnerException.getCode() + "异常msg："
                            + sandBoxInnerException.getMsg());
                } else {
                    System.out.println(e);
                }
            }
        }
    }

    /**
     * 回合开始前的行为
     */
    protected abstract void doBeforeRound(Integer currentRound);

    /**
     * 是否结束
     */
    protected abstract Boolean isFinish(Integer currentRound);

    /**
     * 执行：机器人一回合的行为
     * @param robot
     */
    private void doRobotRound(AbstractRobot robot, Integer round) {
        if (isFinish(round)) {
            gameControlConfig.currentGameFinal = true;
            return;
        }

        // 构造机器人可见情况
        AbstractRobotSeeEntity robotSeeEntity = constructRobotSeeMazeSituation(robot);

        // 通知机器人当前情况
        robot.noticeRobot(robotSeeEntity);

        CommonMoveAction action = null;
        try {
            // 获取机器人的指令
            action = (CommonMoveAction) robot.getNextAction();

            // 执行机器人的指令
            doMoveAction(robot, action);
        } catch (Exception e) {
            RobotException robotException = null;
            if (e instanceof RobotException) {
                robotException = (RobotException) e;
            } else {
                robotException = new RobotException(ErrorCodeDefine.ROBOT_INNER_ERROR_CODE,
                        ErrorCodeDefine.ROBOT_INNER_ERROR_MSG, e, robot.getName());
            }
            elementData.robotBaseInfoMap.get(robotException.getRobotName()).occurException();
            System.out.println(robotException.toString());
        }

        robotBattleDrawJpanel.repaint();
    }

    /**
     * 构造机器人当前可移动的情况
     * @param robot
     * @return
     */
    protected abstract AbstractRobotSeeEntity constructRobotSeeMazeSituation(AbstractRobot robot);

    /**
     * 执行机器人的指令
     * @param mazeRobot
     * @param action
     */
    protected abstract void doMoveAction(AbstractRobot mazeRobot, CommonMoveAction action);

    /**
     * 重置战役
     */
    public abstract void reInit();

}
