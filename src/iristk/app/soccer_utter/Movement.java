package iristk.app.soccer_utter;

public abstract class Movement {
	private String additionalInfo;
	
	public Movement() {additionalInfo = null;}
	
	public void putAdditionalInfo(String info) {
		this.additionalInfo = info;
	}
	
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	
	public abstract String toString();
}
