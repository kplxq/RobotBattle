package com.kxd.code.competition.control;

import com.kxd.code.competition.battle.AbstractRobotBattle;
import com.kxd.code.competition.draw.element.fight.FightResultDraw;
import com.kxd.code.competition.draw.jpanel.AbstractRobotBattleDrawJpanel;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by huangnx on 2017/12/21.
 */
public class FightGameControl extends AbstractGameControl {

    private FightResultDraw fightResultDraw;

    public FightGameControl(AbstractGameControlConfig gameControlConfig, AbstractRobotBattle robotBattle,
            AbstractRobotBattleDrawJpanel robotBattleDrawJpanel) {
        super(gameControlConfig, robotBattle, robotBattleDrawJpanel);
        this.fightResultDraw = new FightResultDraw(null, robotBattle.elementData, robotBattle.gameControlConfig);
    }

    /**
     * 返回键盘监听适配器
     * @return
     */
    @Override
    public KeyAdapter contructKeyAdapter() {
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int c = e.getKeyCode();
                switch (c) {
                case KeyEvent.VK_S:
                    // 开始PK
                    if (!gameControlConfig.gameStart) {
                        thread = new Thread(robotBattle);
                        thread.start();

                        gameControlConfig.gameStart = true;
                    }
                    break;
                case KeyEvent.VK_R:
                    // 重置
                    gameControlConfig.gameStart = false;
                    gameControlConfig.currentGameFinal = false;
                    gameControlConfig.currentRoundNum = 0;
                    robotBattle.reInit();
                    break;
                case KeyEvent.VK_ENTER:
                    if (gameControlConfig.currentGameFinal) {
                        fightResultDraw.drawResult();
                    }
                    break;
                case KeyEvent.VK_UP:
                    gameControlConfig.gameThreadSleepTime -= 10;
                    if (gameControlConfig.gameThreadSleepTime <= 10) {
                        gameControlConfig.gameThreadSleepTime = 5L;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    gameControlConfig.gameThreadSleepTime += 10;
                    break;
                default:
                }
                robotBattleDrawJpanel.repaint();
            }
        };
        return keyAdapter;
    }

}
