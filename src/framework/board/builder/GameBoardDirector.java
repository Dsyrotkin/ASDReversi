package framework.board.builder;

import framework.board.Board;

public class GameBoardDirector {

	private GameBoardBuilder builder = null; //or multiple meal builders

	public GameBoardDirector(GameBoardBuilder builder) {
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
