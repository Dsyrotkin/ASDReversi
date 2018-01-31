package framework;

public interface IRule {
    boolean validateMove(Move from, Move to);
    IMoveOutcome move(Move from, Move to);
}
