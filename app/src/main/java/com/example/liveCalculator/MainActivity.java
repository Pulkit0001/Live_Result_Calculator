package com.example.liveCalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    Fragment mat_frag = new Mathe_Cal_ui();
    Fragment sci_frag = new Scien_Cal_ui();
    FragmentManager manager = getSupportFragmentManager() ;
    FragmentTransaction transaction;
    GestureDetector detector;
    TextView iDisplayTv;
    TextView oDisplayTv;
    View host;
    CalculatorViewModel viewModel;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        detector = new GestureDetector(this,new MyListener());
        iDisplayTv = findViewById(R.id.input_display);
        oDisplayTv = findViewById(R.id.output_display);
        host = findViewById(R.id.buttons);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(CalculatorViewModel.class);
        viewModel.inputLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                iDisplayTv.setText(s);
            }
        });
        viewModel.outputLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                oDisplayTv.setText(s);
            }
        });
        transaction = manager.beginTransaction();
        transaction.add(R.id.buttons,mat_frag);
        transaction.commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(this.detector.onTouchEvent(event))
        {
            return  true;
        }
        else
        {
            return super.onTouchEvent(event);
        }
    }

    private  class MyListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velX, float velY) {
            float diffx = event1.getX() - event2.getX();
            float diffy = event1.getY() - event2.getY();
            System.out.println("DifferenceX"+diffx);
            System.out.println("DifferenceY"+diffy);
            if (Math.abs(diffx) > Math.abs(diffy)) {
                System.out.println("Difference of x is greater than y");
                if ((Math.abs(diffx) > 50) && (Math.abs(velX) > 50)) {

                    transaction = manager.beginTransaction();
                    System.out.println("value of diffx"+diffx);
                    if (diffx > 0.0) {
                        System.out.println("left Fling Occurred"+diffx);
                        transaction.replace(R.id.buttons, sci_frag);

                    }
                    else {
                        System.out.println("right Fling Occurred"+diffx);
                        transaction.replace(R.id.buttons, mat_frag);
                    }
                    transaction.commit();
                }
            }
            return true;
        }
    }
}
