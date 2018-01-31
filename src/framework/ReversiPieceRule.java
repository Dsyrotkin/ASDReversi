package framework;

public class ReversiPieceRule implements IRule {

    IPieceInfo pieceInfo;

    // 3x3 array that holds the pieces that surround a given piece
    private int[][] surrounding;

    @Override
    public boolean validateMove(Move from, Move to) {
        return false;
    }

    @Override
    public IMoveOutcome move(Move from, Move to) {
        return null;
    }

    //@Override
    public IMoveOutcome move(Move move) {
        return null;
    }


}
