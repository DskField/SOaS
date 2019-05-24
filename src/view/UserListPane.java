package view;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class UserListPane extends BorderPane {

	private ClientScene clientscene;
	private ListView<HBox> userlist;
	private ToggleGroup togglegroup;
	private HandleButton handlebutton;
	private ArrayList<String> users;
	
	public UserListPane() {
		
	}
	
	private class HandleButton implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			String[] lobby = ((ToggleButton) e.getSource()).getText().split(" ");
//			createCenter(Integer.parseInt(lobby[1]));
			// TODO TOM only update the variables and set errormessage visible to false
		}
	}
}
