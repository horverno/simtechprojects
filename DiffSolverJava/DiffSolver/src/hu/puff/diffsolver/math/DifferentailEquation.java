package hu.puff.diffsolver.math;

import hu.puff.diffsolver.input.SolvMethod;

public class DifferentailEquation {

	private String expression;
	private int order;
	private char variable;
	private char markFn;
	// time
	Character stepChar = null;

	// Az egyenlet megadásának formája
	// pl.: F'(x) vagy x'
	private enum equationForms {
		FN, COMMA
	};
	private equationForms form;

	public DifferentailEquation() {
		super();
	}

	// A változók aktuális értékének behelytesitése az egyenletbe elsõfokú ode esetén
	private String insertValues(String expression, double t, double y) {
		StringBuilder result = new StringBuilder();
		int i = 0;
		if (form.equals(equationForms.COMMA)) {
			while (i < expression.length()) {
				if (expression.charAt(i) == variable)
					result.append("(" + y + ")");
				else if (stepChar != null && expression.charAt(i) == stepChar)
					result.append("(" + t + ")");
				else {
					result.append(expression.charAt(i));
				}
				i++;
			}
		} else if (form.equals(equationForms.FN)) {
			while (i < expression.length()) {
				if (expression.charAt(i) == markFn) {
					result.append("(" + y + ")");
					i += 4;
				} else if (expression.charAt(i) == variable) {
					result.append("(" + t + ")");
					i++;
				} else {
					result.append(expression.charAt(i));
					i++;
				}
			}
		}
		return result.toString();
	}

	// A változók aktuális értékének behelytesitése az egyenletbe másodfokó ode esetén
	private String insertValues(String expression, double t, double y, double dy) {
		StringBuilder result = new StringBuilder();
		int i = 0;

		if (form.equals(equationForms.COMMA)) {
			while (i < expression.length()) {
				if (expression.charAt(i) == variable
						&& i != expression.length()
						&& expression.charAt(i + 1) == '\'') {
					result.append("(" + dy + ")");
					i += 2;
				} else if (expression.charAt(i) == variable) {
					result.append("(" + y + ")");
					i++;
				} else if (stepChar != null && expression.charAt(i) == stepChar) {
					result.append("(" + t + ")");
					i++;
				} else {
					result.append(expression.charAt(i));
					i++;
				}
			}
		} else if (form.equals(equationForms.FN)) {
			while (i < expression.length()) {
				if (expression.charAt(i) == markFn
						&& expression.charAt(i + 1) == '\'') {
					result.append("(" + dy + ")");
					i += 5;
				} else if (expression.charAt(i) == markFn) {
					result.append("(" + y + ")");
					i += 4;
				} else if (expression.charAt(i) == variable) {
					result.append("(" + t + ")");
					i++;
				} else {
					result.append(expression.charAt(i));
					i++;
				}
			}
		}
		return result.toString();
	}

	// Az egyenlet jobb és bal oldalának szétválasztása
	private String[] splitEquation(String equation) throws DiffMathException {
		String[] sides = equation.split("=");

		if (sides.length == 2) {
			return equation.split("=");
		} else
			throw new DiffMathException("Wrong equation");
	}

