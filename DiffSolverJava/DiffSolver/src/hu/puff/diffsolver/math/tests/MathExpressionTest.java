package hu.puff.diffsolver.math.tests;

import hu.puff.diffsolver.math.EvaluatingMathExpression;

public class MathExpressionTest {
	
	public static float exprTest(String expr, Float excpeted){
		float result = EvaluatingMathExpression.evaluating(expr);
		return excpeted - result;
	}
	
	public static void main(String... args){
		System.out.println(exprTest("((10.2*2)+0.6)*2",42f));
	}
}
