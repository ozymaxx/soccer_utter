package iristk.app.soccer_utter;

public class BallMovement extends Movement {
	int startTeamNo,endTeamNo;
	Point startPoint,endPoint;
	
	public BallMovement(int startTeamNo,int endTeamNo,Point startPoint,Point endPoint) {
		this.startTeamNo = startTeamNo;
		this.endTeamNo = endTeamNo;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public BallMovement(Point startPoint,Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		startTeamNo = endTeamNo = -1;
	}
	
	public void setStartTeamNo(int startTeamNo) {
		this.startTeamNo = startTeamNo;
	}
	
	public void setEndTeamNo(int endTeamNo) {
		this.endTeamNo = endTeamNo;
	}
	
	@Override
	public String toString() {
		return "BALLMOV("+startTeamNo+",("+startPoint.getX()+","+startPoint.getY()+")->"+endTeamNo+",("+endPoint.getX()+","+endPoint.getY()+")"+")";
	}
}
