package reversi;

import framework.board.ScoreBoard;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.gridDriver.bridge.CustomGridDriver;
import framework.piece.Move;
import framework.piece.Piece;
import framework.player.factory.*;

public class ReversiGridDriver extends CustomGridDriver {

    public ReversiGridDriver(int x, int y, int gridSize){
        super(x,y,gridSize);
    }

    @Override
    public boolean initializeGrid() {
        Move move = null;
        Piece piece = getPiece(3,3);
        ReversiPieceWrapper pieceWrapper = (ReversiPieceWrapper) getGrid().getPieceWrapper(piece);
        pieceWrapper.setPiece(new Move(piece, PlayerFactory.getFactory().createPlayer(PlayerType.SECOND), null));

        piece = getPiece(4,4);
        pieceWrapper = (ReversiPieceWrapper) getGrid().getPieceWrapper(piece);
        pieceWrapper.setPiece(new Move(piece, PlayerFactory.getFactory().createPlayer(PlayerType.SECOND), null));

        piece = getPiece(3,4);
        pieceWrapper = (ReversiPieceWrapper) getGrid().getPieceWrapper(piece);
        pieceWrapper.setPiece(new Move(piece, PlayerFactory.getFactory().createPlayer(PlayerType.FIRST),null));

        piece = getPiece(4,3);
        pieceWrapper = (ReversiPieceWrapper) getGrid().getPieceWrapper(piece);
        pieceWrapper.setPiece(new Move(piece, PlayerFactory.getFactory().createPlayer(PlayerType.FIRST),null));

        return true;
    }

    @Override
    public boolean executeMove(Move move) {
        return move.getPiece().doMove(move,move);
    }

    @Override
    public boolean clearGrid() {

        for (int i = 0; i < getRow(); i++){
            for (int j = 0; j < getColumn(); j++){
                clearPiece(i,j);
            }
        }
        return true;
    }

    @Override
    public void setPiece(Move move) {
        ReversiPieceWrapper pieceWrapper = (ReversiPieceWrapper) getGrid().getPiecesUI()[move.getX()][move.getY()];
        pieceWrapper.setPiece(move);
    }

    @Override
    public void clearPiece(Move move) {
        clearPiece(move.getX(),move.getY());
    }

    @Override
    public void clearPiece(int row, int column) {
        ReversiPieceWrapper pieceWrapper
                = (ReversiPieceWrapper) getGrid().getPiecesUI()[row][column];
        pieceWrapper.clearPiece();
    }

    @Override
    public Piece getPiece(int row, int column) {
        return getGrid().getPiece(new PiecePositioner(row,column));
    }

    @Override
    public Move generateMove(Piece piece, Player currentPlayer, Player opposing) {
        Move move = new Move(piece, currentPlayer, opposing);
        return move;
    }

    @Override
    public ScoreBoard getScore() {
        int p1 =0, p2 = 0;
        for (int i = 0; i < getRow(); i++){
            for (int j = 0; j < getColumn(); j++){
                if (getGrid().getPiece(new PiecePositioner(i,j)).getPlayerId() == 1){
                    p1++;
                }else if(getGrid().getPiece(new PiecePositioner(i,j)).getPlayerId() == 2){
                    p2++;
                }
            }
        }
        return new ScoreBoard(p1,p2);
    }

    @Override
    public void accept(GridCreatorVisitor visitor) {
        visitor.visit(this);
    }
}
