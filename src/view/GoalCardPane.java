package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GoalCardPane extends AnchorPane {

	public GoalCardPane(int seqNr) {
		ImageView imageView = new ImageView();
		lookForImage(seqNr, imageView);
		getChildren().add(imageView);
		
	}

	private void lookForImage(int seqNr, ImageView imageView) {
		switch (seqNr) {

		case 1:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Public GoalCards/card1.png"));
			 break;
		case 2:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Public GoalCards/card2.png"));
			 break;
		case 3:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Public GoalCards/card3.png"));	
			 break;
		case 4:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Public GoalCards\\card4.png"));	
			 break;
		case 5:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Public GoalCards\\card5.png"));	
			 break;
		case 6:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Public GoalCards\\card6.png"));	
			 break;
		case 7:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Public GoalCards\\card7.png"));	
			 break;
		case 8:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Public GoalCards\\card8.png"));		
			 break;
		case 9:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Public GoalCards\\card9.png"));	
			 break;
		case 10:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Public GoalCards\\card10.png"));		
			 break;
		default:
				imageView.setImage(new Image(
					"file:Resources\\images\\Priavate GoalCards/back.png"));
			
		}
	}
}
