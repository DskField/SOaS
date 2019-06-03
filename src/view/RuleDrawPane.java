package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

class RuleDrawPane extends StackPane {
	/* CONSTANTS */
	private final int PANEWIDTH = 800, PANEHEIGHT = 750;

	/* VARIABLES */
	private ImageView imageView;

	public RuleDrawPane() {
		setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), null, null)));
		imageView = new ImageView();

		setMaxSize(PANEWIDTH, PANEHEIGHT);
		setMinSize(PANEWIDTH, PANEHEIGHT);

		// this make the images look good on the pane
		imageView.fitWidthProperty().bind(widthProperty());
		imageView.fitHeightProperty().bind(heightProperty());
		imageView.setPreserveRatio(true);

		getChildren().add(imageView);
	}

	/**
	 * Show the rules about the game progression
	 */
	public void showProgression() {
		imageView.setImage(new Image("/images/Rules/Spelverloop.png"));
	}

	/**
	 * Show rules about die placing
	 */
	public void showDiePlacing() {
		imageView.setImage(new Image("/images/Rules/Dobbelsteenplaatsen.png"));
	}

	/**
	 * Show rules about toolcards
	 */
	public void showToolCard() {
		imageView.setImage(new Image("/images/Rules/Gereedschapskaarten.png"));
	}
}
