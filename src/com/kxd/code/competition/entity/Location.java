package com.kxd.code.competition.entity;

/**
 * Created by huangnx on 2017/12/20.
 */
public class Location {

    public int x;

    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Location clone() {
        Location result = new Location(this.x, this.y);
        return result;
    }

    @Override
    public int hashCode() {
        return (this.x + this.y) % 100;
    }

    @Override
    public boolean equals(Object location) {
        if (location == null || !(location instanceof Location)) {
            return false;
        }

        Location locationT = (Location) location;
        return this.x == locationT.x && this.y == locationT.y;
    }

}
