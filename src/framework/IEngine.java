package framework;

import framework.board.Board;
import framework.player.factory.Player;

public interface IEngine {
    void determineGameOver();
    void startGame();
    void updateScore();
    void swapPlayers();
    void determineWinner();
    void setBoard(Board board);
    void doSaveSession();
    void doRestore();
    void performUndo();
}
