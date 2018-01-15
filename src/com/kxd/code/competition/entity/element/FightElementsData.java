package com.kxd.code.competition.entity.element;

import com.kxd.code.competition.entity.element.child.BloodBag;
import com.kxd.code.competition.entity.element.child.Landmine;
import com.kxd.code.competition.robot.AbstractRobot;
import com.kxd.code.competition.robot.entity.FightRobotBaseInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by huangnx on 2017/12/21.
 */
public class FightElementsData extends AbstractElementsData {

    public List<Landmine> landmines = new CopyOnWriteArrayList<>();

    public List<BloodBag> bloodBags = new CopyOnWriteArrayList<>();

    public Integer getCurrentSurvivalRobotNum() {
        Integer num = 0;
        for (AbstractRobot robot : robots) {
            FightRobotBaseInfo fightRobotBaseInfo = (FightRobotBaseInfo) robotBaseInfoMap.get(robot.getName());
            if (fightRobotBaseInfo.bloodNum > 0) {
                num++;
            }
        }
        return num;
    }

    /**
     * 获取最终的存活的机器人（如果游戏未结束，调用的结果是错误的）
     * @return
     */
    public AbstractRobot getFinalSurvivalRobot() {
        java.util.List<AbstractRobot> drawRobots = new ArrayList<>();
        drawRobots.addAll(robots);
        Collections.sort(drawRobots, new Comparator<AbstractRobot>() {
            @Override
            public int compare(AbstractRobot o1, AbstractRobot o2) {
                FightRobotBaseInfo robot1 = (FightRobotBaseInfo) robotBaseInfoMap.get(o1.getName());
                FightRobotBaseInfo robot2 = (FightRobotBaseInfo) robotBaseInfoMap.get(o2.getName());
                return robot2.bloodNum.compareTo(robot1.bloodNum);
            }
        });
        return drawRobots.get(0);
    }

    /**
     * 获取异常最多的机器人
     * @return
     */
    public AbstractRobot getHighestExceptionRobot() {
        java.util.List<AbstractRobot> drawRobots = new ArrayList<>();
        drawRobots.addAll(robots);
        Collections.sort(drawRobots, new Comparator<AbstractRobot>() {
            @Override
            public int compare(AbstractRobot o1, AbstractRobot o2) {
                FightRobotBaseInfo robot1 = (FightRobotBaseInfo) robotBaseInfoMap.get(o1.getName());
                FightRobotBaseInfo robot2 = (FightRobotBaseInfo) robotBaseInfoMap.get(o2.getName());
                return robot2.exceptionNum.compareTo(robot1.exceptionNum);
            }
        });
        return drawRobots.get(0);
    }

    /**
     * 获取存活时间排序的机器人列表(从大到小)
     * @return
     */
    public List<AbstractRobot> getSurvivalSoftRobot() {
        java.util.List<AbstractRobot> drawRobots = new ArrayList<>();
        drawRobots.addAll(robots);
        Collections.sort(drawRobots, new Comparator<AbstractRobot>() {
            @Override
            public int compare(AbstractRobot o1, AbstractRobot o2) {
                FightRobotBaseInfo robot1 = (FightRobotBaseInfo) robotBaseInfoMap.get(o1.getName());
                FightRobotBaseInfo robot2 = (FightRobotBaseInfo) robotBaseInfoMap.get(o2.getName());
                return robot2.deadRound.compareTo(robot1.deadRound);
            }
        });

        java.util.List<AbstractRobot> resultRobot = new ArrayList<>();
        resultRobot.add(drawRobots.get(drawRobots.size() - 1));
        drawRobots.remove(drawRobots.size() - 1);
        resultRobot.addAll(drawRobots);
        return drawRobots;
    }

    /**
     * 获取分数从高到低排序的机器人列表
     * @return
     */
    public List<AbstractRobot> getScoreSoftRobot() {
        java.util.List<AbstractRobot> drawRobots = new ArrayList<>();
        drawRobots.addAll(robots);
        Collections.sort(drawRobots, new Comparator<AbstractRobot>() {
            @Override
            public int compare(AbstractRobot o1, AbstractRobot o2) {
                FightRobotBaseInfo robot1 = (FightRobotBaseInfo) robotBaseInfoMap.get(o1.getName());
                FightRobotBaseInfo robot2 = (FightRobotBaseInfo) robotBaseInfoMap.get(o2.getName());
                return getScore(robot2).compareTo(getScore(robot1));
            }
        });
        return drawRobots;
    }

