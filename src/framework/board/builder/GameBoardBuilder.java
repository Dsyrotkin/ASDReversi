package framework.board.builder;

import framework.board.Board;

public abstract class GameBoardBuilder {

	protected Board board;

	public GameBoardBuilder(Board board){
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
