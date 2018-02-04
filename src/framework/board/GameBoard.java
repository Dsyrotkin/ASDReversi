package framework.board;

import framework.ScoreObserver;

public interface GameBoard extends ScoreObserver {

    void quitGame();
    void startGame();
    //void pauseGame();
}
