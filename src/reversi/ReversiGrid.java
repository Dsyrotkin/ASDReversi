package reversi;

import framework.board.Grid;
import framework.piece.Piece;
import framework.piece.Position;

public class ReversiGrid extends Grid {

    public ReversiGrid(Piece[][] pieces) {
        super(pieces);
    }

    @Override
    public Position getPiece(Piece piece) {
        return null;
    }
}
