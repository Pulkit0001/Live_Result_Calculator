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


public class Scien_Cal_ui extends Fragment {

    private View.OnClickListener trigo_clickListener;
    CalculatorViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trigo_clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatButton btn = (AppCompatButton)v;
                viewModel.receiveFuncInput(btn.getText().toString()+'(');
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){

        final View view = inflater.inflate(R.layout.scie_cal_ui,container,false);
        viewModel = new ViewModelProvider(Objects.requireNonNull(this.getActivity())).get(CalculatorViewModel.class);

        AppCompatButton btnSin = view.findViewById(R.id.sin);
        AppCompatButton btnCos = view.findViewById(R.id.cos);
        AppCompatButton btnTan = view.findViewById(R.id.tan);
        AppCompatButton btnlogn = view.findViewById(R.id.logn);
        AppCompatButton btnlog = view.findViewById(R.id.log);
        AppCompatButton btnFact = view.findViewById(R.id.factorial);
        AppCompatButton Reciprocal = view.findViewById(R.id.reciprocal);
        AppCompatButton Sqrt = view.findViewById(R.id.Button_sqrt);
        AppCompatButton pie = view.findViewById(R.id.pie);
        btnCos.setOnClickListener(trigo_clickListener);
        btnSin.setOnClickListener(trigo_clickListener);
        btnTan.setOnClickListener(trigo_clickListener);
        btnlog.setOnClickListener(trigo_clickListener);
        btnlogn.setOnClickListener(trigo_clickListener);
        Sqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewModel.receiveFuncInput("sqt(");
            }
        });
        btnFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatButton btn = (AppCompatButton)v;
                viewModel.receiveOptInput(btn.getText().toString());
            }
        });
        Reciprocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.receiveNumInput("1");
                viewModel.receiveOptInput("/");
                viewModel.receiveFuncInput("(");
            }
        });
        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatButton btn = (AppCompatButton)v;
                viewModel.receiveFuncInput(btn.getText().toString());
            }
        });
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

}