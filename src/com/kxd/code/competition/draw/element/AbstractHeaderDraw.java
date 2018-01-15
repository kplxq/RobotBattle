package com.kxd.code.competition.draw.element;

import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.robot.AbstractRobot;

/**
 * Created by huangnx on 2017/12/19.
 */
public abstract class AbstractHeaderDraw extends AbstractDraw {

    protected AbstractElementsData      elementData;

    protected AbstractGameControlConfig gameControlConfig;

    public AbstractHeaderDraw(DrawArea drawArea, AbstractElementsData elementData,
            AbstractGameControlConfig gameControlConfig) {
        super(drawArea);
        this.elementData = elementData;
        this.gameControlConfig = gameControlConfig;
    }

    public AbstractHeaderDraw(DrawArea drawArea) {
        super(drawArea);
    }

    @Override
    public void paint() {
        int x = drawArea.begin.x;
        int y = (drawArea.end.y - drawArea.begin.y) / 4;

        int i = 0;
        for (AbstractRobot abstractRobot : elementData.robots) {
            String msg = "";
            msg += abstractRobot.getName() + " : ·";

            g.setColor(elementData.robotBaseInfoMap.get(abstractRobot.getName()).color);
            g.drawChars(msg.toCharArray(), 0, msg.toCharArray().length, x, y);

            i++;
            x += 80;
            if (i % 8 == 0) {
                y += (drawArea.end.y - drawArea.begin.y) / 4;
                x = drawArea.begin.x;
            }
        }
    }
}
