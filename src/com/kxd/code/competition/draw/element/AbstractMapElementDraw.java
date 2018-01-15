package com.kxd.code.competition.draw.element;

import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.element.AbstractElementsData;

/**
 * Created by huangnx on 2017/12/19.
 */
public abstract class AbstractMapElementDraw extends AbstractDraw {

    /**
     * 格子大小
     */
    protected int                 width;

    protected AbstractElementsData elementData;

    public AbstractMapElementDraw(DrawArea drawArea, int width, AbstractElementsData elementData) {
        super(drawArea);
        this.width = width;
        this.elementData = elementData;
    }

    protected int getCenterX(int x) {
        return drawArea.begin.x + x * width + width / 2;
    }

    protected int getCenterY(int y) {
        return drawArea.begin.y + y * width + width / 2;
    }
}
