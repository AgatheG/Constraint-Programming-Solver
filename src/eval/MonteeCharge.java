package eval;

import java.util.ArrayList;

import search.GenerateAndTest;
import definition.Constraint;
import definition.Csp;
import definition.Variable;

// EXO II

public class MonteeCharge {

	public static void circuitInferieurs (int nb, boolean secondPart) {
		ArrayList<Variable> vars = new ArrayList<Variable>();
		ArrayList<Constraint> contr = new ArrayList<Constraint>();

		// CREATION VARIABLES
		for (int i = 0; i < nb; i++) {
			vars.add(new Variable("t"+i, i, 1, 10));
		}

		// CREATION CONSTRAINTS
		for (int i = 0; i < nb/2 - 1; i++) {
			contr.add(new Constraint(vars.get(i), vars.get(i+1), "<="));
		}
		contr.add(new Constraint(vars.get((int)nb/2-1), vars.get(0), "<="));

		for (int i = nb/2; i < nb - 1; i++) {
			contr.add(new Constraint(vars.get(i), vars.get(i+1), "<="));
		}
		contr.add(new Constraint(vars.get((int)nb-1), vars.get((int)nb/2), "<="));

		contr.add(new Constraint(vars.get(0), vars.get((int)nb/2), "<"));
		
		GenerateAndTest gat = new GenerateAndTest();
		Csp csp;
		
		if (!secondPart) {
			csp = new Csp(vars,contr,"");
			gat.solve("BFF", csp, false);
		}

		else {
			contr.add(new Constraint(vars.get((int)nb - 1), vars.get(0), "<"));
			csp = new Csp(vars,contr,"pititRatio");
		}

		// RESOLUTION
		gat.solve("BFF", csp, false);
	}

	public static void main(String args[]) {
		// FIRST PART
		circuitInferieurs(10, false); // 0.074 s

		circuitInferieurs(100, false); // 1.162 s

		circuitInferieurs(1000, false); // 21.377 s
		
		// max val : n = 1000
		// greater values --> pb with memory

		// SECOND PART
		circuitInferieurs(10, true); // 0.012 s

		circuitInferieurs(100, true); // 0.019 s

		circuitInferieurs(1000, true); // 0.123 s
		
		circuitInferieurs(10000, true); // 0.32 s

		circuitInferieurs(100000, true); // 2.022 s

		// max val : n = 100000
		// circuitInferieurs(1000000, true); // 2min approx.
	}
}
