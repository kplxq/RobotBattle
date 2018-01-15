package com.kxd.code.competition.draw.jpanel;

import com.kxd.code.competition.draw.element.maze.MazeHeaderDraw;
import com.kxd.code.competition.draw.element.maze.MazeMapDraw;
import com.kxd.code.competition.draw.element.maze.MazeMapElementDraw;
import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.map.AbstractMap;

/**
 * Created by huangnx on 2017/12/18.
 */
public class RobotMazeBattleDrawJpanel extends AbstractRobotBattleDrawJpanel {

    public RobotMazeBattleDrawJpanel(int width, int padding, AbstractMap map, AbstractElementsData elementData,
            AbstractGameControlConfig gameControlConfig) {
        super(width, padding, map, elementData, gameControlConfig);

        Location bodyStart = new Location(padding, padding * 2);
        Location bodyEnd = new Location(padding + width * map.getSize(), padding * 2 + width * map.getSize());

        headerDraw = new MazeHeaderDraw(
                new DrawArea(new Location(padding / 4, 0),
                        new Location(padding + width * map.getSize() + padding / 4, padding * 2)),
                elementData, gameControlConfig);
        mazeMapDraw = new MazeMapDraw(new DrawArea(bodyStart, bodyEnd), map, width, gameControlConfig);
        elementDraw = new MazeMapElementDraw(new DrawArea(bodyStart, bodyEnd), width, elementData);

        this.setFocusable(true);
    }

    private int getCenterX(int x) {
        return padding + x * width + width / 2;
    }

    private int getCenterY(int y) {
        return padding + y * width + width / 2;
    }

}
