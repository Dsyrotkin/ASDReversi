package framework.board;

public class ScoreBoard {

    private int playerOneScore;

    private int playerTwoScore;

    public ScoreBoard(final int playerOneScore, final int playerTwoScore){
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }
}
