package com.thg.accelerator21.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thg.accelerator23.connectn.ai.good_ai_mate.Boardie;
import com.thg.accelerator23.connectn.ai.good_ai_mate.GoodAiMate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MiniMaxTest {
    @Test
    public void testMiniMaxEmpty() {
        GoodAiMate ai = new GoodAiMate(Counter.X);
        System.out.println("setting config");
        GameConfig myConfig = new GameConfig(10,8,4);
        Board newBoard = new Board(myConfig);
        System.out.println(ai.makeMove(newBoard));
    }
    @Test
    public void testmostofFirstRowFilled() {
        GoodAiMate ai = new GoodAiMate(Counter.X);
        System.out.println("setting config");
        GameConfig myConfig = new GameConfig(10,8,4);
        Board newBoard = new Board(myConfig);
        Boardie newBoardie = new Boardie(newBoard,Counter.X);
        newBoardie.claimLocation(0,7,1);
        newBoardie.claimLocation(0,6,1);
        newBoardie.claimLocation(0,5,1);
        assertEquals(4,newBoardie.getLowestFreeRow(0));
    }
    @Test
    public void testMiniMaxOneRowEmpty() {
        GoodAiMate ai = new GoodAiMate(Counter.X);
        System.out.println("setting config");
        GameConfig myConfig = new GameConfig(10,8,4);
        Counter[][] testMatrix = new Counter[10][8];
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j < 8; j++) {
                if (j == 1){
                    if (i % 2 == 0) {
                        testMatrix[i][j] = Counter.X;
                    } else {
                        testMatrix[i][j] = Counter.O;
                    }
                } else if (j % 2 == 1) {
                    if (i % 2 == 0) {
                        testMatrix[i][j] = Counter.X;
                    } else {
                        testMatrix[i][j] = Counter.O;
                    }
                } else {
                    if (i % 2 == 0) {
                        testMatrix[i][j] = Counter.O;
                    } else {
                        testMatrix[i][j] = Counter.X;
                    }
                }
            }
        }
        Board newBoard = new Board(testMatrix,myConfig);
        System.out.println(ai.makeMove(newBoard));
    }
}
