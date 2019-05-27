package view;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class ToolCardPane extends FlowPane {
	private FlowPane flowPane;
	private static final int vBoxPrefWidth = 230;
	ImageView imageView;
	int seqNr;
	public ToolCardPane(int seqNr) {
		flowPane = new FlowPane();
		flowPane.setPrefWidth(vBoxPrefWidth);
		this.seqNr = seqNr;
		imageView = new ImageView();
		lookForImage(seqNr);
		getChildren().addAll(imageView, flowPane);
	}
	
	public int getSeqNr() {
		return seqNr;
	}

	private void lookForImage(int seqNr) {
		switch(seqNr) {
		
		case 1:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card1.png"));
			 break;
		case 2:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card2.png"));
			 break;
		case 3:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card3.png"));
			 break;
		case 4:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card4.png"));
			 break;
		case 5:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card5.png"));
			 break;
		case 6:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card6.png"));
			 break;
		case 7:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card7.png"));
			 break;
		case 8:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card8.png"));
			 break;
		case 9:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card9.png"));
			 break;
		case 10:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card10.png"));
			 break;
		case 11:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card11.png"));
			 break;
		case 12:
			 imageView.setImage(new Image(
					 "file:Resources\\images\\Toolcards\\card12.png"));
			 break;
		default:
			imageView.setImage(new Image(
					"file:Resources\\images\\Private Goalcards\\back.png"));
			}
	}
	public void updateStones(ArrayList<CurrencyStonePane> stones) {
		flowPane.getChildren().clear();
		flowPane.getChildren().addAll(stones);
	}
}
