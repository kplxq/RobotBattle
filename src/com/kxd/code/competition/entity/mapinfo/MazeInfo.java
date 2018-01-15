package com.kxd.code.competition.entity.mapinfo;

import com.kxd.code.competition.entity.Location;

/**
 * Created by huangnx on 2017/12/21.
 */
public class MazeInfo extends AbstractMapInfo {

    public final Location finalLocation;

    public MazeInfo(Integer size, Location finalLocation) {
        super(size);
        this.finalLocation = finalLocation;
    }

    @Override
    public MazeInfo clone() {
        MazeInfo mazeInfo = new MazeInfo(this.size, this.finalLocation.clone());
        return mazeInfo;
    }
}
