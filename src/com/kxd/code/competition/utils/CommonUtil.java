package com.kxd.code.competition.utils;

import com.kxd.code.competition.entity.lattice.AbstractLattice;

import java.util.Random;

/**
 * Created by huangnx on 2017/12/18.
 */
public class CommonUtil {

    /**
     * 判断是否超出二维数组边界
     * @param arrays
     * @param x
     * @param y
     * @return
     */
    public static Boolean judgeIsOutOfArray(Object[][] arrays, int x, int y) {
        return (x > arrays.length - 1 || y > arrays[0].length - 1 || x < 0 || y < 0) ? true : false;
    }

    /**
     * 随机返回颜色代码
     * @return
     */
    public static Integer[] getRandColorCode() {
        Integer r, g, b;
        Random random = new Random();
        r = random.nextInt(256);
        g = random.nextInt(256);
        b = random.nextInt(256);

        return new Integer[] { r, g, b };
    }
}
