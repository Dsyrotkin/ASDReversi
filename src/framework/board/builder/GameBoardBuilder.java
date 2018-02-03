package framework.board.builder;

import framework.board.GameBoard;

public interface GameBoardBuilder {

	GameBoard getBoard();
	
	void createToolBar();
	void createScore();
	void createBoard();
}
