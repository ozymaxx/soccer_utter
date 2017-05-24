package iristk.app.soccer_utter;

import iristk.util.Record;

public class PutTeamNames implements Runnable {
	private String team1;
	private String team2;
	private boolean setSides;
	private boolean setSidesValue;
	
	public PutTeamNames(String team1,String team2,boolean setSides,boolean setSidesValue) {
		this.team1 = team1;
		this.team2 = team2;
		this.setSides = setSides;
		this.setSidesValue = setSidesValue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (team2 != null) {
			Soccer_utterSystem.query.setTeamName2(team2,setSidesValue);
		}
		
		if (team1 != null) {
			Soccer_utterSystem.query.setTeamName1(team1,setSidesValue);
		}
		
		if (setSides) {
			Soccer_utterSystem.query.setSidesCertain(setSidesValue);
		}
	}

}
