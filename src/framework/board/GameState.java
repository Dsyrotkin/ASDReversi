package framework.board;

import framework.piece.Piece;
import framework.player.factory.Player;

import java.io.Serializable;

/**
 * zack
 */
public class GameState implements Serializable {

    private Player currentPlayer;
    private Player opponent;
    private Piece[][] pieces;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

}
