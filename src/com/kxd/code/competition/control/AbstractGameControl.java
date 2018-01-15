package com.kxd.code.competition.control;

import com.kxd.code.competition.battle.AbstractRobotBattle;
import com.kxd.code.competition.draw.jpanel.AbstractRobotBattleDrawJpanel;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by huangnx on 2017/12/20.
 */
public abstract class AbstractGameControl {

    protected AbstractGameControlConfig     gameControlConfig;

    protected AbstractRobotBattle           robotBattle;

    protected AbstractRobotBattleDrawJpanel robotBattleDrawJpanel;

    protected Thread                        thread;

    public AbstractGameControl(AbstractGameControlConfig gameControlConfig, AbstractRobotBattle robotBattle,
            AbstractRobotBattleDrawJpanel robotBattleDrawJpanel) {
        this.gameControlConfig = gameControlConfig;
        this.robotBattle = robotBattle;
        this.robotBattleDrawJpanel = robotBattleDrawJpanel;
    }

    /**
     * 返回键盘监听适配器
     * @return
     */
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
                    robotBattle.reInit();
                    break;
                case KeyEvent.VK_UP:
                    gameControlConfig.gameThreadSleepTime -= 10;
                    if (gameControlConfig.gameThreadSleepTime <= 10) {
                        gameControlConfig.gameThreadSleepTime = 10L;
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
