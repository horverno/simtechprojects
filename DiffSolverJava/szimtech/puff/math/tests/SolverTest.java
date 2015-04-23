package szimtech.puff.math.tests;

import java.util.Map;

import szimtech.puff.math.Function;
import szimtech.puff.math.ResultStore;
import szimtech.puff.math.Solvers;

public class SolverTest {

	private final float coolingInitialState = 100f;
	private final float coolingStepSize = 1;
	private final int coolingSteps = 100;

	// kezdeti �rt�k: az inf�t a teljes lakoss�g mekkora s�r�s�g� r�sze ismeri
	private final float infoSpreadInitialState = 0.1f;
	private final float infoSpreadStepSize = 1;
	private final int infoSpreadSteps = 100;

	public ResultStore eulerCoolingTest() {
		// H�l�s
		ResultStore coolingResult = Solvers.eulerMethod(new Function() {

			@Override
			public float getFunction(float t, float y) {
				return -0.07f * (y - 20f);
			}
		}, coolingInitialState, coolingStepSize, coolingSteps);

		System.out.println("Euler m�dszer");
		System.out.println("H�l�s:");
		for (Map.Entry<Float, Float> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		return coolingResult;
	}

	public void rungeKuttaFourthOrderCoolingTest() {
		// H�l�s
		ResultStore coolingResult = Solvers.rungeKuttaFourthOrderMethod(
				new Function() {

					@Override
					public float getFunction(float t, float y) {
						return -0.07f * (y - 20f);
					}
				}, coolingInitialState, coolingStepSize, coolingSteps);

		System.out.println("Negyed rend� Runge-Kutta m�dszer");
		System.out.println("H�l�s:");
		for (Map.Entry<Float, Float> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void rungeKuttaSecondOrderCoolingTest() {
		// H�l�s
		ResultStore coolingResult = Solvers.rungeKuttaSecondOrderMethod(
				new Function() {

					@Override
					public float getFunction(float t, float y) {
						return -0.07f * (y - 20f);
					}
				}, coolingInitialState, coolingStepSize, coolingSteps);

		System.out.println("M�sodrend� Runge-Kutta m�dszer");
		System.out.println("H�l�s:");
		for (Map.Entry<Float, Float> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void implicitEulerCoolingTest() {
		// H�l�s
		ResultStore coolingResult = Solvers.implicitEulerMethod(new Function() {

			@Override
			public float getFunction(float t, float y) {
				return -0.07f * (y - 20f);
			}
		}, coolingInitialState, coolingStepSize, coolingSteps);

		System.out.println("Implicit Euler m�dszer");
		System.out.println("H�l�s:");
		for (Map.Entry<Float, Float> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void eulerInfoSpreadTest() {
		// inform�ci� terjed�se
		ResultStore infoSpreadResult = Solvers.eulerMethod(new Function() {

			@Override
			public float getFunction(float t, float y) {
				// konstans: az inform�ci� �tad�s hat�konys�ga
				return 0.1f * y * (1 - y);
			}
			// kezdeti �rt�k: az inf�t a teljes lakoss�g mekkora s�r�s�g� r�sze
			// ismeri
		}, infoSpreadInitialState, infoSpreadStepSize, infoSpreadSteps);

		System.out.println("Euler m�dszer");
		System.out.println("Inform�ci� terjed�se:");
		for (Map.Entry<Float, Float> entry : infoSpreadResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void rungeKuttaFourthOrderInfoSpreadTest() {
		// inform�ci� terjed�se
		ResultStore infoSpreadResult = Solvers.rungeKuttaFourthOrderMethod(
				new Function() {

					@Override
					public float getFunction(float t, float y) {
						// konstans: az inform�ci� �tad�s hat�konys�ga
						return 0.1f * y * (1 - y);
					}
				}, infoSpreadInitialState, infoSpreadStepSize, infoSpreadSteps);

		System.out.println("Negyed rend� Runge-Kutta m�dszer");
		System.out.println("Inform�ci� terjed�se:");
		for (Map.Entry<Float, Float> entry : infoSpreadResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void rungeKuttaSecondOrderInfoSpreadTest() {
		// inform�ci� terjed�se
		ResultStore infoSpreadResult = Solvers.rungeKuttaSecondOrderMethod(
				new Function() {

					@Override
					public float getFunction(float t, float y) {
						// konstans: az inform�ci� �tad�s hat�konys�ga
						return 0.1f * y * (1 - y);
					}
				}, infoSpreadInitialState, infoSpreadStepSize, infoSpreadSteps);

		System.out.println("M�sodrend� Runge-Kutta m�dszer");
		System.out.println("Inform�ci� terjed�se:");
		for (Map.Entry<Float, Float> entry : infoSpreadResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void rungeKuttaFourthOrderInfoSpreadWithFunctionImplTest() {
		// inform�ci� terjed�se
		Function function = new Function("0.1*y*(1-y)");

		ResultStore infoSpreadResult = Solvers.rungeKuttaFourthOrderMethod(
				function, infoSpreadInitialState, infoSpreadStepSize,
				infoSpreadSteps);

		System.out.println("Negyed rend� Runge-Kutta m�dszer FunctionIMPL");
		System.out.println("Inform�ci� terjed�se:");
		for (Map.Entry<Float, Float> entry : infoSpreadResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public static void main(String[] args) {
		SolverTest st = new SolverTest();

//		 st.eulerCoolingTest();
//		
//		 st.implicitEulerCoolingTest();
//		
//		 st.eulerInfoSpreadTest();
		 st.rungeKuttaFourthOrderInfoSpreadTest();
//		
//		 st.rungeKuttaFourthOrderCoolingTest();
//		st.rungeKuttaSecondOrderCoolingTest();
		
		st.rungeKuttaFourthOrderInfoSpreadWithFunctionImplTest();
	}

}
