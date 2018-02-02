package UI;

import engine.IOnClickListener;
import engine.IPieceInfo;
import engine.IPlayer;
import engine.ReversiEngine;
import framework.GameEngine;
import javafx.scene.Group;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/**
 *
 * @author jpereda
 */
public class GridOperator implements IPieceInfo {
    
    private final int gridSize;
    private final List<Integer> traversalX;
    private final List<Integer> traversalY;

    private ReversiPiece[][] pieces;
    
    IOnClickListener onClickListener;
    IPlayer iPlayer;

    public GridOperator(int gridSize, IOnClickListener listener, IPlayer iPlayer){
        this.gridSize=gridSize;
        this.traversalX = IntStream.range(0, gridSize).boxed().collect(Collectors.toList());
        this.traversalY = IntStream.range(0, gridSize).boxed().collect(Collectors.toList());
        this.onClickListener=listener;
        this.iPlayer=iPlayer;
    }

    public void traverseGrid(int player) {
    	for (ReversiPiece[] row: pieces)
    		for (ReversiPiece p: row)
    		{
    			p.setPiece(player);
    		}

    }

    public void restoreGridState(int[][] savedPieces) {
		for (int i = 0; i < savedPieces.length; i++) {
			for(int j = 0; j < savedPieces[i].length; j++) {
				setPiece(i, j, savedPieces[i][j]);
			}
		}
	}

    public GameState getCurrentState() {
    	GameState state = new GameState();
		state.setPieces(pieces);
		state.setCurrentPlayer(iPlayer.getCurrentPlayer());
		return state;
	}

	@Override
	public void storeGameState() {
		RecordManager recordManager = RecordManager.getInstance();
		recordManager.saveState(getCurrentState());
	}

	@Override
	public void clearStoredGameStates() {
		RecordManager recordManager = RecordManager.getInstance();
		recordManager.clearStoredStates();
	}
    
    public int getGridSize(){ return gridSize; }

    public void create(Group group, int CELL_SIZE)
    {
    	pieces=new ReversiPiece[gridSize][gridSize];
    	for (int i=0;i<gridSize;i++)
    		for (int j=0;j<gridSize;j++)
    		{
    			pieces[i][j]=createCell(i,j, CELL_SIZE);
    			group.getChildren().add(pieces[i][j]);
    		}
    }

    private ReversiPiece createCell(int row, int col, int CELL_SIZE){
        return new ReversiPiece(0,row,col,CELL_SIZE,onClickListener,iPlayer );
    }

	@Override
	public int getRow() {
		// TODO Auto-generated method stub
		return pieces.length;
	}

	@Override
	public int getPiece(int row, int col) {
		return pieces[row][col].getPiece();
	}

	@Override
	public void setPiece(int row, int col, int player) {
		pieces[row][col].setPiece(player);
	}
}
