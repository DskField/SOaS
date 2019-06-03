package view;

import controllers.MainApplication;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GoalCardPane extends AnchorPane {
	private final double width = 230*MainApplication.width;
	private final double height = 330*MainApplication.height;
	
	/* VARIABLES */
	private ImageView imageView;

	public GoalCardPane(int id) {
		imageView = new ImageView();
		lookForImage(id);
		getChildren().add(imageView);
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);

	}

	private void lookForImage(int id) {
		switch (id) {
		case 1:
			imageView.setImage(new Image("/images/GoalCards/Public/Card1.png"));
			break;
		case 2:
			imageView.setImage(new Image("/images/GoalCards/Public/Card2.png"));
			break;
		case 3:
			imageView.setImage(new Image("/images/GoalCards/Public/Card3.png"));
			break;
		case 4:
			imageView.setImage(new Image("/images/GoalCards/Public/Card4.png"));
			break;
		case 5:
			imageView.setImage(new Image("/images/GoalCards/Public/Card5.png"));
			break;
		case 6:
			imageView.setImage(new Image("/images/GoalCards/Public/Card6.png"));
			break;
		case 7:
			imageView.setImage(new Image("/images/GoalCards/Public/Card7.png"));
			break;
		case 8:
			imageView.setImage(new Image("/images/GoalCards/Public/Card8.png"));
			break;
		case 9:
			imageView.setImage(new Image("/images/GoalCards/Public/Card9.png"));
			break;
		case 10:
			imageView.setImage(new Image("/images/GoalCards/Public/Card10.png"));
			break;
		default:
			imageView.setImage(new Image("/images/GoalCards/Private/Back.png"));

		}
	}
}
