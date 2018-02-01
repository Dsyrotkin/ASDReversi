package framework.board;

import engine.ScoreObserver;
import framework.player.factory.Player;

public interface GameBoard extends ScoreObserver{

    void quitGame();
    void startGame();
}
