package com.example.liveCalculator;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;


public class Mathe_Cal_ui extends Fragment {
    private View.OnClickListener NumListener;
    private View.OnClickListener OptListener;
    private View.OnClickListener FuncListener;
    private View.OnClickListener reset;
    CalculatorViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NumListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatButton btn = (AppCompatButton)v;
                viewModel.receiveNumInput(btn.getText().toString());
                System.out.println(" Button"+btn.getText()+"clicked");
            }
        };

        OptListener  = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatButton btn = (AppCompatButton)v;
                viewModel.receiveOptInput(btn.getText().toString());
                System.out.println(" Button"+btn.getText()+"clicked");
            }
        };

        FuncListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatButton btn = (AppCompatButton)v;
                viewModel.receiveFuncInput(btn.getText().toString());
                System.out.println(" Button"+btn.getText()+"clicked");
            }
        };

        reset = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatButton btn = (AppCompatButton)v;
                viewModel.reset(btn.getText().toString());
                System.out.println(" Button"+btn.getText()+"clicked");
            }
        };
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.mathe_cal_ui,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel  = new ViewModelProvider(Objects.requireNonNull(this.getActivity())).get(CalculatorViewModel.class);
        AppCompatButton btn0 = view.findViewById(R.id.button0);
        AppCompatButton btn1 = view.findViewById(R.id.button1);
        AppCompatButton btn2 = view.findViewById(R.id.button2);
        AppCompatButton btn3 = view.findViewById(R.id.button3);
        AppCompatButton btn4 = view.findViewById(R.id.button4);
        AppCompatButton btn5 = view.findViewById(R.id.button5);
        AppCompatButton btn6 = view.findViewById(R.id.button6);
        AppCompatButton btn7 = view.findViewById(R.id.button7);
        AppCompatButton btn8 = view.findViewById(R.id.button8);
        AppCompatButton btn9 = view.findViewById(R.id.button9);
        AppCompatButton btn_add = view.findViewById(R.id.button_add);
        AppCompatButton btn_minus = view.findViewById(R.id.button_minus);
        AppCompatButton btn_mul = view.findViewById(R.id.button_multiply);
        AppCompatButton btn_div = view.findViewById(R.id.button_divide);
        AppCompatButton btn_equal = view.findViewById(R.id.button_equals);
        AppCompatButton btn_left = view.findViewById(R.id.buttonlb);
        AppCompatButton btn_right = view.findViewById(R.id.buttonrb);
        AppCompatButton btn_AC = view.findViewById(R.id.buttonAC);
        AppCompatButton btn_dot = view.findViewById(R.id.button_dot);
        AppCompatButton btn_pow = view.findViewById(R.id.button_power);
        AppCompatButton btn_back = view.findViewById(R.id.button_back);



        btn_div.setOnClickListener(OptListener);
        btn_mul.setOnClickListener(OptListener);
        btn_minus.setOnClickListener(OptListener);
        btn_add.setOnClickListener(OptListener);
        btn_pow.setOnClickListener(OptListener);
        btn_equal.setOnClickListener(reset);
        btn9.setOnClickListener(NumListener);
        btn8.setOnClickListener(NumListener);
        btn7.setOnClickListener(NumListener);
        btn6.setOnClickListener(NumListener);
        btn5.setOnClickListener(NumListener);
        btn4.setOnClickListener(NumListener);
        btn3.setOnClickListener(NumListener);
        btn2.setOnClickListener(NumListener);
        btn1.setOnClickListener(NumListener);
        btn0.setOnClickListener(NumListener);
        btn_dot.setOnClickListener(NumListener);
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatButton btn = (AppCompatButton)v;
                viewModel.terminator(btn.getText().toString());
                System.out.println(" Button"+btn.getText()+"clicked");
            }
        });
        btn_left.setOnClickListener(FuncListener);
        btn_AC.setOnClickListener(reset);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { viewModel.backButton();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }








}


