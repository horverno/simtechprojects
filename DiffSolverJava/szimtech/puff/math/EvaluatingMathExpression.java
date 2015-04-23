package szimtech.puff.math;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EvaluatingMathExpression {
	
	public static float evaluating(String expression){
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");	 
	    try {
	    	Object result = engine.eval(expression);
	    	if(result.getClass().equals(Integer.class)){
	    		int i = (int)result;
	    		return (float)i;
	    	}
	    	else if(result.getClass().equals(Double.class)){
				double d = (double)result;
				return (float)d;
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}	
	    return 0f;
	}
	
}
