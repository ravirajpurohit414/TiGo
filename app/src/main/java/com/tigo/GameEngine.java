package com.tigo;

import java.util.Random;

public class GameEngine {

    private static final Random RANDOM = new Random();
    private char[] elts;
    private char currentPlayer;
    private boolean ended;
    private int counter;
    private boolean clickedCurrentPlayer;
    private boolean moveCurrentPlayer;
    private int playerPosition;
    private  int[][] adjacent = { {1, 3, 4}, {2, 4}, {1, 5, 4}, {0,4,6} ,
            {0,1,2,3,5,6,7,8} , {2,4,8} , {3,4,7} , {4,6,8} , {4, 5,7} };

    public GameEngine() {
        elts = new char[9];
        newGame();
    }

    public boolean isEnded() {
        return ended;
    }

    public char play(int x, int y) {

        if (counter == 0 && !ended  &&  elts[3 * y + x] == currentPlayer)
            clickedCurrentPlayer = !clickedCurrentPlayer;

        if (counter > 0 && !ended  &&  elts[3 * y + x] == ' ') {
            elts[3 * y + x] = currentPlayer;
            changePlayer();
            counter--;
        }

        if(moveCurrentPlayer && counter == 0 && !ended  &&  elts[3 * y + x] == ' '){

            boolean isAdjacent = false;
            for (int element : adjacent[playerPosition]) {
                if (element == 3 * y + x) {
                    isAdjacent = true;
                    break;
                }
            }
            if (isAdjacent) {
                elts[playerPosition] = ' ';
                elts[3 * y + x] = currentPlayer;
                moveCurrentPlayer = false;
                clickedCurrentPlayer = false;
                changePlayer();
            } else {
                clickedCurrentPlayer = false;
                moveCurrentPlayer = false;
            }
        }

        if (clickedCurrentPlayer && counter == 0 && !ended  &&  elts[3 * y + x] == currentPlayer) {
            moveCurrentPlayer = true;
            playerPosition = 3*y+x;


        }

        return checkEnd();
    }

    public void changePlayer() {
        currentPlayer = (currentPlayer == 'X' ? 'O' : 'X');
    }

    public char getElt(int x, int y) {
        return elts[3 * y + x];
    }

    public void newGame() {
        for (int i = 0; i  < elts.length; i++) {
            elts[i] = ' ';
        }

        currentPlayer = 'X';
        counter = 6;
        ended = false;
        clickedCurrentPlayer = false;
        moveCurrentPlayer = false;
        playerPosition = 0;
    }

    public char checkEnd() {
        for (int i = 0; i < 3; i++) {
            if (getElt(i, 0) != ' ' &&
                    getElt(i, 0) == getElt(i, 1)  &&
                    getElt(i, 1) == getElt(i, 2)) {
                ended = true;
                return getElt(i, 0);
            }

            if (getElt(0, i) != ' ' &&
                    getElt(0, i) == getElt(1, i)  &&
                    getElt(1, i) == getElt(2, i)) {
                ended = true;
                return getElt(0, i);
            }
        }

        if (getElt(0, 0) != ' '  &&
                getElt(0, 0) == getElt(1, 1)  &&
                getElt(1, 1) == getElt(2, 2)) {
            ended = true;
            return getElt(0, 0);
        }

        if (getElt(2, 0) != ' '  &&
                getElt(2, 0) == getElt(1, 1)  &&
                getElt(1, 1) == getElt(0, 2)) {
            ended = true;
            return getElt(2, 0);
        }

        for (int i = 0; i < 9; i++) {
            if (elts[i] == ' ')
                return ' ';
        }

        return 'T';
    }

    public char computer() {
        if (!ended) {
            int position = -1;

            do {
                position = RANDOM.nextInt(9);
            } while (elts[position] != ' ');

            elts[position] = currentPlayer;
            changePlayer();
        }

        return checkEnd();
    }

}