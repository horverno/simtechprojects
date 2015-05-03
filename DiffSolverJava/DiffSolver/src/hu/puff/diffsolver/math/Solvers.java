package hu.puff.diffsolver.math;

public class Solvers {
	
	public static ResultStore eulerMethod(Function fn, double y0, double deltaT, int n){
		double temp = y0;
		double t;
		ResultStore resultStore = new ResultStore();
	
		t = deltaT;
		resultStore.addPoint(0, y0);
		for (int i = 0; i < n; i += 1) {			
			temp += deltaT * fn.getFunction(t, temp);
			resultStore.addPoint(t, temp);
			t += deltaT;
		}
		return resultStore;
	}
	
	public static ResultStore implicitEulerMethod(Function fn, double y0, double deltaT, int n){				
		double temp = y0;
		double t;
		double forwardEulerResult = y0;

		ResultStore resultStore = new ResultStore();
		
		t = deltaT;
		resultStore.addPoint(0, y0);
		for (int i = 0; i < n; i += 1) {	
			forwardEulerResult = temp;
			forwardEulerResult += deltaT * fn.getFunction(t, temp);
			temp += deltaT * fn.getFunction(t, forwardEulerResult);
			resultStore.addPoint(t, temp);
			t += deltaT;
		}
		return resultStore;
	}
	
	public static ResultStore rungeKuttaSecondOrderMethod(Function fn, double y0, double deltaT, int n){
		double k1, k2;
		double t;
		double temp = y0;
		ResultStore resultStore = new ResultStore();

		t = deltaT;
		resultStore.addPoint(0, y0);
		for (int i = 0; i < n; i += 1) {
			k1 = deltaT * fn.getFunction(t, temp);
			k2 = deltaT * fn.getFunction(t + (deltaT/2), temp + (k1/2));			
			temp += k2;
			resultStore.addPoint(t, temp);
			t += deltaT;
		}		
		return resultStore;
	}
	
	public static ResultStore rungeKuttaFourthOrderMethod(Function fn, double y0, double deltaT, int n){
		double k1, k2 , k3, k4;
		double temp = y0;
		double t;
		ResultStore resultStore = new ResultStore();

		t = deltaT;
		resultStore.addPoint(0, y0);
		for (int i = 0; i < n; i += 1) {
			k1 = deltaT * fn.getFunction(t, temp);
			k2 = deltaT * fn.getFunction(t + (deltaT/2), temp + (k1/2));
			k3 = deltaT * fn.getFunction(t + (deltaT/2), temp + (k2/2));
			k4 = deltaT * fn.getFunction(t + deltaT, temp + k3);
			temp += (1f/6f)*(k1+2f*k2+2f*k3+k4);
			resultStore.addPoint(t, temp);
			t += deltaT;
		}		
		return resultStore;
	}
}
