package it.polimi.se2019.view.components;

import java.io.Serializable;

public class BoardV implements Serializable {
    private SquareV[][] board;

    public void setBoard(SquareV[][] board) {
        this.board = board;
    }

    public SquareV[][] getBoard() {
        return board;
    }
}
