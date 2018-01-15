package com.kxd.code.competition.draw.element.fight;

import com.kxd.code.competition.draw.element.AbstractHeaderDraw;
import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.element.FightElementsData;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.entity.gameconfig.FightGameControlConfig;
import com.kxd.code.competition.robot.entity.FightRobotBaseInfo;

import java.awt.*;
import java.util.Set;

/**
 * Created by huangnx on 2017/12/21.
 */
public class FightHeaderDraw extends AbstractHeaderDraw {

    public FightHeaderDraw(DrawArea drawArea, AbstractElementsData elementData,
            AbstractGameControlConfig gameControlConfig) {
        super(drawArea, elementData, gameControlConfig);
    }

    @Override
    public void paint() {
        FightGameControlConfig fightGameControlConfig = (FightGameControlConfig) gameControlConfig;
        FightElementsData fightElementsData = (FightElementsData) elementData;

        int x = drawArea.begin.x * 12;
        int y = (drawArea.end.y - drawArea.begin.y) / 12;

        String headerMsg = "分数 = (机器人数量 - 存活排名 + 1) * 2 + 剩余血量 + 获得血包数量 + 攻击数 * 2 - 地雷 - 异常数 * 2 | 当前回合："
                + fightGameControlConfig.currentRoundNum;
        g.setColor(Color.BLACK);
        g.drawChars(headerMsg.toCharArray(), 0, headerMsg.toCharArray().length, x, y);

        headerMsg = "每：" + fightGameControlConfig.turnRoundNum + " 的整数倍回合时，场面将随机生成地雷和血包";
        x = drawArea.begin.x * 22;
        y += (drawArea.end.y - drawArea.begin.y) / 12;
        g.drawChars(headerMsg.toCharArray(), 0, headerMsg.toCharArray().length, x, y);

        g.setFont(new Font("", 1, 14));

        int i = 0;
        x = drawArea.begin.x * 5;
        y += (drawArea.end.y - drawArea.begin.y) / 12;
        Set<String> robotKeys = fightElementsData.robotBaseInfoMap.keySet();
        for (String robotName : robotKeys) {
            FightRobotBaseInfo fightRobotBaseInfo = (FightRobotBaseInfo) fightElementsData.robotBaseInfoMap
                    .get(robotName);
            String msg = "";
            msg += fightRobotBaseInfo.name + " : ";
            msg += "血量:" + fightRobotBaseInfo.bloodNum + "|";
            msg += "血包:" + fightRobotBaseInfo.getBloodBagNum + "|";
            msg += "攻击:" + fightRobotBaseInfo.attackRobotNum + "|";
            msg += "地雷:" + fightRobotBaseInfo.stepOnLandmineNum + "|";
            msg += "异常:" + fightRobotBaseInfo.exceptionNum + "|";
            msg += "死亡回合:" + fightRobotBaseInfo.deadRound;

            g.setColor(fightRobotBaseInfo.color);
            g.drawChars(msg.toCharArray(), 0, msg.toCharArray().length, x, y);

            i++;
            x += drawArea.begin.x * 45;
            if (i % 2 == 0) {
                y += (drawArea.end.y - drawArea.begin.y) / 12;
                x = drawArea.begin.x * 5;
            }
        }
    }
}
