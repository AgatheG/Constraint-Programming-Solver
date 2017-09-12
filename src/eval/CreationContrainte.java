package eval;

import java.util.ArrayList;

import search.GenerateAndTest;
import definition.Constraint;
import definition.Csp;
import definition.Max;
import definition.Variable;

// EXO III
public class CreationContrainte {
	public static void main(String args[]) {
		ArrayList<Variable> vars = new ArrayList<Variable>();
		ArrayList<Constraint> contr = new ArrayList<Constraint>();
		
		vars.add(new Variable("x", 0, 6,10));
		vars.add(new Variable("y", 1, 1,5));
		vars.add(new Variable("m", 2, 4,7));
		
		contr.add(new Max(vars.get(0), vars.get(1), vars.get(2)));
		
		Csp csp = new Csp(vars,contr,"pititDom");
		GenerateAndTest gat = new GenerateAndTest();
		
		gat.solve("BFF", csp, true);
	}
}
