package iristk.app.soccer_utter;

public class Point {
	private double x,y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public String toString() {
		return x+"\\t"+y;
	}
	
	public double distanceTo(Point anotherPoint) {
		return Math.sqrt(Math.pow(x-anotherPoint.getX(), 2)+Math.pow(y-anotherPoint.getY(), 2));
	}
}
