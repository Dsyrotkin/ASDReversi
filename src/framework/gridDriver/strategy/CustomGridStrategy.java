package framework.gridDriver.strategy;

import framework.piece.GamePiece;
import framework.IMoveOutcome;
import framework.Move;
import framework.gridCreator.visitor.GridCreatorVisitor;

public abstract class CustomGridStrategy {

    private int column, row;

    public CustomGridStrategy(){
        this.column = 0;
        this.row = 0;
    }

    public CustomGridStrategy(final int column, final int row){
        this.row = row;
        this.column = column;
    }

    private GamePiece[][] grid;

    abstract GamePiece[][] createGrid(int row, int column);
    abstract boolean initializeGrid(GamePiece[][] grid);
    abstract IMoveOutcome executeMove(Move move);

    public GamePiece[][] getGrid() {
        return grid;
    }

    public void setGrid(GamePiece[][] grid) {
        this.grid = grid;
    }

    public abstract void accept(GridCreatorVisitor visitor);
}
