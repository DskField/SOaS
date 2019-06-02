package controllers;

import java.util.ArrayList;

import game.CollectiveGoalCard;
import game.Die;
import game.Player;

public class ScoreHandler {
	// variables
	private ArrayList<CollectiveGoalCard> collectiveGoalCards;
	private GoalCardHandler goalCardHandler;

	/**
	 * Constructor used to create a ScoreHandler object
	 * 
	 * @param collectiveGoalCards
	 *            - List containing all the goalcards in that specific game
	 */
	public ScoreHandler(ArrayList<CollectiveGoalCard> collectiveGoalCards) {
		this.collectiveGoalCards = collectiveGoalCards;
		goalCardHandler = new GoalCardHandler();
	}

	/**
	 * Method used to update all the private score for every player
	 * 
	 * @param players
	 *            - List containing all the players in the game
	 */
	public void updateAllPrivateScore(ArrayList<Player> players) {
		int score;
		for (Player p : players) {
			score = 0;
			score += handlePrivateGoalCard(p);
			score -= handleEmptySpaces(p);
			score += p.getCurrencyStones().size();
			score += goalCardHandler.getGoalCardHandler(p.getGlassWindow(), collectiveGoalCards);
			p.setScore(score);
		}
	}

	/**
	 * Method used to get the current score of the player
	 * 
	 * @param p
	 *            - Object containing the player
	 * @param privateScore
	 *            - boolean containing whether the score should be private or public
	 * @return - int containing the score of the player
	 */
	public int getScore(Player p, boolean privateScore) {
		int score = 0;
		score += privateScore ? handlePrivateGoalCard(p) : 0;
		score -= handleEmptySpaces(p);
		score += p.getCurrencyStones().size();
		score += goalCardHandler.getGoalCardHandler(p.getGlassWindow(), collectiveGoalCards);
		return score;
	}

	/**
	 * Method used to get the points gained from the private goalcard
	 * 
	 * @param player
	 *            - Object containing the player
	 * @return - int containing the score of the player gained with the private goalcard
	 */
	private int handlePrivateGoalCard(Player player) {
		int count = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				Die die = player.getGlassWindow().getSpace(x, y).getDie();
				if (die != null && die.getDieColor() == player.getPersonalGoalCard())
					count += die.getDieValue();
			}
		}
		return count;
	}

	/**
	 * Method used to get the points lost from the empty spaces
	 * 
	 * @param player
	 *            - Object containing the player
	 * @return - int containing the score of the player lost by empty spaces
	 */
	private int handleEmptySpaces(Player player) {
		int count = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				if (player.getGlassWindow().getSpace(x, y).getDie() == null)
					count++;
			}
		}
		return count;
	}
}
