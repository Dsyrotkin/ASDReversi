package engine;

import framework.player.factory.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class ScoreSubject {

	private List<ScoreObserver> observers;

	public ScoreSubject() {
		observers=new ArrayList<>();
	}

	public void addScoreObserver(ScoreObserver observer) {
		observers.add(observer);
	}

	public void removeScoreObserver(ScoreObserver observer) {
		observers.remove(observer);
	}

	public void notifyScore(Player currentplayer) {

		observers.forEach(c -> c.updateScore(currentplayer));

	}

	public void notifyWinner() {
		observers.forEach(c ->  c.updateWinner());
	}
}
