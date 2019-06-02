package view;

import java.util.ArrayList;

import controllers.GameController;
import game.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatPane extends BorderPane {
	/* CONSTANTS */
	private final int chatPaneWidth = 400;
	private final int chatPaneheight = 200;
	private final int sendButtonWidth = 100;
	private final int sendButtonHeight = 20;

	/* VARIABLES */
	private TextField playerMessage;
	private Button sendMessage;

	private ScrollPane scrollPane;
	private HBox bottom;
	private VBox center;
	private GameController gameController;

	public ChatPane(GameController gameController) {
		// Setting the GameController
		this.gameController = gameController;

		// Handles the makeup of the ChatPane
		setPrefSize(chatPaneWidth, chatPaneheight);

		// Creates the Chat
		createChat();

	}

	/**
	 * Creates the entire {@code Chat}. Including the send button and the player input bar.
	 */
	private void createChat() {
		/* INIT */
		playerMessage = new TextField();
		sendMessage = new Button("verstuur");
		scrollPane = new ScrollPane();
		bottom = new HBox();
		center = new VBox();

		/* MAKUP */
		sendMessage.setPrefSize(sendButtonWidth, sendButtonHeight);
		playerMessage.setPrefWidth(chatPaneWidth - sendButtonWidth);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(center);

		// Scrolls scrollbar to bottom
		scrollPane.setVvalue(1d);

		// Sets everything to the ChatPane
		bottom.getChildren().addAll(playerMessage, sendMessage);
		setCenter(scrollPane);
		setBottom(bottom);

		/* HANDLERS */
		// Handles the Enter key for sending messages
		playerMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					sendMessage();
				} else if (event.getCode() == KeyCode.ESCAPE) {
					event.consume();
				}
			}
		});

		// Handles the "verzenden" button to send messages
		sendMessage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sendMessage();
			}
		});
	}

	/**
	 * Adds Messages to the {@code Chat}.
	 * 
	 * @param messages - {@code ArrayList<Message>} that will be added to the chat
	 */
	public void updateChat(ArrayList<Message> messages) {
		double scrollPos = scrollPane.getVvalue();

		scrollPane.setVvalue(1d);
		for (Message message : messages) {
			center.getChildren().add(new MessagePane(message.getUserName(), message.getMessage(), message.getChatTime()));
		}
		scrollPane.setVvalue(scrollPos);

	}

	/**
	 * Gives the the String from playerMessage to the sendMessage method of the game controller and
	 * clears the playerMessage field.
	 */
	private void sendMessage() {
		gameController.sendMessage(playerMessage.getText());
		playerMessage.clear();
		scrollPane.setVvalue(1d);
	}
}
