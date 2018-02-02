package reversi;

import framework.IMoveOutcome;
import framework.piece.*;
import framework.player.factory.Player;

public class ReversiPiece extends Piece {

    public ReversiPiece(RuleStrategy rule, int row, int column) {
        super(rule, row, column);
    }

    @Override
    public IMoveOutcome action(Move from, Move to) {
        return rule.move(from, to);
    }

    @Override
    public boolean validateMove(Move from, Move to) {
        return rule.validateMove(from, to);
    }

    @Override
    public void setPlayer(Player player) {

    }

    @Override
    public Position getIndex() {
        return new Position(row,column);
    }

    @Override
    public CellStatus getCellStatus() {
        return cellStatus;
    }
}
