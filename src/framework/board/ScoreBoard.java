package framework.board;

import framework.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    protected List<Piece> playerOneScore;
    protected List<Piece> playerTwoScore;

    public ScoreBoard(){
        playerOneScore = new ArrayList<>();
        playerTwoScore = new ArrayList<>();
    }

    public void addPiece(Piece piece){
        removePiece(piece);
        if (piece.getPlayerId() == 1){
            playerOneScore.add(piece);
        }else if (piece.getPlayerId() == 2){
            playerTwoScore.add(piece);
        }
    }

    public int getPlayerOneScore() {
        return playerOneScore.size();
    }

    public int getPlayerTwoScore() {
        return playerTwoScore.size();
    }

    public boolean removePiece(Piece piece){
        if (playerOneScore.contains(piece)){
            playerOneScore.remove(piece);
            return true;
        }else if (playerTwoScore.contains(piece)){
            playerTwoScore.remove(piece);
            return true;
        }

        return false;
    }

    public void clearBoard(){
        playerTwoScore.clear();
        playerOneScore.clear();
    }

}
