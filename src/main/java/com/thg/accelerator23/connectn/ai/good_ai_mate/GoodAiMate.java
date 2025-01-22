package com.thg.accelerator23.connectn.ai.good_ai_mate;

import com.thehutgroup.accelerator.connectn.player.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class GoodAiMate extends Player {
  //Fields we only want to calculate once (will this work or the player instantiated each time?)
  private final ArrayList<Position> quadruplets;
  private final Counter botPiece;
  private final Counter oppPiece;

  public GoodAiMate(Counter counter) {
    //TODO: fill in your name here
    super(counter, GoodAiMate.class.getName());
    this.quadruplets = setHorizontalQuadruplets(10,8,4);
    quadruplets.addAll(setVerticalQuadruplets(10,8,4));
    quadruplets.addAll(setPositiveDiagonalQuadruplets(10,8,4));
    quadruplets.addAll(setNegativeDiagonalQuadruplets(10,8,4));
    this.botPiece = counter;
    if (counter == Counter.X){
      this.oppPiece = Counter.O;
    } else {
      this.oppPiece = Counter.X;
    }
  }

  private ArrayList<Position> setHorizontalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> horizontalQuadruplets = new ArrayList<>();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col <= width - nToWin; col++) {
        horizontalQuadruplets.add(new Position(col,row));
        horizontalQuadruplets.add(new Position(col+1,row ));
        horizontalQuadruplets.add(new Position(col+2,row));
        horizontalQuadruplets.add(new Position(col+3,row ));
        //Window currWindow = new Window(board.getCounterAtPosition(position1),board.getCounterAtPosition(position2),board.getCounterAtPosition(position3),board.getCounterAtPosition(position4));
      }
    }
    return horizontalQuadruplets;
  }

  private ArrayList<Position> setVerticalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> verticalQuadruplets = new ArrayList<>();
    for (int col = 0; col < width; col++){
      for (int row = 0; row <= height - nToWin; row++) {
        verticalQuadruplets.add(new Position(col, row));
        verticalQuadruplets.add(new Position(col,row + 1));
        verticalQuadruplets.add(new Position(col,row + 2));
        verticalQuadruplets.add(new Position(col,row + 3));
      }
    }
    return verticalQuadruplets;
  }

  private ArrayList<Position> setPositiveDiagonalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> positiveDiagonalQuadruplets = new ArrayList<>();
    //Positive diagonals (col and row increase)
    for (int row = 0; row <= height - nToWin; row++) {
      for (int col = 0; col <= width - nToWin; col++) {
        positiveDiagonalQuadruplets.add(new Position(col,row));
        positiveDiagonalQuadruplets.add(new Position(col + 1,row + 1));
        positiveDiagonalQuadruplets.add(new Position(col + 2,row + 2));
        positiveDiagonalQuadruplets.add(new Position(col + 3,row + 3));
      }
    }
    return positiveDiagonalQuadruplets;
  }

  private ArrayList<Position> setNegativeDiagonalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> negativeDiagonalQuadruplets = new ArrayList<>();
    //Negative diagonals (col decreases, row increases)
    for (int col = width - 1; col >= 3; col--) {
      for (int row = 0; row <= height - nToWin; row++) {
        negativeDiagonalQuadruplets.add(new Position(col,row));
        negativeDiagonalQuadruplets.add(new Position(col-1,row+1));
        negativeDiagonalQuadruplets.add(new Position(col-2,row+2));
        negativeDiagonalQuadruplets.add(new Position(col-3,row+3));
      }
    }
    return negativeDiagonalQuadruplets;
  }

  public int getScore(Board board) {
    int score = 0;
    for (int i = 0; i < this.quadruplets.size() / 4; i++){
      Window currWindow = new Window(board.getCounterAtPosition(quadruplets.get(i*4)),board.getCounterAtPosition(quadruplets.get(i*4+1)),board.getCounterAtPosition(quadruplets.get(i*4+2)),board.getCounterAtPosition(quadruplets.get(i*4+3)));
      score = score + currWindow.getWindowScore(this.botPiece,this.oppPiece);
    }
    return score;
  }

  private ArrayList<Position> validPositions(Board board){
    ArrayList<Position> validPositions = new ArrayList<>();
    for (int x = 0; x < 10; x++){
      for (int y = 0; y < 8; y++){
        Position currPosition = new Position(x, y);
        if (board.hasCounterAtPosition(currPosition)){
          validPositions.add(currPosition);
        }
      }
    }
    return validPositions;
  }

  private int getRandomCol(int max){
    Random rand = new Random();
    return rand.nextInt(max);
  }

  private int getMinVacantY(int x, int height, Board board) {
    for (int i = height - 1; i >= 0; --i) {
      if (i == 0 || board.getCounterPlacements()[x][i - 1] != null) {
        return i;
      }
    }
    return -1;
  }

  public int[] minimax(Board board, int depth, int alpha, int beta, boolean maximisingPlayer) {
    ArrayList<Position> validPositions = validPositions(board);
    ArrayList<Integer> validColumns = new ArrayList<>();
    for (Position validPosition : validPositions) {
      validColumns.add(validPosition.getX());
    }
    int score = getScore(board);
    if (score > 900000 || score < -900000 || validPositions.isEmpty() || depth == 0) {
      if (score > 900000 || score < -900000) {
        return new int[]{-1, score};
      } else {
        return new int[]{-1, 0};
      }
    }
    if (maximisingPlayer) {
      int currValue = -1000000;
      int currColumn = validColumns.get(getRandomCol(validColumns.size()));
      ArrayList<Position> nextPositions = new ArrayList<>();
      for (int col : validColumns) {
        int row = getMinVacantY(col, 8, board);
        if (row != -1) {
          Position currPosition = new Position(col, row);
          nextPositions.add(new Position(col, row));
          try {
            Board b_copy = new Board(board, currPosition.getX(), this.botPiece);
            int[] newColValue = minimax(b_copy, depth - 1, alpha, beta, false);
            if (newColValue[1] > currValue) {
              currValue = newColValue[1];
              currColumn = newColValue[0];
            }
          } catch (InvalidMoveException ignored) {
            ;
          }
        }
      }
      return new int[]{currColumn, currValue};
    }
    else {
      int currValue = 1000000;
      int currColumn = validColumns.get(getRandomCol(validColumns.size()));;
      ArrayList<Position> nextPositions = new ArrayList<>();
      for (int col : validColumns) {
        int row = getMinVacantY(col, 8, board);
        if (row != -1) {
          Position currPosition = new Position(col, row);
          nextPositions.add(new Position(col, row));
          try {
            Board b_copy = new Board(board, currPosition.getX(), this.oppPiece);
            int[] newColValue = minimax(b_copy, depth - 1, alpha, beta, true);
            if (newColValue[1] > currValue) {
              currValue = newColValue[1];
              currColumn = newColValue[0];
            }
          } catch (InvalidMoveException ignored) {
            ;
          }
        }
      }
      return new int[]{currColumn, currValue};
    }
  }

    @Override
    public int makeMove (Board board){
      //TODO: some crazy analysis
      //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
      return 4;
    }
  }
