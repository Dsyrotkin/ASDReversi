package reversi;

import framework.piece.Position;

public class PiecePositioner implements Position {

    private int row;

    private int column;

    public PiecePositioner(final int row, final int column){
        this.row = row;
        this.column = column;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public int getRow() {
        return row;
    }
}
