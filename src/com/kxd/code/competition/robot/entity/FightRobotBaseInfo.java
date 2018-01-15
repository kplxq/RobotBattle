package com.kxd.code.competition.robot.entity;

import com.kxd.code.competition.entity.AttractRecord;
import com.kxd.code.competition.entity.Location;
import com.kxd.code.competition.entity.element.child.BloodBag;
import com.kxd.code.competition.entity.element.child.Landmine;

import java.awt.*;

/**
 * Created by huangnx on 2017/12/22.
 */
public class FightRobotBaseInfo extends AbstractRobotBaseInfo {

    /**
     * 血量
     */
    public Integer bloodNum;

    public Integer stepOnLandmineNum = 0;

    public Integer getBloodBagNum    = 0;

    public Integer attackRobotNum    = 0;

    public Integer deadRound         = 0;

    public FightRobotBaseInfo(String name, Color color, Location location, Integer bloodNum) {
        super(name, color, location);
        this.bloodNum = bloodNum;
    }

    @Override
    public final FightRobotBaseInfo clone() {
        FightRobotBaseInfo fightRobotBaseInfo = new FightRobotBaseInfo(this.name, this.color,
                this.currentLocation.clone(), this.bloodNum.intValue());
        fightRobotBaseInfo.currentCanStepNum = this.currentCanStepNum.intValue();

        fightRobotBaseInfo.stepOnLandmineNum = this.stepOnLandmineNum.intValue();
        fightRobotBaseInfo.getBloodBagNum = this.getBloodBagNum.intValue();
        fightRobotBaseInfo.attackRobotNum = this.attackRobotNum.intValue();
        fightRobotBaseInfo.deadRound = this.deadRound.intValue();

        return fightRobotBaseInfo;
    }

    public AttractRecord attractRobot(FightRobotBaseInfo fightRobot, Integer roundNum) {
        this.attackRobotNum += 1;
        // this.optBloodAdd(1);
        fightRobot.beAttracted(roundNum);
        return new AttractRecord(this.name, fightRobot.name);
    }

    public void beAttracted(Integer roundNum) {
        this.optBloodSub(2, roundNum);
        this.currentCanStepNum = 2;
    }

    /**
     * 踩地雷
     * @param landmine
     * @param roundNum
     */
    public void stepOnLandmine(Landmine landmine, Integer roundNum) {
        this.optBloodSub(landmine.bloodNum, roundNum);
        this.stepOnLandmineNum += 1;
    }

    /**
     * 获得血包
     * @param bloodBag
     */
    public void getBloodBag(BloodBag bloodBag) {
        this.optBloodAdd(bloodBag.bloodNum);
        this.getBloodBagNum += 1;
    }

    private void optBloodAdd(Integer blood) {
        this.bloodNum += blood;
    }

    private void optBloodSub(Integer blood, Integer roundNum) {
        this.bloodNum -= blood;
        if (this.bloodNum <= 0) {
            this.bloodNum = 0;
            this.deadRound = roundNum;
        }
    }

}
