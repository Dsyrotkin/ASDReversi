package framework;

import framework.board.Board;
import framework.piece.Move;
import framework.piece.Piece;
import framework.player.factory.Player;
import framework.player.factory.PlayerFactory;
import framework.gridDriver.bridge.GridDriver;

public class GameEngine implements IEngine, IMoveListener {

    private Player currentPlayer;
    private Player opponent;

    private GridDriver gridDriver;
    private Board board;

    public GameEngine(final GridDriver gridDriver, final Board board){
        this.currentPlayer = PlayerFactory.getFactory().createPlayer();
        this.opponent = PlayerFactory.getFactory().createPlayer();
        this.gridDriver = gridDriver;
        this.board = board;
    }

    public void onClick(int row, int col) {

    }

    public void onMove(Piece piece) {
        Move move = gridDriver.generateMove(piece, currentPlayer, opponent);
        board.doMove(move);

    }

    @Override
    public void determineGameOver() {

    }

    @Override
    public void initializeGame() {
        gridDriver.initializeGame();
    }

    @Override
    public void updateScore() {

    }

    @Override
    public void swapPlayers() {

    }

    @Override
    public void determineWinner() {

    }
}