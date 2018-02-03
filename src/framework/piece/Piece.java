package framework.piece;

import framework.IMoveOutcome;
import framework.player.factory.Player;
import framework.player.factory.PlayerFactory;
import framework.player.factory.PlayerType;

public abstract class Piece implements GamePiece {

    public int column, row;
    public boolean isValidPiece;
    public RuleStrategy rule;
    public CellStatus cellStatus;
    public int playerId;

    public Piece(RuleStrategy rule, int row, int column){
        this.rule = rule;
        this.isValidPiece = false;
        this.row = row;
        this.column = column;
        this.playerId = 0;
    }

    public final boolean doMove(Move from, Move to){

        if (!validateMove(from,to)){

            return false;

        }

        action(from,to);

        return true;
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

    public int getPlayerId() {
        return playerId;
    }

    @Override
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
