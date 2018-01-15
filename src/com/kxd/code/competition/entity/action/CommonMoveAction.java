package com.kxd.code.competition.entity.action;

import com.kxd.code.competition.constants.MoveActionCommandEnum;

/**
 * Created by huangnx on 2017/12/19.
 */
public class CommonMoveAction extends ActionEntity {

    public CommonMoveAction(MoveActionCommandEnum actionCommand) {
        super.actionCommand = actionCommand;
    }
}
