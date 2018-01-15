package com.kxd.code.competition.draw.element.fight;

import com.kxd.code.competition.draw.element.AbstractMapDraw;
import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.map.AbstractMap;

import java.awt.*;

/**
 * Created by huangnx on 2017/12/21.
 */
public class FightMapDraw extends AbstractMapDraw {

    public FightMapDraw(DrawArea drawArea, AbstractMap map, int width, AbstractGameControlConfig gameControlConfig) {
        super(drawArea, map, width, gameControlConfig);
    }

    @Override
    public void paint() {
        // 画网格
        g.setColor(Color.GRAY);
        for (int i = 0; i <= map.getSize(); i++) {
            g.drawLine(drawArea.begin.x + i * width, drawArea.begin.y, drawArea.begin.x + i * width, drawArea.end.y);
        }
        for (int j = 0; j <= map.getSize(); j++) {
            g.drawLine(drawArea.begin.x, drawArea.begin.y + j * width, drawArea.end.x, drawArea.begin.y + j * width);
        }
    }
}
