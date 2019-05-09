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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ChatPane extends BorderPane {
	// constants
	private final int chatPaneWidth = 600;
	private final int chatPaneheight = 200;
	private final int sendButtonWidth = 100;
	private final int sendButtonHeight = 20;
	// variables
	private TextField playerMessage;
	private Button sendMessage;
	private HBox bottom;
	private VBox center;
	private MyButtonHandler myButtonHandler;
	private MyMessageSendHandler myMessageSendHandler;
	private GameController gameController;
	private ScrollPane scrollPane;

	public ChatPane(GameController gameController) {
		this.gameController = gameController;

		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		setPrefSize(chatPaneWidth, chatPaneheight);

		createChat();
	}

	private void createChat() {
		// initialize
		playerMessage = new TextField();
		sendMessage = new Button("verstuur");
		bottom = new HBox();
		center = new VBox();
		myButtonHandler = new MyButtonHandler();
		myMessageSendHandler = new MyMessageSendHandler();
		scrollPane = new ScrollPane();
		// Handels makeup
		sendMessage.setPrefSize(sendButtonWidth, sendButtonHeight);
		sendMessage.setOnAction(myButtonHandler);
		playerMessage.setPrefWidth(chatPaneWidth - sendButtonWidth);
		playerMessage.setOnKeyPressed(myMessageSendHandler);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setFitToWidth(true);
		;
		scrollPane.setContent(center);
		// sets everything to the ChatPane
		bottom.getChildren().addAll(playerMessage, sendMessage);
		setCenter(scrollPane);
		setBottom(bottom);
	}

	public void updateChat(ArrayList<Message> messages) {
		for (Message message : messages) {
			center.getChildren().add(new MessagePane(message.getUserName(), message.getMessage(), message.getChatTime()));
		}
		scrollPane.vvalueProperty().bind(center.heightProperty());
	}

	private class MyButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			sendMessage();
		}
	}

	private class MyMessageSendHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER) {
				sendMessage();
			}
		}

	}

	private void sendMessage() {
		gameController.sendMessages(playerMessage.getText());
		playerMessage.clear();

	}
}
