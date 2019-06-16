package database;

import java.sql.Timestamp;
import java.util.ArrayList;

import client.Challenge;
import client.Lobby;
import client.User;
import controllers.PatternCardGenerator;
import game.CollectiveGoalCard;
import game.CurrencyStone;
import game.Die;
import game.GlassWindow;
import game.Message;
import game.PatternCard;
import game.Player;
import game.Round;
import game.ToolCard;

public class PersistenceFacade {
	private BaseDAO baseDAO = new BaseDAO();
	private GameDAO gameDAO = new GameDAO(baseDAO.getConnection());
	private PlayerDAO playerDAO = new PlayerDAO(baseDAO.getConnection());
	private MessageDAO messageDAO = new MessageDAO(baseDAO.getConnection());
	private DieDAO dieDAO = new DieDAO(baseDAO.getConnection());
	private PatternCardDAO patternCardDAO = new PatternCardDAO(baseDAO.getConnection());
	private ToolCardDAO toolCardDAO = new ToolCardDAO(baseDAO.getConnection());
	private CollectiveGoalCardDAO collectiveGoalCardDAO = new CollectiveGoalCardDAO(baseDAO.getConnection());
	private CurrencyStoneDAO currencyStoneDAO = new CurrencyStoneDAO(baseDAO.getConnection());
	private SpaceGlassDAO spaceGlassDAO = new SpaceGlassDAO(baseDAO.getConnection());
	private SpacePatternDAO spacePatternDAO = new SpacePatternDAO(baseDAO.getConnection());
	// Client
	private ChallengeDAO challengeDAO = new ChallengeDAO(baseDAO.getConnection());
	private LobbyDAO lobbyDAO = new LobbyDAO(baseDAO.getConnection());
	private UserDAO userDAO = new UserDAO(baseDAO.getConnection());

	// Account
	private AccountDAO accountDAO = new AccountDAO(baseDAO.getConnection());

	// Account
	public boolean loginCorrect(String username, String password) {
		return accountDAO.loginCorrect(username, password);
	}

	public boolean insertCorrect(String username, String password) {
		return accountDAO.insertCorrect(username, password);
	}

	public ArrayList<String> getAllUsername(boolean orderASC) {
		return accountDAO.getAllUsernames(orderASC);
	}

	// ChallengeDAO
	public ArrayList<Integer> getChallenges(String username) {
		return challengeDAO.getChallenges(username);
	}

	public Challenge getChallenge(int idGame) {
		return challengeDAO.getChallenge(idGame);
	}

	public void updatePlayerStatus(String username, boolean accepted, int idGame) {
		challengeDAO.updateStatus(username, accepted, idGame);
	}

	public boolean hasOpenInvite(String username, String opponentname) {
		return challengeDAO.hasOpenInvite(username, opponentname);
	}

	public ArrayList<Integer> checkCreatedChallanges(String username) {
		return challengeDAO.checkCreatedChallenges(username);
	}

	// LobbyDAO
	public ArrayList<Integer> getAllLobbies(boolean orderASC) {
		return lobbyDAO.getAllLobbyID(orderASC);
	}

	public ArrayList<Integer> getAllPlayerLobbies(String username) {
		return lobbyDAO.getAllPlayerLobbyID(username);
	}

	public Lobby getLobby(int idGame) {
		return lobbyDAO.getLobby(idGame);
	}

	public ArrayList<ArrayList<String>> getScoreboard(int idGame) {
		return lobbyDAO.getScoreboard(idGame);
	}

	// UserDAO
	public User getUser(String username) {
		return userDAO.getUser(username);
	}

	public boolean updateUser(String username, User oldUser) {
		return userDAO.checkUpdate(username, oldUser);
	}

