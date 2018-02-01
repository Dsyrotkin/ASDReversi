package framework.piece;

import framework.IMoveOutcome;
import framework.IRule;
import framework.Move;

public abstract class Piece implements GamePiece {

    protected int column, row;
    public boolean isValidPiece;
    protected IRule rule;

    protected Piece(IRule rule){
        this.rule = rule;
        this.isValidPiece = false;
    }

    public final IMoveOutcome doMove(Move from, Move to){

        if (validateMove(from,to)){

            return action(from,to);

        }

        return null;
    }

    public void setValidPiece(boolean validPiece) {
        isValidPiece = validPiece;
    }

    public boolean isValidPiece() {
        return isValidPiece;
    }
}
