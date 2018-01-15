package com.kxd.code.competition.draw.element;

import com.kxd.code.competition.entity.DrawArea;

import java.awt.*;

/**
 * Created by huangnx on 2017/12/19.
 */
public abstract class AbstractDraw {

    protected Graphics g;

    protected Color    backGroundColor;

    protected DrawArea drawArea;

    public AbstractDraw(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    /**
     * 绘图
     */
    public abstract void paint();

    public void setG(Graphics g) {
        this.g = g;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }
}
