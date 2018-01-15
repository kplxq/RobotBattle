package com.kxd.code.competition.entity.lattice;

/**
 * Created by huangnx on 2017/12/18.
 */
public class MazeLattice extends AbstractLattice {

    public MazeLattice(int xx, int yy) {
        super(xx, yy);
    }

    /**
     * 用作初始化
     */
    public class InitFlagConsts {
        public static final int INTREE    = 1;

        public static final int NOTINTREE = 0;
    }

    private int         flag   = InitFlagConsts.NOTINTREE;

    /**
     * 父亲节点的引用。该节点可以移动到父亲节点或者自己的子节点
     */
    private MazeLattice father = null;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int f) {
        flag = f;
    }

    public MazeLattice getFather() {
        return father;
    }

    public void setFather(MazeLattice f) {
        father = f;
    }

}
