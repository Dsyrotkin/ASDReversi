package UI;

import java.io.Serializable;

/**
 * Created by Dmitry on 1/31/2018.
 */
public class GameState implements Serializable {
    private int[][] savedPieces;
    private int currentPlayer;

    public int[][] getPieces() {
        return savedPieces;
    }

    public void setPieces(ReversiPiece[][] pieces) {
        int length = pieces.length;
        savedPieces = new int[length][pieces[0].length];
        for (int i = 0; i < length; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                savedPieces[i][j] = pieces[i][j].getPiece();
            }
        }
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
