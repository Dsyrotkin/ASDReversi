package framework.piece;

import framework.IMoveOutcome;
import framework.player.factory.Player;

public interface GamePiece {

    /**
     * the effect of playing a piece
     */
    IMoveOutcome action(Move from, Move to);

    boolean validateMove(Move from, Move to);

    /**
     * changes the piece type
     */
    void setPlayer(Player player);

    //void clearCell();

    //void addMoveListener(IMoveListener listener);

    Position getIndex();

    CellStatus getCellStatus();
}
