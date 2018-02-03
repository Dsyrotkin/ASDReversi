package reversi;

import framework.IMoveOutcome;
import framework.IPieceInfo;
import framework.gridDriver.bridge.CustomGridDriver;
import framework.piece.CellStatus;
import framework.piece.Move;
import framework.piece.Piece;
import framework.piece.RuleStrategy;
import framework.player.factory.Player;

public class ReversiPieceRule extends RuleStrategy {

    // 3x3 array that holds the pieces that surround a given piece
    private int[][] surrounding;
    // 3x3 array that determines if a reverse can be made in any direction
    private boolean[][] can_reverse;

    //IPieceInfo pieceInfo;

    public ReversiPieceRule(CustomGridDriver driver){
        super(driver);
        this.surrounding = new int[3][3];
        this.can_reverse = new boolean[3][3];
    }

    @Override
    public boolean validateMove(Move from, Move to) {
        return validateMove(from);
    }

    @Override
    public IMoveOutcome move(Move from, Move to) {
        placePiece(from);
        return null;
    }

    //@Override
    public IMoveOutcome move(Move move) {
        placePiece(move);
        return null;
    }

    private boolean validateMove(Move move){
        int pieceOwner = move.getPiece().getPlayerId();
        if (pieceOwner == 0)
            //pieceInfo.setPiece(move.getX(), move.getY(), move.getCurrentPlayer().getId());
            gridDriver.setPiece(move);
        else
            return false;

        determineSurrounding(move.getX(), move.getY());

        if(!adjacentOpposingPiece(move))
        {
            //pieceInfo.setPiece(move.getX(), move.getY(), 0);
            gridDriver.clearPiece(move);
            return false;
        }

        // see if a reverse can be made in any direction if none can be made then return
        if(!determineReverse(move))
        {
            //pieceInfo.setPiece(move.getX(), move.getY(), 0);
            gridDriver.clearPiece(move);
            return false;
        }

        return true;

    }

    // public method that will try to place a piece in the given x,y coordinate
    public void placePiece(final Move move) {

        for (int i=0;i<gridDriver.getRow();i++)
        {
            for (int j=0;j<gridDriver.getRow();j++)
                System.out.print(gridDriver.getPiece(i, j).getPlayerId()+", ");
            System.out.println();
        }

        // determine what pieces surround the current piece. if there is no opposing
        // pieces then a valid move cannot be made.

        // at this point we have done all the checks and they have passed so now we can place
        // the piece and perform the reversing also check if the game has ended
        placeAndReverse(move);

    }

    // private method for determining which pieces surround x,y will update the
    // surrounding array to reflect this
    private void determineSurrounding(final int x, final int y) {
        for(int i = x - 1; i <= x + 1; i++)
            for(int j = y - 1; j <= y + 1; j++) {
                if(isValidIndex(i, j))
                    //surrounding[i - (x - 1)][j - (y - 1)] = pieceInfo.getPiece(i, j);
                    surrounding[i - (x - 1)][j - (y - 1)] = gridDriver.getPiece(i,j).getPlayerId();
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
        if(!isValidIndex(tempX, tempY) || gridDriver.getPiece(tempX, tempY).getPlayerId() != move.getOpposingPlayer().getId())
            return false;

        while(gridDriver.getPiece(tempX, tempY).getPlayerId()  == move.getOpposingPlayer().getId()) {
            if(!isValidIndex(tempX + dx, tempY + dy)) return false;
            tempX += dx;
            tempY += dy;
        }

        return gridDriver.getPiece(tempX, tempY).getPlayerId()  == move.getCurrentPlayer().getId();
    }

    private boolean isValidIndex(int x, int y) {
        return x >= 0 && y >= 0 && x < gridDriver.getRow() && y < gridDriver.getRow();
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

    // private method for placing a piece and reversing pieces
    private void placeAndReverse(Move move) {
        int x = move.getX();
        int y = move.getY();
        //pieceInfo.setEllipse(x,y,current_player);
        for(int i = x - 1; i <= x + 1; i++)
            for(int j = y - 1; j <= y + 1; j++) {
                if(isValidIndex(i, j) && (i != x || j != y) && can_reverse[i-(x-1)][j-(y-1)]) {
                    reverseChain(x, y, i - x, j - y, move);
                }
            }
    }

    // private method to reverse a chain
    private void reverseChain(final int x, final int y, final int dx, final int dy, Move move) {
        if(!isValidIndex(x + dx, y + dy)) return;
        if(gridDriver.getPiece(x + dx,y + dy).getPlayerId() != move.getOpposingPlayer().getId()) return;
        Piece piece = gridDriver.getPiece(x+dx, y+dy);
        Move move1 = new Move(piece, move.getCurrentPlayer(), move.getOpposingPlayer());
        gridDriver.setPiece(move1);
        reverseChain(x + dx, y + dy, dx, dy, move);
    }

}
