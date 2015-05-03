package hu.puff.diffsolver.output;

public class MeasurementItem {
	public double value;
	public double timeStamp;
	
	
	
	public MeasurementItem(double value, double timeStamp) {
		super();
		this.value = value;
		this.timeStamp = timeStamp;
	}



	@Override
	public String toString() {
		return "MeasurementItem [value=" + value + ", timeStamp=" + timeStamp
				+ "]";
	}
}
