package definition;


// extends Constraint to offer a new type of constraint where var3 = max(vr1, var2)

public class Max extends Constraint {
	private Variable var3; 

	public Max(Variable var1, Variable var2, Variable var3) {
		super(var1, var2, "");
		this.var3 = var3;
	}

	public Variable[] getVars() {
		return new Variable[]{super.getVars()[0], super.getVars()[1],var3};
	}

	public boolean isSatisfied() {	
		if ((this.getVars()[0].isInstantiated()) && (this.getVars()[1].isInstantiated()) && (this.getVars()[2].isInstantiated())) {
			return Math.max(this.getVars()[0].getValue(), this.getVars()[1].getValue()) == this.getVars()[2].getValue();
		}
		return false;
	}

	public boolean isNecessary() {
		for (int valeur : this.getVars()[0].getDomain()) {
			for (int valeur2 : this.getVars()[1].getDomain()) {
				for (int valeur3 : this.getVars()[0].getDomain()) {
					if (Math.max(valeur, valeur2) == valeur3) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isInstantiated() {
		return super.isInstantiated() && this.var3.isInstantiated();
	} 

	public boolean filter() {
		while (this.getVars()[2].getInf() < this.getVars()[0].getInf()) {
			this.getVars()[2].remValue(this.getVars()[2].getInf());
		}

		while (this.getVars()[2].getInf() < this.getVars()[1].getInf()) {
			this.getVars()[2].remValue(this.getVars()[2].getInf());
		}

		while (this.getVars()[2].getSup() < this.getVars()[0].getSup()) {
			this.getVars()[0].remValue(this.getVars()[0].getSup());
		}

		while (this.getVars()[2].getSup() < this.getVars()[1].getSup()) {
			this.getVars()[1].remValue(this.getVars()[1].getSup());
		}

		return this.getVars()[0].getDomainSize()!=0 && this.getVars()[1].getDomainSize()!=0 && this.getVars()[2].getDomainSize()!=0;
	}

	public String toString() {
		return super.toString() + this.getVars()[2].getId();
	}
}
