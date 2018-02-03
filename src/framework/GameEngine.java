package framework;

import engine.ScoreSubject;
import engine.ScoreSubjectNew;
import framework.board.Board;
import framework.piece.Move;
import framework.piece.Piece;
import framework.player.factory.Player;
import framework.player.factory.PlayerFactory;
import framework.gridDriver.bridge.GridDriver;
import framework.player.factory.PlayerType;

public class GameEngine extends ScoreSubjectNew implements IEngine, IMoveListener {

    private Player currentPlayer;
    private Player opponent;

    private GridDriver gridDriver;
    private Board board;

    public GameEngine(){
        this.currentPlayer = PlayerFactory.getFactory().createPlayer(PlayerType.FIRST);
        this.opponent = PlayerFactory.getFactory().createPlayer(PlayerType.SECOND);
    }

    public void onClick(int row, int col) {

    }

    public void onMove(Piece piece) {
        Move move = gridDriver.generateMove(piece, currentPlayer, opponent);

        boolean validMove = gridDriver.executeMove(move);

        if (validMove){
            swapPlayers();
        }

        updateScore();
    }

    @Override
    public void determineGameOver() {

    }

    @Override
    public void startGame() {
        gridDriver.initializeGame();
    }

    @Override
    public void updateScore() {
        super.notifyScore(currentPlayer);
    }

    @Override
    public void swapPlayers() {
        Player temp = opponent;
        opponent = currentPlayer;
        currentPlayer = temp;
    }

    @Override
    public void determineWinner() {

    }

    public void setBoard(Board board) {
        this.board = board;
        this.gridDriver = board.getGridDriver();
    }

}