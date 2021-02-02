package com.example.liveCalculator;


import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Calculator {

    // A linked list defined to contain the data of Infix expression
    private List<Double> InfixData = new LinkedList<>();

    //An object containing the index up to which the query has been expressed in to postfix form
    Boxer Index;

    String Infix;

    public Calculator() {

    }

    public Double beginCalculation(StringBuilder Infix, List<Double> InfixData){

        this.InfixData.addAll(InfixData);
        this.Infix=Infix.toString();
        System.out.println(Infix);
        Index  = new Boxer(-1);
        System.out.println(InfixData);


        return FunctionSolver();
    }




    public Double FunctionSolver () {

        // A string containing the function type
        String func = Infix.substring(Index.getIndex()+1,Index.getIndex()+5);
        System.out.println("Value of func in Function Solver"+func+"And Infix String is"+Infix);

        //Increment the Index as function type has been captured in the string func
        Index.increment(4);

        System.out.println("Inside Function Solver");

        // A switch statement that checks which types of function is to be solved
        if(func.equals("art(")){
            System.out.println("Inside art case");
            InfixData.add(IntoPostFunc());
        }
        if (func.equals("Sin(")){
            System.out.println("Inside Sin case");
            InfixData.add(Math.sin((IntoPostFunc()*22)/1260));
        }
        if (func.equals("Cos(")){
            System.out.println("Inside Cos case");
            InfixData.add(Math.cos((IntoPostFunc()*22)/1260));
        }
        if (func.equals("Tan(")){
            System.out.println("Inside Tan case");
            InfixData.add(Math.tan((IntoPostFunc()*22)/1260));
        }
        if (func.equals("Log(")){
            System.out.println("Inside Log case");
            InfixData.add(Math.log(IntoPostFunc()));
        }
        if (func.equals("Lgn(")){
            System.out.println("Inside Lgn case");
            InfixData.add(Math.log10(IntoPostFunc()));
        }
        if(func.equals("sqt(")){
            InfixData.add(Math.sqrt(IntoPostFunc()));
        }

        return InfixData.remove(0);
    }

    private double IntoPostFunc(){


        // A linked list defined to contain the data of postfix expression
        List<Double> PostfixData = new LinkedList<>();

        StringBuilder Postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        System.out.println("Inside IntoPostFunc");


        for ( int i= Index.getIndex()+1; i < Infix.length();i++) {
            char c = Infix.charAt(i);
            System.out.println("character scanned"+c);
            //System.out.println(Postfix);
            switch (c) {
                case '+':
                case '-':
                case '*':
                case '^':
                case '/': {
                    Index.increment(1);
                    if(!stack.isEmpty()) {
                        while (priority(stack.peek()) >= priority(c) ) {
                            Postfix.append(stack.pop());
                            if(stack.isEmpty())
                                break;
                        }

                    }
                    stack.push(c);
                    break;
                }
                case 'x':{

                    Postfix.append('x');
                    PostfixData.add(InfixData.remove(0));
                    Index.increment(1);
                    break;
                }
                case ')':{
                    if(!stack.isEmpty()) {
                        while (!stack.isEmpty()) {
                            Postfix.append(stack.pop());
                        }
                    }
                    Index.increment(1);
                    return evaluate(Postfix,PostfixData);
                }
                default: {
                    if(c=='!'){
                        double k = PostfixData.remove(PostfixData.size()-1);
                        int j = (int) k;
                        double num=1.0;
                        while(j>1){
                            num=num*j;
                            j--;

                        }
                        PostfixData.add(num);
                        Index.increment(1);
                    }
                    else {
                        Postfix.append('x');
                        PostfixData.add(FunctionSolver());
                        i = Index.getIndex();
                    }
                }
            }
            //System.out.println("Switch case exited");
            System.out.println("Postfix expression"+Postfix);
        }
        return evaluate(Postfix,PostfixData);
    }


    private double evaluate(StringBuilder Postfix,List<Double> Data)
    {
        Stack<Double> stack = new Stack<>();
        System.out.println("postfix expression in evaluate"+Postfix);
        System.out.println(Data);
        for(int i=0;i<Postfix.length();i++)
        {

            System.out.println(stack);

                char opt=Postfix.charAt(i);
                switch (opt)
                {
                    case '+':
                    {
                        Double x=stack.pop();
                        stack.push(stack.pop()+x);
                        break;
                    }
                    case '-':
                    {
                        Double x=stack.pop();
                        stack.push(stack.pop()-x);
                        break;
                    }
                    case '*':
                    {
                        Double x=stack.pop();
                        stack.push(stack.pop()*x);
                        break;
                    }
                    case '/':
                    {
                        Double x=stack.pop();
                        stack.push(stack.pop()/x);
                        break;
                    }
                    case '^':
                    {
                        Double x=stack.pop();
                        stack.push(Math.pow(stack.pop(),x));
                        break;
                    }
                    default:{
                        System.out.println(Data.size());
                        stack.push(Data.remove(0));
                    }
            }
        }
        return stack.peek();

    }
    private int priority(char c)
    {
        switch (c)
        {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 3;
        }
    }
}
