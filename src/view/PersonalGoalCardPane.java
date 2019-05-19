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
			imageView.setImage(new Image("file:Resources\\images\\Priavate GoalCards\\red.png"));
			break;
		case GREEN:
			imageView.setImage(new Image("file:Resources\\images\\Priavate GoalCards\\green.png"));
			break;
		case BLUE:
			imageView.setImage(new Image("file:Resources\\images\\Priavate GoalCards\\blue.png"));
			break;
		case YELLOW:
			imageView.setImage(new Image("file:Resources\\images\\Priavate GoalCards\\yellow.png"));
			break;
		case PURPLE:
			imageView.setImage(new Image("file:Resources\\images\\Priavate GoalCards\\purple.png"));
			break;
		default:
			imageView.setImage(new Image("file:Resources\\images\\Priavate GoalCards\\back.png"));
			break;
		}
	}

	public void loadCardBack() {
		imageView.setImage(new Image("file:Resources\\images\\Priavate GoalCards\\back.png"));
	}

}
