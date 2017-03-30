package com.example.berka.asajmentzaslanje;

import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    private int counterForPressedFields = 0;
    private int textViewForComparationCounter = 0;
    TextView firstTextViewForComparation;
    TextView secondTextViewForComparation;
    TextView points;
    private int numOfOpenedFields = 0;
    private int[] nonPressedFields = {1, 1, 1, 1, 1, 1, 1, 1, 1};
    private int pressedField = 2;
    private int tag = 0;
    private int brojOtvaranja = 0;
    TextView winnerMessage;

    MyDBManager myDb;
    EditText editName;
    Button btnAddData;
    Button btnViewAll;

    // ONCREATE METOD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new MyDBManager(this, null, null, 1);
        editName = (EditText) findViewById(R.id.playerInputName);
        points = (TextView) findViewById(R.id.numOfClicksTextView);
        btnAddData = (Button) findViewById(R.id.addScoreButton);
        btnViewAll = (Button) findViewById(R.id.viewAllButton);
        addData();
        viewAll();
    }
    public void show(View view) throws InterruptedException {

        textView = (TextView) view;

        tag = Integer.parseInt(textView.getTag().toString());

        if (nonPressedFields[tag] == 1) {

            nonPressedFields[tag] = pressedField;

            if (counterForPressedFields < 2) {
                textViewForComparationCounter += 1;
                counterForPressedFields += 1;
                brojOtvaranja += 1;
                points.setText(Integer.toString(brojOtvaranja));

                switch (tag) {

                    case 0:
                        textView = (TextView) findViewById(R.id.front1);
                        textView.setText("7");
                        break;

                    case 1:
                        textView = (TextView) findViewById(R.id.front2);
                        textView.setText("9");
                        break;

                    case 2:
                        textView = (TextView) findViewById(R.id.front3);
                        textView.setText("7");
                        break;
                    case 3:
                        textView = (TextView) findViewById(R.id.front4);
                        textView.setText("9");
                        break;
                    case 4:
                        textView = (TextView) findViewById(R.id.front5);
                        textView.setText("2");
                        break;
                    case 5:
                        textView = (TextView) findViewById(R.id.front6);
                        textView.setText("8");
                        break;
                    case 6:
                        textView = (TextView) findViewById(R.id.front7);
                        textView.setText("2");
                        break;
                    case 7:
                        textView = (TextView) findViewById(R.id.front8);
                        textView.setText("4");
                        break;
                    case 8:
                        textView = (TextView) findViewById(R.id.front9);
                        textView.setText("4");
                        break;
                    default:
                        break;
                }

                if (textViewForComparationCounter == 1) {
                    firstTextViewForComparation = (TextView) view;
                    firstTextViewForComparation = textView;

                } else if (textViewForComparationCounter == 2) {
                    secondTextViewForComparation = (TextView) view;
                    secondTextViewForComparation = textView;
                }
            }

            if (counterForPressedFields == 2) {
                if (firstTextViewForComparation.getText().toString().equals(secondTextViewForComparation.getText().toString())) {

                    counterForPressedFields = 0;
                    textViewForComparationCounter = 0;

                    numOfOpenedFields += 2;

                } else {
                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        public void run() {

                            firstTextViewForComparation.setText("*");
                            secondTextViewForComparation.setText("*");
                        }
                    };
                    handler.postDelayed(runnable, 500);

                    nonPressedFields[Integer.parseInt(firstTextViewForComparation.getTag().toString())] = 1;
                    nonPressedFields[Integer.parseInt(secondTextViewForComparation.getTag().toString())] = 1;
                    counterForPressedFields = 0;
                    textViewForComparationCounter = 0;
                }
            }
            if (numOfOpenedFields == 8) {

                winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText("Your score: " + brojOtvaranja);

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                linearLayout.setVisibility(View.VISIBLE);

                brojOtvaranja = 0;
                numOfOpenedFields = 0;

            }

        }

    }

    public void playAgain(View view) {

        btnAddData.setVisibility(View.VISIBLE);
//        btnAddData.setClickable(true);
        editName.setVisibility(View.VISIBLE);
//        editName.setClickable(true);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        linearLayout.setVisibility(View.INVISIBLE);

        tag = 0;
        brojOtvaranja = 0;
        for (int i = 0; i < nonPressedFields.length; i++) {
            nonPressedFields[i] = 1;

        }

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        for (int i = 0; i < tableLayout.getChildCount(); i++) {

            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);

            for (int j = 0; j < tableRow.getChildCount(); j++) {

                ((TextView) tableRow.getChildAt(j)).setText("*");
            }
        }

    }



    public void addData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean pressed = myDb.insertData(editName.getText().toString(), points.getText().toString());
                        if (pressed) {
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            editName.setText(null);
                            points.setText(null);
                            winnerMessage.setText(null);
                            btnAddData.setVisibility(View.INVISIBLE);
                            //  btnAddData.setClickable(false);
                            editName.setVisibility(View.INVISIBLE);
                            // editName.setClickable(false);


                        } else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }

                }

        );

    }


    public void viewAll() {

        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("error ", " noting found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("id: " + res.getString(0) + "\n");
                            buffer.append("name " + res.getString(1) + "\n");
                            buffer.append("score: " + res.getString(2) + "\n\n");
                        }

                        //show all
                        showMessage("Data", buffer.toString());

                    }
                }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder bilder = new AlertDialog.Builder(this);
        bilder.setCancelable(true);
        bilder.setTitle(title);
        bilder.setMessage(message);
        bilder.show();
    }
}