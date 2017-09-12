package search;

import java.util.ArrayList;
import java.util.HashMap;

import definition.*;

/**
 * Created with Eclipse
 */

public class GenerateAndTest {
	private ArrayList<ArrayList<Variable>> solutions;
	private long time;
	private int nodes;
	private int nbBacktracks;
	private int fails;
	
	/**
	 * constructor
	 */
	public GenerateAndTest() {
		this.solutions = new ArrayList<ArrayList<Variable>>();
		this.time = System.currentTimeMillis();
		this.nodes = 0;
		this.nbBacktracks = 0;
		this.fails = 0;
	}

	/**
	 * Réinitialize the CSP instance
	 * @param csp - The CSP to be reinitialized
	 */
	public void reinit(Csp csp) {
		this.solutions.clear();
		this.nodes = 0;
		this.nbBacktracks= 0;
		this.fails = 0;
		this.time = System.currentTimeMillis();
		for (Variable v : csp.getVars()) {
			v.raz();
		}
		csp.superFilter();
	}

	/**
	 * Add the solution to the set of solutions for the give CSP
	 * @param vars - A list of variables that solve the CSP
	 */
	public void add(ArrayList<Variable> vars) {
		this.solutions.add(vars);
	}

	/**
	 *  Lunches the solver for the given CSP, with the method specified as a String
	 * @param s - A string to specify what method to use (BF = BRUTE FORCE | BFF = BRUTE FORCE WITH FILTER | BT = BACKTRACKING)
	 * @param csp - The CSP instance
	 * @param - If set to true, displays each solution (= a list of variables with their value)
	 */
	public void solve(String s, Csp csp, boolean display) {
		this.reinit(csp);
		if (s.equals("BF")) {
			this.bruteForceSearchNoFilter(csp);
		}

		if (s.equals("BFF")) {
			this.bruteForceSearchFilter(csp);
		}

		if (s.equals("BT")) {
			this.backtrackingBis(csp);
		}
		this.time = System.currentTimeMillis() - time;
		this.print(display);
	}

	/**
	 * Logs the nr of solutions, nodes visited, number of backtracks
	 * @param - If set to true, displays each solution (= a list of variables with their value)
	 */
	public void print(boolean display) {
		System.out.println("Nombre de solutions : " + this.solutions.size());
		System.out.println("Nombre de noeuds : " + this.nodes);
		System.out.println("Nombre de backtracks : " + this.nbBacktracks);
		System.out.println("Nombre d'erreurs : " + this.fails);
		if (display) {
			for (int i = 0; i < this.solutions.size(); i ++) {
				for (int j = 0; j < this.solutions.get(i).size(); j++) {
					System.out.print(this.solutions.get(i).get(j).toString() + " ");
				}
				System.out.print("\n");
			}
		}
		System.out.println("Temps écoulé : " + this.time*0.001 + "s");

	}
	
	/**
	 * Brute force search
	 * @param csp - The given CSP
	 */
	public void bruteForceSearchNoFilter(Csp csp) {
		if (csp.allInstanciated()) {
			if (csp.hasSolution()) {
				ArrayList<Variable> list = new ArrayList<Variable>();
				for (Variable v : csp.getVars()) {
					list.add(new Variable(v));
				}
				this.add(list);
			}
			else {
				this.fails ++;
			}
		}
		else {
			Variable xCourante = csp.randomVar();
			Domain xDomdom = xCourante.getDomain().clone();

			for (int valeur : xDomdom) {
				xCourante.instantiate(valeur);
				this.nodes ++;
				bruteForceSearchNoFilter(csp);
			}
			xCourante.raz();
		}
	}

