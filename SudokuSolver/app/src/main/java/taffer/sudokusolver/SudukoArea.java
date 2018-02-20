package taffer.sudokusolver;

/**
 * Created by Simon on 2017-11-21.
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SudukoArea {
    private Set<Integer> numbers = new HashSet();
    private int areaNumber;

    /**
     * Creates a new SudukoArea, gives it a number.
     *
     * @param areaNumber
     */
    public SudukoArea(int areaNumber) {
        this.areaNumber = areaNumber;
    }

    /**
     * Returns the areanumber of the Area.
     *
     * @return the areanumber.
     */
    public int getAreaNumber() {
        return this.areaNumber;
    }

    /**
     * Checks if the area contains a number.
     *
     * @param n,
     *            number that is to checked.
     * @return true, if the area contains the number, false if it doesn't.
     */
    public boolean contains(int n) {
        return this.numbers.contains((n));
    }

    /**
     * Returns a String representation of the Area.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator var3 = this.numbers.iterator();

        while(var3.hasNext()) {
            Integer i = (Integer)var3.next();
            sb.append(i + " ");
        }

        return sb.toString();
    }
    /**
     * Removes the number from the area.
     *
     * @param n
     * @return
     */
    public boolean remove(int n) {
        if(this.numbers.contains((n))) {
            this.numbers.remove((n));
            return true;
        } else {
            return false;
        }
    }
    /**
     * Adds the number to the area.
     * @param n
     * @return true if the number could be added, false if it couldn't.
     */
    public boolean add(int n) {
        if(this.numbers.contains((n))) {
            return false;
        } else {
            this.numbers.add((n));
            return true;
        }
    }
}