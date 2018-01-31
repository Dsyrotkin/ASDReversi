package framework;

public abstract class Piece implements GamePiece {

    protected int column, row;

    protected IRule rule;

    protected Piece(IRule rule){
        this.rule = rule;
    }

    public final IMoveOutcome doMove(Move from, Move to){

        if (validateMove(from,to)){

            return action(from,to);

        }

        return null;
    }

}
