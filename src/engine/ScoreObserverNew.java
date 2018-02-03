package engine;

import framework.player.factory.Player;

public interface ScoreObserverNew {
	void updateScore(Player currentplayer);
	void updateWinner(String info);
}