	// Az egyentel megadás módjának és a változók betüjelének meghatározása
	private void setDifferentialEquationVariableAndForm(String equationLeftSide)
			throws DiffMathException {
		if (equationLeftSide.length() == 2) {
			if (Character.isLetter(equationLeftSide.charAt(0))
					&& equationLeftSide.charAt(1) == '\'')
				form = equationForms.COMMA;
			variable = equationLeftSide.charAt(0);
			return;
		} else if (equationLeftSide.length() == 3) {
			if (Character.isLetter(equationLeftSide.charAt(0))
					&& equationLeftSide.charAt(1) == '\''
					&& equationLeftSide.charAt(2) == '\'')
				form = equationForms.COMMA;
			variable = equationLeftSide.charAt(0);
			return;
		} else if (equationLeftSide.length() == 5) {
			if (equationLeftSide.charAt(2) == '('
					&& equationLeftSide.charAt(4) == ')'
					&& Character.isLetter(equationLeftSide.charAt(3)))
				markFn = equationLeftSide.charAt(0);
			form = equationForms.FN;
			variable = equationLeftSide.charAt(3);
			return;
		} else if (equationLeftSide.length() == 6) {
			if (equationLeftSide.charAt(3) == '('
					&& equationLeftSide.charAt(5) == ')'
					&& Character.isLetter(equationLeftSide.charAt(4)))
				markFn = equationLeftSide.charAt(0);
			form = equationForms.FN;
			variable = equationLeftSide.charAt(4);
			return;
		}
		throw new DiffMathException("Wrong equation");
	}

	// Az egyentel rendjének meghatározása
	private void setDifferentialEquationOrder(String equationLeftSide)
			throws DiffMathException {
		int order = 0;
		for (int i = 0; i < equationLeftSide.length(); i++) {
			if (equationLeftSide.charAt(i) == '\'')
				order++;
		}
		if (order > 2 || order < 1)
			throw new DiffMathException("Only for first and second order ODE");
		else
			this.order = order;
	}

	// validálás
	private void validateEquation(String rightSide) throws DiffMathException {
		char actChar;
		if (order == 1) {
			if (form.equals(equationForms.COMMA)) {
				for (int i = 0; i < rightSide.length(); i++) {
					actChar = rightSide.charAt(i);
					if (actChar == '\'') {
						throw new DiffMathException("Wrong equation");
					}
					if (Character.isLetter(actChar) && actChar != variable) {
						if (stepChar == null || stepChar == actChar)
							stepChar = new Character(actChar);
						else
							throw new DiffMathException("Wrong equation");
					}
				}
			} else if (form.equals(equationForms.FN)) {
				for (int i = 0; i < rightSide.length(); i++) {
					actChar = rightSide.charAt(i);
					if (actChar == '\'') {
						throw new DiffMathException("Wrong equation");
					}
					if (actChar == markFn) {
						if (rightSide.charAt(i + 1) != '('
								|| rightSide.charAt(i + 3) != ')'
								|| rightSide.charAt(i + 2) != variable) {
							throw new DiffMathException("Wrong equation");
						}
					} else if (Character.isLetter(actChar)
							&& actChar != variable) {
						throw new DiffMathException("Wrong equation");
					}
				}
			}
		} else if (order == 2) {
			if (form.equals(equationForms.COMMA)) {
				if (rightSide.charAt(0) == '\'')
					throw new DiffMathException("Wrong equation");
				for (int i = 0; i < rightSide.length(); i++) {
					actChar = rightSide.charAt(i);
					if (actChar == '\'' && i != rightSide.length() - 1
							&& rightSide.charAt(i + 1) == '\'') {
						throw new DiffMathException("Wrong equation");
					}
					if (Character.isLetter(rightSide.charAt(i))
							&& rightSide.charAt(i) != variable) {
						if (stepChar == null || stepChar == actChar)
							stepChar = new Character(actChar);
						else
							throw new DiffMathException("Wrong equation");
					} else if (actChar == '\'' && i != 0
							&& rightSide.charAt(i - 1) != variable) {
						throw new DiffMathException("Wrong equation");
					}
				}
			} else if (form.equals(equationForms.FN)) {
				for (int i = 0; i < rightSide.length(); i++) {
					actChar = rightSide.charAt(i);
					if (actChar == '\'' && i != rightSide.length() - 1
							&& rightSide.charAt(i + 1) == '\'') {
						throw new DiffMathException("Wrong equation");
					}
					if (actChar == markFn) {
						if (rightSide.charAt(i + 1) != '('
								|| rightSide.charAt(i + 3) != ')'
								|| rightSide.charAt(i + 2) != variable)
							if (rightSide.charAt(i + 1) != '\''
									|| rightSide.charAt(i + 2) != '('
									|| rightSide.charAt(i + 3) != variable
									|| rightSide.charAt(i + 4) != ')')
								throw new DiffMathException("Wrong equation");
					} else if (Character.isLetter(actChar)
							&& actChar != variable) {
						throw new DiffMathException("Wrong equation");
					}
				}
			}
		}
	}

