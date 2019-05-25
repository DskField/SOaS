package view;

import controllers.ClientController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LoginPane extends BorderPane {

	private HBox buttons;
	private ClientController clientcontroller;
	private TextField username;
	private PasswordField password;
	private Label errorMessage;

	// Magic Numbers Label
	final private static int errorMessageSize = 23;
	final private static Color errorMessageColor = Color.RED;
	
	// Magic Numbers constructor
	final private static int paneWidth = 500;
	final private static int paneHeight = 500;
	final private static Color backgroundColor = Color.GRAY;

	// Magic Numbers buttons
	final private static int buttonWidth = 100;
	final private static int buttonHeight = 50;
	final private static int buttonSpacing = 30;

	// Magic Numbers textfields
	final private static int textfieldsSpacing = 20;
	final private static int fieldsSpacing = 10;
	final private static int butAndfieldsSpacing = 10;
	final private static int butAndfieldsWidth = 200;
	final private static int butAndfieldsHeight = 50;
	final private static double textfieldsScaling = 1.3;

	public LoginPane(ClientController clientcontroller) {
		this.clientcontroller = clientcontroller;
		setPrefSize(paneWidth, paneHeight);
		setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
		createButtons();
		createTextFields();

		// Try to login on enterkey
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.ENTER) {
					clientcontroller.handleLogin(username.getText(), password.getText());
				}
			}
		});
	}

	public void createButtons() {
		buttons = new HBox();

		Button login = new Button("Login");
		login.setPrefSize(buttonWidth, buttonHeight);
		login.setOnAction(e -> handleLogin(username.getText(), password.getText()));

		Button register = new Button("Registreer");
		register.setPrefSize(buttonWidth, buttonHeight);
		register.setOnAction(e -> handleRegister(username.getText(), password.getText()));

		buttons.setSpacing(buttonSpacing);
		buttons.getChildren().addAll(login, register);
		buttons.setAlignment(Pos.CENTER);

	}
	
	private void handleLogin(String username, String password) {
		if (!clientcontroller.handleLogin(username, password)) 
			errorMessage.setText("Gebruikersnaam of Wachtwoord is incorrect");
	}
	
	private void handleRegister(String username, String password) {
		if (!clientcontroller.handleRegister(username, password))
			errorMessage.setText("Gebruikersnaam is al in gebruik");
		else
			errorMessage.setText("");
	}

	public void createTextFields() {
		Label sagrada = new Label("Sagrada");
		sagrada.setStyle(" -fx-font-size:60px;-fx-text-fill: white");

		this.username = new TextField();
		username.setPromptText("Gebruikersnaam");
		username.setAlignment(Pos.CENTER);

		password = new PasswordField();
		password.setPromptText("Wachtwoord");
		password.setAlignment(Pos.CENTER);

		VBox textfields = new VBox();
		textfields.setSpacing(textfieldsSpacing);

		VBox fields = new VBox(username, password);
		fields.setSpacing(fieldsSpacing);
		fields.setStyle("-fx-font-size:20px;");

		VBox butAndfields = new VBox(fields, buttons);
		butAndfields.setSpacing(butAndfieldsSpacing);
		butAndfields.setMinSize(butAndfieldsWidth, butAndfieldsHeight);
		butAndfields.setMaxWidth(butAndfieldsWidth);
		textfields.setScaleX(textfieldsScaling);
		textfields.setScaleY(textfieldsScaling);

		Button quit = new Button("Afsluiten");
		quit.setPrefSize((buttonWidth * 2), buttonHeight);
		quit.setOnAction(e -> Platform.exit());
		
		errorMessage = new Label();
		errorMessage.setFont(Font.font(errorMessageSize));
		errorMessage.setTextFill(errorMessageColor);
		errorMessage.setAlignment(Pos.CENTER);
		
		textfields.getChildren().addAll(sagrada, butAndfields, quit, errorMessage);
		textfields.setAlignment(Pos.CENTER);
		setCenter(textfields);

		// TODO DELETE
		username.setText("speler1");
		password.setText("speler1");
	}
}