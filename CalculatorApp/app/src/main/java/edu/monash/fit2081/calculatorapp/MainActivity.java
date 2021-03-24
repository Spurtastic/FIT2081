package edu.monash.fit2081.calculatorapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private double valueOne = Double.NaN;
    private double valueTwo;
    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';
    private static final char NO_OPERATION = '?';

    private char CURRENT_ACTION;
    private DecimalFormat decimalFormat;
    public TextView interScreen;  // Intermediate result Screen
    private TextView resultScreen; // Result Screen
    private String intermediate;
    private String resultant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Reference both TextViews
        interScreen =  findViewById(R.id.InterScreen);
        resultScreen =  findViewById(R.id.resultScreen);
        decimalFormat = new DecimalFormat("#.##########");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Calc", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Calc", "onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Calc", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Calc", "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Calc", "onDestroy");

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("Calc", "onSavedInstance");

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("Calc", "onRestoreInstanceState: ");

    }
    //0
    public void buttonZeroClick(View v) {
        interScreen.setText(interScreen.getText() + "0");
    }
    //1
    public void buttonOneClick(View v) {
        interScreen.setText(interScreen.getText() + "1");
    }
    //2
    public void buttonTwoClick(View v) {
        interScreen.setText(interScreen.getText() + "2");
    }
    //3
    public void buttonThreeClick(View v) {
        interScreen.setText(interScreen.getText() + "3");
    }
    //4
    public void buttonFourClick(View v) {
        interScreen.setText(interScreen.getText() + "4");
    }
    //5
    public void buttonFiveClick(View v) {interScreen.setText(interScreen.getText() + "5");  }
    //6
    public void buttonSixClick(View v) {
        interScreen.setText(interScreen.getText() + "6");
    }

    public void buttonSevenClick(View v) {
        interScreen.setText(interScreen.getText() + "7");
    }

    public void buttonEightClick(View v) {
        interScreen.setText(interScreen.getText() + "8");
    }

    public void buttonNineClick(View v) {
        interScreen.setText(interScreen.getText() + "9");
    }
    public void buttonPeriodClick(View v) {
        interScreen.setText(interScreen.getText() + ".");
    }

    public void buttonDivisionClick(View v) {
        computeCalculation();
        if (Double.isNaN(valueOne)) {
            showToast("Invalid Key");
        } else {
            CURRENT_ACTION = DIVISION;
            resultScreen.setText(decimalFormat.format(valueOne) + "/");
            interScreen.setText(null);
        }
    }
    public void buttonAdditionClick(View v) {
        computeCalculation();
        if (Double.isNaN(valueOne)) {
            showToast("Invalid Key");
        } else {
            CURRENT_ACTION = ADDITION;
            resultScreen.setText(decimalFormat.format(valueOne) + "+");
            interScreen.setText(null);
        }
    }
    public void buttonMultiplyClick(View v) {
        computeCalculation();
        if (Double.isNaN(valueOne)) {
            showToast("Invalid Key");
        } else {
            CURRENT_ACTION = MULTIPLICATION;
            resultScreen.setText(decimalFormat.format(valueOne) + "*");
            interScreen.setText(null);
        }
    }
    public void buttonMinusClick(View v) {
        computeCalculation();
        if (Double.isNaN(valueOne)) {
            showToast("Invalid Key");
        } else {
            CURRENT_ACTION = SUBTRACTION;
            resultScreen.setText(decimalFormat.format(valueOne) + "-");
            interScreen.setText(null);
        }
    }

    public void buttonEqualClick(View v) {

        /*
        * Call ComputeCalculation method
        * Update the result TextView by adding the '=' char and result of operation
        * Reset valueOne
        * Set CURRENT_ACTION to NO_OPERATION
        * */

        computeCalculation();
        if (Double.isNaN(valueOne)) {
            showToast("Invalid Key");
        } else {

            CURRENT_ACTION = NO_OPERATION;
//            System.out.println(intermediate);
            intermediate= interScreen.getText().toString();
            resultant =resultScreen.getText().toString();
            resultScreen.setText(resultant+intermediate+"="+decimalFormat.format(valueOne) );
            interScreen.setText(null);
            valueOne =Double.NaN;
        }

    }

    public void buttonClearClick(View v) {
        /*
        * if the intermediate TextView has text then
        *       delete the last character
        * else
              * reset valueOne, valueTwo, the content of result TextView,
              * and the content of intermediate TextView
        * */
        String intermediate;
        resultScreen = findViewById(R.id.resultScreen);

        if ((interScreen.getText().toString().length()>=1)){
            intermediate = interScreen.getText().toString().substring(0,(interScreen.getText().toString().length())-1);
            interScreen.setText(intermediate);
        }
        else{
            resultScreen.setText("");
            valueOne = Double.NaN;
        }



    }


    private void computeCalculation() {
        if (!Double.isNaN(valueOne)) {
            String valueTwoString = interScreen.getText().toString();
            if (!valueTwoString.equals("")) {
                valueTwo = Double.parseDouble(valueTwoString);
//                interScreen.setText(null);
                if (CURRENT_ACTION == ADDITION)
                    valueOne = this.valueOne + valueTwo;
                else if (CURRENT_ACTION == SUBTRACTION)
                    valueOne = this.valueOne - valueTwo;
                else if (CURRENT_ACTION == MULTIPLICATION)
                    valueOne = this.valueOne * valueTwo;
                else if (CURRENT_ACTION == DIVISION)
                    valueOne = this.valueOne / valueTwo;
            }
        } else {
            try {
                valueOne = Double.parseDouble(interScreen.getText().toString());
            } catch (Exception e) {
            }

        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
