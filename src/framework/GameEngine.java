package framework;

import framework.board.GameBoard;
import framework.piece.GamePiece;
import framework.player.factory.Player;
import framework.player.factory.PlayerFactory;
import framework.gridDriver.strategy.AbstractGridDriver;

public class GameEngine implements IEngine, IMoveListener {

    private Player currentPlayer;
    private Player opponent;

    private AbstractGridDriver gridDriver;
    private GameBoard board;

    public GameEngine(final AbstractGridDriver gridDriver, final GameBoard board){
        this.currentPlayer = PlayerFactory.getFactory().createPlayer();
        this.opponent = PlayerFactory.getFactory().createPlayer();
        this.gridDriver = gridDriver;
        this.board = board;
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
        gridDriver.initializeGame();
    }
}