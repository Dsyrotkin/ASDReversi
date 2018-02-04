package framework.piece;

public interface GamePiece {

    /**
     * the effect of playing a piece
     */
    void action(Move from, Move to);

    boolean validateMove(Move from, Move to);

    /**
     * changes the piece type
     */
    void setPlayerId(int playerId);

    //void clearCell();

    //void addMoveListener(IMoveListener listener);

    //Position getIndex();

    CellStatus getCellStatus();
    void setCellStatus(CellStatus cellStatus);
}
