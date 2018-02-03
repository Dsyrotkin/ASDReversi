package framework.board;

import engine.ScoreObserver;
import engine.ScoreObserverNew;

public interface GameBoardNew extends ScoreObserverNew{

    void quitGame();
    void startGame();
    //void pauseGame();
}
