package framework.piece;

public class Position {

    private int row;

    private int column;

    public Position(final int row, final int column){
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