	// GAME
	/**
	 * Creates a game in the database
	 * 
	 * @param users - List of users in the game, The first user NEEDS to be the creator
	 * @param patternCardGenerator
	 */
	public void createGame(ArrayList<String> users, boolean useRandomPatternCards, PatternCardGenerator patternCardGenerator) {
		int gameID = gameDAO.createGame();
		dieDAO.insertDice(gameID);
		currencyStoneDAO.insertCurrencyStones(gameID);
		playerDAO.insertPlayers(gameID, users);
		gameDAO.updateCurrentPlayer(gameID, playerDAO.getCurrentPlayer(gameID));
		spaceGlassDAO.insertGlassWindows(playerDAO.getAllPlayersInGame(gameID));
		setCardsGame(gameID, useRandomPatternCards, patternCardGenerator);
	}

	/*
	 * sets the card options for players
	 */
	/**
	 * sets the cards and patterncardoptions for all players in that game
	 * 
	 * @param idGame
	 * @param useRandomPatternCards
	 * @param patternCardGenerator
	 */
	public void setCardsGame(int idGame, boolean useRandomPatternCards, PatternCardGenerator patternCardGenerator) {
		ArrayList<Player> players = playerDAO.getAllPlayersInGame(idGame);

		ArrayList<PatternCard> patternCards = new ArrayList<>();

		for (Player player : players) {// for every player do one of those 2 actions
			if (useRandomPatternCards) {
				patternCards.clear();
				int amount = 0;
				while (amount <= 4) {//generate 4 Pattercards
					PatternCard generated = patternCardGenerator.generateCard();
					generated.setID(patternCardDAO.insertPatternCard(generated));
					spacePatternDAO.insertPattern(generated);
					patternCards.add(generated);
					amount++;
				}
				patternCardDAO.insertPatternCardOptions(player.getPlayerID(), patternCards, useRandomPatternCards);

			} else {
				patternCardDAO.insertPatternCardOptions(player.getPlayerID(), patternCards, useRandomPatternCards);
			}
			patternCards.addAll(patternCardDAO.getPlayerOptions(player.getPlayerID()));

		}
		collectiveGoalCardDAO.insertRandomSharedCollectiveGoalCards(idGame);
		toolCardDAO.insertRandomGameToolCards(idGame);
	}

	public int getCurrentRound(int idgame) {
		return gameDAO.getCurrentRound(idgame);
	}

	// CollectiveGoalCardDAO
	public ArrayList<CollectiveGoalCard> getSharedCollectiveGoalCards(int idGame) {
		return collectiveGoalCardDAO.getSharedCollectiveGoalCards(idGame);
	}

	public CollectiveGoalCard getSelectedCollectiveGoalCard(int idpublic_objectivecard) {
		return collectiveGoalCardDAO.getSelectedCollectiveGoalCard(idpublic_objectivecard);
	}

	public void insertRandomSharedCollectiveGoalCards(int idGame) {
		collectiveGoalCardDAO.insertRandomSharedCollectiveGoalCards(idGame);
	}

	// CurrencyStoneDAO
	public ArrayList<CurrencyStone> getAllStonesInGame(int idGame) {
		return currencyStoneDAO.getAllStonesInGame(idGame);
	}

	public ArrayList<CurrencyStone> getCurrencyStonesPlayer(int idGame, int playerID) {
		return currencyStoneDAO.getCurrencyStonesPlayer(idGame, playerID);
	}

	public ArrayList<CurrencyStone> getCurrencyStonesOnCard(int idToolCard, int idGame) {
		return currencyStoneDAO.getCurrencyStonesOnCard(idToolCard, idGame);
	}

	public void updateGameFavorTokenUsed(int stoneID, int idGame, int gametoolcard) {
		currencyStoneDAO.updateGameFavorTokenUsed(stoneID, idGame, gametoolcard);
	}

	public void updateGivePlayerCurrencyStones(int idGame, int idPlayer, int ammount) {
		currencyStoneDAO.updateGivePlayerCurrencyStones(idGame, idPlayer, ammount);
	}

	// PlayerDAO
	public int getPlayerScore(int idPlayer) {
		return playerDAO.getScore(idPlayer);
	}

	public void updateStatusUitgespeeld(int idPlayer) {
		playerDAO.updateStatusUitgespeeld(idPlayer);
	}

	public void updateScore(int score, int idPlayer) {
		playerDAO.updateScore(score, idPlayer);
	}

