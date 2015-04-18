package szimtech.puff.output;

public class MeasurementItem {
	public float value;
	public float timeStamp;
	
	
	
	public MeasurementItem(float value, float timeStamp) {
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
