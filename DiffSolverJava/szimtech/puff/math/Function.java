package szimtech.puff.math;

public class Function {
	
	private String expression;
	
	public Function(String expression) {
		super();
		this.expression = expression;
	}

	public Function() {
		super();
	}
	
	private static String insertVariables(String expression, float y, float t){
		String result = expression.replaceAll("x", String.valueOf(y));
		result = result.replaceAll("y", String.valueOf(y));
		result = result.replaceAll("t", String.valueOf(t));
		return result;
	}
	
	public float getFunction(float t, float y) {
		String expression = insertVariables(this.expression, y, t);
		return EvaluatingMathExpression.evaluating(expression);
	}

}
