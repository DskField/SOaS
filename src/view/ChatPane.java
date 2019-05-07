package view;

import java.sql.Timestamp;

import controllers.GameController;
import game.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ChatPane extends BorderPane {
	// constants
	private final int chatPaneWidth = 600;
	private final int chatPaneheight = 200;
	private final int sendButtonWidth = 100;
	private final int sendButtonHeight = 20;
	// variables
	TextArea chat;
	TextField playerMessage;
	Button sendMessage;
	ScrollPane scrollPane;
	HBox bottom;
	MyButtonHandler myButtonHandler;
	MyTextfieldHandler myTextfieldHandler;
	GameController gameController;

	public ChatPane(GameController gameController) {
		this.gameController = gameController;

		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		setPrefSize(chatPaneWidth, chatPaneheight);

		createChat();
	}

	private void createChat() {
		// initialize
		chat = new TextArea();
		playerMessage = new TextField();
		sendMessage = new Button("verstuur");
		bottom = new HBox();
		scrollPane = new ScrollPane();
		myButtonHandler = new MyButtonHandler();
		myTextfieldHandler = new MyTextfieldHandler();
		// Handels makeup
		sendMessage.setPrefSize(sendButtonWidth, sendButtonHeight);
		sendMessage.setOnAction(myButtonHandler);
		playerMessage.setPrefWidth(chatPaneWidth - sendButtonWidth);
		playerMessage.setOnKeyPressed(myTextfieldHandler);
		scrollPane.setContent(chat);
		chat.setWrapText(true);
		chat.setEditable(false);
		// sets everything to the ChatPane
		bottom.getChildren().addAll(playerMessage, sendMessage);
		setCenter(chat);
		setBottom(bottom);
	}

	public void updateChat(String string) {
		chat.setText(string);
	}

	private class MyButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			sendMessage();
		}
	}

	private class MyTextfieldHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER) {
				sendMessage();
			}
		}

	}

	private void sendMessage() {
		Message message = new Message(playerMessage.getText(), gameController.getClientUser(), new Timestamp(System.currentTimeMillis()));
		System.out.println("1");
		gameController.sendMessages(message);
		playerMessage.clear();

	}
}
