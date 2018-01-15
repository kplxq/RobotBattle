package com.kxd.code.competition.entity.element.child;

import com.kxd.code.competition.constants.ElementTypeEnum;
import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.element.AbstractElement;

/**
 * Created by huangnx on 2017/12/22.
 */
public class BloodBag extends AbstractElement {

    public Location location;

    /**
     * 血量
     */
    public Integer  bloodNum;

    public BloodBag(Location location, Integer bloodNum) {
        super(ElementTypeEnum.BLOOD_BAG);
        this.location = location;
        this.bloodNum = bloodNum;
    }

    @Override
    public BloodBag clone() {
        BloodBag bloodBag = new BloodBag(location.clone(), this.bloodNum.intValue());
        return bloodBag;
    }
}
