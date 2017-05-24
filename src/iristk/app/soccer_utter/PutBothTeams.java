package iristk.app.soccer_utter;

import iristk.util.Record;

public class PutBothTeams implements Runnable {
	private String team1;
	private String team2;
	
	public PutBothTeams(String team1,String team2) {
		this.team1 = team1;
		this.team2 = team2;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Soccer_utterSystem.query.setTeamName1(team1,false);
		Soccer_utterSystem.query.setTeamName2(team2,false);
		Soccer_utterSystem.query.setSidesCertain(false);
		Soccer_utterSystem.query.removeTeamNames();
	}

}
