package com.thg.accelerator23.connectn.ai.good_ai_mate;

import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;

public class PositionWithScore {

    private Position position;
    private int score;

    public PositionWithScore(Position position, int score) {
        this.position = position;
        this.score = score;
    }

    public Position getPosition() {
        return position;
    }

    public int getScore() {
        return score;
    }

    public static ArrayList<PositionWithScore> getNextAvailablePositionsWithScores(Boardie boardie, ArrayList<Position> quadruplets) {
        ArrayList<PositionWithScore> availablePositionsWithScores = new ArrayList<>();
        ArrayList<Position> availablePositions = boardie.getNextAvailablePositions();

        for (Position position : availablePositions) {
            int score = boardie.getScore(quadruplets);
            // Add the position and its score to the list
            availablePositionsWithScores.add(new PositionWithScore(position, score));
        }

        return availablePositionsWithScores;
    }
}


