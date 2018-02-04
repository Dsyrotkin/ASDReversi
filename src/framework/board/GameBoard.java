package framework.board;

import engine.ScoreObserver;

public interface GameBoard extends ScoreObserver {

    void quitGame();
    void startGame();
    //void pauseGame();
}
