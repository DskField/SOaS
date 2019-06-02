package controllers;

import java.util.ArrayList;

import game.CollectiveGoalCard;
import game.Die;
import game.Player;

public class ScoreHandler {
	/* VARIABLES */
	private ArrayList<CollectiveGoalCard> collectiveGoalCards;
	private GoalCardHandler goalCardHandler;

	/**
	 * Constructor used to create a ScoreHandler object
	 * 
	 * @param collectiveGoalCards - {@code ArrayList} containing all the {@code GoalCards} in that
	 * specific game
	 */
	public ScoreHandler(ArrayList<CollectiveGoalCard> collectiveGoalCards) {
		this.collectiveGoalCards = collectiveGoalCards;
		goalCardHandler = new GoalCardHandler();
	}

	/**
	 * Method used to update all the private score for every {@code Player}
	 * 
	 * @param players - {@code ArrayList} containing all the {@code Players} in the game
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
	 * Method used to get the current score of the {@code Player}
	 * 
	 * @param p - The {@code Player}
	 * @param privateScore - {@code boolean} containing whether the score should be private or public
	 * @return - {@code int} containing the score of the {@code Player}
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
	 * Method used to get the points gained from the {@code PrivateGoalCard}
	 * 
	 * @param player - The {@code Player}
	 * @return - {@code int} containing the score of the player gained with the {@code PrivateGoalCard}
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
	 * Method used to get the points lost from the empty {@code Spaces}
	 * 
	 * @param player - The player
	 * @return - {@code int} containing the score of the {@code Player} lost by empty spaces
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
