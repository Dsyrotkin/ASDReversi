package framework.gridDriver.bridge;

import framework.IMoveOutcome;
import framework.board.Grid;
import framework.piece.Move;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.piece.Piece;
import framework.player.factory.Player;

public abstract class CustomGridDriver {

    private int column, row;
    private int gridSize;

    public CustomGridDriver(){
        this.column = 0;
        this.row = 0;
    }

    public CustomGridDriver(final int column, final int row, int gridSize){
        this.row = row;
        this.column = column;
        this.gridSize = gridSize;
    }

    private Grid grid;

    public abstract Grid createGrid(GridCreatorVisitor visitor);
    public abstract boolean initializeGrid();
    public abstract IMoveOutcome executeMove(Move move);
    public abstract boolean clearGrid();
    public abstract void setPiece(Move move);
    public abstract void clearPiece(Move move);
    public abstract Piece getPiece(int row, int column);
    public abstract Move generateMove(Piece piece, Player currentPlayer, Player opposing);

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


    public abstract void accept(GridCreatorVisitor visitor);
}