	public ArrayList<Player> getAllPlayers() {
		return playerDAO.getAllPlayers();
	}

	public ArrayList<Player> getAllPlayersInGame(int idGame) {
		return playerDAO.getAllPlayersInGame(idGame);
	}

	public Player getCurrentPlayer(int idGame) {
		return playerDAO.getCurrentPlayer(idGame);
	}

	public void updatePlayerTurn(Player oldPlayer, Player newPlayer, int idgame) {
		playerDAO.updatePlayerTurn(oldPlayer, newPlayer);
		gameDAO.updateCurrentPlayer(idgame, newPlayer);
	}

	public void setPlayerPaternCard(int idPatternCard, int idPlayer) {
		playerDAO.setPlayerPaternCard(idPatternCard, idPlayer);
	}

	public ArrayList<Player> getPlayersWithoutPatternCard(int idGame) {
		return playerDAO.getPlayersWithoutPatternCard(idGame);
	}

	public ArrayList<Player> getPlayerWithPatternCardButWithoutCurrencyStones(int idGame) {
		return playerDAO.getPlayerWithPatternCardButWithoutCurrencyStones(idGame);
	}

	// ToolCardDAO
	public ArrayList<ToolCard> getGameToolCards(int idGame) {
		return toolCardDAO.getGameToolCards(idGame);
	}

	public ToolCard getSelectedToolCard(int idtoolcard) {
		return toolCardDAO.getSelectedToolCard(idtoolcard);
	}

	public void insertRandomGameToolCards(int idGame) {
		toolCardDAO.insertRandomGameToolCards(idGame);
	}

	// DieDAO
	public void updateDiceRoll(int gameID, ArrayList<Die> dice) {
		dieDAO.updateDiceRoll(gameID, dice);
	}

	public void updateDiceRound(int gameID, int round, ArrayList<Die> dice) {
		dieDAO.updateDiceRound(gameID, round, dice);
	}

	public ArrayList<Die> getGameDice(int gameID) {
		return dieDAO.getGameDice(gameID);
	}

	public Round[] getRoundTrack(int gameID) {
		return dieDAO.getRoundTrack(gameID);
	}

	public ArrayList<Die> getTableDice(int gameID, int round) {
		return dieDAO.getTableDice(gameID, round);
	}

	// MessageDAO
	public ArrayList<Message> getALLMessages(ArrayList<Player> players) {
		return messageDAO.getALLMessages(players);
	}

	public ArrayList<Message> updateChat(ArrayList<Player> players, Timestamp time) {
		return messageDAO.updateChat(players, time);
	}

	public void insertMessage(Message message) {
		messageDAO.sendMessage(message);
	}

	// SpaceGlassDAO
	public GlassWindow getGlassWindow(int idPlayer) {
		return spaceGlassDAO.getGlassWindow(idPlayer);
	}

	public void updateSpaceGlass(int idPlayer, GlassWindow glassWindow, int gameId) {
		spaceGlassDAO.updateSpaceGlass(idPlayer, glassWindow, gameId);
	}

	// PatternCardDAO
	public PatternCard getplayerPatternCard(int idPlayer) {
		return patternCardDAO.getplayerPatternCard(idPlayer);
	}

	public ArrayList<PatternCard> getPlayerOptions(int idPlayer) {
		return patternCardDAO.getPlayerOptions(idPlayer);
	}

	// Check if the player can join a game
	public boolean isGameReady(int idGame) {
		// Check if game has toolcards
		if (getGameToolCards(idGame).size() == 0) {
			System.err.println("no toolcards");
			return false;
		}

		// Check if game has public goalcards
		if (getSharedCollectiveGoalCards(idGame).size() == 0) {
			System.err.println("no goalcards");
			return false;
		}

		// Check if all players have patterncard options
		for (Player p : getAllPlayersInGame(idGame)) {
			if (getPlayerOptions(p.getPlayerID()).size() == 0) {
				System.err.println("no patterncard options player " + p.getPlayerID());
				return false;
			}
		}

		return true;
	}

	public void closeConnection() {
		baseDAO.closeConnection();
	}
}
