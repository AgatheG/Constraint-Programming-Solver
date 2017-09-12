package eval;

import java.util.ArrayList;

import search.GenerateAndTest;
import definition.Constraint;
import definition.Csp;
import definition.Variable;

// EXO I

public class CalculSolutions {
	
	public static void resolutionPb (int valMax) {
		ArrayList<Variable> vars = new ArrayList<Variable>();
		ArrayList<Constraint> contr = new ArrayList<Constraint>();
		
		// CREATION VARIABLES
		Variable x0 = new Variable("x0", 0, 1,valMax);
		Variable x1 = new Variable("x1", 1, 1,valMax);
		Variable x2 = new Variable("x2", 2, 1,valMax); 
		Variable x3 = new Variable("x3", 3, 1,valMax);
		Variable x4 = new Variable("x4", 4, 1,valMax);
		Variable x5 = new Variable("x5", 5, 1,valMax);
		Variable x6 = new Variable("x6", 6, 1,valMax);
		Variable x7 = new Variable("x7", 7, 1,valMax);
		Variable x8 = new Variable("x8", 8, 1,valMax);
		Variable x9 = new Variable("x9", 9, 1,valMax);
		
		vars.add(x0);
		vars.add(x1);
		vars.add(x2);
		vars.add(x3);
		vars.add(x4);
		vars.add(x5);
		vars.add(x6);
		vars.add(x7);
		vars.add(x8);
		vars.add(x9);
		
		// CREATION CONSTRAINTS
		Constraint c1 = new Constraint(x0,x1,"<");
		Constraint c2 = new Constraint(x0,x4,"<=");
		Constraint c3 = new Constraint(x1,x2,"!=");
		Constraint c4 = new Constraint(x1,x3,"<");
		Constraint c5 = new Constraint(x1,x7,"<");
		Constraint c6 = new Constraint(x2,x3,"<");
		Constraint c7 = new Constraint(x4,x1,"<");
		Constraint c8 = new Constraint(x4,x6,"<");
		Constraint c9 = new Constraint(x4,x7,"<");
		Constraint c10 = new Constraint(x5,x9,"<=");
		Constraint c11 = new Constraint(x6,x7,"<");
		Constraint c12 = new Constraint(x6,x8,"!=");
		Constraint c13 = new Constraint(x7,x8,"<=");
		Constraint c14 = new Constraint(x8,x3,"<");
		Constraint c15 = new Constraint(x9,x8,"<");
		
		contr.add(c1);
		contr.add(c2);
		contr.add(c3);
		contr.add(c4);
		contr.add(c5);
		contr.add(c6);
		contr.add(c7);
		contr.add(c8);
		contr.add(c9);
		contr.add(c10);
		contr.add(c11);
		contr.add(c12);
		contr.add(c13);
		contr.add(c14);
		contr.add(c15);
		
		// SOLVING
		Csp csp = new Csp(vars,contr,"pititRatio");
		GenerateAndTest gat = new GenerateAndTest();
		
		gat.solve("BT", csp, false);
	}
	
	public static void main(String[] args) {
		resolutionPb(3); // 0 solution
		
		resolutionPb(4); // 6 solutions
		
		resolutionPb(5); // 141 solutions
		
		resolutionPb(6); // 1401 solutions
		
		resolutionPb(7); // 8751 solutions
	}
}
