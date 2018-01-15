package com.kxd.code.competition.draw.jpanel;

import com.kxd.code.competition.draw.element.fight.FightElementDraw;
import com.kxd.code.competition.draw.element.fight.FightHeaderDraw;
import com.kxd.code.competition.draw.element.fight.FightMapDraw;
import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.map.AbstractMap;

/**
 * Created by huangnx on 2017/12/21.
 */
public class RobotFightBattleDrawJpanel extends AbstractRobotBattleDrawJpanel {

    public RobotFightBattleDrawJpanel(int width, int padding, AbstractMap map, AbstractElementsData elementData,
            AbstractGameControlConfig gameControlConfig) {
        super(width, padding, map, elementData, gameControlConfig);

        Location bodyStart = new Location(padding, padding * 5);
        Location bodyEnd = new Location(padding + width * map.getSize(), padding * 5 + width * map.getSize());

        headerDraw = new FightHeaderDraw(
                new DrawArea(new Location(padding / 4, 0),
                        new Location(padding + width * map.getSize() + padding / 4, padding * 5)),
                elementData, gameControlConfig);
        mazeMapDraw = new FightMapDraw(new DrawArea(bodyStart, bodyEnd), map, width, gameControlConfig);
        elementDraw = new FightElementDraw(new DrawArea(bodyStart, bodyEnd), width, elementData);

        this.setFocusable(true);
    }
}