    /**
     * 获取踩雷最多的机器人
     * @return
     */
    public AbstractRobot getStepOnLandmineHighestRobot() {
        java.util.List<AbstractRobot> drawRobots = new ArrayList<>();
        drawRobots.addAll(robots);
        Collections.sort(drawRobots, new Comparator<AbstractRobot>() {
            @Override
            public int compare(AbstractRobot o1, AbstractRobot o2) {
                FightRobotBaseInfo robot1 = (FightRobotBaseInfo) robotBaseInfoMap.get(o1.getName());
                FightRobotBaseInfo robot2 = (FightRobotBaseInfo) robotBaseInfoMap.get(o2.getName());
                return robot2.stepOnLandmineNum.compareTo(robot1.stepOnLandmineNum);
            }
        });
        return drawRobots.get(0);
    }

    /**
     * 获取得到血包最多的机器人
     * @return
     */
    public AbstractRobot getBloodBagMoreRobot() {
        java.util.List<AbstractRobot> drawRobots = new ArrayList<>();
        drawRobots.addAll(robots);
        Collections.sort(drawRobots, new Comparator<AbstractRobot>() {
            @Override
            public int compare(AbstractRobot o1, AbstractRobot o2) {
                FightRobotBaseInfo robot1 = (FightRobotBaseInfo) robotBaseInfoMap.get(o1.getName());
                FightRobotBaseInfo robot2 = (FightRobotBaseInfo) robotBaseInfoMap.get(o2.getName());
                return robot2.getBloodBagNum.compareTo(robot1.getBloodBagNum);
            }
        });
        return drawRobots.get(0);
    }

    /**
     * 获取得到攻击次数最多的机器人
     * @return
     */
    public AbstractRobot getAttractMoreRobot() {
        java.util.List<AbstractRobot> drawRobots = new ArrayList<>();
        drawRobots.addAll(robots);

        Collections.sort(drawRobots, new Comparator<AbstractRobot>() {
            @Override
            public int compare(AbstractRobot o1, AbstractRobot o2) {
                FightRobotBaseInfo robot1 = (FightRobotBaseInfo) robotBaseInfoMap.get(o1.getName());
                FightRobotBaseInfo robot2 = (FightRobotBaseInfo) robotBaseInfoMap.get(o2.getName());
                return robot2.attackRobotNum.compareTo(robot1.attackRobotNum);
            }
        });
        return drawRobots.get(0);
    }

    public Integer getScore(FightRobotBaseInfo fightRobotBaseInfo) {
        List<AbstractRobot> survivalSoftList = getSurvivalSoftRobot();

        int rank = 0;
        for (int i = 0; i < survivalSoftList.size(); i++) {
            if (fightRobotBaseInfo.name.equals(survivalSoftList.get(i).getName())) {
                rank = i + 1;
                break;
            }
        }

        String text = "";
        text += "(" + survivalSoftList.size() + "-" + rank + "+" + "1)*2";
        text += "+" + fightRobotBaseInfo.bloodNum;
        text += "+" + fightRobotBaseInfo.getBloodBagNum;
        text += "+" + fightRobotBaseInfo.attackRobotNum + "*2";
        text += "-" + fightRobotBaseInfo.stepOnLandmineNum;
        text += "-" + fightRobotBaseInfo.exceptionNum * 2;
        System.out.println(fightRobotBaseInfo.name + ":" + text);

        return (survivalSoftList.size() - rank + 1) * 2 + fightRobotBaseInfo.bloodNum
                + fightRobotBaseInfo.getBloodBagNum + fightRobotBaseInfo.attackRobotNum * 2
                - fightRobotBaseInfo.stepOnLandmineNum - fightRobotBaseInfo.exceptionNum * 2;
    }
}
