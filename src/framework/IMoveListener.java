package framework;

import framework.piece.Piece;

public interface IMoveListener {
	void onClick(int row, int col);
	void onMove(Piece piece);
}
