package com.kxd.code.competition.main;

import com.kxd.code.competition.battle.AbstractRobotBattle;
import com.kxd.code.competition.battle.RobotFightBattle;
import com.kxd.code.competition.battle.RobotMazeBattle;
import com.kxd.code.competition.control.AbstractGameControl;
import com.kxd.code.competition.control.FightGameControl;
import com.kxd.code.competition.control.MazeGameControl;
import com.kxd.code.competition.draw.jpanel.AbstractRobotBattleDrawJpanel;
import com.kxd.code.competition.draw.jpanel.RobotFightBattleDrawJpanel;
import com.kxd.code.competition.draw.jpanel.RobotMazeBattleDrawJpanel;
import com.kxd.code.competition.entity.element.AbstractElementsData;
import com.kxd.code.competition.entity.element.FightElementsData;
import com.kxd.code.competition.entity.element.MazeElementsData;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.entity.gameconfig.FightGameControlConfig;
import com.kxd.code.competition.entity.gameconfig.MazeGameControlConfig;
import com.kxd.code.competition.map.AbstractMap;
import com.kxd.code.competition.map.FightMap;
import com.kxd.code.competition.map.Maze;
import com.kxd.code.competition.robot.AbstractRobot;
import com.kxd.code.competition.robot.entity.AbstractRobotBaseInfo;
import com.kxd.code.competition.robot.fight.*;
import com.kxd.code.competition.robot.maze.DemoMazeRobot;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangnx on 2017/12/18.
 */
public class RobotBattleMain {

    public final static void main(String[] args) {
        fightBattle(args);
    }

    /**
     * 迷宫大逃亡
     * @param args
     */
    private final static void mazeBattle(String[] args) {
        final int mazeSize = 60, width = 660, padding = 30;

        // 初始化地图
        Maze maze = new Maze(mazeSize);

        AbstractElementsData mazeElementData = new MazeElementsData();
        {
            // 初始化参赛机器人
            List<AbstractRobot> mazeRobots = new ArrayList<AbstractRobot>();
            mazeRobots.add(new DemoMazeRobot());

            // 初始化机器人server属性
            Map<String, AbstractRobotBaseInfo> robotPropertiesMap = new HashMap<>(mazeRobots.size());

            mazeElementData.setRobotDatas(mazeRobots, robotPropertiesMap);
        }

        // 初始化游戏控制参数
        long sleepTime = 180 - mazeElementData.robots.size() * 10;
        sleepTime = sleepTime <= 50 ? 50 : sleepTime;
        AbstractGameControlConfig mazeGameControlConfig = new MazeGameControlConfig(10000, sleepTime);

        // 初始化可视化界面
        AbstractRobotBattleDrawJpanel robotMazeBattleDrawJpanel = new RobotMazeBattleDrawJpanel(
                (width - 2 * padding) / mazeSize, padding, maze, mazeElementData, mazeGameControlConfig);

        // 创建一场战役
        AbstractRobotBattle robotMazeBattle = new RobotMazeBattle(maze, mazeElementData, robotMazeBattleDrawJpanel,
                mazeGameControlConfig);
        robotMazeBattle.reInit();

        // 初始化战役控制器
        AbstractGameControl mazeGameControl = new MazeGameControl(mazeGameControlConfig, robotMazeBattle,
                robotMazeBattleDrawJpanel);

        robotMazeBattleDrawJpanel.setKeyListener(mazeGameControl.contructKeyAdapter());

        // 呈现游戏界面
        JFrame frame = new JFrame(mazeGameControlConfig.gameTitle);
        frame.getContentPane().add(robotMazeBattleDrawJpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, width + 2 * padding);
        frame.setLocation(300, 60);
        frame.setVisible(true);
    }

    /**
     * 机器人大作战
     * @param args
     */
    private final static void fightBattle(String[] args) {
        final int mapSize = 20, width = 630, padding = 40;

        // 初始化地图
        AbstractMap fightMap = new FightMap(mapSize);

        AbstractElementsData fightElementData = new FightElementsData();
        {
            // 初始化参赛机器人
            List<AbstractRobot> robots = new ArrayList<AbstractRobot>();
            
            robots.add(new DemoFightRobot("demo1"));
            

            // 初始化机器人server属性
            Map<String, AbstractRobotBaseInfo> robotPropertiesMap = new HashMap<>(robots.size());

            fightElementData.setRobotDatas(robots, robotPropertiesMap);
        }

        // 初始化游戏控制参数
        long sleepTime = 180 - fightElementData.robots.size() * 10;
        sleepTime = sleepTime <= 50 ? 50 : sleepTime;
        AbstractGameControlConfig gameControlConfig = new FightGameControlConfig(10000, 50, sleepTime, 10, 1, 1);

        // 初始化可视化界面
        AbstractRobotBattleDrawJpanel robotMazeBattleDrawJpanel = new RobotFightBattleDrawJpanel(
                (width - 2 * padding) / mapSize, padding, fightMap, fightElementData, gameControlConfig);

        // 创建一场战役
        AbstractRobotBattle robotMazeBattle = new RobotFightBattle(fightMap, fightElementData,
                robotMazeBattleDrawJpanel, gameControlConfig);
        robotMazeBattle.reInit();

        // 初始化战役控制器
        AbstractGameControl fightGameControl = new FightGameControl(gameControlConfig, robotMazeBattle,
                robotMazeBattleDrawJpanel);
        robotMazeBattleDrawJpanel.setKeyListener(fightGameControl.contructKeyAdapter());

        // 呈现游戏界面
        JFrame frame = new JFrame(gameControlConfig.gameTitle);
        frame.getContentPane().add(robotMazeBattleDrawJpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width + 10 * padding, width + 8 * padding);
        frame.setLocation(50, 10);
        frame.setVisible(true);
    }
}
