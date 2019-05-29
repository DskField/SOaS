package view;

import game.GameColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PersonalGoalCardPane extends StackPane {
	ImageView imageView;

	public PersonalGoalCardPane() {
		imageView = new ImageView();
		getChildren().add(imageView);
	}

	public void loadPersonalGoalCardImage(GameColor gameColor) {
		switch (gameColor) {
		case RED:
			imageView.setImage(new Image("/images/GoalCards/Private/Red.png"));
			break;
		case GREEN:
			imageView.setImage(new Image("/images/GoalCards/Private/Green.png"));
			break;
		case BLUE:
			imageView.setImage(new Image("/images/GoalCards/Private/Blue.png"));
			break;
		case YELLOW:
			imageView.setImage(new Image("/images/GoalCards/Private/Yellow.png"));
			break;
		case PURPLE:
			imageView.setImage(new Image("/images/GoalCards/Private/Purple.png"));
			break;
		default:
			imageView.setImage(new Image("/images/GoalCards/Private/Back.png"));
			break;
		}
	}

	public void loadCardBack() {
		imageView.setImage(new Image("/images/GoalCards/Private/Back.png"));
	}

}
