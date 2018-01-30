package engine;

public interface GamePiece {

    /**
     * the effect of playing a piece
     */
    IMoveOutcome action(Move from, Move to);

    boolean validateMove(Move from, Move to);

    /**
     * changes the piece type
     */
    void setPiece(Player player);

    void clearCell();

    public void addOnMouseClickListener(IOnClickListener listener);
}
