package framework.piece;

import framework.IMoveOutcome;
import framework.player.factory.Player;
import framework.player.factory.PlayerFactory;
import framework.player.factory.PlayerType;

import java.io.Serializable;

public abstract class Piece {

    protected int column, row;
    protected boolean isValidPiece;
    protected transient RuleStrategy rule;
    protected CellStatus cellStatus;
    protected int playerId;
    protected String pieceId;

    public Piece(RuleStrategy rule, int row, int column){
        this.rule = rule;
        this.isValidPiece = false;
        this.row = row;
        this.column = column;
        this.playerId = 0;
    }

    public Piece(Piece piece){
        this.column = piece.getColumn();
        this.row = piece.getRow();
        this.isValidPiece = piece.isValidPiece();
        this.rule = piece.getRule();
        this.cellStatus = piece.getCellStatus();
        this.playerId = piece.playerId;
        this.pieceId = piece.pieceId;
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

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPieceId() {
        return pieceId;
    }

    public void setPieceId(String pieceId) {
        this.pieceId = pieceId;
    }

    public void action(Move from, Move to) {
        rule.move(from, to);
    }

    public boolean validateMove(Move from, Move to) {
        return rule.validateMove(from, to);
    }

    /*@Override
    public Position getIndex() {
        return new Position(row,column);
    }*/

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }

    public RuleStrategy getRule() {
        return rule;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null){
            return false;
        }

        if (getClass() != obj.getClass()){
            return false;
        }

        final Piece another = (Piece) obj;

        if (another.getRow() != getRow() || another.getColumn() != getColumn()){
            return false;
        }

        return true;
    }
}
