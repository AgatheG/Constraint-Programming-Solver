package definition;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;

public class DomainBitSet implements Domain {
	private BitSet values; 

	/**
	 * Constructor
	 * @param min - Inferior bound (included)
	 * @param max - Sup bound (included)
	 */
	public DomainBitSet(int min, int max) {
		this.values = new BitSet();
		this.values.set(min,max+1);
	}

	/**
	 * Constructor by copy
	 * @param dom - Another DomainBitSet
	 */
	public DomainBitSet(DomainBitSet dom) {
		this.values = (BitSet)dom.values.clone();
	}

	/**
	 * Overrides the method copy
	 * Copies the DomainBitSet
	 * @return - A new instance of DOmainBitSet
	 */
	public DomainBitSet clone() {
		return new DomainBitSet(this);
	}

	/**
	 * @return the size of the domain
	 */
	public int size() {
		return this.values.cardinality();
	}

	/**
	 * @return the lowest value of the domain
	 */
	public int firstValue() {
		return this.values.nextSetBit(0);
	}

	/**
	 * @return The highest value of the domain
	 */
	public int lastValue() {
		return this.values.previousSetBit(this.values.length());
	}

	/**
	 * Overrides toString
	 */
	public String toString() {
		return this.values.toString();
	}

	// IMPLEMENTS AN ITERATOR
	
	public class Iter implements Iterator<Integer>  {
		private ArrayList<Integer> liste;
		private int index;

		/**
		 * Constructor
		 * @param liste - The iterations
		 */
		public Iter(ArrayList<Integer> liste) {
			this.liste = liste;
			this.index = 0;
		}

		/**
		 * @return True if there is still a value in the domain
		 */
		public boolean hasNext() {
			return this.index < this.liste.size();
		}

		/**
		 * @return The next value
		 */
		public Integer next() {
			if (this.hasNext()) {
				this.index ++;
				return this.liste.get(this.index - 1);
			}
			return null;
		}

	}

	public Iterator<Integer> iterator() {
		ArrayList<Integer> liste = new ArrayList<Integer>();
		if (this.firstValue() > -1) {
			for (int i = this.firstValue(); i <= this.lastValue(); i ++) {
				if (this.contains(i)) {
					liste.add(i);
				}
			}
		}
		return new Iter(liste);
	}

	/**
	 * @return True if the domain contains v
	 */
	public boolean contains(int v) {
		return this.values.get(v);
	}

	/**
	 * Removes the value v from the domain
	 * @param v - The integer value to be removed
	 */
	public void remove(int v) {
		this.values.clear(v);
	}

	/**
	 * Sets the domain to a unique value
	 */
	public void fix(int v) {
		this.values.clear();
		this.values.set(v);
	}
}
