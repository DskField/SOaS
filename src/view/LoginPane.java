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
	/* CONSTANTS */
	// Label
	private final int errorMessageSize = 23;
	private final Color errorMessageColor = Color.RED;
	private final Color errorMessageConfirmColor = Color.LIMEGREEN;

	// Constructor
	private final int paneWidth = 500;
	private final int paneHeight = 500;
	private final Color backgroundColor = Color.GRAY;

	// Buttons
	private final int buttonWidth = 100;
	private final int buttonHeight = 50;
	private final int buttonSpacing = 30;

	// TextFields
	private final int textfieldsSpacing = 20;
	private final int fieldsSpacing = 10;
	private final int butAndfieldsSpacing = 10;
	private final int butAndfieldsWidth = 200;
	private final int butAndfieldsHeight = 50;
	private final double textfieldsScaling = 1.3;

	/* VARIABLES */
	private HBox buttons;
	private ClientController clientcontroller;
	private TextField username;
	private PasswordField password;
	private Label errorMessage;

	/**
	 * Constructor used to create a LoginPane Object
	 * 
	 * @param clientcontroller - Object containing the reference to ClientController
	 */
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

	/**
	 * Method used to create all the buttons
	 */
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

	/**
	 * Method used to create the input fields
	 */
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
	}

	/**
	 * Method used to handle the login button. If login failed show an error message
	 *
	 * @param username - {@code String} containing the username input
	 * @param password - {@code String} containing the password input
	 */
	private void handleLogin(String username, String password) {
		if (!clientcontroller.handleLogin(username, password)) {
			errorMessage.setText("Gebruikersnaam of Wachtwoord is incorrect");
			errorMessage.setTextFill(errorMessageColor);
		}
	}

	/**
	 * Method used to handle the register button. If register failed show an error message
	 * 
	 * @param username - {@code String} containing the username input
	 * @param password - {@code String} containing the password input
	 */
	private void handleRegister(String username, String password) {
		if (!clientcontroller.handleRegister(username, password)) {
			errorMessage.setText("Mislukt, controleer de gebruikersnaam en wachtwoord");
			errorMessage.setTextFill(errorMessageColor);
		} else {
			errorMessage.setText("Account geregistreerd");
			errorMessage.setTextFill(errorMessageConfirmColor);
		}
	}
}