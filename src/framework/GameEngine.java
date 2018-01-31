package framework;

import framework.playerFactory.Player;
import framework.playerFactory.PlayerFactory;

public class GameEngine implements IEngine, IMoveListener {

    private Player currentPlayer;
    private Player opponent;

    public GameEngine(){
        currentPlayer = PlayerFactory.getFactory().createPlayer();
        opponent = PlayerFactory.getFactory().createPlayer();
    }

    public void onClick(int row, int col) {

    }

    public void onMove(GamePiece piece) {

    }

    @Override
    public void determineGameOver() {

    }

    @Override
    public void initializeGame() {

    }
}