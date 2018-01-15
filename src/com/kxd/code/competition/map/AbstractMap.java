package com.kxd.code.competition.map;

import com.kxd.code.competition.entity.lattice.AbstractLattice;

/**
 * Created by huangnx on 2017/12/18.
 */
public abstract class AbstractMap {

    /**
     * 迷宫大小
     */
    protected int                 size;

    /**
     * 迷宫格子的二维数组
     */
    protected AbstractLattice[][] map;

    public AbstractMap(int size) {
        this.size = size;
        initMap();
    }

    /**
     * 初始化化map数组
     */
    protected abstract void initMap();

    public int getSize() {
        return size;
    }

    public AbstractLattice[][] getMap() {
        return map;
    }
}
