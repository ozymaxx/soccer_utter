package iristk.app.soccer_utter;

public class PlayerMovement extends Movement {
	int teamNo;
	Point startPoint,endPoint;
	
	public PlayerMovement(Point startPoint,Point endPoint) {
		teamNo = -1;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public PlayerMovement(int teamNo,Point startPoint,Point endPoint) {
		this.teamNo = teamNo;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	@Override
	public String toString() {
		return "PLMOV("+teamNo+",("+startPoint.getX()+","+startPoint.getY()+")->("+endPoint.getX()+","+endPoint.getY()+"))";
	}
}
