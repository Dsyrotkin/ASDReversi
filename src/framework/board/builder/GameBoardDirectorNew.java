package framework.board.builder;

import framework.board.Board;

public class GameBoardDirectorNew {

	private GameBoardBuilderNew builder = null; //or multiple meal builders

	public GameBoardDirectorNew(GameBoardBuilderNew builder) {
		this.builder = builder;
	}
	
	public void constructBoard() {
		builder.createScore();
		builder.createToolBar();
		builder.createBoard();
	}

	public Board getBoard() {
		return builder.getBoard();
	}
}
