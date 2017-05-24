package iristk.app.soccer_utter;

import javafx.scene.layout.Pane;

public class PutSingleTeamName implements Runnable {
	private Sketch symbol;
	private Pane root;
	private String teamName;
	
	public PutSingleTeamName(Sketch symbol,Pane root,String teamName) {
		this.symbol = symbol;
		this.root = root;
		this.teamName = teamName;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		symbol.putTeamName(root, teamName);
	}

}
