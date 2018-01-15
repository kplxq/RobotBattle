package com.kxd.code.competition.map;

import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.lattice.MazeLattice;
import com.kxd.code.competition.utils.CommonUtil;

import java.util.Random;
import java.util.Stack;

/**
 * Created by huangnx on 2017/12/18.
 */
public class Maze extends AbstractMap {

    public Maze(int size) {
        super(size);
    }

    @Override
    public void initMap() {
        Random random = new Random(System.currentTimeMillis());
        while (true) {
            map = new MazeLattice[size][size];
            for (int i = 0; i <= size - 1; i++) {
                for (int j = 0; j <= size - 1; j++) {
                    map[i][j] = new MazeLattice(i, j);
                }
            }

            Location rootLocation = new Location(random.nextInt() % (size / 6) + size / 2,
                    random.nextInt() % (size / 6) + size / 2);

            createMaze(rootLocation);

            // 从迷宫出口开始回朔，标记迷宫树的root节点，并将路线上的格子标记成2
            MazeLattice outFindMazeLattice = (MazeLattice) map[size - 1][size - 1];
            while (outFindMazeLattice.getFather() != null) {
                outFindMazeLattice = outFindMazeLattice.getFather();
            }

            MazeLattice inFindMazeLattice = (MazeLattice) map[0][0];
            while (inFindMazeLattice.getFather() != null) {
                inFindMazeLattice = inFindMazeLattice.getFather();
            }

            if (inFindMazeLattice.getX() == rootLocation.x && inFindMazeLattice.getY() == rootLocation.y) {
                if (outFindMazeLattice.getX() == rootLocation.x && outFindMazeLattice.getY() == rootLocation.y) {
                    break;
                }
            }
        }
    }

    /**
     * 随机构造迷宫
     */
    private void createMaze(Location rootLocation) {
        Random random = new Random();

        int rx = rootLocation.x;
        int ry = rootLocation.y;

        Stack<MazeLattice> s = new Stack<MazeLattice>();
        MazeLattice p = (MazeLattice) map[rx][ry];
        MazeLattice neis[] = null;
        s.push(p);
        while (!s.isEmpty()) {
            p = s.pop();
            p.setFlag(MazeLattice.InitFlagConsts.INTREE);

            neis = getNeis(p);
            int neisSize = 4;
            for (int a = 0; a < neisSize; a++) {
                int ran = Math.abs(random.nextInt()) % neisSize;
                if (neis[ran] == null || neis[ran].getFlag() == MazeLattice.InitFlagConsts.INTREE) {
                    continue;
                }
                neis[ran].setFather(p);

                s.push(neis[ran]);
            }
        }
    }

    /**
     * 找到一个节点的邻居节点
     * @param p
     * @return
     */
    private MazeLattice[] getNeis(MazeLattice p) {
        final int[] adds = { -1, 0, 1, 0, -1 };// 顺序为上右下左
        if (isOutOfBorder(p)) {
            return null;
        }
        MazeLattice[] ps = new MazeLattice[4];// 顺序为上右下左
        int xt;
        int yt;
        for (int i = 0; i <= 3; i++) {
            xt = p.getX() + adds[i];
            yt = p.getY() + adds[i + 1];
            if (CommonUtil.judgeIsOutOfArray(map, xt, yt)) {
                continue;
            }
            ps[i] = (MazeLattice) map[xt][yt];
        }
        return ps;
    }

    /**
     * 是否超出边界
     * @param p
     * @return
     */
    private boolean isOutOfBorder(MazeLattice p) {
        return CommonUtil.judgeIsOutOfArray(map, p.getX(), p.getY());
    }

}