	// A megoldó módszer által van meghivva. Kiszámolja a fgv értékét az adott pontban.
	private double getFunction(double t, double y) throws Exception {	
		String expression = insertValues(this.expression, t, y);
		return EvaluatingMathExpression.evaluating(expression);
	}

	private double getFunction(double t, double y, double dy) throws Exception {
		String expression = insertValues(this.expression, t, y, dy); 
		return EvaluatingMathExpression.evaluating(expression);
	}

	// Az osztály egyentlen pulikus metódusa.  Az input csapat ennek adhatja át 
	// a bemeneti értékeket, az output csapat ettõl kapja az eredményeket.
	public ResultStore calculateResulte(String equation, SolvMethod solver,
			double y0, double dy0, double deltaT, int n, int t0)
			throws DiffMathException {

		ResultStore result = null;
		equation = equation.replaceAll("\\s+", "");
		String[] sides = splitEquation(equation);

		sides[1] = sides[1].replaceAll("sin", "&&&");
		sides[1] = sides[1].replaceAll("cos", "@@@");
		sides[1] = sides[1].replaceAll("tan", "###");
		
		setDifferentialEquationOrder(sides[0]);
		setDifferentialEquationVariableAndForm(sides[0]);
		validateEquation(sides[1]);

		expression = sides[1];

		try {
			if (order == 1 && solver.equals(SolvMethod.EULER)) {
				result = Solvers.eulerMethod(this, y0, deltaT, n, t0);
			} else if (order == 2 && solver.equals(SolvMethod.EULER)) {
				result = Solvers.eulerMethodSecondOrderOde(this, y0, dy0,
						deltaT, n, t0);
			} else if (order == 1 && solver.equals(SolvMethod.MIDPOINT)) {
				result = Solvers.mipointMethod(this, y0, deltaT,
						n, t0);
			} else if (order == 2 && solver.equals(SolvMethod.MIDPOINT)) {
				result = Solvers.midpointMethodSecondOrderOde(
						this, y0, dy0, deltaT, n, t0);
			} else if (order == 1 && solver.equals(SolvMethod.RUNGEKUTTA4)) {
				result = Solvers.rungeKuttaFourthOrderMethod(this, y0, deltaT,
						n, t0);
			} else if (order == 2 && solver.equals(SolvMethod.RUNGEKUTTA4)) {
				result = Solvers.rungeKuttaFourthOrderMethodSecondOrderOde(
						this, y0, dy0, deltaT, n, t0);
			} else if (order == 1 && solver.equals(SolvMethod.RUNGEKUTTA3)) {
				result = Solvers.rungeKuttaThirdOrderMethod(this, y0, deltaT, n, t0);
			} else if (order == 2 && solver.equals(SolvMethod.RUNGEKUTTA3)) {
				result = Solvers.rungeKuttaThirdOrderMethodSecondOrderOde(this, y0, dy0,
						deltaT, n, t0);
			}
		} catch (Exception ex) {
			throw new DiffMathException("Error at evaluating the equation: "
					+ ex.getMessage());
		}

		return result;
	}

	// megoldó módszerek
	private static class Solvers {

