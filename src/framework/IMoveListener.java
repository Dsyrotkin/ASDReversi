package framework;

import framework.piece.GamePiece;

public interface IMoveListener {
	void onClick(int row, int col);
	void onMove(GamePiece piece);
}
