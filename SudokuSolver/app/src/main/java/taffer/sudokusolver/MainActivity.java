package taffer.sudokusolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int[][] placements = new int[9][9];
        public void clear(View v){

            for(int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    String squareID = "editText"+r+c;
                    int id = getResources().getIdentifier(squareID, "id", getPackageName());
                    EditText test = (EditText) findViewById(id);
                    test.setText("");
                }
            }

            TextView tv = (TextView) findViewById(R.id.textView);
            tv.setText("Board cleared");
        }


        public void solve(View v){

            for(int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    String squareID = "editText" + r + c;
                    int id = getResources().getIdentifier(squareID, "id", getPackageName());
                    EditText test = (EditText) findViewById(id);

                    if (!test.getText().toString().equals("")) {
                        placements[r][c] = Integer.parseInt(test.getText().toString());
                    } else {
                        placements[r][c] = 0;
                    }

                }
            }
            Board board = new Board(placements);
            board.printAreasAndWhatTheyContain();
           if(board.tryToCatchInvalidBoard(placements)!=null){
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText("Invalid placement at row: " + board.tryToCatchInvalidBoard(placements)[0] + " col: " + board.tryToCatchInvalidBoard(placements)[1]);
                return;
            }
            if(!board.checkRules(placements)){
                int[] error = board.returnError(placements);
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText("Invalid placement at row: " + error[0] + " col: " + error[1]);
                return;
            }

            if(!board.solve()){
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText("No solution!");
                return;
            }
            int[][] solvedBoard = board.getPlacements();
            for(int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    String squareID = "editText"+r+c;
                    int id = getResources().getIdentifier(squareID, "id", getPackageName());
                    EditText test = (EditText) findViewById(id);
                    test.setText(("" + solvedBoard[r][c]));
                }
            }
            TextView tv = (TextView) findViewById(R.id.textView);
            tv.setText("Solved!");

    }


}
