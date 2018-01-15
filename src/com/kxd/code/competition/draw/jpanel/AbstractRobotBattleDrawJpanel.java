package com.kxd.code.competition.draw.jpanel;

import com.kxd.code.competition.draw.element.AbstractHeaderDraw;
import com.kxd.code.competition.draw.element.AbstractMapDraw;
import com.kxd.code.competition.draw.element.AbstractMapElementDraw;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.map.AbstractMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;

/**
 * Created by huangnx on 2017/12/20.
 */
public abstract class AbstractRobotBattleDrawJpanel extends JPanel {

    /**
     * 地图一个格子的宽度；padding多少个格子
     */
    protected int                       width, padding;

    protected AbstractMap               map;

    protected AbstractElementsData      elementData;

    protected AbstractGameControlConfig gameControlConfig;

    protected AbstractHeaderDraw        headerDraw;

    protected AbstractMapDraw           mazeMapDraw;

    protected AbstractMapElementDraw    elementDraw;

    public AbstractRobotBattleDrawJpanel(int width, int padding, AbstractMap map, AbstractElementsData elementData,
            AbstractGameControlConfig gameControlConfig) {
        this.width = width;
        this.padding = padding;
        this.map = map;
        this.elementData = elementData;
        this.gameControlConfig = gameControlConfig;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        initDraw(g);

        // 画头信息
        headerDraw.paint();
        // 画地图
        mazeMapDraw.paint();
        // 画element
        elementDraw.paint();
    }

    /**
     * 设置键盘监听器
     * @param keyAdapter
     */
    public final void setKeyListener(KeyAdapter keyAdapter) {
        this.addKeyListener(keyAdapter);
    }

    /**
     * 初始化绘笔
     */
    protected void initDraw(Graphics g) {
        headerDraw.setG(g);
        headerDraw.setBackGroundColor(this.getBackground());

        mazeMapDraw.setG(g);
        mazeMapDraw.setBackGroundColor(this.getBackground());

        elementDraw.setG(g);
        elementDraw.setBackGroundColor(this.getBackground());
    }
}
