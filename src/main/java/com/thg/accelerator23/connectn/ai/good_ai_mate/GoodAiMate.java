package com.thg.accelerator23.connectn.ai.good_ai_mate;

import com.thehutgroup.accelerator.connectn.player.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class GoodAiMate extends Player {
  //Fields in refactored version
  private final ArrayList<Position> quadruplets;
  private int boardWidth;
  private int boardHeight;

  //Fields we only want to calculate once (will this work or the player instantiated each time?)
  //private final ArrayList<Position> quadruplets;
  private final Counter botPiece;
  private final Counter oppPiece;
  private int width;
  private int height;
  private int depth;
  private Integer alpha;
  private Integer beta;

  public GoodAiMate(Counter counter) {
    //TODO: fill in your name here
    super(counter, GoodAiMate.class.getName());
    this.quadruplets = setHorizontalQuadruplets(10, 8, 4);
    quadruplets.addAll(setVerticalQuadruplets(10, 8, 4));
    quadruplets.addAll(setPositiveDiagonalQuadruplets(10, 8, 4));
    quadruplets.addAll(setNegativeDiagonalQuadruplets(10, 8, 4));
    this.botPiece = counter;
    if (counter == Counter.X) {
      this.oppPiece = Counter.O;
    } else {
      this.oppPiece = Counter.X;
    }
    this.depth = 2;
    this.alpha = Integer.MIN_VALUE;
    this.beta = Integer.MAX_VALUE;
  }

  private ArrayList<Position> setHorizontalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> horizontalQuadruplets = new ArrayList<>();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col <= width - nToWin; col++) {
        horizontalQuadruplets.add(new Position(col, row));
        horizontalQuadruplets.add(new Position(col + 1, row));
        horizontalQuadruplets.add(new Position(col + 2, row));
        horizontalQuadruplets.add(new Position(col + 3, row));
      }
    }
    return horizontalQuadruplets;
  }

  private ArrayList<Position> setVerticalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> verticalQuadruplets = new ArrayList<>();
    for (int col = 0; col < width; col++) {
      for (int row = 0; row <= height - nToWin; row++) {
        verticalQuadruplets.add(new Position(col, row));
        verticalQuadruplets.add(new Position(col, row + 1));
        verticalQuadruplets.add(new Position(col, row + 2));
        verticalQuadruplets.add(new Position(col, row + 3));
      }
    }
    return verticalQuadruplets;
  }

  private ArrayList<Position> setPositiveDiagonalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> positiveDiagonalQuadruplets = new ArrayList<>();
    //Positive diagonals (col and row increase)
    for (int row = 0; row <= height - nToWin; row++) {
      for (int col = 0; col <= width - nToWin; col++) {
        positiveDiagonalQuadruplets.add(new Position(col, row));
        positiveDiagonalQuadruplets.add(new Position(col + 1, row + 1));
        positiveDiagonalQuadruplets.add(new Position(col + 2, row + 2));
        positiveDiagonalQuadruplets.add(new Position(col + 3, row + 3));
      }
    }
    return positiveDiagonalQuadruplets;
  }

  private ArrayList<Position> setNegativeDiagonalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> negativeDiagonalQuadruplets = new ArrayList<>();
    //Negative diagonals (col decreases, row increases)
    for (int col = width - 1; col >= 3; col--) {
      for (int row = 0; row <= height - nToWin; row++) {
        negativeDiagonalQuadruplets.add(new Position(col, row));
        negativeDiagonalQuadruplets.add(new Position(col - 1, row + 1));
        negativeDiagonalQuadruplets.add(new Position(col - 2, row + 2));
        negativeDiagonalQuadruplets.add(new Position(col - 3, row + 3));
      }
    }
    return negativeDiagonalQuadruplets;
  }

  public ArrayList<Position> getQuadruplets() {
    return this.quadruplets;
  }

  public int[] minimax(Boardie board, int depth, Integer alpha, Integer beta, int player, int depthCounter) {
    //Step 1: update the depth we're at
    int currentDepth = depthCounter + 1;
    //Step 2: gather the free column numbers (0-9), if any

    ArrayList<Position> availablePositions = board.getNextAvailablePositions();
    /*
    Step 4: get score of board including if there is a win/loss/draw
    Win = 1000000, loss = -1000000, draw = -999999. Anything else is fair game
    */
    int currentScore = board.getScore(this.quadruplets);
    /*
    Step 5: check if a) massive score means win/loss, b) available positions c) not hit depth limit
    This is the "base case" if no moves are left and should not occur in the first iteration anyway.
    */

    if (currentDepth == depth || currentScore > 1000000 || currentScore < -1000000 || availablePositions.isEmpty()) {
      return new int[]{availablePositions.get(0).getX(), currentScore}; //In this case, the game would be over.
    }
    //The next code only runs if we haven't reached the terminus...
    int bestColumn = availablePositions.get(availablePositions.size()-1).getX();
    int bestScore;
    if (player == 1 ) {
      bestScore = -9999999;
      for (int i = 0; i < availablePositions.size(); i++) { //Alternatively we could fill randomly.
        Boardie newBoardie = new Boardie(board);
        newBoardie.claimLocation(availablePositions.get(i).getX(), availablePositions.get(i).getY(), player);
        int nextPlayer = 2;
        int newScore = minimax(newBoardie, depth, alpha, beta, nextPlayer, currentDepth)[1];
          if (newScore > bestScore) {
            bestScore = newScore;
            bestColumn = availablePositions.get(i).getX();
          }
          alpha = Math.max(alpha, bestScore);
          if (alpha >= beta) {
            break;
          }
        }
      return new int[]{bestColumn, bestScore};
    } else {
      bestScore = 9999999;
      for (int i = 0; i < availablePositions.size(); i++) { //Alternatively we could fill randomly.
        Boardie newBoardie = new Boardie(board);
        newBoardie.claimLocation(availablePositions.get(i).getX(), availablePositions.get(i).getY(), player);
        int nextPlayer = 1;
        int newScore = minimax(newBoardie, depth, alpha, beta, nextPlayer, currentDepth)[1];
        if (newScore < bestScore) {
          bestScore = newScore;
          bestColumn = availablePositions.get(i).getX();
        }
        beta = Math.min(beta, bestScore);
        if (alpha >= beta) {
          break;
        }
      }
      return new int[]{bestColumn, bestScore};
    }
  }


  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    //Step 1: convert board data into a custom Boardie
    Boardie currentBoard = new Boardie(board, this.getCounter());
    //Step 2: run minimax on the current board setup (player 2 set first since they last played)
    int[] result = minimax(currentBoard, 8, this.alpha, this.beta, 1, 0);
    return result[0];
  }
}
