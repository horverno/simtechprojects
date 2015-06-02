package hu.puff.diffsolver.math.tests;

import hu.puff.diffsolver.input.SolvMethod;
import hu.puff.diffsolver.math.DifferentailEquation;
import hu.puff.diffsolver.math.ResultStore;

import java.util.Map;

public class SolverTest {

	private final double coolingInitialState = 100f;
	private final  double coolingDerivateInitialState = 0f;
	private final double coolingStepSize = 10;
	private final int coolingSteps = 6;
	private final int coolingStartTime = 0;
	
	private final double secODEinitialState = 4f;
	private final  double secODEderivateInitialState = 1f;
	private final double secODEstepSize = 10;
	private final int secODEsteps = 10;
	private final int secODEstartTime = 0;

	public void eulerCoolingTest() throws Exception {
		DifferentailEquation difeq = new DifferentailEquation();
		ResultStore coolingResult = difeq.calculateResulte(
				"T'(t) = 0.07*(20-T(t))", SolvMethod.EULER, coolingInitialState, coolingDerivateInitialState,
				coolingStepSize, coolingSteps, coolingStartTime);

		System.out.println("Euler m�dszer");
		System.out.println("H�l�s:");
		for (Map.Entry<Double, Double> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public void rungeKuttaThirdOrderCoolingTest() throws Exception {
		DifferentailEquation difeq = new DifferentailEquation();
		ResultStore coolingResult = difeq.calculateResulte(
				"T'(t) = 0.07*(20-T(t))", SolvMethod.RUNGEKUTTA3, 100, coolingDerivateInitialState,
				coolingStepSize, coolingSteps, coolingStartTime);

		System.out.println("Harmadrend� Runge Kutta m�dszer");
		System.out.println("H�l�s:");
		for (Map.Entry<Double, Double> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public void rungeKuttaFourthOrderCoolingTest() throws Exception {
		// H�l�s
		DifferentailEquation difeq = new DifferentailEquation();
		ResultStore coolingResult = difeq.calculateResulte(
				"F'(x)=-0.07*(x-20)", SolvMethod.RUNGEKUTTA4, coolingInitialState, coolingDerivateInitialState,
				coolingStepSize, coolingSteps, coolingStartTime);


		System.out.println("Negyedrend� Runge Kutta m�dszer");
		System.out.println("H�l�s:");
		for (Map.Entry<Double, Double> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void midpointCoolingTest() throws Exception {
		// H�l�s
		DifferentailEquation difeq = new DifferentailEquation();
		ResultStore coolingResult = difeq.calculateResulte(
				"T'(t) = 0.07*(20-T(t))", SolvMethod.MIDPOINT, coolingInitialState, coolingDerivateInitialState,
				coolingStepSize, coolingSteps, coolingStartTime);


		System.out.println("Midpoint m�dszer");
		System.out.println("H�l�s:");
		for (Map.Entry<Double, Double> entry : coolingResult.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public void rungeKuttaFourthOrderSecondOrderOdeTest() throws Exception {

		DifferentailEquation difeq = new DifferentailEquation();
		ResultStore result = difeq.calculateResulte(
				"F''(x)=1/100*(1-F'(x)-2*F(x))", SolvMethod.RUNGEKUTTA4, secODEinitialState, secODEderivateInitialState,
				secODEstepSize, secODEsteps, secODEstartTime);


		System.out.println("Negyed rend� Runge Kutta m�dszer");
		System.out.println("M�sodrend� ode teszt:");
		for (Map.Entry<Double, Double> entry : result.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void midpointSecondOrderOdeTest() throws Exception {
	
		DifferentailEquation difeq = new DifferentailEquation();
		ResultStore result = difeq.calculateResulte(
				"F''(x)=1/100*(1-F'(x)-2*F(x))", SolvMethod.MIDPOINT, secODEinitialState, secODEderivateInitialState,
				secODEstepSize, secODEsteps, secODEstartTime);


		System.out.println("Midpoint m�dszer");
		System.out.println("M�sodrend� ode teszt:");
		for (Map.Entry<Double, Double> entry : result.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public void eulerSecondOrderOdeTest() throws Exception {

		DifferentailEquation difeq = new DifferentailEquation();
		ResultStore result = difeq.calculateResulte(
				"F''(x)=1/100*(1-F'(x)-2*F(x))", SolvMethod.EULER, secODEinitialState, secODEderivateInitialState,
				secODEstepSize, secODEsteps, secODEstartTime);


		System.out.println("Euler m�dszer");
		System.out.println("M�sodrend� ode teszt:");
		for (Map.Entry<Double, Double> entry : result.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public void rungeKuttaThirdOrderSecondOrderOdeTest() throws Exception {

		DifferentailEquation difeq = new DifferentailEquation();
		ResultStore result = difeq.calculateResulte(
				"x''=1/100*(1-x'-2*x)", SolvMethod.RUNGEKUTTA3, secODEinitialState, secODEderivateInitialState,
				secODEstepSize, secODEsteps, secODEstartTime);


		System.out.println("Harmadrend� Runge Kutta m�dszer");
		System.out.println("M�sodrend� ode teszt:");
		for (Map.Entry<Double, Double> entry : result.getResult()
				.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}
	
	public static void main(String[] args) throws Exception {
		SolverTest st = new SolverTest();
		
//		st.eulerCoolingTest();
//		st.midpointCoolingTest();
//		st.rungeKuttaFourthOrderCoolingTest();
//		st.rungeKuttaThirdOrderCoolingTest();
//		
//		st.rungeKuttaFourthOrderSecondOrderOdeTest();	
//		st.eulerSecondOrderOdeTest();
//		st.midpointSecondOrderOdeTest();
		st.rungeKuttaThirdOrderSecondOrderOdeTest();
	}

}
