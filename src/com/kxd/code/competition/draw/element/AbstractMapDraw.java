package com.kxd.code.competition.draw.element;

import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.map.AbstractMap;

/**
 * Created by huangnx on 2017/12/19.
 */
public abstract class AbstractMapDraw extends AbstractDraw {

    protected AbstractGameControlConfig gameControlConfig;

    protected int                       width;

    protected AbstractMap               map;

    public AbstractMapDraw(DrawArea drawArea, AbstractMap map, int width, AbstractGameControlConfig gameControlConfig) {
        super(drawArea);
        this.map = map;
        this.width = width;
        this.gameControlConfig = gameControlConfig;
    }

    public AbstractMapDraw(DrawArea drawArea) {
        super(drawArea);
    }

    protected int getCenterX(int x) {
        return drawArea.begin.x + x * width + width / 2;
    }

    protected int getCenterY(int y) {
        return drawArea.begin.y + y * width + width / 2;
    }
}
