package view;

import java.util.ArrayList;

import client.User;
import controllers.MainApplication;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class UserListPane extends BorderPane {
	/* CONSTANTS */
	// Left
	private final double listWidth = 250 * MainApplication.width;
	private final double listHeight = Screen.getPrimary().getBounds().getMaxY();
	private final double viewStatsWidth = 70 * MainApplication.width;
	private final double viewStatsHeight = 30 * MainApplication.height;;
	private final double inviteRadioButtonWidth = 100 * MainApplication.width;
	private final double inviteRadioButtonHeight = 30 * MainApplication.height;;

	private final double orderButtonHeight = 45 * MainApplication.height;;
	private final double userListHeight = Screen.getPrimary().getVisualBounds().getMaxY() - orderButtonHeight - 5;

	// Center
	private final double inviteButtonSize = 40 * MainApplication.height;;
	private final double errorMessageSize = 23 * MainApplication.height;;
	private final Color errorMessageColorRed = Color.RED;
	private final Color errorMessageColorGreen = Color.LIMEGREEN;
	private final double buttonAndLabelSpacing = 10 * MainApplication.height;;

	// StatsPane
	private final Color statsBackgroundColor = Color.AQUAMARINE;
	private final double statsBoxWidth = 400 * MainApplication.width;
	private final double statsBoxHeight = 340 * MainApplication.height;
	private final double usernameLabelSize = 50 * MainApplication.height;;
	private final double textSize = 25 * MainApplication.width;;

	/* VARIABLES */
	private ClientScene clientscene;

	// Left
	private ListView<BorderPane> userList;
	private ToggleGroup togglegroup;
	private ArrayList<String> users;
	private HandleRadioButton handleradiobutton;
	private ArrayList<CheckBox> inviteCheckBoxGroup;
	private Button orderButton;
	private boolean orderASC;

	// Center
	private Label errorMessage;
	private Button invitePlayers;
	private String opponentUsername;
	private boolean useRandomChecked = false;
	private CheckBox useRandomPatternCards;

	/**
	 * Constructor used to create a UserListPane Object
	 * 
	 * @param clientscene - Object containing the reference to clientscene
	 */
	public UserListPane(ClientScene clientscene) {
		this.clientscene = clientscene;
		this.userList = new ListView<BorderPane>();
		this.togglegroup = new ToggleGroup();
		this.handleradiobutton = new HandleRadioButton();
		this.inviteCheckBoxGroup = new ArrayList<CheckBox>();
		this.opponentUsername = "";
		this.orderASC = true;

		createLeft();
		createCenter(opponentUsername);
		errorMessage.setVisible(false);
	}

	/**
	 * Method used to create the List of users and the sort button.
	 */
	public void createLeft() {
		userList.getItems().clear();
		userList.setMinSize(listWidth, listHeight);
		userList.setMaxSize(listWidth, listHeight);
		togglegroup.getToggles().clear();
		users = clientscene.getUsers();

		// Remove yourself from selectable list
		users.remove(clientscene.getUsername());

		orderButton = new Button(orderASC ? "gesorteerd op meeste wins" : "gesorteerd op minste wins");
		orderButton.setOnAction(e -> handleOrderButton());
		orderButton.setMinHeight(orderButtonHeight);
		orderButton.setMaxHeight(orderButtonHeight);

		for (String username : users) {
			CheckBox inviteRadioButton = new CheckBox();
			inviteRadioButton.setOnMouseClicked(handleradiobutton);
			inviteRadioButton.setText(username);
			inviteRadioButton.setMinSize(inviteRadioButtonWidth, inviteRadioButtonHeight);
			inviteRadioButton.setMaxSize(inviteRadioButtonWidth, inviteRadioButtonHeight);

			Button viewStats = new Button("Vergelijk");
			viewStats.setMinSize(viewStatsWidth, viewStatsHeight);
			viewStats.setOnAction(e -> createCenter(username));

			for (CheckBox box : inviteCheckBoxGroup) {
				if (box.getText().equals(inviteRadioButton.getText()))
					inviteRadioButton.setSelected(true);
			}

			BorderPane playerLine = new BorderPane();
			playerLine.setLeft(inviteRadioButton);
			playerLine.setRight(viewStats);
			userList.getItems().add(playerLine);
		}
		userList.setMinHeight(userListHeight);
		userList.setMaxHeight(userListHeight);
		orderButton.setMinWidth(userList.getWidth());
		orderButton.setMaxWidth(userList.getMaxWidth());

		VBox leftBox = new VBox();
		leftBox.getChildren().addAll(orderButton, userList);
		leftBox.setAlignment(Pos.CENTER);
		this.setLeft(leftBox);
	}

	/**
	 * Method used to create the two stats pane, the error message, invite button and randompatterncard
	 * toggle.
	 * 
	 * @param opponentUsername - String containing the username of the opponent used to get the data for
	 * the opponent stats box
	 */
	public void createCenter(String opponentUsername) {
		this.opponentUsername = opponentUsername;
		VBox centerpane = new VBox();

		errorMessage = new Label("Je hebt een speler al recent uitgenodigd");
		errorMessage.setFont(Font.font(errorMessageSize));
		errorMessage.setTextFill(errorMessageColorRed);
		errorMessage.setAlignment(Pos.CENTER);
		errorMessage.setVisible(false);

		invitePlayers = new Button("Spelers uitnodigen");
		invitePlayers.setMinWidth(statsBoxWidth);
		invitePlayers.setMaxWidth(statsBoxWidth);
		invitePlayers.setFont(Font.font(inviteButtonSize));
		invitePlayers.setOnAction(e -> handleInvitePlayers());
		invitePlayers.setDisable(true);

		useRandomPatternCards = new CheckBox("Gebruik random patroonkaarten");
		useRandomPatternCards.setAlignment(Pos.TOP_CENTER);
		useRandomPatternCards.setSelected(useRandomChecked);

		VBox buttonAndLabel = new VBox();
		buttonAndLabel.getChildren().addAll(errorMessage, invitePlayers, useRandomPatternCards);
		buttonAndLabel.setAlignment(Pos.CENTER);
		buttonAndLabel.setSpacing(buttonAndLabelSpacing);

		centerpane.getChildren().addAll(createStatsPane(clientscene.getUser()), createStatsPane(clientscene.getOpponent(opponentUsername)), buttonAndLabel);
		centerpane.setAlignment(Pos.CENTER);
		centerpane.setSpacing(50);

		// At first when no opponent is selected hide stats
		if (opponentUsername.equals(""))
			centerpane.getChildren().get(1).setVisible(false);
		else
			centerpane.getChildren().get(1).setVisible(true);

		this.setCenter(centerpane);
	}

	/**
	 * Method used to create 1 square filled with stats.
	 * 
	 * @param user - Object containing the user used to get the data for the stats
	 * @return - Object containing a borderpane the square with all the stats
	 */
	private BorderPane createStatsPane(User user) {
		BorderPane bp = new BorderPane();
		bp.setBackground(new Background(new BackgroundFill(statsBackgroundColor, null, null)));

		Label username = new Label(user.getUsername());
		username.setFont(Font.font(usernameLabelSize));

		Label gamesPlayed = new Label("Spellen gespeeld: " + Integer.toString(user.getGamesPlayed()));
		gamesPlayed.setFont(Font.font(textSize));

		Label gamesWon = new Label("Spellen gewonnen: " + Integer.toString(user.getGamesWon()));
		gamesWon.setFont(Font.font(textSize));

		Label gamesLost = new Label("Spellen verloren: " + Integer.toString(user.getGamesLost()));
		gamesLost.setFont(Font.font(textSize));

		Label maxScore = new Label("Hoogste score: " + Integer.toString(user.getMaxScore()));
		maxScore.setFont(Font.font(textSize));

		Label mostPlacedColor = new Label("Meest geplaatste kleur: " + user.getMostPlacedColor().getDatabaseName());
		mostPlacedColor.setFont(Font.font(textSize));

		Label mostPlacedValue = new Label("Meest geplaatste waarde: " + Integer.toString(user.getMostPlacedValue()));
		mostPlacedValue.setFont(Font.font(textSize));

		Label totalOpponents = new Label("Aantal tegenstanders: " + Integer.toString(user.getTotalOpponents()));
		totalOpponents.setFont(Font.font(textSize));

		VBox labelBox = new VBox();
		labelBox.getChildren().addAll(username, gamesPlayed, gamesWon, gamesLost, maxScore, mostPlacedColor, mostPlacedValue, totalOpponents);
		labelBox.setAlignment(Pos.CENTER);
		bp.setCenter(labelBox);
		bp.setMinSize(statsBoxWidth, statsBoxHeight);
		bp.setMaxSize(statsBoxWidth, statsBoxHeight);

		return bp;
	}

	/**
	 * Method used to sort the {@code Usernames} in the list.
	 */
	private void handleOrderButton() {
		orderASC = orderASC ? false : true;
		clientscene.changeUserOrder(orderASC);
		createLeft();
	}

	/**
	 * Method used to gather all the usernames needed to invite and create a game.
	 */
	private void handleInvitePlayers() {
		ArrayList<String> result = new ArrayList<>();

		result.add(clientscene.getUser().getUsername());
		for (CheckBox box : inviteCheckBoxGroup) {
			result.add(box.getText());
		}
		clientscene.updateClient();
		if (clientscene.createGame(result, useRandomPatternCards.isSelected())) {
			errorMessage.setText("Spelers succesvol uitgenodigd");
			errorMessage.setTextFill(errorMessageColorGreen);
			errorMessage.setVisible(true);
		} else {
			errorMessage.setText("Je hebt de speler(s) al recent uitgenodigd");
			errorMessage.setTextFill(errorMessageColorRed);
			errorMessage.setVisible(true);
		}
	}

	/**
	 * Method used to handle the radio button If selected add to the List If deselected remove from the
	 * List Check if only 3 checkboxes are checked. Disable the button if no checkbox is selected.
	 */
	private class HandleRadioButton implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			errorMessage.setVisible(false);
			// handle deselect
			if (!((CheckBox) e.getSource()).isSelected()) {
				for (int i = 0; i < inviteCheckBoxGroup.size(); i++) {
					if (inviteCheckBoxGroup.get(i).getText().equals(((CheckBox) e.getSource()).getText()))
						inviteCheckBoxGroup.remove(i);
				}
			} else {
				// you can only select 3
				if (inviteCheckBoxGroup.size() == 3) {
					inviteCheckBoxGroup.get(0).setSelected(false);
					inviteCheckBoxGroup.remove(0);
				}
				inviteCheckBoxGroup.add((CheckBox) e.getSource());
			}

			// when no opponents are selected, disable invite button
			if (inviteCheckBoxGroup.size() == 0)
				invitePlayers.setDisable(true);
			else
				invitePlayers.setDisable(false);
		}
	}
}
