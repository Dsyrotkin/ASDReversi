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

    private boolean isInPlay;

    private GridDriver gridDriver;
    private Board board;

    public GameEngine(){
        this.currentPlayer = PlayerFactory.getFactory().createPlayer(PlayerType.FIRST);
        this.opponent = PlayerFactory.getFactory().createPlayer(PlayerType.SECOND);
        this.isInPlay = true;
    }

    public void onClick(int row, int col) {

    }

    public void onMove(Piece piece) {

        Move move = gridDriver.generateMove(piece, currentPlayer, opponent);

        boolean validMove = gridDriver.executeMove(move);

        if (validMove){
            swapPlayers();
            gridDriver.tempSaveGame(currentPlayer,opponent);
        }

        updateScore();

        determineGameOver();

        if (!isInPlay){
            determineWinner();
        }
    }

    @Override
    public void determineGameOver() {
        isInPlay = gridDriver.determineEndGame(currentPlayer,opponent);
    }

    @Override
    public void startGame() {
        gridDriver.initializeGame();
        gridDriver.tempSaveGame(currentPlayer,opponent);
        updateScore();
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
        this.notifyWinner();
    }

    public void setBoard(Board board) {
        this.board = board;
        this.gridDriver = board.getGridDriver();
    }

    @Override
    public void doSaveSession() {
        gridDriver.saveGame(currentPlayer, opponent);
    }

    @Override
    public void performUndo() {
        if (gridDriver.undo(currentPlayer, opponent)){
            swapPlayers();
            updateScore();

            System.out.println("undo");
            for (int i=0;i<gridDriver.getGridSize();i++) {
                for (int j=0;j<gridDriver.getGridSize();j++)
                    System.out.print(gridDriver.getGrid().getPieces()[i][j].getPlayerId()+", ");
                System.out.println();
            }
        }
    }


    private void print(Piece[][] pieces){
        System.out.println("saved state");
        for (Piece[] pieces1 : pieces){
            for (Piece piece : pieces1){
                System.out.print(piece.getPlayerId());
            }
            System.out.println();
        }
    }

}