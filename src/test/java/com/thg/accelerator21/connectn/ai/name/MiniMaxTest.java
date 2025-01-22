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
        GameConfig myConfig = new GameConfig(10,8,4);
        Counter[][] matrix = new Counter[][] {
                {null, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X},
                {Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O},
                {Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X},
                {Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O},
                {Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X},
                {Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O},
                {Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X},
                {Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O, Counter.X, Counter.O}
        };
        Board myBoard = new Board(matrix,myConfig);
        ai.minimax(myBoard,1,1,1,true);
    }
}
