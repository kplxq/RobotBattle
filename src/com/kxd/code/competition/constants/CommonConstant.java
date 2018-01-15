package com.kxd.code.competition.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangnx on 2017/12/20.
 */
public class CommonConstant {

    /**
     * 顺序:上右下左
     */
    public final static List<Integer>               directXY    = new ArrayList<Integer>() {
                                                                    {
                                                                        add(-1);
                                                                        add(0);
                                                                        add(1);
                                                                        add(0);
                                                                        add(-1);

                                                                    }
                                                                };

    /**
     * 顺序:上右下左
     */
    public final static List<MoveActionCommandEnum> directCodes = new ArrayList<MoveActionCommandEnum>() {
                                                                    {
                                                                        add(MoveActionCommandEnum.MOVE_TOP);
                                                                        add(MoveActionCommandEnum.MOVE_RIGHT);
                                                                        add(MoveActionCommandEnum.MOVE_DOWN);
                                                                        add(MoveActionCommandEnum.MOVE_LEFT);
                                                                    }

                                                                };

}
