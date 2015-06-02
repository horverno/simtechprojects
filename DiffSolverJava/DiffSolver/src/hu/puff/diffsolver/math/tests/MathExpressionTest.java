package hu.puff.diffsolver.math.tests;

import hu.puff.diffsolver.math.EvaluatingMathExpression;

public class MathExpressionTest {
	
	public static double exprTest(String expr, Float excpeted) throws Exception{
		double result = EvaluatingMathExpression.evaluating(expr);
		return excpeted - result;
	}
	
	public static void main(String... args) throws Exception{
		System.out.println(exprTest("Math.tan(0)",0f));
	}
}
