package com.thg.accelerator21.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thg.accelerator23.connectn.ai.good_ai_mate.GoodAiMate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MiniMaxTest {
    @Test
    public void testMiniMax() {
        GoodAiMate ai = new GoodAiMate(Counter.X);
        System.out.println("setting config");
        GameConfig myConfig = new GameConfig(10,8,4);
        System.out.println("creating test matrix");
        Counter[][] matrix = new Counter[10][8];        //Note! It works in columns then rows in Board.
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 10; x++) {
                matrix[x][y] = null;
            }
        }
        System.out.println("creating test matrix");
        ai.prettyPrint(matrix);
        matrix[0][7] = Counter.X;
        matrix[1][7] = Counter.X;
        Board myBoard = new Board(matrix,myConfig);
        ai.minimax(myBoard,2,1,1,true,0);
    }
}