	/**
	 * Brute force with a filter to fasten the search/prune wrong paths sooner (instead of reaching the max depths for every branch)
	 * @param csp - The given CSP
	 */
	public void bruteForceSearchFilter(Csp csp) {
		if (csp.allInstanciated()) {
			if (csp.hasSolution()) {
				ArrayList<Variable> list = new ArrayList<Variable>();
				for (Variable v : csp.getVars()) {
					list.add(new Variable(v));
				}
				this.add(list);
			}
			else {
				this.fails ++;
			}
		}
		else {
			Variable xCurrent = csp.randomVar();
			Domain xDom = xCurrent.getDomain().clone();

			HashMap<Variable, Integer> temp = new HashMap<Variable, Integer>();
			for (Variable v :csp.getVars()) {
				if ((xCurrent.compareTo(v) == 1)){
					temp.put(v, v.getValue());
				}
			}

			for (int valeur : xDom) {
				xCurrent.instantiate(valeur);
				for (Variable v : csp.getVars()) {
					if (xCurrent.compareTo(v) == -1) {
						v.raz();
					}
					else {
						if (v.getDomainSize() == 0) {
							v.instantiate(temp.get(v));
						}
					}
				}
				this.nodes ++;
				boolean supFil = csp.superFilter();

				if (supFil) {
					bruteForceSearchFilter(csp);
				}
				else {
					this.nbBacktracks ++;
				}
			}
			xCurrent.setDomain(xDom);
		}
	}

	/**
	 * Backtracking
	 * @param csp - The given CSP
	 */
	public void backtrackingBis(Csp csp) {
		if (csp.allInstanciated()) {
			if (csp.hasSolution()) {
				ArrayList<Variable> list = new ArrayList<Variable>();
				for (Variable v : csp.getVars()) {
					list.add(new Variable(v));
				}
				this.add(list);
			}
			else {
				this.fails ++;
			}
		}

		else {
			Variable xCurrent = csp.randomVar();
			Domain xDom = xCurrent.getDomain().clone();

			for (int valeur : xDom) {
				xCurrent.instantiate(valeur);
				this.nodes ++;

				int i = 0;
				boolean doNotPrune = true;


				while ((i < csp.getConstraints().size()) && (doNotPrune)) {
					if (!csp.getConstraints().get(i).isNecessary()) {
						doNotPrune = false;
					}
					i++;
				}

				if (doNotPrune) {
					backtrackingBis(csp);
				}
				else {
					this.nbBacktracks ++;
				}
			}
			xCurrent.raz();
		}
	}

	/**
	 * A random generator of CSP instances
	 * @param - nrVars : number of variables
	 * @param - domaineSize : the size of the domain for the variables
	 * @param - nrConstr : the number of constraints
	 */
	final static private String[] OPERATORS = new String[]{"==","!=","<",">","<=",">="};

	public static Csp GeneratorCSP(int nrVars, int[] domainSize, int nrConstr) {
		ArrayList<Variable> vars = new ArrayList<Variable>();
		ArrayList<Constraint> contr = new ArrayList<Constraint>();

		for (int i = 0; i <= nrVars; i++) {
			int borneInf = (int)(1000*Math.random());
			vars.add(new Variable("x" + i+1, i, borneInf, borneInf + domainSize[i] - 1));
		}

		for (int i = 0; i <= nrConstr; i++) {
			contr.add(new Constraint(vars.get((int)(nrVars*Math.random())), vars.get((int)(nrVars*Math.random())), OPERATORS[(int)(6*Math.random())]));
		}

		return new Csp(vars,contr,"");
	}

	//TEST 
	public static void main(String[] args) {
		Variable x1 = new Variable("x1",1,0,2);
		Variable x2 = new Variable("x2",2,0,2);
		Variable x3 = new Variable("x3",3,0,2);

		Constraint c1 = new Constraint(x1,x2,"<");
		Constraint c2 = new Constraint(x1,x3,"!=");

		ArrayList<Variable> vars = new ArrayList<Variable>();
		vars.add(x1); vars.add(x2); vars.add(x3);

		ArrayList<Constraint> contraintes = new ArrayList<Constraint>();
		contraintes.add(c1); contraintes.add(c2);

		Csp csp = new Csp(vars, contraintes, "");
		GenerateAndTest gat = new GenerateAndTest();

		gat.bruteForceSearchNoFilter(csp);
		gat.print(false);
	}

}
