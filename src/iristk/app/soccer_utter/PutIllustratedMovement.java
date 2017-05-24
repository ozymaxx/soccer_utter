package iristk.app.soccer_utter;

import iristk.util.Record;

public class PutIllustratedMovement implements Runnable {
	private String semantic;
	
	public PutIllustratedMovement(String semantic) {
		this.semantic = semantic;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Soccer_utterSystem.query.putAddInfoToLastMovement(semantic);
	}

}
