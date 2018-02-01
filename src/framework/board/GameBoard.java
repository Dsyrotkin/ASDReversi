package framework.board;

import framework.player.factory.Player;

public interface GameBoard {

    void updateScore(Player currentPlayer, int score1, int score2);
    void updateWinner(String info);
    void quitGame();
    void startGame();
}