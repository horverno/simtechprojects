package szimtech.puff.math.tests;

import java.util.Map;
import szimtech.puff.math.Function;
import szimtech.puff.math.ResultStore;
import szimtech.puff.math.Solvers;

public class SolverTest {

	private final float coolingInitialState = 100f;
	private final float coolingStepSize = 1;
	private final int coolingSteps = 100;
	
	// kezdeti érték: az infót a teljes lakosság mekkora sûrûségû része ismeri
	private final float infoSpreadInitialState = 0.1f;
	private final float infoSpreadStepSize = 1;
	private final int infoSpreadSteps = 100;
	
	public void eulerCoolingTest() {
		// Hûlés
		ResultStore coolingResult = Solvers.eulerMethod(new Function() {

			@Override
			public float getFunction(float t, float y) {
				return -0.07f * (y - 20f);
			}
		}, coolingInitialState, coolingStepSize, coolingSteps);

		System.out.println("Euler módszer");
		System.out.println("Hûlés:");
		for (Map.Entry<Float, Float> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void rungeKuttaFourthOrderCoolingTest() {
		// Hûlés
		ResultStore coolingResult = Solvers.rungeKuttaFourthOrderMethod(new Function() {

			@Override
			public float getFunction(float t, float y) {
				return -0.07f * (y - 20f);
			}
		}, coolingInitialState, coolingStepSize, coolingSteps);

		System.out.println("Negyed rendû Runge-Kutta módszer");
		System.out.println("Hûlés:");
		for (Map.Entry<Float, Float> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public void rungeKuttaSecondOrderCoolingTest() {
		// Hûlés
		ResultStore coolingResult = Solvers.rungeKuttaSecondOrderMethod(new Function() {

			@Override
			public float getFunction(float t, float y) {
				return -0.07f * (y - 20f);
			}
		}, coolingInitialState, coolingStepSize, coolingSteps);

		System.out.println("Másodrendû Runge-Kutta módszer");
		System.out.println("Hûlés:");
		for (Map.Entry<Float, Float> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public void implicitEulerCoolingTest() {
		//Hûlés
		ResultStore coolingResult = Solvers.implicitEulerMethod(new Function() {

			@Override
			public float getFunction(float t, float y) {
				return -0.07f * (y - 20f);
			}
		}, coolingInitialState, coolingStepSize, coolingSteps);

		System.out.println("Implicit Euler módszer");
		System.out.println("Hûlés:");
		for (Map.Entry<Float, Float> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public void eulerInfoSpreadTest() {
		// információ terjedése
		ResultStore infoSpreadResult = Solvers.eulerMethod(new Function() {

			@Override
			public float getFunction(float t, float y) {
				// konstans: az információ átadás hatékonysága
				return 0.1f * y * (1 - y);
			}
			// kezdeti érték: az infót a teljes lakosság mekkora sûrûségû része
			// ismeri
		}, infoSpreadInitialState, infoSpreadStepSize, infoSpreadSteps);

		System.out.println("Euler módszer");
		System.out.println("Információ terjedése:");
		for (Map.Entry<Float, Float> entry : infoSpreadResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void rungeKuttaFourthOrderInfoSpreadTest() {
		// információ terjedése
		ResultStore infoSpreadResult = Solvers.rungeKuttaFourthOrderMethod(new Function() {

			@Override
			public float getFunction(float t, float y) {
				// konstans: az információ átadás hatékonysága
				return 0.1f * y * (1 - y);
			}
		}, infoSpreadInitialState, infoSpreadStepSize, infoSpreadSteps);

		System.out.println("Negyed rendû Runge-Kutta módszer");
		System.out.println("Információ terjedése:");
		for (Map.Entry<Float, Float> entry : infoSpreadResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public void rungeKuttaSecondOrderInfoSpreadTest() {
		// információ terjedése
		ResultStore infoSpreadResult = Solvers.rungeKuttaSecondOrderMethod(new Function() {

			@Override
			public float getFunction(float t, float y) {
				// konstans: az információ átadás hatékonysága
				return 0.1f * y * (1 - y);
			}			
		}, infoSpreadInitialState, infoSpreadStepSize, infoSpreadSteps);

		System.out.println("Másodrendû Runge-Kutta módszer");
		System.out.println("Információ terjedése:");
		for (Map.Entry<Float, Float> entry : infoSpreadResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public static void main(String[] args) {		
		SolverTest st = new SolverTest();
//		
//		st.eulerCoolingTest();
//			
//		st.implicitEulerCoolingTest();
		
//		st.eulerInfoSpreadTest();
		st.rungeKuttaFourthOrderInfoSpreadTest();
//		
//		st.rungeKuttaFourthOrderCoolingTest();
//		st.rungeKuttaSecondOrderCoolingTest();
	}

}
