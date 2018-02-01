package framework;

import framework.player.factory.Player;

public class Move {

    private int x = 0, y = 0;

    private IPieceInfo pieceInfo;

    private Player currentPlayer;
    private Player opposingPlayer;

    public Move(int x, int y, Player  currentPlayer, Player opposingPlayer){
        this.x = x;
        this.y = y;
        this.currentPlayer = currentPlayer;
        this.opposingPlayer = opposingPlayer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOpposingPlayer() {
        return opposingPlayer;
    }
}
