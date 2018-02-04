package framework.board.decorator;

import framework.board.PieceWrapper;
import framework.piece.Piece;

public class PieceLayoutDecoratorImpl extends PieceLayoutDecorator {

    public PieceLayoutDecoratorImpl(PieceLayout pieceLayout) {
        super(pieceLayout);
    }

    @Override
    protected PieceWrapper wrapPiece(Piece piece) {
        return null;
    }
}
