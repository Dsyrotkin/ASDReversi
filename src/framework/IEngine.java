package framework;

public interface IEngine {
    void determineGameOver();
    void initializeGame();
    void updateScore();
    void swapPlayers();
    void determineWinner();
}
