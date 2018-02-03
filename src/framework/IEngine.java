package framework;

import framework.board.Board;

public interface IEngine {
    void determineGameOver();
    void startGame();
    void updateScore();
    void swapPlayers();
    void determineWinner();
    void setBoard(Board board);
}
