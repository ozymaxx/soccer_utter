package iristk.app.soccer_utter;

import iristk.util.Record;

public class SetTime implements Runnable {
	private int time;
	
	public SetTime(int time) {
		this.time = time;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Soccer_utterSystem.query.setTime(time);
	}

}
