package com.thg.accelerator23.connectn.ai.good_ai_mate;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;

import java.util.ArrayList;
import java.util.Objects;

public class Boardie {
    //Board data stored in [x] (col) [y] (row) format!!!
    byte[][] boardData;
    //Player 1 = bot, player 2 = opposition.
    Counter botCounter;
    byte botByte;
    Counter oppCounter;
    byte oppByte;
    int width;
    int height;

    public Boardie(Board board, Counter myCounter) {
        this.botCounter = myCounter;
        this.oppCounter = botCounter.getOther();
        this.botByte = 1;
        this.oppByte = 2;
        this.width = board.getConfig().getWidth();
        this.height = board.getConfig().getHeight();
        Counter[][] counterArray = board.getCounterPlacements();
        this.boardData = new byte[counterArray.length][counterArray[0].length];
        for (int i = 0; i < counterArray.length; i++) {
            for (int j = 0; j < counterArray[i].length; j++) {
                if (counterArray[i][j] == this.botCounter) {
                    boardData[i][j] = 1;
                } else if (counterArray[i][j] == this.oppCounter) {
                    boardData[i][j] = 2;
                } else {
                    boardData[i][j] = 0;
                }
            }
        }
    }

    public Boardie(Boardie existingBoardie) {
        this.botCounter = existingBoardie.botCounter;
        this.oppCounter = existingBoardie.oppCounter;
        this.width = existingBoardie.width;
        this.height = existingBoardie.height;
        this.boardData = new byte[existingBoardie.boardData.length][existingBoardie.boardData[0].length];
        //Copying the 2D array is more complex - we need to make sure it's a deep copy...
        for (int i = 0; i < this.width; i++) {
            System.arraycopy(existingBoardie.boardData[i], 0, this.boardData[i], 0, this.height);
        }
        this.botByte = existingBoardie.botByte;
        this.oppByte = existingBoardie.oppByte;
    }

    public int[] getFreeColumns() {
        ArrayList<Integer> freeColumns = new ArrayList<Integer>();
        for (int i = 0; i < boardData.length; i++) {
            if (boardData[i][0] == 0) { //We only need to check if the first row is empty.
                freeColumns.add(i);
            }
        }
        return freeColumns.stream().mapToInt(i -> i).toArray(); //Not sure why this is necessary. Thx intelliJ.
    }

    public int getLowestFreeRow(int column) {
        for (int i = boardData[column].length - 1; i >= 0 ; i--) {
            if (boardData[column][i] == 0) {
                return i;
            }
        }
        return -1;      //If no free slot was found in a  column.
    }

    public void claimLocation(int column, int row, int player) {
        if (player == 1) {
            boardData[column][row] = this.botByte;
        } else if (player == 2) {
            boardData[column][row] = this.oppByte;
        } else {
            boardData[column][row] = 0;
        }

    }

    public short getLocationValue(int column, int row) {
        return boardData[column][row];
    }

    public String prettyPrint() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < boardData[0].length; x++) {
            for (int y = 0; y < boardData.length; y++) {
                if (boardData[y][x] == 1) {
                    sb.append(" ").append(this.botCounter).append(" ");
                } else if (boardData[y][x] == 2) {
                    sb.append(" ").append(this.oppCounter).append(" ");
                } else {
                    sb.append(" _ ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
