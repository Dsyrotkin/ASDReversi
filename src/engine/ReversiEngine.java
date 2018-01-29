package engine;

import UI.ReversiPiece;

public class ReversiEngine implements IOnClickListener,IPlayer{

    // the current player who is playing and who is his opposition
    private int current_player;
    private int opposing;
    // is the game currently in play
    private boolean in_play=true;
    // current scores of player 1 and player 2
    private int player1_score;
    private int player2_score;
    
    // 3x3 array that holds the pieces that surround a given piece
    private int[][] surrounding;
    // 3x3 array that determines if a reverse can be made in any direction
    private boolean[][] can_reverse;
    
    IPieceInfo pieceInfo;
    
    IScoreListener scoreListener;
    
    public ReversiEngine()
    {
    	current_player = 2;
        opposing = 1;
        surrounding = new int[3][3];
        can_reverse = new boolean[3][3];
    }
    public void setPiece(IPieceInfo pieceInfo)
    {
    	this.pieceInfo=pieceInfo;
    }
    public void setScoreListener(IScoreListener scoreListener)
    {
    	this.scoreListener=scoreListener;
    }
    
	@Override
	public void onClick(int row, int col) {
		System.out.println("onclik "+row+","+col);		
		placePiece(row,col);
	}

    // public method that will try to place a piece in the given x,y coordinate
    public void placePiece(final int cellx, final int celly) {

        // if the game is not in play then do nothing
        if(!in_play)
            return;

        int currentpiece=pieceInfo.getPiece(cellx, celly);
        if (currentpiece==0)
        	pieceInfo.setPiece(cellx, celly, current_player);
    	

        // determine what pieces surround the current piece. if there is no opposing
        // pieces then a valid move cannot be made.
        determineSurrounding(cellx, celly);
        
//        if(!adjacentOpposingPiece())
//            return;
//
//        // see if a reverse can be made in any direction if none can be made then return
//        if(!determineReverse(cellx, celly))
//            return;
//        
//        // at this point we have done all the checks and they have passed so now we can place
//        // the piece and perform the reversing also check if the game has ended
//        placeAndReverse(cellx, celly);

        // if we get to this point then a successful move has been made so swap the
        // players and update the scores
        swapPlayers();
        updateScores();
        determineEndGame();

        // print out some information
        System.out.println("placed at: " + cellx + ", " + celly);
        System.out.println("White: " + player1_score + " Black: " + player2_score);
        if(current_player == 1)
            System.out.println("current player is White");
        else
            System.out.println("current player is Black");

        if(!in_play) determineWinner();
    }
    
    // private method for determining which pieces surround x,y will update the
    // surrounding array to reflect this
    private void determineSurrounding(final int x, final int y) {
        for(int i = x - 1; i <= x + 1; i++)
            for(int j = y - 1; j <= y + 1; j++) {
                if(isValidIndex(i, j))
                    surrounding[i - (x - 1)][j - (y - 1)] = pieceInfo.getPiece(i, j);
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
                    can_reverse[i - (x - 1)][j - (y - 1)] = isReverseChain(x, y, i - x, j - y, current_player);
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
        if(!isValidIndex(tempX, tempY) || pieceInfo.getPiece(tempX, tempY) != opposing)
            return false;

        while(pieceInfo.getPiece(tempX, tempY)  == opposing) {
            if(!isValidIndex(tempX + dx, tempY + dy)) return false;
            tempX += dx;
            tempY += dy;
        }

        return pieceInfo.getPiece(tempX, tempY)  == current_player;
    }

    private boolean isValidIndex(int x, int y) {
        return x >= 0 && y >= 0 && x < pieceInfo.getRow() && y < pieceInfo.getRow();
    }
    
    // private method for determining if any of the surrounding pieces are an opposing
    // piece. if a single one exists then return true otherwise false
    private boolean adjacentOpposingPiece() {
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                if((i != 1 || j != 1) && (surrounding[i][j] != 0 && surrounding[i][j] != current_player))
                    return true;
            }
        return false;
    }

    // private method for placing a piece and reversing pieces
    private void placeAndReverse(final int x, final int y) {
        //pieceInfo.setPiece(x,y,current_player);
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
        if(pieceInfo.getPiece(x + dx,y + dy) != opposing) return;
        pieceInfo.setPiece(x + dx,y + dy,current_player);
        reverseChain(x + dx, y + dy, dx, dy);
    }
    // private method that determines who won the game
    private void determineWinner() {
        if(player1_score > player2_score) System.out.println("Player 1 is winner");
        else if(player2_score > player1_score) System.out.println("Player 2 is winner");
        else System.out.println("No winner");
    }
    // private method for swapping the players
    private void swapPlayers() {
        int temp = opposing;
        opposing = current_player;
        current_player = temp;
    }

    // private method for updating the player scores
    private void updateScores() {
        player1_score = player2_score = 0;
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                switch(pieceInfo.getPiece(i, j)) {
                    case 1:
                        player1_score++;
                        break;
                    case 2:
                        player2_score++;
                        break;
                }
        scoreListener.update(this.current_player, player1_score, player2_score);
    }
    // private method that will determine if the end of the game has been reached
    private void determineEndGame() {
        if(player1_score + player2_score == 64 ||
                (player1_score == 0 || player2_score == 0)) {
            in_play = false;
        } else if(!canMove()) {
            swapPlayers();
            if(!canMove()) {
                in_play = false;
            }
        }
    }
    // private method to determine if a player has a move available
    private boolean canMove() {
        // NOTE: this is to keep the compiler happy until you get to this part
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++) {
                if(pieceInfo.getPiece(i,j) == 0) {
                    determineSurrounding(i, j);
                    if(adjacentOpposingPiece() && determineReverse(i, j))
                        return true;
                }
            }
        return false;
    }

    public void startGame()
    {
    	pieceInfo.setPiece(3, 3, 1);
    	pieceInfo.setPiece(4, 4, 1);

    	pieceInfo.setPiece(3,4,2);
    	pieceInfo.setPiece(4,3,2);
        player1_score = 2;
        player2_score = 2;
        
        current_player = 2;
        updateScores();
    }
	@Override
	public int getCurrentPlayer() {
		return current_player;
	}
}
