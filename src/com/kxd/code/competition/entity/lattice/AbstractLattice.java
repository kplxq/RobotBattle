package com.kxd.code.competition.entity.lattice;

/**
 * Created by huangnx on 2017/12/18.
 */
public abstract class AbstractLattice {

    /**
     * X坐标
     */
    protected int x = -1;

    /**
     * Y坐标
     */
    protected int y = -1;

    public AbstractLattice(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return new String("(" + x + "," + y + ")\n");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractLattice) {
            AbstractLattice targetLattice = (AbstractLattice) obj;
            return (this.x == targetLattice.x) && (this.y == targetLattice.y);
        } else {
            return false;
        }
    }
}
