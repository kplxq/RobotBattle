package com.kxd.code.competition.draw.element.fight;

import com.kxd.code.competition.draw.element.AbstractHeaderDraw;
import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.element.FightElementsData;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.entity.gameconfig.FightGameControlConfig;
import com.kxd.code.competition.robot.AbstractRobot;
import com.kxd.code.competition.robot.entity.FightRobotBaseInfo;

import javax.swing.*;

/**
 * Created by huangnx on 2017/12/28.
 */
public class FightResultDraw extends AbstractHeaderDraw {

    public FightResultDraw(DrawArea drawArea, AbstractElementsData elementData,
            AbstractGameControlConfig gameControlConfig) {
        super(drawArea, elementData, gameControlConfig);
    }

    public void drawResult() {
        FightGameControlConfig fightGameControlConfig = (FightGameControlConfig) gameControlConfig;
        FightElementsData fightElementsData = (FightElementsData) elementData;

        if (fightGameControlConfig.currentGameFinal) {
            String msg = "";

            msg += "分数排行榜：\n";

            int rank = 0;
            java.util.List<AbstractRobot> fightRobots = fightElementsData.getScoreSoftRobot();
            for (AbstractRobot robot : fightRobots) {
                rank += 1;
                FightRobotBaseInfo fightRobotBaseInfo = (FightRobotBaseInfo) fightElementsData.robotBaseInfoMap
                        .get(robot.getName());
                msg += "  第" + rank + "名：" + fightRobotBaseInfo.name + "(分数："
                        + fightElementsData.getScore(fightRobotBaseInfo) + ")\n";
            }

            FightRobotBaseInfo xiaoqiang = (FightRobotBaseInfo) fightElementsData.robotBaseInfoMap
                    .get(fightElementsData.getFinalSurvivalRobot().getName());
            msg += "我是小强：" + xiaoqiang.name + "(活到最后)\n";

            FightRobotBaseInfo warGod = (FightRobotBaseInfo) fightElementsData.robotBaseInfoMap
                    .get(fightElementsData.getAttractMoreRobot().getName());
            if (warGod.attackRobotNum > 0) {
                msg += "开鑫战神：" + warGod.name + "(攻击数：" + warGod.attackRobotNum + ")\n";
            }

            FightRobotBaseInfo blessRobot = (FightRobotBaseInfo) fightElementsData.robotBaseInfoMap
                    .get(fightElementsData.getBloodBagMoreRobot().getName());
            if (blessRobot.getBloodBagNum > 0) {
                msg += "大神庇佑：" + blessRobot.name + "(血包数：" + blessRobot.getBloodBagNum + ")\n";
            }

            FightRobotBaseInfo pitGod = (FightRobotBaseInfo) fightElementsData.robotBaseInfoMap
                    .get(fightElementsData.getStepOnLandmineHighestRobot().getName());
            if (pitGod.stepOnLandmineNum > 0) {
                msg += "我是衰神：" + pitGod.name + "(踩雷数：" + pitGod.stepOnLandmineNum + ")\n";
            }

            FightRobotBaseInfo highestException = (FightRobotBaseInfo) fightElementsData.robotBaseInfoMap
                    .get(fightElementsData.getHighestExceptionRobot().getName());
            if (highestException.exceptionNum > 0) {
                msg += "回炉改造：" + highestException.name + "(异常：" + highestException.exceptionNum + ")\n";
            }

            JOptionPane.showMessageDialog(null, msg, "游戏结束", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
