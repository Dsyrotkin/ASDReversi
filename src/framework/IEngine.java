package framework;

public interface IEngine {
    void determineGameOver();
    void initializeGame();
    default void updateScore(){
        ///update score, some games doesn't keep score like chess
    }
}
