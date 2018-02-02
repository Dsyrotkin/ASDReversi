package reversi;

import framework.IMoveOutcome;
import framework.board.Grid;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.gridDriver.bridge.CustomGridDriver;
import framework.piece.Move;
import framework.piece.Piece;
import framework.player.factory.Player;

public class ReversiGridDriver extends CustomGridDriver {

    public ReversiGridDriver(int x, int y, int gridSize){
        super(x,y,gridSize);
    }

    @Override
    public Grid createGrid(GridCreatorVisitor visitor) {
        visitor.visit(this);
        return getGrid();
    }

    @Override
    public boolean initializeGrid() {
        return false;
    }

    @Override
    public IMoveOutcome executeMove(Move move) {
        Grid grid = getGrid();
        //grid.getPiece()
        return null;
    }

    @Override
    public boolean clearGrid() {
        return false;
    }

    @Override
    public void setPiece(Move move) {

        ReversiPieceWrapper pieceWrapper = (ReversiPieceWrapper) getGrid().getPiecesUI()[move.getX()][move.getY()];

        pieceWrapper.setPiece(move);
    }

    @Override
    public void clearPiece(Move move) {

        ReversiPieceWrapper pieceWrapper = (ReversiPieceWrapper) getGrid().getPiecesUI()[move.getX()][move.getY()];

        pieceWrapper.setPiece(move);
    }

    @Override
    public Piece getPiece(int row, int column) {
        return null;
    }

    @Override
    public Move generateMove(Piece piece, Player currentPlayer, Player opposing) {
        Move move = new Move(piece, currentPlayer, opposing);
        return move;
    }

    @Override
    public void accept(GridCreatorVisitor visitor) {
        visitor.visit(this);
    }
}
