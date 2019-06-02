package view;

import java.util.ArrayList;

import controllers.GameController;
import controllers.MainApplication;
import game.GameColor;
import game.GlassWindow;
import game.Player;
import game.SpaceGlass;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class GlassWindowPane extends BorderPane {
	/* CONSTANTS */
	private final double glassWindowWidth = 400*MainApplication.width;
	private final double glassWindowHeight = 800*MainApplication.height;
	private final double spacingAmount = 20*MainApplication.height;
	private final double borderwidth = 5*MainApplication.width;
	
	private final double smalRadius = 60*MainApplication.height;
	private final double bigRadius = 140*MainApplication.height;
	private final double smallFont = 20*MainApplication.height;
	private final double bigFont = 40*MainApplication.height;
	private final double smallInset = 10*MainApplication.height;
	private final Insets insets = new Insets(10*MainApplication.height, 20*MainApplication.height, 0*MainApplication.height, 20*MainApplication.height);
	private final double scoreCircleX = 180*MainApplication.width;
	private final double scoreCircleY = 180*MainApplication.height;
	private final double scoreCircleRadius = 140*MainApplication.height;

	/* VARIABLES */
	private FieldPane fieldPane;

	private StackPane scoreField;
	private CornerRadii windowCurve;
	private GameColor color;
	private Circle circle;
	private Label labelScore;
	private Label labelName;

	private int switchingNumber;
	private boolean clientPlayer;

	private boolean isSmall = false;

	public GlassWindowPane(int number, Player player, GameScene gameScene, GameController gameController) {
		switchingNumber = number;
		clientPlayer = (player.getPlayerID() == gameController.getClientPlayer().getPlayerID()) ? true : false;
		this.color = player.getColor();

		fieldPane = new FieldPane(player.getGlassWindow().getPatternCard(), gameController);

		if (!clientPlayer) {
			fieldPane.setDisable(true);
		}

		windowCurve = new CornerRadii(5, 3, 3, 5, 0, 0, 0, 0, true, true, true, true, false, false, false, false);

		setBackground(new Background(new BackgroundFill(Color.rgb(68, 47, 25), windowCurve, null)));
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, windowCurve, new BorderWidths(5))));

		setScore();
		setName(player.getUsername());
		setBottom(fieldPane);

		setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (isSmall) {
					gameScene.switchGlassWindows(switchingNumber);
				}
			}
		});

		resize();
	}

	public void toggleIsSmall() {
		isSmall = !isSmall;
		resize();
	}

	private void resize() {
		if (isSmall) {
			setPrefSize(glassWindowWidth / 2, glassWindowHeight / 2.5);
			setMaxSize(glassWindowWidth / 2, glassWindowHeight / 2.5);
			setMargin(fieldPane, new Insets(smallInset));
			circle.setRadius(smalRadius);
			labelScore.setFont(Font.font(smallFont));
			labelName.setFont(Font.font(smallFont));
			setMargin(scoreField, insets);
		} else {
			setPrefSize(glassWindowWidth, glassWindowHeight);
			setMaxSize(glassWindowWidth, glassWindowHeight);
			circle.setRadius(bigRadius);
			labelScore.setFont(Font.font(bigFont));
			labelName.setFont(Font.font(bigFont));
			setMargin(scoreField, new Insets(spacingAmount));
			setMargin(fieldPane, new Insets(spacingAmount));
		}

		fieldPane.resize(isSmall);
	}

	public void updateScore(int score) {
		labelScore.setText(String.valueOf(score));
	}

	public void updateGlassWindow(GlassWindow glassWindow) {
		fieldPane.loadGlassWindow(glassWindow);
	}

	private void setName(String name) {
		labelName = new Label(name);
		labelName.setTextFill(Color.WHITE);
		labelName.setFont(Font.font(bigFont));

		setCenter(labelName);
	}

	private void setScore() {
		scoreField = new StackPane();
		labelScore = new Label();
		labelScore.setTextFill(Color.BLACK);
		labelScore.setFont(Font.font(bigFont));

		circle = new Circle(scoreCircleX, scoreCircleY, scoreCircleRadius);
		circle.setFill(color.getColor());
		circle.setStroke(Color.BLACK);

		setMargin(scoreField, new Insets(spacingAmount));

		scoreField.getChildren().addAll(circle, labelScore);
		setTop(scoreField);
	}

	public void setSwitchingNumber(int num) {
		switchingNumber = num;
	}

	public int getSwitchingNumber() {
		return switchingNumber;
	}

	public void highlightSpaces(ArrayList<SpaceGlass> toHighlight) {
		fieldPane.highlightSpaces(toHighlight);
	}

	public void removeHighlightSpaces() {
		fieldPane.removeHilightSpaces();
	}

	public GameColor getColor() {
		return color;
	}

	public boolean isClientPlayer() {
		return clientPlayer;
	}

	public void setActiveBorder() {
		setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, windowCurve, new BorderWidths(borderwidth))));
	}

	public void setInactiveBorder() {
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, windowCurve, new BorderWidths(borderwidth))));
	}
}
