package com.kxd.code.competition.control;

import com.kxd.code.competition.battle.AbstractRobotBattle;
import com.kxd.code.competition.draw.jpanel.AbstractRobotBattleDrawJpanel;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.entity.gameconfig.MazeGameControlConfig;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by huangnx on 2017/12/19.
 */
public class MazeGameControl extends AbstractGameControl {

    public MazeGameControl(AbstractGameControlConfig gameControlConfig, AbstractRobotBattle robotBattle,
            AbstractRobotBattleDrawJpanel robotBattleDrawJpanel) {
        super(gameControlConfig, robotBattle, robotBattleDrawJpanel);
    }

    @Override
    public KeyAdapter contructKeyAdapter() {
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                MazeGameControlConfig mazeGameControllConfig = (MazeGameControlConfig) gameControlConfig;

                int c = e.getKeyCode();
                switch (c) {
                case KeyEvent.VK_SPACE:
                    // 是否展示最优路径
                    mazeGameControllConfig.setDrawPath(!mazeGameControllConfig.isDrawPath());
                    break;
                case KeyEvent.VK_S:
                    // 开始PK
                    if (!mazeGameControllConfig.gameStart) {
                        thread = new Thread(robotBattle);
                        thread.start();

                        mazeGameControllConfig.gameStart = true;
                    }
                    break;
                case KeyEvent.VK_R:
                    // 重置
                    mazeGameControllConfig.gameStart = false;
                    robotBattle.reInit();
                    break;
                default:
                }
                robotBattleDrawJpanel.repaint();
            }
        };
        return keyAdapter;
    }

}
