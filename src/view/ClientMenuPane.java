package view;

import controllers.MainApplication;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class ClientMenuPane extends VBox {
	/* CONSTANTS */
	private final double buttonWidth = 300 * MainApplication.width;
	private final double buttonHeight = 100 * MainApplication.height;;
	private final double paneHeight = Screen.getPrimary().getBounds().getMaxY();

	/* VARIABLES */
	private ClientScene clientscene;
	private ToggleButton userListButton;
	private ToggleButton lobbyListButton;
	private ToggleButton challengeListButton;
	private ToggleButton logoutButton;
	private ToggleButton quitButton;

	/**
	 * Constructor used to create a {@code ClientMenuPane}
	 * 
	 * @param clientscene - contains the reference to clientscene
	 */
	public ClientMenuPane(ClientScene clientscene) {
		super();
		this.setMinSize(buttonWidth + 2, paneHeight);
		this.setMaxSize(buttonWidth + 2, paneHeight);
		this.clientscene = clientscene;
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		createButtons();
	}

	/**
	 * Method used to create the buttons
	 */
	private void createButtons() {
		userListButton = new ToggleButton("Spelers");
		userListButton.setMinSize(buttonWidth, buttonHeight);
		userListButton.setMaxSize(buttonWidth, buttonHeight);
		userListButton.setOnAction(e -> clientscene.handleUserListButton());

		lobbyListButton = new ToggleButton("Lobby's");
		lobbyListButton.setMinSize(buttonWidth, buttonHeight);
		lobbyListButton.setMaxSize(buttonWidth, buttonHeight);
		lobbyListButton.setOnAction(e -> clientscene.handleLobbyListButton());

		challengeListButton = new ToggleButton("Uitdaging");
		challengeListButton.setMinSize(buttonWidth, buttonHeight);
		challengeListButton.setMaxSize(buttonWidth, buttonHeight);
		challengeListButton.setOnAction(e -> clientscene.handleChallengeListButton());

		logoutButton = new ToggleButton("Uitloggen");
		logoutButton.setMinSize(buttonWidth, buttonHeight);
		logoutButton.setMaxSize(buttonWidth, buttonHeight);
		logoutButton.setOnAction(e -> clientscene.logOut());

		quitButton = new ToggleButton("Afsluiten");
		quitButton.setMinSize(buttonWidth, buttonHeight);
		quitButton.setMaxSize(buttonWidth, buttonHeight);
		quitButton.setOnAction(e -> clientscene.handleQuit());

		VBox topbox = new VBox();
		topbox.getChildren().addAll(userListButton, lobbyListButton, challengeListButton);
		VBox bottombox = new VBox();
		bottombox.getChildren().addAll(logoutButton, quitButton);

		BorderPane bp = new BorderPane();
		bp.setTop(topbox);
		bp.setBottom(bottombox);
		bp.setMinSize(buttonWidth + 2, Screen.getPrimary().getBounds().getMaxY());
		bp.setMaxSize(buttonWidth + 2, Screen.getPrimary().getBounds().getMaxY());

		ToggleGroup togglegroup = new ToggleGroup();
		togglegroup.getToggles().addAll(userListButton, lobbyListButton, challengeListButton, logoutButton, quitButton);

		this.getChildren().addAll(bp);
	}
}