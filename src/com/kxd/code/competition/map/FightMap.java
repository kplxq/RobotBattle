package com.kxd.code.competition.map;

import com.kxd.code.competition.entity.lattice.FightLattice;

/**
 * Created by huangnx on 2017/12/20.
 */
public class FightMap extends AbstractMap {

    public FightMap(int size) {
        super(size);
    }

    @Override
    protected void initMap() {
        map = new FightLattice[size][size];
        for (int i = 0; i <= size - 1; i++) {
            for (int j = 0; j <= size - 1; j++) {
                map[i][j] = new FightLattice(i, j);
            }
        }
    }
}
