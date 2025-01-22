package com.thg.accelerator21.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thg.accelerator23.connectn.ai.good_ai_mate.Boardie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardieTest {
    @Test
    public void testBoardieFreeCols() {
        GameConfig myConfig = new GameConfig(10,8,4);
        Board myBoard = new Board(myConfig);
        Boardie boardie = new Boardie(myBoard, Counter.X);
        int[] expectedCols = new int[]{0,1,2,3,4,5,6,7,8,9};
        int[] actualCols = boardie.getFreeColumns();
        for (int i = 0; i < expectedCols.length; i++) {
            assertEquals(expectedCols[i], actualCols[i]);
        }
    }
    @Test
    public void testBoardieLowestFreeRowAndPrint() {
        GameConfig myConfig = new GameConfig(10,8,4);
        Board myBoard = new Board(myConfig);
        Boardie boardie = new Boardie(myBoard, Counter.X);
        for (int i = 0; i < myConfig.getWidth(); i++){
            assertEquals(7,boardie.getLowestFreeRow(i));
        }
        boardie.claimLocation(0,7, 1);
        byte expected = 6;
        assertEquals(expected,boardie.getLowestFreeRow(0));
        boardie.claimLocation(0,6, 2);
        expected = 5;
        assertEquals(expected,boardie.getLowestFreeRow(0));
    }

    @Test
    public void testNoSpaceInColumn() {
        GameConfig myConfig = new GameConfig(10,8,4);
        Board myBoard = new Board(myConfig);
        Boardie boardie = new Boardie(myBoard, Counter.X);
        boardie.claimLocation(0,7, 2);
        boardie.claimLocation(0,6, 2);
        boardie.claimLocation(0,5, 2);
        boardie.claimLocation(0,4, 2);
        boardie.claimLocation(0,3, 2);
        boardie.claimLocation(0,2, 2);
        boardie.claimLocation(0,1, 2);
        boardie.claimLocation(0,0, 2);
        assertEquals(-1,boardie.getLowestFreeRow(0));
    }
    @Test
    public void testBoardieFromBoardieWithCounter() {
        GameConfig myConfig = new GameConfig(10,8,4);
        Board myBoard = new Board(myConfig);
        Boardie boardie = new Boardie(myBoard, Counter.X);
        boardie.claimLocation(0,7, 1);
        Boardie boardie2 = new Boardie(boardie);
        boardie2.claimLocation(1,7, 2);
        System.out.println(boardie.prettyPrint());
        System.out.println(boardie2.prettyPrint());
        assertEquals(2,boardie2.getLocationValue(1,7));
        assertEquals(2,boardie2.getNumberOfFilledPositions());
        boardie2.claimLocation(2,7, 2);
        assertEquals(3,boardie2.getNumberOfFilledPositions());
        boardie2.claimLocation(2,7, 1);
        assertEquals(3,boardie2.getNumberOfFilledPositions());
        boardie2.claimLocation(2,7, 0);
        assertEquals(2,boardie2.getNumberOfFilledPositions());
    }
}
