package framework.gridDriver.bridge;

import framework.board.Grid;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.piece.Move;
import framework.piece.Piece;
import framework.player.factory.Player;

public abstract class GridDriver {

    protected CustomGridDriver customGridDriver;

    public GridDriver(CustomGridDriver customGridDriver){
        this.customGridDriver = customGridDriver;
    }

    public abstract void executeMove(Move move);

    public abstract void initializeGame();

    public abstract Grid createGrid(GridCreatorVisitor visitor);

    public abstract boolean clearGrid();

    public abstract Move generateMove(Piece piece, Player currentPlayer, Player opposing);

    public int getGridSize(){
        return customGridDriver.getGridSize();
    }
}