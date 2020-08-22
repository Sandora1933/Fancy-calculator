package com.example.fancycalculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener {

    //Current text on calculator display
    String currentDisplayText;

    //Views
    TextView currentResultTextView;
    Button nineDigitButton, eightDigitButton, sevenDigitButton, sixDigitButton, fiveDigitButton,
             fourDigitButton, threeDigitButton, twoDigitButton, oneDigitButton, zeroDigitButton,
             plusOperatorButton, minusOperatorButton, multiplyOperatorButton, divideOperatorButton,
             equalityButton, acButton, backButton;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setListeners();
        setBasicDisplayText();

        //Prohibit screen rotation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    private void init(){
        currentResultTextView = findViewById(R.id.currentResultTextView);

        nineDigitButton = findViewById(R.id.nineDigitButton);
        eightDigitButton = findViewById(R.id.eightDigitButton);
        sevenDigitButton = findViewById(R.id.sevenDigitButton);
        sixDigitButton = findViewById(R.id.sixDigitButton);
        fiveDigitButton = findViewById(R.id.fiveDigitButton);
        fourDigitButton = findViewById(R.id.fourDigitButton);
        threeDigitButton = findViewById(R.id.threeDigitButton);
        twoDigitButton = findViewById(R.id.twoDigitButton);
        oneDigitButton = findViewById(R.id.oneDigitButton);
        zeroDigitButton = findViewById(R.id.zeroDigitButton);

        plusOperatorButton = findViewById(R.id.additionButton);
        minusOperatorButton = findViewById(R.id.subtractionButton);
        multiplyOperatorButton = findViewById(R.id.productButton);
        divideOperatorButton = findViewById(R.id.divideButton);

        equalityButton = findViewById(R.id.equalityButton);
        acButton = findViewById(R.id.acButton);
        backButton = findViewById(R.id.backButton);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners(){
        //OnClickListeners
        nineDigitButton.setOnClickListener(this);
        eightDigitButton.setOnClickListener(this);
        sevenDigitButton.setOnClickListener(this);
        sixDigitButton.setOnClickListener(this);
        fiveDigitButton.setOnClickListener(this);
        fourDigitButton.setOnClickListener(this);
        threeDigitButton.setOnClickListener(this);
        twoDigitButton.setOnClickListener(this);
        oneDigitButton.setOnClickListener(this);
        zeroDigitButton.setOnClickListener(this);

        plusOperatorButton.setOnClickListener(this);
        minusOperatorButton.setOnClickListener(this);
        multiplyOperatorButton.setOnClickListener(this);
        divideOperatorButton.setOnClickListener(this);

        equalityButton.setOnClickListener(this);
        acButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        //OnTouchListeners
        nineDigitButton.setOnTouchListener(this);
        eightDigitButton.setOnTouchListener(this);
        sevenDigitButton.setOnTouchListener(this);
        sixDigitButton.setOnTouchListener(this);
        fiveDigitButton.setOnTouchListener(this);
        fourDigitButton.setOnTouchListener(this);
        threeDigitButton.setOnTouchListener(this);
        twoDigitButton.setOnTouchListener(this);
        oneDigitButton.setOnTouchListener(this);
        zeroDigitButton.setOnTouchListener(this);

        plusOperatorButton.setOnTouchListener(this);
        minusOperatorButton.setOnTouchListener(this);
        multiplyOperatorButton.setOnTouchListener(this);
        divideOperatorButton.setOnTouchListener(this);

        equalityButton.setOnTouchListener(this);
        acButton.setOnTouchListener(this);
        backButton.setOnTouchListener(this);
    }

    private void setBasicDisplayText(){
        currentDisplayText = "0";
        currentResultTextView.setText(currentDisplayText);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                v.getBackground().setColorFilter(getResources().getColor(R.color.colorPink),
                        PorterDuff.Mode.SRC_ATOP);
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                v.getBackground().clearColorFilter();
                v.invalidate();
                break;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.acButton:
                setBasicDisplayText();
                break;
            case R.id.backButton:
                removeLastSymbol();
                break;
            case R.id.nineDigitButton:
                appendDigitSymbol('9');
                break;
            case R.id.eightDigitButton:
                appendDigitSymbol('8');
                break;
            case R.id.sevenDigitButton:
                appendDigitSymbol('7');
                break;
            case R.id.sixDigitButton:
                appendDigitSymbol('6');
                break;
            case R.id.fiveDigitButton:
                appendDigitSymbol('5');
                break;
            case R.id.fourDigitButton:
                appendDigitSymbol('4');
                break;
            case R.id.threeDigitButton:
                appendDigitSymbol('3');
                break;
            case R.id.twoDigitButton:
                appendDigitSymbol('2');
                break;
            case R.id.oneDigitButton:
                appendDigitSymbol('1');
                break;
            case R.id.zeroDigitButton:
                appendDigitSymbol('0');
                break;
            case R.id.additionButton:
                appendOperatorSymbol('+');
                break;
            case R.id.subtractionButton:
                appendOperatorSymbol('-');
                break;
            case R.id.productButton:
                appendOperatorSymbol('*');
                break;
            case R.id.divideButton:
                appendOperatorSymbol('/');
                break;
            case R.id.equalityButton:
                calculate();
                break;
            default:
                break;
        }

    }

    private void removeLastSymbol(){
        //Case1 : Initial zero -> nothing to do
        //Case2 : Just one any digit except zero -> remove to zero
        //Case3 : Any other case -> remove last symbol

        if (currentDisplayText.trim().length() != 1 ||
                currentDisplayText.charAt(0) != '0') {
            if (currentDisplayText.trim().length() == 1 &&
                currentDisplayText.charAt(0) != '0'){
                setBasicDisplayText();
            }
            else{
                currentDisplayText = currentDisplayText.trim()
                        .substring(0, currentDisplayText.length()-1);
                currentResultTextView.setText(currentDisplayText);
            }
        }

    }

    //Method manipulates with display text when digit clicked
    private void appendDigitSymbol(char digitSymbol){
        //Case1: starting zero
        //Case2: digit at the end
        //Case3: operator at the end

        if (currentDisplayText.length() == 8){
            Toast.makeText(this, "too many symbols", Toast.LENGTH_SHORT).show();
            return;
        }

        //Case1
        if (currentDisplayText.equals("0")){
            currentDisplayText = String.valueOf(digitSymbol);
            currentResultTextView.setText(currentDisplayText);
        }
        //Case2 & Case3
        else{
            currentDisplayText += String.valueOf(digitSymbol);
            currentResultTextView.setText(currentDisplayText);
        }

    }

    //Method manipulates with display text when operator clicked
    private void appendOperatorSymbol(char operatorSymbol){
        //State1: starting zero -> simple append
        //State2: single digit -> simple append
        //State3: operator at the end -> no action
        //State4: expression \d\op\d -> auto-calculation and appending.
        //State5: several digits without operator

        if (currentDisplayText.length() == 8){
            Toast.makeText(this, "too many symbols", Toast.LENGTH_SHORT).show();
            return;
        }

        //Regex for \d\op\d [\digit \operator \digit]
        String expressionString = "[0-9]+[\\+|\\-|\\*|\\/][0-9]+";

        //State1 && State2
        if (currentDisplayText.trim().length() == 1 && currentDisplayText.trim().charAt(0) != '-'){
            currentDisplayText += String.valueOf(operatorSymbol);
            currentResultTextView.setText(currentDisplayText);
        }
        //State4
        else if (currentDisplayText.matches(expressionString)){
            calculate();
            currentDisplayText += String.valueOf(operatorSymbol);
            currentResultTextView.setText(currentDisplayText);
        }
        //State5
        else if (currentDisplayText.charAt(currentDisplayText.length() - 1) != '+' &&
                currentDisplayText.charAt(currentDisplayText.length() - 1) != '-' &&
                currentDisplayText.charAt(currentDisplayText.length() - 1) != '*' &&
                currentDisplayText.charAt(currentDisplayText.length() - 1) != '/'){
            currentDisplayText += String.valueOf(operatorSymbol);
            currentResultTextView.setText(currentDisplayText);
        }
    }

    private void appendCommaSymbol(){
        throw new UnsupportedOperationException();
    }

    private void calculate(){
        //Case1: starting zero
        //Case2: single digit
        //Case3: digit + operator
        //Case4: digit + operator + digit or more same cases

        String firstNumberStr, secondNumberStr;
        char operator;
        int operatorIndex;
        boolean hasInitialMinus = false;

        //Check for initial minus
        if (currentDisplayText.charAt(0) == '-'){
            hasInitialMinus = true;
        }

        //Check for error when no any operators and equals() called:
        if (!hasInitialMinus){
            if (currentDisplayText.indexOf('+') == -1 &&
                    currentDisplayText.indexOf('-') == -1 &&
                    currentDisplayText.indexOf('*') == -1 &&
                    currentDisplayText.indexOf('/') == -1) {
                return;
            }
        }
        //Check the case when no operators exist apart from initial minus (as negation for number)
        else{
            if (currentDisplayText.trim().substring(1, currentDisplayText.length()).indexOf('+') == -1 &&
                    currentDisplayText.trim().substring(1, currentDisplayText.length()).indexOf('-') == -1 &&
                    currentDisplayText.trim().substring(1, currentDisplayText.length()).indexOf('*') == -1 &&
                    currentDisplayText.trim().substring(1, currentDisplayText.length()).indexOf('/') == -1) {
                return;
            }
        }

        //Plus operator occurred
        if (currentDisplayText.indexOf('+') != -1){
            operator = '+';
            operatorIndex = currentDisplayText.indexOf('+');
        }
        //Multiply operator occurred
        else if (currentDisplayText.indexOf('*') != -1){
            operator = '*';
            operatorIndex = currentDisplayText.indexOf('*');
        }
        //Divide operator occurred
        else if (currentDisplayText.indexOf('/') != -1){
            operator = '/';
            operatorIndex = currentDisplayText.indexOf('/');
        }
        //Minus operator occurred but also taking into account possible initial minus
        else{
            if (!hasInitialMinus && currentDisplayText.indexOf('-') != -1){
                operator = '-';
                operatorIndex = currentDisplayText.indexOf('-');
            }
            else{
                operator = '-';
                operatorIndex = currentDisplayText.substring(1).indexOf('-') + 1;
            }
        }

        Log.d("operatorIndexValue", "" + operatorIndex);

        double firstNumber, secondNumber;

        //Getting first operand
        if (hasInitialMinus){
            firstNumberStr = currentDisplayText.substring(1, operatorIndex);
            firstNumber = (-1) * Double.parseDouble(firstNumberStr);
        }
        else{
            firstNumberStr = currentDisplayText.substring(0, operatorIndex);
            firstNumber = Double.parseDouble(firstNumberStr);
        }

        secondNumberStr = currentDisplayText.substring(operatorIndex+1);

        Log.d("currentResultTextView", currentDisplayText);
        Log.d("firstNumberStr", firstNumberStr);
        Log.d("secondNumberStr", secondNumberStr);

        if (secondNumberStr.isEmpty()){
            return;
        }
        else if (secondNumberStr.equals("0") && operator == '/'){
            Toast.makeText(this, "Division by zero", Toast.LENGTH_SHORT).show();
            setBasicDisplayText();
            return;
        }

        //Getting second operand
        secondNumber = Double.parseDouble(secondNumberStr);

        //Maintain calculation
        if (operator == '+'){
            double result = firstNumber + secondNumber;
            currentDisplayText = Integer.toString((int)result);
            currentResultTextView.setText(currentDisplayText);
        }
        else if (operator == '-'){
            double result = firstNumber - secondNumber;
            currentDisplayText = Integer.toString((int)result);
            currentResultTextView.setText(currentDisplayText);
        }
        else if (operator == '*'){
            double result = firstNumber * secondNumber;
            currentDisplayText = Integer.toString((int)result);
            currentResultTextView.setText(currentDisplayText);
        }
        else {
            double result = firstNumber / secondNumber;
            currentDisplayText = Integer.toString((int)result);
            currentResultTextView.setText(currentDisplayText);
        }
    }
}
