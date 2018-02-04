package framework.piece;

import framework.player.factory.Player;

public class Move {

    private Player currentPlayer;
    private Player opposingPlayer;

    private Piece piece;

    public Move(Piece piece, Player currentPlayer, Player opposingPlayer){
        this.piece = piece;
        this.currentPlayer = currentPlayer;
        this.opposingPlayer = opposingPlayer;
    }

    public int getX() {
        return piece.getRow();
    }

    public int getY() {
        return piece.getColumn();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOpposingPlayer() {
        return opposingPlayer;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
