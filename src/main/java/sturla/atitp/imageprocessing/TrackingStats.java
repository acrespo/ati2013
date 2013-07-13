package sturla.atitp.imageprocessing;

import java.util.List;

public class TrackingStats {

	private double mu;
	
	private int quant = 0;
	private int acum = 0;
	
	private double[] lastCenter;
	
	private boolean objectLost;
	
	private List<Point> lastSelection;
	
	private double[] avgIn;
	private double[] avgOut;
	
	public TrackingStats(double mu) {
		this.mu = mu;
	}
	
	public void setAvgIn(double[] avgIn) {
		this.avgIn = avgIn;
	}
	
	public void setAvgOut(double[] avgOut) {
		this.avgOut = avgOut;
	}
	
	public double[] getAvgIn() {
		return avgIn;
	}
	
	public double[] getAvgOut() {
		return avgOut;
	}
	
	public void firstSelection(List<Point> selection) {
		lastSelection = selection;
	}
	
	public void addSelection(List<Point> selection) {
			if (objectLost) {
				lastSelection = selection;
				objectLost = false;
			} else {
				if (quant != 0 && selection.size() < ( acum / quant * mu)) {
					System.out.println("Object lost!");
					System.out.println("Curr: " + selection.size());
					System.out.println("Avg: " + acum / quant);
					objectLost = true;
				} else {
					quant++;
					acum += selection.size();
				}
				lastSelection = selection;
			}
	}
	
	public boolean isObjectLost() {
		return objectLost;
	}
	
	
	public void setLastCenter(double[] lastCenter) {
		this.lastCenter = lastCenter;
	}
	
	public double[] getLastCenter() {
		return lastCenter;
	}
	
	public List<Point> getLastSelection() {
		return lastSelection;
	}
}
