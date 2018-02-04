package framework.board;

import framework.piece.Piece;
import framework.piece.Position;

public abstract class Grid {

    protected Piece[][] pieces;

    protected PieceWrapper[][] piecesUI;

    protected int row, column;

    public Grid(Piece[][] pieces){
        this.pieces = pieces;
        this.row = pieces.length;
        this.column = pieces[0].length;
    }

    public PieceWrapper[][] getPiecesUI() {
        return piecesUI;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void setPiecesUI(PieceWrapper[][] piecesUI) {
        this.piecesUI = piecesUI;
    }

    public PieceWrapper getPieceWrapper(Piece piece){
        return piecesUI[piece.getRow()][piece.getColumn()];
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public abstract Piece getPiece(Position position);

}
