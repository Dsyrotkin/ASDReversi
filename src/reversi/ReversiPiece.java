package reversi;

import framework.piece.*;

public class ReversiPiece extends Piece {

    public ReversiPiece(RuleStrategy rule, int row, int column) {
        super(rule, row, column);
    }

    public ReversiPiece(Piece piece){
        super(piece);
    }
}
