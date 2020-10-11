package digital.wander.covid.models;

public class ReportedData {
	
	private int confirmed;
	private int active;
	private int recovered;
	private int deceased;
	
	
	public int getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getRecovered() {
		return recovered;
	}
	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}
	public int getDeceased() {
		return deceased;
	}
	public void setDeceased(int deceased) {
		this.deceased = deceased;
	}
	
	

}
