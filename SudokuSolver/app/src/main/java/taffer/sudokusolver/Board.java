package taffer.sudokusolver;

/**
 * Created by Simon on 2017-11-21.
 */
import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] placements = new int[9][9];
    private boolean[][] startingPlacements = new boolean[9][9];
    private List<SudukoArea> areas = new ArrayList();

    /**
     * Creates a new Board from the startingPlacements. Saves where the starting
     * placements where set. Also saves the placements to add to the board, also
     * adds the number to the area it belongs. Also creates 9 new SudukoAreas.
     *
     * @param startingPlacements
     */
    public Board(int[][] startingPlacements) {
        int r;
        for(r = 0; r < 9; r++) {
            this.areas.add(new SudukoArea(r));
        }

        for(r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                if(startingPlacements[r][c] == 0) {
                    this.startingPlacements[r][c] = false;
                    this.placements[r][c] = startingPlacements[r][c];
                } else {
                    this.startingPlacements[r][c] = true;
                    this.placements[r][c] = startingPlacements[r][c];
                    this.addToArea(placements[r][c],findAreaNumber(r,c));
                }
            }
        }

    }
    private void unPlaceBoard(){

        for(int r = 0; r < 9; r++){
            for(int c = 0 ; c < 9; c++){
                unplace(r,c);
            }
        }
    }
    /**
     * Tries to catch an Invalid board
     * @param startingPlacements
     * @return the coordinates where the error occured, null if there is no error.
     */
    public int[] tryToCatchInvalidBoard(int[][] startingPlacements){
        List<SudukoArea> tempAreas = new ArrayList();
        int[]toReturn = new int[2];
        int r;
        for(r = 0; r < 9; r++) {
            tempAreas.add(new SudukoArea(r));
        }

        for(r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if(startingPlacements[r][c] != 0) {
                    if (!tempAreas.get(findAreaNumber(r, c)).add(startingPlacements[r][c])) {
                        toReturn[0] = r + 1;
                        toReturn[1] = c + 1;
                        unPlaceBoard();
                        return toReturn;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks the rules for a given board, if there are placements on the board
     * that violates the rules of Suduko, the method returns false.
     *
     * @return boolean true, if all rules are ok, false if not.
     */
    public boolean checkRules(int[][] placements){


        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                int temp = placements[r][c];
                unplace(r,c);
                if(!canPlace(temp,r,c) && placements[r][c] != 0){
                    place(temp,r,c);
                    return false;
                }
                place(temp,r,c);
            }
        }
        return true;
    }
    /**
     * Returns where there is an error on the board, in a vector [row,col],
     * representing where the first error was found.
     *
     * @param placements,
     *            the placements of the board.
     * @return int[] [row,col], representing where the first error was found, if
     *         no error was found then returns null.
     */

    public int[] returnError (int[][] placements){
        int[] toReturn = new int[2];
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                int temp = placements[r][c];
                unplace(r,c);
                if(!canPlace(placements[r][c],r,c) && placements[r][c] != 0){
                    place(temp,r,c);
                    toReturn[0] = r+1;
                    toReturn[1] = c+1;
                    return toReturn;
                }
            }
        }

        return null;
    }

    /**
     * Returns a matrix consisting of the placements currently on the board.
     *
     * @return int[][] with the placements currently on the board.
     */

    public int[][] getPlacements() {
        return this.placements;
    }


    /**
     * Prints what the SudukoAreas currently contain.
     */

    public void printAreasAndWhatTheyContain() {
        for(int i = 1; i <= 9; ++i) {
            System.out.println(i + "[" + ((SudukoArea)this.areas.get(i - 1)).toString() + "]");
        }

    }

    /**
     * Returns a string representation of the Board.
     *
     * @return a string representation of the board.
     */

    public String toString() {
        String sb = " ";

        for(int r = 0; r < 9; ++r) {
            sb = sb + "\n";

            for(int c = 0; c < 9; ++c) {
                sb = sb + this.placements[r][c] + " ";
            }
        }

        return sb.trim();
    }

    /**
     * Returns the area which the coordinates r,c belong to.
     *
     * @param r,
     *            row
     * @param c,
     *            col
     * @return SudukoArea which the coordinates r,c belong to.
     */

    public SudukoArea getArea(int r, int c) {
        return r < 9 && c < 9?(SudukoArea)this.areas.get(this.findAreaNumber(r, c)):null;
    }


    /**
     * Returns the area which areaNumber belongs to.
     *
     * @param int
     *            areaNumber
     * @return SudukoArea which areaNumber belongs to.
     */

    public SudukoArea getArea(int areaNumber) {
        return (SudukoArea)this.areas.get(areaNumber);
    }

    /**
     * Adds the number to the area.
     *
     * @param number,
     *            which is to be added.
     * @param areaNumber
     * @return true, if the number was added, false if it was not.
     */

    public boolean addToArea(int number, int areaNumber) {
        return ((SudukoArea)this.areas.get(areaNumber)).add(number);
    }

    /**
     * Removes the number from the area with areaNumber areaNumber.
     *
     * @param number,
     *            which is to be added.
     * @param areaNumber
     * @return true, if the number was added, false if it was not.
     */
    public boolean removeFromArea(int number, int areaNumber) {
        return ((SudukoArea)this.areas.get(areaNumber)).remove(number);
    }
    /**
     * Solves the board.
     *
     * @return true, if the board could be solved, false if it couldn't.
     */
    public boolean solve() {
        return this.nextPlace(0, 0);
    }

    private boolean nextPlace(int r, int c) {
        if(c == 9) {
            c = 0;
            ++r;
        }

        if(r == 9) {
            return true;
        } else {
            if(this.startingPlacements[r][c]) {
                if(this.nextPlace(r, c + 1)) {
                    return true;
                }
            } else {
                for(int num = 1; num <= 9; ++num) {
                    if(this.canPlace(num, r, c)) {
                        this.place(num, r, c);
                        if(this.nextPlace(r, c + 1)) {
                            return true;
                        }

                        this.unplace(r, c);
                    }
                }
            }

            if(!this.startingPlacements[r][c]) {
                this.unplace(r, c);
            }

            return false;
        }
    }

    private void unplace(int r, int c) {
        ((SudukoArea)this.areas.get(this.findAreaNumber(r, c))).remove(this.placements[r][c]);
        this.placements[r][c] = 0;
    }

    private void place(int num, int r, int c) {
        ((SudukoArea)this.areas.get(this.findAreaNumber(r, c))).add(num);
        this.placements[r][c] = num;
    }
    /**
     * Checks if the number can be added according to the rules of Suduko.
     *
     * @param number,
     *            which is to be added.
     * @param row,
     *            the row where the number is to be added.
     * @param col.
     *            the coloumn where the number is to be added.
     * @return true, if the number can be added, false if it can't be added.
     */
    public boolean canPlace(int number, int row, int col) {
        if(this.getArea(row,col).contains(number)) {

            return false;
        } else {
            for(int i = 0; i < 9; ++i) {
                if(this.placements[row][i] == number || this.placements[i][col] == number) {
                    return false;
                }
            }

            return true;
        }
    }
    /**
     * Finds which area a row and coloumn belongs to.
     *
     * @param r,
     *            row
     * @param c,
     *            coloumn
     * @return the area number.
     */

    public int findAreaNumber(int r, int c) {
        return r <= 2?(c <= 2?0:(c <= 5?1:2)):(r <= 5?(c <= 2?3:(c <= 5?4:5)):(c <= 2?6:(c <= 5?7:8)));
    }

    /**
     * Prints where the starting placements where set.
     */
    public void printStartingPlacements() {
        for(int r = 0; r < 9; ++r) {
            System.out.println();

            for(int c = 0; c < 9; ++c) {
                System.out.print(this.startingPlacements[r][c] + " ");
            }
        }

    }
}
