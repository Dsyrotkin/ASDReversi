package framework.gridDriver.bridge;

import framework.board.GameState;
import framework.board.Grid;
import framework.board.RecordManager;
import framework.board.ScoreBoard;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.piece.Move;
import framework.piece.Piece;
import framework.player.factory.Player;
import reversi.ReversiPiece;

public abstract class CustomGridDriver {

    private int column, row;
    private int gridSize;
    private ScoreBoard scoreBoard;
    private Grid grid;
    RecordManager recordManager = null;

    public CustomGridDriver(){
        this.column = 0;
        this.row = 0;
    }

    public CustomGridDriver(final int column, final int row, int gridSize){
        this.row = row;
        this.column = column;
        this.gridSize = gridSize;
        this.scoreBoard = new ScoreBoard();
        this.recordManager = RecordManager.getInstance();
    }

    public Grid createGrid(GridCreatorVisitor visitor) {
        accept(visitor);
        //visitor.visit(this);
        return getGrid();
    }

    public GameState getGameState(Player currentPlayer, Player opponent){
        GameState gameState = new GameState();
        gameState.setPieces(clonePieces(getGrid().getPieces()));
        gameState.setCurrentPlayer(currentPlayer);
        gameState.setOpponent(opponent);
        return gameState;
    }

    private Piece[][] clonePieces(Piece[][] pieces){
        Piece[][] pieces1 = new Piece[pieces.length][pieces[0].length];
        for (int i = 0; i < pieces.length; i++){
            for (int j = 0; j < pieces[0].length; j++){
                pieces1[i][j] = new ReversiPiece(pieces[i][j]);
            }
        }

        return pieces1;
    }

    public void storeGameState(Player currentPlayer, Player opponent){
        recordManager.saveState(getGameState(currentPlayer, opponent));
    }

    public boolean saveGameToFile(Player currentPlayer, Player opponenet){
        GameState state = getGameState(currentPlayer, opponenet);
        return recordManager.saveRecordToFile(state);
    }

    public boolean undoMove(Player currentPlayer, Player opponent){
        GameState previousState = recordManager.getPreviousState();
        if(previousState != null && restoreGameState(previousState, currentPlayer, opponent)) {
            recordManager.removeLastState();
            return true;
        }
        return false;
    }

    private boolean restoreGameState(GameState state, Player currentPlayer, Player opponent) {
        if(state != null) {
            restoreGridState(state);
            /*if (!state.getCurrentPlayer().equals(currentPlayer)) {
                swapPlayers(currentPlayer, opponent);
            }*/
            //engine.updateScores();
            return true;
        }
        return false;
    }

    public void swapPlayers(Player currentPlayer, Player opponent){
        Player temp = opponent;
        opponent = currentPlayer;
        currentPlayer = temp;
    }

    public void clearStoredState(){
        recordManager.clearStoredStates();
    }

    public abstract void restoreGridState(GameState gameState);
    public abstract boolean initializeGrid();
    public abstract boolean executeMove(Move move);
    public abstract boolean clearGrid();
    public abstract void setPiece(Move move);
    public abstract void setPiece(Piece piece);
    public abstract void clearPiece(Move move);
    public abstract void clearPiece(int row, int column);
    public abstract Piece getPiece(int row, int column);
    public abstract Move generateMove(Piece piece, Player currentPlayer, Player opposing);
    public abstract boolean determineEndGame(Player currentPlayer, Player opponent);

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public ScoreBoard getScore(){
        return scoreBoard;
    }

    public void accept(GridCreatorVisitor visitor){
        visitor.visit(this);
    }
}
