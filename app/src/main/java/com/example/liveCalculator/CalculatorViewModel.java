package com.example.liveCalculator;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class CalculatorViewModel extends AndroidViewModel {



    MutableLiveData<String> inputLiveData = new MutableLiveData<>();
    MutableLiveData<String> outputLiveData = new MutableLiveData<>();
    Context context;
    StringBuilder infixString;
    StringBuilder displayString;
    int left_bracket;
    int right_bracket;
    double result;
    String buffer;
    char sign;
    List<Double> InfixData;
    Calculator MyCalculator = new Calculator();

    public CalculatorViewModel(Application context){
        super(context);
        this.context = context;
        infixString = new StringBuilder();
        displayString = new StringBuilder();
        buffer = "art(";
        left_bracket=1;
        right_bracket=0;
        result = 0;
        sign = '+';
        InfixData = new LinkedList<>();
    }


    public void receiveNumInput(String input) {

        char c = buffer.charAt(buffer.length()-1);
        // checks if the last entry is ")" the a number inout is not acceptable and prints a toast that
        // says enter a simple mathematical operator
        if(c==')'){
            receiveOptInput("*");
        }
        // numbered input is accessible as after a number, operator or Function start
        displayString = displayString.append(input);
        inputLiveData.setValue(displayString.toString());

        // here it inputs the numerical digit and combine it with the previous digit to represent an operand
        if ((c <= 57 && c >= 48)||c=='.') {
            buffer = buffer + input;
            if (InfixData.remove(InfixData.size() - 1) < 0)
                InfixData.add(Double.parseDouble("-" + buffer));
            else
                InfixData.add(Double.parseDouble(buffer));
        }
        else {
            infixString.append(buffer);
            buffer = input;
            InfixData.add(Double.parseDouble(sign + buffer));
            sign = '+';
            infixString.append('x');
        }
        StringBuilder query = new StringBuilder();
        query.append(infixString);
        int bracket = right_bracket;
        while(left_bracket>bracket){
            query.append(")");
            bracket++;
        }
        result=(MyCalculator.beginCalculation(query, InfixData));
        outputLiveData.setValue(""+result);
    }

    public void receiveOptInput(String Input){
        char c = buffer.charAt(buffer.length()-1);
        if(c=='(')
        {
            if(Input.equals("-")){
                if(sign=='+') {
                    sign = '-';
                    displayString.append("-");
                    inputLiveData.setValue(displayString.toString());
                }

            }
            else
                Toast.makeText(context, "Enter a number", Toast.LENGTH_SHORT).show();
        }
        else{
            if(((c<=57&&c>=48)||c=='.')||c==')'){
                buffer =Input;
                displayString.append(Input);

                //code to interpret an factorial operator input
                if(Input.equals("!")) {
                    infixString.append("!");
                    buffer =")";
                    StringBuilder query = new StringBuilder();
                    query.append(infixString);
                    int bracket = right_bracket;
                    while (left_bracket > bracket) {
                        query.append(")");
                        bracket++;
                    }
                    result = MyCalculator.beginCalculation(query, InfixData);
                    outputLiveData.setValue(""+result);
                }
            }
            else{
                if(Input.equals("-")){
                    receiveFuncInput("(");
                    receiveOptInput(Input);
                }
                else{
                    buffer = buffer.replace(c,Input.charAt(0));
                    displayString.deleteCharAt(displayString.length()-1);
                    displayString.append(Input);
                }
            }
           inputLiveData.setValue(displayString.toString());        }
    }

    public void receiveFuncInput(String Input) {

        char c = buffer.charAt(buffer.length()-1);
        if(c==')'||((c <= 57 && c >= 48)||c=='.')){
            receiveOptInput("*");
        }

        //code to represent Pie(22/7) is entered
        if(Input.equals("\u03C0"))
        {
            infixString.append(buffer);
            buffer =")";
            infixString.append('x');
            Double pie = 22.0/7.0;
            InfixData.add(pie);
            displayString.append("\u03C0");
            StringBuilder query = new StringBuilder();
            query.append(infixString);
            int bracket = right_bracket;
            while(left_bracket>bracket){
                query.append(")");
                bracket++;
            }

            result=MyCalculator.beginCalculation(query, InfixData);
        }
        else {
            displayString = displayString.append(Input);
            if(Input.equals("(")){
                Input = "art"+Input;
            }
            buffer = buffer + Input;
            left_bracket++;
        }
        inputLiveData.setValue(displayString.toString());
    }

    public void terminator(String Input){

        char c = buffer.charAt(buffer.length()-1);
        if(((c<=57&&c>=48) || c=='.' || c==')') && left_bracket<right_bracket )
        {
            buffer =")";
            infixString.append(Input);
            right_bracket++;
            displayString.append(Input);
        }
        else{
            Toast.makeText(context, "Enter a number", Toast.LENGTH_SHORT).show();
        }
        if(left_bracket==right_bracket){
            displayString.deleteCharAt(displayString.length()-1);
            reset("=");
        }
        inputLiveData.setValue(displayString.toString());

    }

    public void reset(String Input){

        displayString.append("\n").append(result).append("\n");
        inputLiveData.setValue(displayString.toString());
        buffer ="art(";
        infixString.delete(0, infixString.length());
        InfixData.removeAll(InfixData);
        result=0;
        left_bracket=1;
        right_bracket=0;


        // Code for AC(All Clear) button
        if(!Input.equals("=")){

            displayString.delete(0, displayString.length());
            inputLiveData.setValue(displayString.toString());
            outputLiveData.setValue("0.0");

        }
    }

    public void backButton(){
        if(displayString.length()>0) {
            char c = displayString.charAt(displayString.length() - 1);
            displayString.deleteCharAt(displayString.length() - 1);
            int status = 0;
            if (c == ')') {
                cutTerminator();
                status =1;
            }
            if ((c >= 48 && c <= 57) || c == '.') {
                cutDigit(c);
                status = 1;
            }
            if (c == '(') {
                cutFunc(displayString.substring((displayString.length()-3),displayString.length()));
                status = 1;
            }
            if(status==0)
            {
                cutOpt(c);
            }
            inputLiveData.setValue(displayString.toString());
        }
    }

    private void cutFunc(String func){
        if (sign == '-') {
            sign = '+';
        } else{
            int i= buffer.length();
            if(!buffer.substring(i - 4, i).equals("art("));
            {
                displayString.deleteCharAt(displayString.length()-1);
                displayString.deleteCharAt(displayString.length()-1);
                displayString.deleteCharAt(displayString.length()-1);
            }
            buffer = buffer.substring(0,i-4);
            if(buffer.isEmpty())
                buffer ="art(";
            left_bracket--;
        }
    }
    private void cutTerminator(){
        DecimalFormat Format = new DecimalFormat("0.#");
        System.out.println(") is present in buffer");
        infixString.deleteCharAt(infixString.length() - 1);
        right_bracket--;
        if (infixString.charAt(infixString.length() - 1) != ')') {
            buffer = Format.format(InfixData.get(InfixData.size() - 1));

        }
    }
    private void cutOpt(char opt){
        DecimalFormat Format = new DecimalFormat("0.#");
        if (displayString.charAt(displayString.length() - 1) == ')') {
            buffer = ")";
        } else {
            buffer = Format.format(InfixData.get(InfixData.size() - 1));
        }
    }
    private void cutDigit(char digit){
        System.out.println("A number is present in buffer");
        InfixData.remove(InfixData.size() - 1);
        if (buffer.length() == 1) {
            infixString.deleteCharAt(infixString.length()-1);
            buffer ="";
            while(infixString.charAt(infixString.length()-1)!='x'&& infixString.charAt(infixString.length()-1)!=')'){
                buffer = infixString.charAt(infixString.length()-1)+ buffer;
                infixString.deleteCharAt(infixString.length()-1);
                if(infixString.length()==0)
                    break;
            }
            System.out.println("After deleting the single digit number the value of buffer is "+ buffer);

        }
        else {
            System.out.println("Before deleting the value of last digit in buffer is " + buffer);
            buffer = buffer.substring(0, buffer.length() - 1);
            System.out.println("The digits in number was greater than two and after deleting the last digit the value of buffer is "+ buffer);
            InfixData.add(Double.parseDouble(buffer));
        }
        if(infixString.length()>4) {
            StringBuilder query = new StringBuilder();
            query.append(infixString);
            int bracket = right_bracket;
            while (left_bracket > bracket) {
                query.append(")");
                bracket++;
            }
            result = MyCalculator.beginCalculation(query, InfixData);
            outputLiveData.setValue(""+result);
        }
        else{
            result = 0.0;
        }
    }
}
