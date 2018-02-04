package framework.gridDriver.bridge;

import framework.board.Grid;
import framework.board.ScoreBoard;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.piece.Move;
import framework.piece.Piece;
import framework.player.factory.Player;

public class GridDriverImpl extends GridDriver {

    public GridDriverImpl(CustomGridDriver customGridDriver){
        super(customGridDriver);
    }

    @Override
    public boolean executeMove(Move move) {
        return this.customGridDriver.executeMove(move);
    }

    @Override
    public void initializeGame() {
        this.customGridDriver.initializeGrid();
    }

    @Override
    public Grid createGrid(GridCreatorVisitor visitor) {
        return customGridDriver.createGrid(visitor);
    }

    @Override
    public boolean clearGrid() {
        customGridDriver.clearStoredState();
        return customGridDriver.clearGrid();
    }

    @Override
    public Move generateMove(Piece piece, Player currentPlayer, Player opposing) {
        return customGridDriver.generateMove(piece,currentPlayer,opposing);
    }

    @Override
    public ScoreBoard getScore() {
        return customGridDriver.getScore();
    }

    @Override
    public boolean determineEndGame(Player currentPlayer, Player opponent) {
        return customGridDriver.determineEndGame(currentPlayer,opponent);
    }

    @Override
    public boolean saveGame(Player currentPlayer, Player opponent) {
        return customGridDriver.saveGameToFile(currentPlayer,opponent);
    }

    @Override
    public void tempSaveGame(Player currentPlayer, Player opponent) {
        customGridDriver.storeGameState(currentPlayer, opponent);
    }

    @Override
    public boolean undo(Player currentPlayer, Player opponent) {
        return customGridDriver.undoMove(currentPlayer, opponent);
    }
}
