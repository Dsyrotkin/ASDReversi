package framework;

import framework.player.factory.Player;

public interface ScoreObserver {
	void updateScore(Player currentplayer);
	void updateWinner();
}
