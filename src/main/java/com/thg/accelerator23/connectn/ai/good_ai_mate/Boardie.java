package com.thg.accelerator23.connectn.ai.good_ai_mate;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;

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
    int filledPositions;


    //Contructor for initial creation from a Board object
    public Boardie(Board board, Counter myCounter) {
        this.botCounter = myCounter;
        this.oppCounter = botCounter.getOther();
        this.botByte = 1;
        this.oppByte = 2;
        this.width = board.getConfig().getWidth();
        this.height = board.getConfig().getHeight();
        this.filledPositions = 0;
        Counter[][] counterArray = board.getCounterPlacements();
        this.boardData = new byte[counterArray.length][counterArray[0].length];
        for (int i = 0; i < counterArray.length; i++) {
            for (int j = 0; j < counterArray[i].length; j++) {
                if (counterArray[i][j] == this.botCounter) {
                    boardData[i][j] = 1;
                    this.filledPositions++;
                } else if (counterArray[i][j] == this.oppCounter) {
                    boardData[i][j] = 2;
                    this.filledPositions++;
                } else {
                    boardData[i][j] = 0;
                }
            }
        }
    }

    //Constructor when copying from another Boardie - it remembers things about the previous state to aid tail recursion
    public Boardie(Boardie existingBoardie) {
        this.botCounter = existingBoardie.botCounter;
        this.oppCounter = existingBoardie.oppCounter;
        this.width = existingBoardie.width;
        this.height = existingBoardie.height;
        this.boardData = new byte[existingBoardie.boardData.length][existingBoardie.boardData[0].length];
        this.filledPositions = existingBoardie.filledPositions;
        //Copying the 2D array is more complex - we need to make sure it's a deep copy...
        for (int i = 0; i < this.width; i++) {
            System.arraycopy(existingBoardie.boardData[i], 0, this.boardData[i], 0, this.height);
        }
        this.botByte = existingBoardie.botByte;
        this.oppByte = existingBoardie.oppByte;
    }

    public ArrayList<Integer> getFreeColumns() {
        ArrayList<Integer> freeColumns = new ArrayList<>();
        for (int i = 0; i < this.width; i++) {
            if (boardData[i][0] == 0) { //We only need to check if the first row is empty.
                freeColumns.add(i);
            }
        }
        return freeColumns;
    }

    public int getLowestFreeRow(int column) {
        for (int i = this.height - 1; i >= 0 ; i--) {
            if (boardData[column][i] == 0) {
                return i;
            }
        }
        return -1;      //If no free slot was found in a  column.
    }

    public void claimLocation(int column, int row, int player) {
        if (player == 1) {
            if (boardData[column][row] == 0){
                this.filledPositions++;
            }
            boardData[column][row] = this.botByte;
        } else if (player == 2) {
            if (boardData[column][row] == 0){
                this.filledPositions++;
            }
            boardData[column][row] = this.oppByte;
        } else {
            if (boardData[column][row] != 0){
                this.filledPositions--;
            }
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

    public int getNumberOfFilledPositions(){
        return this.filledPositions;
    }

    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }

    public int getScore(ArrayList<Position> quadruplets){
        int score = 0;
        //I've made this without IF statements just in case...and it's fun
        for (int i = 0; i < quadruplets.size(); i+=4) {
            int[] values = new int[4];
            values[0] = this.getLocationValue(quadruplets.get(i).getX(), quadruplets.get(i).getY());
            values[1] = this.getLocationValue(quadruplets.get(i+1).getX(), quadruplets.get(i+1).getY());
            values[2] = this.getLocationValue(quadruplets.get(i+2).getX(), quadruplets.get(i+2).getY());
            values[3] = this.getLocationValue(quadruplets.get(i+3).getX(), quadruplets.get(i+3).getY());
//            for (int j = 0; j < 4; j++) {
//                //Add up scores from bot
//                score += values[j] % 2;
//                //Subtract scores from opponent
//                score -= values[j] / 2;
//            }
//            //Add additional factor for bot if four in a row - win
//            score += (values[0]%2)*(values[1]%2)*(values[2]%2)*(values[3]%2)*2000000;
//            //Subtract additional factor for opponent four in a row - loss
//            score -= (values[0]/2)*(values[1]/2)*(values[2]/2)*(values[3]/2)*2000000;
//            if (quadruplets.get(i).getX() == 4 || quadruplets.get(i).getX() == 5 ) {
//                score += 1000;
//            }
            int player1Count = 0;
            int player2Count = 0;
            int sum = 0;
            for (int j = 0; j < 4; j++) {
                if (values[j] == 1) {
                    player1Count++;
                } else if (values[j] == 2) {
                    player2Count++;
                }
            }
            sum += (player1Count - player2Count);
            if (player1Count == 4){
                sum += 2000000;
            }
            if (player2Count == 4){
                sum -= 2000000;
            }
            if (((values[0] == 1 && values[1] == 1) || (values[1] ==1 && values[2] == 1) || (values[2] ==1 && values[3] == 1)) && player1Count == 2) {
                sum+=50;
            }
            if (((values[0] == 2 && values[1] == 2) || (values[1] ==2 && values[2] == 2) || (values[2] ==2 && values[3] == 2)) && player2Count == 2) {
                sum-=50;
            }
            if (player1Count == 3){
                sum+=1000;
            }
            if (player2Count == 3){
                sum-=1000;
            }
            score = score + sum;
            //System.out.println("Quadruplet: "+ quadruplets.get(i).getX() + "," + quadruplets.get(i).getY() + " - " + quadruplets.get(i+3).getX() + "," + quadruplets.get(i+3).getY() + "Sum: " + sum);

        }
        return score;
    }

    public ArrayList<Position> getNextAvailablePositions(){
        ArrayList<Position> availablePositions = new ArrayList<>();
        for (int i = 0; i < this.width; i++) {
            int nextRow = getLowestFreeRow(i);
            if (nextRow != -1) {
                availablePositions.add(new Position(i, nextRow));
            }
        }
        return availablePositions;
    }
}
