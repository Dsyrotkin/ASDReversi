package framework.board.builder;

import framework.board.Board;
import framework.board.GameBoard;

public abstract class GameBoardBuilderNew {

	protected Board board;

	public GameBoardBuilderNew(Board board){
		this.board = board;
	}

	public final Board getBoard(){
		createToolBar();
		createScore();
		createBoard();

		return board;
	}
	
	public abstract void createToolBar();
	public abstract void createScore();
	public abstract void createBoard();
}
