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

    Player current_player = null;
    Player opponent = null;
    //IPieceInfo pieceInfo;

    public ReversiPieceRule(CustomGridDriver driver){
        super(driver);
        this.surrounding = new int[3][3];
        this.can_reverse = new boolean[3][3];
    }

    @Override
    public boolean validateMove(Move from, Move to) {
        current_player = from.getCurrentPlayer();
        opponent = from.getOpposingPlayer();
        return validateMove(from);
    }

    @Override
    public IMoveOutcome move(Move from, Move to) {
        current_player = from.getCurrentPlayer();
        opponent = from.getOpposingPlayer();
        placePiece(from.getX(), from.getY());
        return null;
    }

    //@Override
    public IMoveOutcome move(Move move) {
        current_player = move.getCurrentPlayer();
        opponent = move.getOpposingPlayer();
        placePiece(move.getX(), move.getY());
        return null;
    }

    private boolean validateMove(Move move){

        final int cellx = move.getX();

        final int celly = move.getY();

        int pieceOwner = move.getPiece().getPlayerId();

        if (pieceOwner == 0)
            //pieceInfo.setPiece(move.getX(), move.getY(), move.getCurrentPlayer().getId());
            gridDriver.setPiece(move);
        else
            return false;

        determineSurrounding(cellx, celly);

        if(!adjacentOpposingPiece()) {
            //pieceInfo.setPiece(move.getX(), move.getY(), 0);
            gridDriver.clearPiece(move);
            return false;
        }

        // see if a reverse can be made in any direction if none can be made then return
        if(!determineReverse(cellx,celly))
        {
            //pieceInfo.setPiece(move.getX(), move.getY(), 0);
            gridDriver.clearPiece(move);
            return false;
        }

        return true;

    }

    // public method that will try to place a piece in the given x,y coordinate
    public void placePiece(final int cellx, final int celly) {


        // determine what pieces surround the current piece. if there is no opposing
        // pieces then a valid move cannot be made.

        // at this point we have done all the checks and they have passed so now we can place
        // the piece and perform the reversing also check if the game has ended
        placeAndReverse(cellx, celly);

        System.out.println("Move: " + cellx +"," + celly);
        for (int i=0;i<gridDriver.getRow();i++) {
            for (int j=0;j<gridDriver.getRow();j++)
                System.out.print(gridDriver.getPiece(i, j).getPlayerId()+", ");
            System.out.println();
        }

    }

    // private method for determining which pieces surround x,y will update the
    // surrounding array to reflect this
    private void determineSurrounding(final int x, final int y) {
        for(int i = x - 1; i <= x + 1; i++)
            for(int j = y - 1; j <= y + 1; j++) {
                if(isValidIndex(i, j))
                    surrounding[i - (x - 1)][j - (y - 1)] = gridDriver.getPiece(i, j).getPlayerId();
            }
    }

    // private method for determining if a reverse can be made will update the can_reverse
    // array to reflect the answers will return true if a single reverse is found
    private boolean determineReverse(final int x, final int y) {
        // NOTE: this is to keep the compiler happy until you get to this part
        boolean hasReverse = false;
        for(int i = x - 1; i <= x + 1; i++)
            for(int j = y - 1; j <= y + 1; j++) {
                if(!isValidIndex(i, j)) can_reverse[i - (x - 1)][j - (y - 1)] = false;
                else if(i != x || j != y) {
                    can_reverse[i - (x - 1)][j - (y - 1)] = isReverseChain(x, y, i - x, j - y, current_player.getId());
                    if(can_reverse[i - (x - 1)][j - (y - 1)]) hasReverse = true;
                }
            }
        return hasReverse;
    }

    // private method for determining if a reverse can be made from a position (x,y) for
    // a player piece in the given direction (dx,dy) returns true if possible
    // assumes that the first piece has already been checked
    private boolean isReverseChain(final int x, final int y, final int dx, final int dy, final int player) {
        // NOTE: this is to keep the compiler happy until you get to this part
        int tempX = x + dx;
        int tempY = y + dy;
        if(!isValidIndex(tempX, tempY) || gridDriver.getPiece(tempX, tempY).getPlayerId() != opponent.getId())
            return false;

        while(gridDriver.getPiece(tempX, tempY).getPlayerId()  == opponent.getId()) {
            if(!isValidIndex(tempX + dx, tempY + dy)) return false;
            tempX += dx;
            tempY += dy;
        }

        return gridDriver.getPiece(tempX, tempY).getPlayerId()  == current_player.getId();
    }

    private boolean isValidIndex(int x, int y) {
        return x >= 0 && y >= 0 && x < gridDriver.getRow() && y < gridDriver.getRow();
    }

    // private method for determining if any of the surrounding pieces are an opposing
    // piece. if a single one exists then return true otherwise false
    private boolean adjacentOpposingPiece() {
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                if((i != 1 || j != 1) && (surrounding[i][j] != 0
                        && surrounding[i][j] != current_player.getId()))
                    return true;
            }
        return false;
    }

    // private method for placing a piece and reversing pieces
    private void placeAndReverse(final int x, final int y) {
        //pieceInfo.setEllipse(x,y,current_player);
        for(int i = x - 1; i <= x + 1; i++)
            for(int j = y - 1; j <= y + 1; j++) {
                if(isValidIndex(i, j) && (i != x || j != y) && can_reverse[i-(x-1)][j-(y-1)]) {
                    reverseChain(x, y, i - x, j - y);
                }
            }
    }

    // private method to reverse a chain
    private void reverseChain(final int x, final int y, final int dx, final int dy) {
        if(!isValidIndex(x + dx, y + dy)) return;
        if(gridDriver.getPiece(x + dx,y + dy).getPlayerId() != opponent.getId()) return;
        Piece piece = gridDriver.getPiece(x + dx,y + dy);
        piece.setPlayerId(current_player.getId());
        gridDriver.setPiece(piece);
        reverseChain(x + dx, y + dy, dx, dy);
    }

}
