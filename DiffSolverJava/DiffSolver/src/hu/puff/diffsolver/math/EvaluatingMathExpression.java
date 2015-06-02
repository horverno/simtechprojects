package hu.puff.diffsolver.math;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EvaluatingMathExpression {

	public static double evaluating(String expression) throws Exception {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		expression = expression.replaceAll("&&&","Math.sin");
		expression = expression.replaceAll("@@@","Math.cos");
		expression = expression.replaceAll("###","Math.tan");
		
		Object result = engine.eval(expression);
		if(result.toString().equals("NaN"))
			throw new DiffMathException("Error at evaluating.");
		if (result.getClass().equals(Integer.class)) {
			int i = (int) result;
			return (double) i;
		} else if (result.getClass().equals(Double.class)) {
			return (double) result;
		}
		
		return 0f;
	}

}
