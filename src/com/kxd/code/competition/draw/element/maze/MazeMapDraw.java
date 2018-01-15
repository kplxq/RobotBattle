package com.kxd.code.competition.draw.element.maze;

import com.kxd.code.competition.draw.element.AbstractMapDraw;
import com.kxd.code.competition.entity.DrawArea;
import com.kxd.code.competition.entity.gameconfig.AbstractGameControlConfig;
import com.kxd.code.competition.entity.gameconfig.MazeGameControlConfig;
import com.kxd.code.competition.entity.lattice.MazeLattice;
import com.kxd.code.competition.map.AbstractMap;

import java.awt.*;

/**
 * Created by huangnx on 2017/12/19.
 */
public class MazeMapDraw extends AbstractMapDraw {

    public MazeMapDraw(DrawArea drawArea, AbstractMap map, int width, AbstractGameControlConfig gameControlConfig) {
        super(drawArea, map, width, gameControlConfig);
    }

    /**
     * 画迷宫地图
     */
    @Override
    public void paint() {
        // 画网格
        g.setColor(Color.black);
        for (int i = 0; i <= map.getSize(); i++) {
            g.drawLine(drawArea.begin.x + i * width, drawArea.begin.y, drawArea.begin.x + i * width, drawArea.end.y);
        }
        for (int j = 0; j <= map.getSize(); j++) {
            g.drawLine(drawArea.begin.x, drawArea.begin.y + j * width, drawArea.end.x, drawArea.begin.y + j * width);
        }

        // 解析迷宫树结构，画迷宫
        g.setColor(backGroundColor);
        MazeLattice[][] mazeLattices = (MazeLattice[][]) map.getMap();
        for (int i = map.getSize() - 1; i >= 0; i--) {
            for (int j = map.getSize() - 1; j >= 0; j--) {
                MazeLattice f = mazeLattices[i][j].getFather();
                if (f != null) {
                    int fx = f.getX(), fy = f.getY();
                    clearFence(i, j, fx, fy, g);
                }
            }
        }

        // 画迷宫的出入口
        int lastX = drawArea.begin.x + map.getSize() * width;
        int lastY = drawArea.begin.y + map.getSize() * width;
        g.drawLine(drawArea.begin.x, drawArea.begin.y + 1, drawArea.begin.x, drawArea.begin.y + width - 1);
        g.drawLine(lastX, lastY - 1, lastX, lastY - width + 1);

        // 画最优路径
        MazeGameControlConfig mazeGameControlConfig = (MazeGameControlConfig) gameControlConfig;

        if (mazeGameControlConfig.isDrawPath()) {
            drawPath(g, backGroundColor);
        }
    }

    private void clearFence(int i, int j, int fx, int fy, Graphics g) {
        int sx = drawArea.begin.x + ((j > fy ? j : fy) * width);
        int sy = drawArea.begin.y + ((i > fx ? i : fx) * width);
        int dx = (i == fx ? sx : sx + width);
        int dy = (i == fx ? sy + width : sy);
        if (sx != dx) {
            sx++;
            dx--;
        } else {
            sy++;
            dy--;
        }
        g.drawLine(sx, sy, dx, dy);
    }

    /**
     * 画最优路径
     * @param g
     * @param backGroundColor
     */
    private void drawPath(Graphics g, Color backGroundColor) {
        Color PATH_COLOR = Color.ORANGE, BOTH_PATH_COLOR = Color.PINK;

        g.setColor(PATH_COLOR);

        MazeLattice[][] mazeLattices = (MazeLattice[][]) map.getMap();

        // 从迷宫出口开始回朔，标记迷宫树的root节点，并将路线上的格子标记成2
        MazeLattice p = mazeLattices[map.getSize() - 1][map.getSize() - 1];
        while (p.getFather() != null) {
            p.setFlag(2);
            p = p.getFather();
        }
        g.fillOval(getCenterX(p.getY()) - width / 3, getCenterY(p.getX()) - width / 3, width / 2, width / 2);

        // 从迷宫入口开始回朔，如果路线上的格子已被标记为2，则格子标记为3；画线
        p = mazeLattices[0][0];
        while (p.getFather() != null) {
            if (p.getFlag() == 2) {
                p.setFlag(3);
                g.setColor(BOTH_PATH_COLOR);
            }
            g.drawLine(getCenterX(p.getY()), getCenterY(p.getX()), getCenterX(p.getFather().getY()),
                    getCenterY(p.getFather().getX()));
            p = p.getFather();
        }

        // 从迷宫出口回朔，父节点标记不是3的，画线
        g.setColor(PATH_COLOR);
        p = mazeLattices[map.getSize() - 1][map.getSize() - 1];
        while (p.getFather() != null) {
            if (p.getFlag() == 3) {
                break;
            }
            g.drawLine(getCenterX(p.getY()), getCenterY(p.getX()), getCenterX(p.getFather().getY()),
                    getCenterY(p.getFather().getX()));
            p = p.getFather();
        }

    }

}
