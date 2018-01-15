package com.kxd.code.competition.entity.element.child;

import com.kxd.code.competition.constants.ElementTypeEnum;
import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.element.AbstractElement;

/**
 * Created by huangnx on 2017/12/22.
 */
public class Landmine extends AbstractElement {

    public Location location;

    /**
     * 血量
     */
    public Integer  bloodNum;

    public Landmine(Location location, Integer bloodNum) {
        super(ElementTypeEnum.LANDMINE);
        this.location = location;
        this.bloodNum = bloodNum;
    }

    @Override
    public Landmine clone() {
        Landmine landmine = new Landmine(location.clone(), this.bloodNum.intValue());
        return landmine;
    }
}
