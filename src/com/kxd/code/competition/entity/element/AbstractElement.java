package com.kxd.code.competition.entity.element;

import com.kxd.code.competition.constants.ElementTypeEnum;

/**
 * Created by huangnx on 2017/12/21.
 */
public abstract class AbstractElement {

    public final ElementTypeEnum elementType;

    public AbstractElement(ElementTypeEnum elementType) {
        this.elementType = elementType;
    }

    /**
     * 需实现深克隆
     * @return
     */
    @Override
    public abstract AbstractElement clone();

}
