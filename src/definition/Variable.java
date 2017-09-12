package definition;

public class Variable implements java.lang.Comparable<Variable> {
	private String name; // name of var
	private int idx; // id
	private Domain dom; // associated domain
	private double criteriaComp; // a criteria of comparison to sort the variables according to it
	private int nrConstr; //	nr of constraints using this var 
	private int[] boundaries; // boundaries of the domain

	/**
	 * Constructor
	 * @param name - Name of var
	 * @param idx - Id
	 * @param min - Inferior bound (included)
	 * @param max - Superior bound (included)
	 */
	public Variable(String name, int idx, int min, int max) {
		this.name = name;
		this.idx = idx;
		this.dom = new DomainBitSet(min,max);
		this.boundaries = new int[]{min,max};
		this.criteriaComp = idx;
		this.nrConstr = 0;
	}

	/**
	 * Constructor by copy
	 * @param v - Another variable
	 */
	public Variable(Variable v) {
		this.name = v.name;
		this.idx = v.idx;
		this.dom = v.getDomain().clone();
		this.boundaries = v.boundaries;
		this.criteriaComp = v.criteriaComp;
	}

	/**
	 * Set the heuristic used to sort the variables
	 * @param choice - A string to define what heuristic is to be applied
	 */
	public void setHeuristic(String choice) {
		if (choice.equals("smallerDomain")) {
			this.criteriaComp = this.dom.size();
		}
		if (choice.equals("smallerRatio")) { // the ratio being the ratio between the domain size and the nr of constraints
			this.criteriaComp = this.dom.size()/(double)this.nrConstr;
		}
		else {
			this.criteriaComp = this.idx;
		} 
	}

	/**
	 * 
	 * @return - The criteria of comparison
	 */
	public double getCritereComp() {
		return criteriaComp;
	}

	/**
	 * 
	 * @return The nr of constraints
	 */
	public int getNrC() {
		return this.nrConstr;
	}

	/**
	 * Sets the nr of constraints
	 * @param i - The nr of constraints
	 */
	public void setNrC(int i) {
		this.nrConstr = i;
	}

	/**
	 * 
	 * @return - The domain
	 */
	public Domain getDomain() {
		return this.dom;
	}

	/**
	 * Sets the domain
	 * @param d - The new domain
	 */
	public void setDomain(Domain d) {
		this.dom = d;
	}

	/**
	 * 
	 * @return - The smaller value in the domain
	 */
	public int getInf() {
		return this.getDomain().firstValue();
	}

	/**
	 * @return - The largest value in the domain
	 */
	public int getSup() {
		return this.getDomain().lastValue();
	}
	
	/**
	 * 
	 * @return - The value given to this variable if it is instantiated, else returns -1
	 */
	public int getValue() {
		if (this.isInstantiated()) {
			return this.getDomain().firstValue();
		}
		return -1;
	}

	/**
	 * 
	 * @return The id of the variable
	 */
	public int getId() {
		return this.idx;
	}
	
	/**
	 * 
	 * @return - true is the domain size == 1, ie. the variable has only one possible value
	 */
	public boolean isInstantiated() {
		return this.dom.size() == 1;
	}

	/**
	 * 
	 * @param v - An integer value
	 * @return - True if the value v is within the domain of this variable
	 */
	public boolean canBeInstantiatedTo(int v) {
		return this.dom.contains(v);
	}

	/**
	 * 
	 * @return - The nr of values within the domain of the variable
	 */
	public int getDomainSize() {
		return this.dom.size();
	}

	/**
	 * Reset the domainback to its original value
	 */
	public void raz() {
		this.dom = new DomainBitSet(boundaries[0],boundaries[1]);
	}

	/**
	 * Remove the value v from the domain of this variable
	 * @param v - the integer value to be removed
	 */
	public void remValue(int v) {
		this.dom.remove(v);
	}

	/**
	 * Instantiate the variable to the value v
	 * @param v - The integer value
	 */
	public void instantiate(int v) {
		this.dom.fix(v);
	}

	/**
	 * @param o - An Object
	 * @return - True is o is a Variable with the same id
	 */
	public boolean equals(Object o) {
		return (o instanceof Variable) && (((Variable)o).getId() == this.getId());
	}

	/**
	 * Overrides the toString method
	 */
	public String toString() {
		return this.name + " := " + this.dom;
	}

	/**
	 * @param v - The variable to be compared to this variable, based on the criteria of comparison set
	 * @return - 1 if this variable is greater than v in the sense of the comparison criteria, 0 if it is equal to it, else -1
	 */
	public int compareTo(Variable v) {
		double c1 = this.criteriaComp;
		double c2 =  v.criteriaComp;

		if (c1 > c2) {
			return 1;
		}
		else if (c1 == c2) {
			if (this.getId() < v.getId()) {
				return -1;
			}
			if (this.getId() > v.getId()) {
				return 1;
			}
			else {
				return 0;
			}
		}
		else  {
			return -1;
		}
	}
}