package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

class RuleDrawPane extends StackPane {
	// constants
	private static final int PANEWIDTH = 800, PANEHEIGHT = 750;

	// instance variables
	private ImageView imageView;

	// constructor
	RuleDrawPane() {
		setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), null, null)));
		imageView = new ImageView();

		setMaxSize(PANEWIDTH, PANEHEIGHT);
		setMinSize(PANEWIDTH, PANEHEIGHT);

		// this make the images look good on the pane
		imageView.fitWidthProperty().bind(widthProperty());
		imageView.fitHeightProperty().bind(heightProperty());
		imageView.setPreserveRatio(true);

		getChildren().add(imageView);
		showProgression();
	}

	//show rules about progression
	void showProgression() {
		imageView.setImage(new Image("file:Resources/images/Rules/Spelverloop.png"));
	}

	// show rules about die placing
	void showDiePlacing() {
		imageView.setImage(new Image("file:Resources/images/Rules/Dobbelsteenplaatsen.png"));
	}

	//show rules about toolcards
	void showToolCard() {
		imageView.setImage(new Image("file:Resources/images/Rules/Gereedschapskaarten.png"));
	}
}
