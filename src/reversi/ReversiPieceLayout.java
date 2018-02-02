package reversi;

import framework.IMoveListener;
import framework.board.PieceWrapper;
import framework.board.decorator.PieceLayout;
import framework.piece.Piece;

public class ReversiPieceLayout extends PieceLayout{

    public ReversiPieceLayout(IMoveListener listener) {
        super(listener);
    }

    @Override
    protected PieceWrapper wrapPiece(Piece piece) {
        return new ReversiPieceWrapper(piece,moveListener);
    }

}
