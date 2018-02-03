package reversi;

import framework.piece.*;

public class ReversiPiece extends Piece {

    public ReversiPiece(RuleStrategy rule, int row, int column) {
        super(rule, row, column);
    }

    @Override
    public void action(Move from, Move to) {
        rule.move(from, to);
    }

    @Override
    public boolean validateMove(Move from, Move to) {
        return rule.validateMove(from, to);
    }

    /*@Override
    public Position getIndex() {
        return new Position(row,column);
    }*/

    @Override
    public CellStatus getCellStatus() {
        return cellStatus;
    }

    @Override
    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }
}
