package com.thg.accelerator21.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.good_ai_mate.Boardie;
import com.thg.accelerator23.connectn.ai.good_ai_mate.GoodAiMate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetScoreTest {
    @Test
    public void test4InARowScore() {
        GoodAiMate ai = new GoodAiMate(Counter.X);
        GameConfig myConfig = new GameConfig(10,8,4);
        Board myBoard = new Board(myConfig);
        Boardie myBoardie = new Boardie(myBoard, Counter.X);
        myBoardie.claimLocation(0,7,1);
        myBoardie.claimLocation(1,7,1);
        myBoardie.claimLocation(2,7,1);
        myBoardie.claimLocation(3,7,1);
        System.out.println(myBoardie.prettyPrint());
        assertEquals(2000010, myBoardie.getScore(ai.quadruplets));
    }
    @Test
    public void test4alternatingScore() {
        GoodAiMate ai = new GoodAiMate(Counter.X);
        GameConfig myConfig = new GameConfig(10,8,4);
        Board myBoard = new Board(myConfig);
        Boardie myBoardie = new Boardie(myBoard, Counter.X);
        myBoardie.claimLocation(0,7,1);
        myBoardie.claimLocation(1,7,2);
        myBoardie.claimLocation(2,7,1);
        myBoardie.claimLocation(3,7,2);
        System.out.println(myBoardie.prettyPrint());
        ai.setHorizontalQuadruplets(10,8,4);
        ai.setVerticalQuadruplets(10,8,4);
        ai.setNegativeDiagonalQuadruplets(10,8,4);
        ai.setPositiveDiagonalQuadruplets(10,8,4);
        System.out.println(ai.quadruplets.size());
        assertEquals(2000010, myBoardie.getScore(ai.quadruplets));
    }
}
