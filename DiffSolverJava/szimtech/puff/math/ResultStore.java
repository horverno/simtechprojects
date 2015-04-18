package szimtech.puff.math;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultStore other = (ResultStore) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "ResultStore [result=" + result + "]";
	}
}