		public static ResultStore eulerMethod(DifferentailEquation fn,
				double y0, double deltaT, int n, int t0) throws Exception {
			double temp = y0;
			double t;
			ResultStore resultStore = new ResultStore();
			
			t = t0;
			resultStore.addPoint(t, y0);
			t += deltaT;
			for (int i = 0; i < n; i += 1) {
				temp += deltaT * fn.getFunction(t, temp);

				if (temp > Double.MAX_VALUE)
					break;

				resultStore.addPoint(t, temp);
				t += deltaT;
			}
			return resultStore;
		}

		public static ResultStore eulerMethodSecondOrderOde(
				DifferentailEquation fn, double y0, double dy0, double deltaT, int n, int t0) throws Exception {
			double temp = y0;
			double dtemp = dy0;
			double nextTemp = 0;
			double nextDtemp = 0;
			double t;
			ResultStore resultStore = new ResultStore();

			t = t0;
			resultStore.addPoint(t, y0);
			t += deltaT;
			for (int i = 0; i < n; i += 1) {

				nextTemp = temp + deltaT * dtemp;
				nextDtemp = dtemp + deltaT * fn.getFunction(t, temp, dtemp);
				temp = nextTemp;
				dtemp = nextDtemp;

				if (temp > Double.MAX_VALUE)
					break;

				resultStore.addPoint(t, temp);
				t += deltaT;
			}
			return resultStore;
		}

		public static ResultStore rungeKuttaThirdOrderMethod(DifferentailEquation fn,
				double y0, double deltaT, int n, int t0) throws Exception {
			double temp = y0;
			double t, k1, k2, k3;
			ResultStore resultStore = new ResultStore();

			t = t0;
			resultStore.addPoint(t, y0);
			t += deltaT;
			for (int i = 0; i < n; i += 1) {
							
				k1 = deltaT * fn.getFunction(t, temp);
				k2 = deltaT* fn.getFunction(t + (deltaT/2), temp + (k1/2));
				k3 = deltaT* fn.getFunction(t + deltaT, temp + 2*k2 - k1);
				temp += k1/6 + 2*k2/3 + k3/6;

				if (temp > Double.MAX_VALUE)
					break;

				resultStore.addPoint(t, temp);
				t += deltaT;
			}
			return resultStore;
		}

		public static ResultStore rungeKuttaThirdOrderMethodSecondOrderOde(
				DifferentailEquation fn, double y0, double dy0, double deltaT,
				int n, int t0) throws Exception {
			double k1, k1d, k2, k2d, k3, k3d;
			double t;
			double temp = y0;
			double dtemp = dy0;
			ResultStore resultStore = new ResultStore();

			t = t0;
			resultStore.addPoint(t, y0);
			t += deltaT;
			for (int i = 0; i < n; i += 1) {
		
				k1 = deltaT * dtemp;
				k1d = deltaT * fn.getFunction(t, temp, dtemp);
						
				k2 = deltaT * (dtemp + (k1d/2));
				k2d = deltaT * fn.getFunction(t + (deltaT/2), temp + (k1/2), dtemp + (k1d/2));
				
				k3 = deltaT * (dtemp + (2*k2d - k1d));
				k3d = deltaT * fn.getFunction(t + deltaT, temp + 2*k2 - k1, dtemp + 2*k2d - k1d);
				
				temp += k1/6 + 2*k2/3 + k3/6;
				dtemp += k1d/6 + 2*k2d/3 + k3d/6;

				if (temp > Double.MAX_VALUE)
					break;

				resultStore.addPoint(t, temp);
				t += deltaT;
			}
			return resultStore;
		}

