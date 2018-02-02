package framework.piece;

import framework.IMoveOutcome;
import framework.player.factory.Player;

public abstract class Piece implements GamePiece {

    public int column, row;
    public boolean isValidPiece;
    public RuleStrategy rule;
    public CellStatus cellStatus;
    public Player player;

    public Piece(RuleStrategy rule, int row, int column){
        this.rule = rule;
        this.isValidPiece = false;
        this.row = row;
        this.column = column;
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

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }
}
