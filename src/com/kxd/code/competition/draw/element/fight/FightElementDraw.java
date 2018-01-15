package com.kxd.code.competition.draw.element.fight;

import com.kxd.code.competition.draw.element.AbstractMapElementDraw;
import com.kxd.code.competition.entity.AttractRecord;
import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.element.FightElementsData;
import com.kxd.code.competition.entity.element.child.BloodBag;
import com.kxd.code.competition.entity.element.child.Landmine;
import com.kxd.code.competition.robot.AbstractRobot;
import com.kxd.code.competition.robot.entity.FightRobotBaseInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by huangnx on 2017/12/21.
 */
public class FightElementDraw extends AbstractMapElementDraw {

    public FightElementDraw(DrawArea drawArea, int width, AbstractElementsData elementData) {
        super(drawArea, width, elementData);
    }

    @Override
    public void paint() {
        FightElementsData fightElementsData = (FightElementsData) elementData;

        // 画机器人
        java.util.List<AbstractRobot> drawRobots = new ArrayList<>();
        drawRobots.addAll(fightElementsData.robots);
        Collections.sort(drawRobots, new Comparator<AbstractRobot>() {
            @Override
            public int compare(AbstractRobot o1, AbstractRobot o2) {
                FightRobotBaseInfo robot1 = (FightRobotBaseInfo) elementData.robotBaseInfoMap.get(o1.getName());
                FightRobotBaseInfo robot2 = (FightRobotBaseInfo) elementData.robotBaseInfoMap.get(o2.getName());
                return robot1.bloodNum.compareTo(robot2.bloodNum);
            }
        });

        g.setFont(new Font(null, 0, 14));

        for (AbstractRobot abstractMazeRobot : drawRobots) {
            FightRobotBaseInfo fightRobotBaseInfo = (FightRobotBaseInfo) elementData.robotBaseInfoMap
                    .get(abstractMazeRobot.getName());
            int currentX = fightRobotBaseInfo.currentLocation.x;
            int currentY = fightRobotBaseInfo.currentLocation.y;

            g.setColor(fightRobotBaseInfo.color);
            g.fillOval(getCenterX(currentY) - width / 3, getCenterY(currentX) - width / 3, width * 2 / 3,
                    width * 2 / 3);

            if (fightRobotBaseInfo.bloodNum <= 0) {
                g.setColor(Color.white);
                g.drawLine(getCenterX(currentY) - width / 6, getCenterY(currentX) - width / 6,
                        getCenterX(currentY) + width / 6, getCenterY(currentX) + width / 6);
                g.drawLine(getCenterX(currentY) - width / 6, getCenterY(currentX) + width / 6,
                        getCenterX(currentY) + width / 6, getCenterY(currentX) - width / 6);
            } else {
                g.setColor(Color.red);
                g.fillOval(getCenterX(currentY) - width / 8, getCenterY(currentX) - width / 8, width / 4, width / 4);
            }

            g.setColor(Color.WHITE);
            g.drawChars(new char[] { abstractMazeRobot.getName().charAt(0) }, 0, 1, getCenterX(currentY) - width / 4,
                    getCenterY(currentX) + width / 5);
        }

        // 画地雷
        Iterator<Landmine> landmineIiIterator = fightElementsData.landmines.iterator();
        while (landmineIiIterator.hasNext()) {
            Landmine landmine = landmineIiIterator.next();

            g.setColor(Color.RED);
            g.drawLine(getCenterX(landmine.location.y) - width / 6, getCenterY(landmine.location.x) - width / 6,
                    getCenterX(landmine.location.y) + width / 6, getCenterY(landmine.location.x) + width / 6);
            g.drawLine(getCenterX(landmine.location.y) - width / 6, getCenterY(landmine.location.x) + width / 6,
                    getCenterX(landmine.location.y) + width / 6, getCenterY(landmine.location.x) - width / 6);
        }

        // 画血包
        for (BloodBag bloodBag : fightElementsData.bloodBags) {
            g.setColor(Color.decode("#B23AEE"));
            g.drawRect(getCenterX(bloodBag.location.y) - width / 6, getCenterY(bloodBag.location.x) - width / 6,
                    width / 3, width / 3);
            g.drawRect(getCenterX(bloodBag.location.y) - width / 8, getCenterY(bloodBag.location.x) - width / 8,
                    width / 4, width / 4);
            g.drawRect(getCenterX(bloodBag.location.y) - width / 16, getCenterY(bloodBag.location.x) - width / 16,
                    width / 8, width / 8);
        }

        // 画攻击记录
        String attractMsg = "";
        attractMsg += "发生攻击事件数量：" + fightElementsData.attractRecords.size();

        int area = 30;

        int drawBeginX = drawArea.end.x + area * 2;
        int drawBeginY = drawArea.begin.y + area;

        g.setColor(Color.black);
        g.drawChars(attractMsg.toCharArray(), 0, attractMsg.length(), drawBeginX, drawBeginY);

        drawBeginY += area;
        String text = "最近15次攻击事件如下：";
        g.drawChars(text.toCharArray(), 0, text.length(), drawBeginX, drawBeginY);

        int num = 0;
        drawBeginX = drawArea.end.x + area * 3;
        for (int j = fightElementsData.attractRecords.size() - 1; j >= 0; j--) {
            AttractRecord attractRecord = fightElementsData.attractRecords.get(j);

            num++;
            if (num <= 15) {
                drawBeginY += area;

                Color robotAColor = fightElementsData.robotBaseInfoMap.get(attractRecord.robotAName).color;
                Color robotBColor = fightElementsData.robotBaseInfoMap.get(attractRecord.robotBName).color;

                g.setColor(robotAColor);
                g.drawChars(attractRecord.robotAName.toCharArray(), 0, attractRecord.robotAName.length(), drawBeginX,
                        drawBeginY);

                g.setColor(Color.black);
                g.drawChars("fire".toCharArray(), 0, "fire".length(), drawBeginX + area * 4, drawBeginY);

                g.setColor(robotBColor);
                g.drawChars(attractRecord.robotBName.toCharArray(), 0, attractRecord.robotBName.length(),
                        drawBeginX + area * 6, drawBeginY);
            } else {
                break;
            }
        }
    }
}
