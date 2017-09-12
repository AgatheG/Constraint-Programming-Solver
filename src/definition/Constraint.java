package definition;

public class Constraint {
	private Variable var1;
	private Variable var2;
	private String operator;

	/**
	 * Constructor
	 * @param var1 - The first variable
	 * @param var2 - The secnd variable
	 * @param op - The operator
	 */
	public Constraint(Variable var1, Variable var2, String op) {
		this.var1 = var1;
		this.var2 = var2;
		this.operator = op;
	}


	/**
	 * @return - The variables of the constraint
	 */
	public Variable[] getVars() {
		return new Variable[]{var1,var2};
	}

	/**
	 * @return - The operator of the constraint
	 */
	public String getOp() {
		return this.operator;
	}

	/**
	 * @param o - An object
	 * @return - true o is an instance of Constraint with the same variables and operator, otherwise false
	 */
	public boolean equals(Object o) {
		return (o instanceof Constraint) && (((this.var1.equals(((Constraint)o).getVars()[0])) && (this.var2.equals(((Constraint)o).getVars()[1]))) || ((this.var1.equals(((Constraint)o).getVars()[1])) && (this.var2.equals(((Constraint)o).getVars()[0])))) && (this.operator.equals(((Constraint)o).getOp()));
	}

	/**
	 * 
	 * @return True if both variables are instantiated and the constraint is satisfied
	 */
	public boolean isSatisfied() {	
		if ((this.getVars()[0].isInstantiated()) && (this.getVars()[1].isInstantiated())) {
			if (this.operator.equals("=")) {
				return this.getVars()[0].getValue() == this.getVars()[1].getValue();
			}
			if (this.operator.equals("!=")) {
				return this.getVars()[0].getValue() != this.getVars()[1].getValue();
			}
			if (this.operator.equals("<")) {
				return this.getVars()[0].getValue() < this.getVars()[1].getValue();
			}
			if (this.operator.equals(">")) {
				return this.getVars()[0].getValue() > this.getVars()[1].getValue();
			}
			if (this.operator.equals("<=")) {
				return this.getVars()[0].getValue() <= this.getVars()[1].getValue();
			}
			if (this.operator.equals(">=")) {
				return this.getVars()[0].getValue() >= this.getVars()[1].getValue();
			}
		}
		return false;
	}

	/**
	 * 
	 * @return True if there still exists at least a tuple of values from each of the domain of the variables that satisfy this constraint
	 */
	public boolean isNecessary() {
		if (this.operator.equals("=")) {
			for (int valeur : this.getVars()[0].getDomain()) {
				if (this.getVars()[1].canBeInstantiatedTo(valeur)) {
					return true;
				}
			}
		}
		if (this.operator.equals("!=")) {
			return this.getVars()[0].getDomainSize()>1||this.getVars()[1].getDomainSize()>1||this.getVars()[0].getValue()!=this.getVars()[1].getValue();

		}
		if (this.operator.equals("<")) {
			return this.getVars()[0].getInf() < this.getVars()[1].getSup();
		}
		if (this.operator.equals(">")) {
			return this.getVars()[0].getSup() > this.getVars()[1].getInf();
		}		
		if (this.operator.equals("<=")) {
			return this.getVars()[0].getInf() <= this.getVars()[1].getSup();
		}
		if (this.operator.equals(">=")) {
			return this.getVars()[0].getSup() >= this.getVars()[1].getInf();
		}
		return false;
	}

	/**
	 * 
	 * @return True if both variables are instantiated ie. they have taken one peculiar value amongst the values in their domian
	 */
	public boolean isInstantiated() {
		return this.var1.isInstantiated() && this.var2.isInstantiated();
	} 

	/**
	 * Filter the values of the domain from each variable that cannot be part of the solution
	 * @return  true if each of the variables as still a domain of a size greater than one after the filter (ie can be instantiated), false otherwise
	 */
	public boolean filtrer() {

		//EGAL
		if (this.operator.equals("=")) {
			for (int valeur : this.getVars()[0].getDomain()) {
				if (!this.getVars()[1].canBeInstantiatedTo(valeur)) {
					//this.getVars()[0].setInterdite(valeur);
					this.getVars()[0].remValue(valeur);
				}
			}
			for (int valeur : this.getVars()[1].getDomain()) {
				if (!this.getVars()[0].canBeInstantiatedTo(valeur)) {
					//this.getVars()[1].setInterdite(valeur);
					this.getVars()[1].remValue(valeur);
				}
			}
		}

		//DIFFERENT
		if (this.operator.equals("!=")) {
			if ((this.getVars()[0].getDomainSize() == 1) && (this.getVars()[1].canBeInstantiatedTo(this.getVars()[0].getValue())) ) {
				this.getVars()[1].remValue(this.getVars()[0].getValue());
			}
			if ((this.getVars()[1].getDomainSize() == 1) && (this.getVars()[0].canBeInstantiatedTo(this.getVars()[1].getValue())) ) {
				this.getVars()[0].remValue(this.getVars()[1].getValue());
			}
		}

		//INF STRICT
		if (this.operator.equals("<")) {
			while ((this.getVars()[1].getInf() <= this.getVars()[0].getInf()) && (this.getVars()[1].getInf() != -1)) {
				this.getVars()[1].remValue(this.getVars()[1].getInf());
			}
			while ( (this.getVars()[0].getSup() >= this.getVars()[1].getSup()) && (this.getVars()[0].getSup() != -1) ) {
				this.getVars()[0].remValue(this.getVars()[0].getSup());
			}
		}

		//SUP STRICT
		if (this.operator.equals(">")) {
			while ((this.getVars()[0].getInf() <= this.getVars()[1].getInf()) && (this.getVars()[0].getInf() != -1) ) {
				this.getVars()[0].remValue(this.getVars()[0].getInf());
			}
			while ((this.getVars()[1].getSup() >= this.getVars()[0].getSup()) && (this.getVars()[1].getSup() !=-1)) {
				this.getVars()[1].remValue(this.getVars()[1].getSup());
			}
		}		

		//INF 
		if (this.operator.equals("<=")) {
			while ((this.getVars()[1].getInf() < this.getVars()[0].getInf()) && (this.getVars()[1].getInf() != -1)) {
				this.getVars()[1].remValue(this.getVars()[1].getInf());
			}
			while ((this.getVars()[0].getSup()) > this.getVars()[1].getSup() && (this.getVars()[0].getSup()!= -1)) {
				this.getVars()[0].remValue(this.getVars()[0].getSup());
			}
		}

		//SUP
		if (this.operator.equals(">=")) {
			while ((this.getVars()[0].getInf() < this.getVars()[1].getInf()) && (this.getVars()[0].getInf() != -1)) {
				this.getVars()[0].remValue(this.getVars()[0].getInf());
			}
			while ((this.getVars()[1].getSup() > this.getVars()[0].getSup()) && (this.getVars()[1].getSup() != -1)) {
				this.getVars()[1].remValue(this.getVars()[1].getSup());
			}		
		}
		return this.getVars()[0].getDomainSize()!=0 && this.getVars()[1].getDomainSize()!=0;
	}

	/**
	 * Overrides the toString() method
	 */
	public String toString() {
		return this.getVars()[0].getId() + " " + this.getVars()[1].getId() + " " + this.getOp();
	}

}
