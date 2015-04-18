package szimtech.puff.output;


import java.util.TreeMap;

public class ResultStore {
	private TreeMap<Float, Float> result;

	public ResultStore(TreeMap<Float, Float> result) {
		super();
		this.result = result;
	}

	public ResultStore() {
		super();
		result = new TreeMap<Float, Float>();
	}

	public TreeMap<Float, Float> getResult() {
		return result;
	}

	public void setResult(TreeMap<Float, Float> result) {
		this.result = result;
	}
	
	public void addPoint(float t, float y){
		result.put(t, y);
	}
}
