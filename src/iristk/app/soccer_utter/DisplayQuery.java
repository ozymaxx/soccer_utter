package iristk.app.soccer_utter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DisplayQuery implements Runnable {
	
	private String query;
	
	public DisplayQuery(String query) {
		this.query = query;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Your Query");
		alert.setHeaderText("Your DB Queries");
		alert.setContentText(query);
		alert.show();
	}

}
