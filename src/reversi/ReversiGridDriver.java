package reversi;

import framework.board.ScoreBoard;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.gridDriver.bridge.CustomGridDriver;
import framework.piece.Move;
import framework.piece.Piece;
import framework.player.factory.*;

public class ReversiGridDriver extends CustomGridDriver {

    // 3x3 array that holds the pieces that surround a given piece
    private int[][] surrounding;
    // 3x3 array that determines if a reverse can be made in any direction
    private boolean[][] can_reverse;

    public ReversiGridDriver(int x, int y, int gridSize){
        super(x,y,gridSize);
        this.surrounding = new int[3][3];
        this.can_reverse = new boolean[3][3];
    }

    @Override
    public boolean initializeGrid() {
        Move move = null;
        Piece piece = getPiece(3,3);
        move = new Move(piece, PlayerFactory.getFactory().createPlayer(PlayerType.SECOND), null);
        setPiece(move);

        piece = getPiece(4,4);
        move = new Move(piece, PlayerFactory.getFactory().createPlayer(PlayerType.SECOND), null);
        setPiece(move);

        piece = getPiece(3,4);
        move =  new Move(piece, PlayerFactory.getFactory().createPlayer(PlayerType.FIRST),null);
        setPiece(move);

        piece = getPiece(4,3);
        move = new Move(piece, PlayerFactory.getFactory().createPlayer(PlayerType.FIRST),null);
        setPiece(move);

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
        getScore().addPiece(pieceWrapper.getPiece());
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
        getScore().removePiece(getGrid().getPiece(new PiecePositioner(row,column)));
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
    public boolean determineEndGame(Player currentPlayer, Player opponent) {
        ScoreBoard scoreBoard = getScore();
        if (scoreBoard.getPlayerOneScore() + scoreBoard.getPlayerTwoScore() == 64
                || scoreBoard.getPlayerTwoScore() == 0 || scoreBoard.getPlayerOneScore() == 0){
            return false;
        }else if(!canMove(new Move(null, currentPlayer, opponent))) {
            Player temp = opponent;
            opponent = currentPlayer;
            currentPlayer = temp;
            if(!canMove(new Move(null, currentPlayer, opponent))) {
                return false;
            }
        }
        return true;
    }

    // private method to determine if a player has a move available
    private boolean canMove(Move move) {
        // NOTE: this is to keep the compiler happy until you get to this part
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++) {
                if(getPiece(i,j).getPlayerId() == 0) {
                    determineSurrounding(i, j);
                    move.setPiece(getGrid().getPiece(new PiecePositioner(i,j)));
                    if(adjacentOpposingPiece(move) && determineReverse(move))
                        return true;
                }
            }
        return false;
    }

    // private method for determining which pieces surround x,y will update the
    // surrounding array to reflect this
    private void determineSurrounding(final int x, final int y) {
        for(int i = x - 1; i <= x + 1; i++)
            for(int j = y - 1; j <= y + 1; j++) {
                if(isValidIndex(i, j))
                    //surrounding[i - (x - 1)][j - (y - 1)] = pieceInfo.getPiece(i, j);
                    surrounding[i - (x - 1)][j - (y - 1)] = getPiece(i,j).getPlayerId();
            }
    }

    // private method for determining if a reverse can be made will update the can_reverse
    // array to reflect the answers will return true if a single reverse is found
    private boolean determineReverse(final Move move) {
        int x = move.getX();
        int y = move.getY();
        // NOTE: this is to keep the compiler happy until you get to this part
        boolean hasReverse = false;
        for(int i = x - 1; i <= x + 1; i++)
            for(int j = y - 1; j <= y + 1; j++) {
                if(!isValidIndex(i, j)) can_reverse[i - (x - 1)][j - (y - 1)] = false;
                else if(i != x || j != y) {
                    can_reverse[i - (x - 1)][j - (y - 1)] = isReverseChain(x, y, i - x, j - y, move);
                    if(can_reverse[i - (x - 1)][j - (y - 1)]) hasReverse = true;
                }
            }
        return hasReverse;
    }

    // private method for determining if a reverse can be made from a position (x,y) for
    // a player piece in the given direction (dx,dy) returns true if possible
    // assumes that the first piece has already been checked
    private boolean isReverseChain(final int x, final int y, final int dx, final int dy, Move move) {
        // NOTE: this is to keep the compiler happy until you get to this part
        int tempX = x + dx;
        int tempY = y + dy;
        if(!isValidIndex(tempX, tempY) || getPiece(tempX, tempY).getPlayerId() != move.getOpposingPlayer().getId())
            return false;

        while(getPiece(tempX, tempY).getPlayerId()  == move.getOpposingPlayer().getId()) {
            if(!isValidIndex(tempX + dx, tempY + dy)) return false;
            tempX += dx;
            tempY += dy;
        }

        return getPiece(tempX, tempY).getPlayerId()  == move.getCurrentPlayer().getId();
    }

    private boolean isValidIndex(int x, int y) {
        return x >= 0 && y >= 0 && x < getRow() && y < getRow();
    }

    // private method for determining if any of the surrounding pieces are an opposing
    // piece. if a single one exists then return true otherwise false
    private boolean adjacentOpposingPiece(Move move) {
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                if((i != 1 || j != 1) && (surrounding[i][j] != 0 && surrounding[i][j] != move.getCurrentPlayer().getId()))
                    return true;
            }
        return false;
    }

    @Override
    public void accept(GridCreatorVisitor visitor) {
        visitor.visit(this);
    }
}
