package iristk.app.soccer_utter;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class SearchQuery {
	private String team1Name;
	private String team2Name;
	private int time;
	private ArrayList<Point> playersTeam1;
	private ArrayList<Point> playersTeam2;
	private ArrayList<Movement> movements;
	private boolean sidesCertain;
	private Pane root;
	private MotionStory ms;
	
	public SearchQuery(Pane root,MotionStory ms) {
		playersTeam1 = new ArrayList<Point>();
		playersTeam2 = new ArrayList<Point>();
		movements = new ArrayList<Movement>();
		time = -1;
		team1Name = null;
		team2Name = null;
		sidesCertain = false;
		this.root = root;
		this.ms = ms;
	}
	
	public void removeTeamNames() {
		ms.removeTeamNames(root);
	}
	
	public void addPlayer(Point playerCentroid,int teamNo) {
		if (teamNo == 1) {
			playersTeam1.add(playerCentroid);
		}
		else {
			playersTeam2.add(playerCentroid);
		}
	}
	
	public void setTeamName1(String team, boolean cert) {
		team1Name = team;
		
		if (cert) {
			ms.putTeamNames(root, team, null);
		}
		
		String windowTitle = Soccer_utterSystem.mainStage.getTitle();
		String[] tokens = windowTitle.split("-");
		String sides = team;
		
		if (team2Name != null) {
			sides += "/"+team2Name;
		}
		else {
			sides += "/{unknown}";
		}
		
		if (sidesCertain) {
			sides += " (sides certain)";
		}
		else {
			sides += " (sides not certain)";
		}
		
		if (tokens.length == 1) {
			Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+sides);
		}
		else if (tokens.length == 2) {
			if (tokens[1].substring(0,4).equals("Time")) {
				Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+tokens[1]+"-"+sides);
			}
			else {
				Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+sides);
			}
		}
		else if (tokens.length == 3) {
			Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+tokens[1]+"-"+sides);
		}
	}
	
	public void setTeamName2(String team, boolean cert) {
		team2Name = team;
		
		if (cert) {
			ms.putTeamNames(root, null, team);
		}
		
		String windowTitle = Soccer_utterSystem.mainStage.getTitle();
		String[] tokens = windowTitle.split("-");
		String sides = team;
		
		if (team1Name != null) {
			sides = team1Name+"/"+sides;
		}
		else {
			sides = "{unknown}/"+sides;
		}
		
		if (sidesCertain) {
			sides += " (sides certain)";
		}
		else {
			sides += " (sides not certain)";
		}
		
		if (tokens.length == 1) {
			Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+sides);
		}
		else if (tokens.length == 2) {
			if (tokens[1].substring(0,4).equals("Time")) {
				Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+tokens[1]+"-"+sides);
			}
			else {
				Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+sides);
			}
		}
		else if (tokens.length == 3) {
			Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+tokens[1]+"-"+sides);
		}
	}
	
	public void setSidesCertain(boolean val) {
		this.sidesCertain = val;
	}
	
	public boolean getSidesCertain() {
		return sidesCertain;
	}
	
	public String getTeamName1() {
		return team1Name;
	}
	
	public String getTeamName2() {
		return team2Name;
	}
	
	public void setTime(int time) {
		this.time = time;
		String windowTitle = Soccer_utterSystem.mainStage.getTitle();
		String[] tokens = windowTitle.split("-");
		
		if (tokens.length == 1) {
			Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+"Time (in mins.): "+this.time);
		}
		else if (tokens.length == 2) {
			if (tokens[1].substring(0,4).equals("Time")) {
				Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+"Time (in mins.): "+this.time);
			}
			else {
				Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+"Time (in mins.): "+this.time+"-"+tokens[1]);
			}
		}
		else if (tokens.length == 3) {
			Soccer_utterSystem.mainStage.setTitle(tokens[0]+"-"+"Time (in mins.): "+this.time+"-"+tokens[2]);
		}
		//Soccer_utterSystem.mainStage.setTitle("SoccerSearch - Time (in mins.): "+this.time);
	}
	
	public void addMovement(Movement mv) {
		movements.add(mv);
	}
	
	public void putAddInfoToLastMovement(String info) {
		movements.get(movements.size()-1).putAdditionalInfo(info);
		
		Sketch lastMovement = ms.getLatestMovementInProperCase();
		
		if (lastMovement != null) {
			lastMovement.putMovementSemantic(root, info);
		}
	}
	
	public String toString() {
		StringBuilder queryBuilder = new StringBuilder();
		
		/*
		queryBuilder.append("(");
		
		if (team1Name != null) {
			queryBuilder.append("TEAMS(1->"+team1Name+",2->"+team2Name+")");
		}
		
		queryBuilder.append(",");
		
		if (time != -1) {
			queryBuilder.append("TIME("+time+")");
		}
		
		queryBuilder.append(",");
		
		if (playersTeam1.size() > 0) {
			queryBuilder.append("PLAYERS_1(");
			
			for (int i = 0; i < playersTeam1.size(); ++i) {
				queryBuilder.append("("+playersTeam1.get(i).getX()+","+playersTeam1.get(i).getY()+")");
				
				if (i < playersTeam1.size()-1) {
					queryBuilder.append(",");
				}
			}
			
			queryBuilder.append(")");
		}
		
		queryBuilder.append(",");
		
		if (playersTeam2.size() > 0) {
			queryBuilder.append("PLAYERS_2(");
			
			for (int i = 0; i < playersTeam2.size(); ++i) {
				queryBuilder.append("("+playersTeam2.get(i).getX()+","+playersTeam2.get(i).getY()+")");
				
				if (i < playersTeam1.size()-1) {
					queryBuilder.append(",");
				}
			}
			
			queryBuilder.append(")");
		}
		
		queryBuilder.append(",");
		
		queryBuilder.append("MOVEMENTS(");
		
		for (int i = 0; i < movements.size(); ++i) {
			queryBuilder.append(movements.get(i).toString());
			
			if (i < movements.size()-1) {
				queryBuilder.append(",");
			}
		}
		
		queryBuilder.append(")");*/
		
		queryBuilder.append("{");
		
		StringBuilder condition;
		int startTeamNo;
		double startx,endx,starty,endy;
		String semantic;
		for (int i = 0; i < movements.size(); ++i) {
			queryBuilder.append("{select:'*',");
			condition = new StringBuilder();
			
			if (movements.get(i) instanceof BallMovement) {
				condition.append("motion == \"ball\"");
				startTeamNo = ((BallMovement) movements.get(i)).getStartTeamNo();
				startx = ((BallMovement) movements.get(i)).getStartPoint().getX();
				starty = ((BallMovement) movements.get(i)).getStartPoint().getY();
				endx = ((BallMovement) movements.get(i)).getEndPoint().getX();
				endy = ((BallMovement) movements.get(i)).getEndPoint().getY();
			}
			else {
				condition.append("motion == \"player\"");
				startTeamNo = ((PlayerMovement) movements.get(i)).getTeamNo();
				startx = ((PlayerMovement) movements.get(i)).getStartPoint().getX();
				starty = ((PlayerMovement) movements.get(i)).getStartPoint().getY();
				endx = ((PlayerMovement) movements.get(i)).getEndPoint().getX();
				endy = ((PlayerMovement) movements.get(i)).getEndPoint().getY();
			}
			
			if (startTeamNo != -1) {
				if (startTeamNo == 1) {
					if (sidesCertain) {
						condition.append(" && team == \"");
						condition.append(team1Name+"\"");
					}
					else {
						condition.append(" && team == \"");
						condition.append(team1Name+"\" || ");
						condition.append("team == \"");
						condition.append(team2Name+"\"");
					}
				}
				else {
					if (sidesCertain) {
						condition.append(" && team == \"");
						condition.append(team2Name+"\"");
					}
					else {
						condition.append(" && team == \"");
						condition.append(team1Name+"\" || ");
						condition.append("team == \"");
						condition.append(team2Name+"\"");
					}
				}
			}
			
			semantic = movements.get(i).getAdditionalInfo();
			
			if (semantic != null) {
				condition.append(" && semantic == \""+semantic+"\"");
			}
			
			if (i == 0) {
				if (time != -1) {
					condition.append(" && time == ");
					condition.append(time);
				}
			}
			
			if (sidesCertain) {
				if (team1Name != null) {
					condition.append(" && team1 == \"");
					condition.append(team1Name+"\"");
				}
				
				if (team2Name != null) {
					condition.append(" && team2 == \"");
					condition.append(team2Name+"\"");
				}
			}
			else {
				if (team1Name != null || team2Name != null) {
					condition.append("&& (");
					
					if (team1Name != null) {
						condition.append("team1 == \"");
						condition.append(team1Name+"\"");
					}
					
					if (team2Name != null) {
						if (team1Name != null) {
							condition.append(" && ");
						}
						
						condition.append("team2 == \"");
						condition.append(team2Name+"\"");
					}
					
					condition.append(") || (");
					
					if (team1Name != null) {
						condition.append("team2 == \"");
						condition.append(team1Name+"\"");
					}
					
					if (team2Name != null) {
						if (team1Name != null) {
							condition.append(" && ");
						}
						
						condition.append("team1 == \"");
						condition.append(team2Name+"\"");
					}
					
					condition.append(")");
				}
			}
			
			queryBuilder.append("condition:'");
			queryBuilder.append(condition);
			queryBuilder.append("',");
			queryBuilder.append("kNN-q:'<"+startx+","+starty+","+endx+","+endy+">,");
			queryBuilder.append("top:10}");
			
			if (i < movements.size()-1) {
				queryBuilder.append(",");
			}
		}
		
		queryBuilder.append("}");
		
		return queryBuilder.toString();
	}
	
}
