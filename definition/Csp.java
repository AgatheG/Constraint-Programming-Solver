package definition;

import java.util.ArrayList;
import java.util.TreeSet;

public class Csp {
	private TreeSet<Variable> vars; // set of variables
	private ArrayList<Constraint> constr; //set of constraints

	public Csp(ArrayList<Variable> vars, ArrayList<Constraint> constr, String choiceHeuristic) {
		this.vars = new TreeSet<Variable>();
		for (Variable v : vars) {
			for (Constraint c : constr) {
				if (c.getVars()[0].equals(v)) {
					v.setNrC(v.getNrC() + 1);
				}
				if (c.getVars()[1].equals(v)) {
					v.setNrC(v.getNrC() + 1);
				}
			}
			v.setHeuristic(choiceHeuristic);
			this.vars.add(v);
		}
		this.constr = constr;
	}

	public TreeSet<Variable> getVars() {
		return vars;
	}

	public ArrayList<Constraint> getConstraints() {
		return this.constr;
	}

	// retourne la premiere variable non instanciee du csp
	public Variable randomVar() {
		for (Variable v : this.vars) {
			if (!v.isInstantiated()) {
				return v;
			}
		}
		return null;
	}

	public boolean superFilter() {
		for (Constraint c : this.constr) {
			boolean possible = c.filtrer();
			if (!possible) {
				return false;
			}
		}
		return true;
	}

	// retourne vrai ssi toutes les variables sont instanciees
	public boolean allInstanciated() {
		for (Variable v : this.vars) {
			if (!v.isInstantiated()) {
				return false;
			}
		}
		return true;
	}

	// retourne vrai ssi l'ensemble des contraintes du CSP est verifie
	public boolean hasSolution() {
		for (int i = 0; i < this.getConstraints().size(); i++) {
			if (!this.getConstraints().get(i).isSatisfied()) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		String s = "";
		for (Variable v : this.vars) {
			s += v + "\n";
		}
		return s;
	}
}