		public static ResultStore midpointMethodSecondOrderOde(
				DifferentailEquation fn, double y0, double dy0, double deltaT,
				int n, int t0) throws Exception {
			double k1, k1d;
			double t;
			double temp = y0;
			double dtemp = dy0;
			double nextTemp = 0;
			double nextDtemp = 0;
			ResultStore resultStore = new ResultStore();

			t = t0;
			resultStore.addPoint(t, y0);
			t += deltaT;
			for (int i = 0; i < n; i += 1) {

				k1 = deltaT * dtemp;
				k1d = deltaT * fn.getFunction(t, temp, dtemp);
				
				nextTemp = temp + deltaT * (dtemp + (k1d / 2));
				nextDtemp = dtemp
						+ deltaT
						* fn.getFunction(t + deltaT, temp + (k1 / 2), dtemp
								+ (k1d / 2));
				temp = nextTemp;
				dtemp = nextDtemp;

				if (temp > Double.MAX_VALUE)
					break;

				resultStore.addPoint(t, temp);
				t += deltaT;
			}
			return resultStore;
		}

		public static ResultStore mipointMethod(
				DifferentailEquation fn, double y0, double deltaT, int n, int t0)
				throws Exception {
			double k1, k2;
			double t;
			double temp = y0;
			ResultStore resultStore = new ResultStore();

			t = t0;
			resultStore.addPoint(t, y0);
			t += deltaT;
			for (int i = 0; i < n; i += 1) {
				k1 = deltaT * fn.getFunction(t, temp);
				k2 = deltaT * fn.getFunction(t + deltaT, temp + (k1 / 2));
				temp += k2;

				if (temp > Double.MAX_VALUE)
					break;

				resultStore.addPoint(t, temp);
				t += deltaT;
			}
			return resultStore;
		}

		public static ResultStore rungeKuttaFourthOrderMethodSecondOrderOde(
				DifferentailEquation fn, double y0, double dy0, double deltaT,
				int n, int t0) throws Exception {
			double k1, k2, k3, k4;
			double k1d, k2d, k3d, k4d;
			double temp = y0;
			double dtemp = dy0;
			double t;
			ResultStore resultStore = new ResultStore();

			t = t0;
			resultStore.addPoint(t, y0);
			t += deltaT;
			for (int i = 0; i < n; i += 1) {

				k1 = deltaT * dtemp;
				k1d = deltaT * fn.getFunction(t, temp, dtemp);
			
				k2 = deltaT * (dtemp + (k1d / 2));
				k2d = deltaT
						* fn.getFunction(t + deltaT / 2, temp + (k1 / 2), dtemp
								+ (k1d / 2));
			
				k3 = deltaT * (dtemp + (k2d / 2));
				k3d = deltaT
						* fn.getFunction(t + deltaT / 2, temp + (k2 / 2), dtemp
								+ (k2d / 2));
			
				k4 = deltaT * (dtemp + k3d);
				k4d = deltaT
						* fn.getFunction(t + deltaT, temp + k3, dtemp + k3d);
				
				temp += (1f / 6f) * (k1 + 2f * k2 + 2f * k3 + k4);
				dtemp += (1f / 6f) * (k1d + 2f * k2d + 2f * k3d + k4d);

				if (dtemp > Double.MAX_VALUE)
					break;

				resultStore.addPoint(t, temp);
				t += deltaT;
			}
			return resultStore;
		}

		public static ResultStore rungeKuttaFourthOrderMethod(
				DifferentailEquation fn, double y0, double deltaT, int n, int t0)
				throws Exception {
			double k1, k2, k3, k4;
			double temp = y0;
			double t;
			ResultStore resultStore = new ResultStore();

			t = t0;
			resultStore.addPoint(t, y0);
			t += deltaT;
			for (int i = 0; i < n; i += 1) {
				k1 = deltaT * fn.getFunction(t, temp);
				k2 = deltaT * fn.getFunction(t + deltaT / 2, temp + (k1 / 2));
				k3 = deltaT * fn.getFunction(t + deltaT / 2, temp + (k2 / 2));
				k4 = deltaT * fn.getFunction(t + deltaT, temp + k3);
				temp += (1f / 6f) * (k1 + 2f * k2 + 2f * k3 + k4);

				if (temp > Double.MAX_VALUE)
					break;

				resultStore.addPoint(t, temp);
				t += deltaT;
			}
			return resultStore;
		}
	}
}
