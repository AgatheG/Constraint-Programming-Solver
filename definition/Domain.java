package definition;

import java.util.Iterator;

public interface Domain extends Iterable<Integer> {
    public Domain clone();
    
    public int size();

    public boolean contains(int v);

    public int firstValue();

    public int lastValue();

    public void remove(int v);

    public void fix(int v);
    
    public Iterator<Integer> iterator();
}
