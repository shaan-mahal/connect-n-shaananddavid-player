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
    public void test() {
        GoodAiMate ai = new GoodAiMate(Counter.X);
        GameConfig myConfig = new GameConfig(10,8,4);
        Board myBoard = new Board(myConfig);
        Boardie myBoardie = new Boardie(myBoard,ai.getCounter());
        myBoardie.claimLocation(0,7,1);
        myBoardie.claimLocation(0,6,1);
        myBoardie.claimLocation(0,5,1);
        System.out.println(myBoardie.prettyPrint());
        assertEquals(1062,myBoardie.getScore(ai.getQuadruplets()));
    }
}
