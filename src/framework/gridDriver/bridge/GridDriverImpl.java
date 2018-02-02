package framework.gridDriver.bridge;

import framework.board.Grid;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.piece.Move;
import framework.piece.Piece;
import framework.player.factory.Player;

public class GridDriverImpl extends GridDriver {

    public GridDriverImpl(CustomGridDriver customGridDriver){
        super(customGridDriver);
    }

    @Override
    public void executeMove(Move move) {
        this.customGridDriver.executeMove(move);
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
        return customGridDriver.clearGrid();
    }

    @Override
    public Move generateMove(Piece piece, Player currentPlayer, Player opposing) {
        return customGridDriver.generateMove(piece,currentPlayer,opposing);
    }
}
