package com.example.kdvu.timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.TypedValue;

public class Timer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle timerData = getIntent().getExtras();
        if (timerData == null){
            return;
        }

        String hour = timerData.getString("hour");
        String min = timerData.getString("min");
        String sec = timerData.getString("sec");

        //Layout
        RelativeLayout rLayout = new RelativeLayout(this);

        //TextViews
        final TextView hourDisplay = new TextView(this);
        final TextView minDisplay = new TextView(this);
        final TextView secDisplay = new TextView(this);

        hourDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        minDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        secDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);

        hourDisplay.setText(hour + " :");
        minDisplay.setText(" " + min + " : ");
        secDisplay.setText(sec);

        hourDisplay.setId(R.id.hourDisplay);
        minDisplay.setId(R.id.minDisplay);
        secDisplay.setId(R.id.secDisplay);

        //Buttons
        final Button startBtn = new Button(this);
        final Button restartBtn = new Button(this);

        startBtn.setText("Start");
        restartBtn.setText("Reset");

        RelativeLayout.LayoutParams startBtnDetails = wrapContent();
        RelativeLayout.LayoutParams restartBtnDetails = wrapContent();
        RelativeLayout.LayoutParams hourDisplayDetails = wrapContent();
        RelativeLayout.LayoutParams minDisplayDetails = wrapContent();
        RelativeLayout.LayoutParams secDisplayDetails = wrapContent();

        minDisplayDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        minDisplayDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        hourDisplayDetails.addRule(RelativeLayout.LEFT_OF, minDisplay.getId());
        hourDisplayDetails.addRule(RelativeLayout.CENTER_VERTICAL);
        hourDisplayDetails.setMargins(0,0,0,50);

        secDisplayDetails.addRule(RelativeLayout.RIGHT_OF, minDisplay.getId());
        secDisplayDetails.addRule(RelativeLayout.CENTER_VERTICAL);
        secDisplayDetails.setMargins(0,0,0,50);

        startBtnDetails.addRule(RelativeLayout.BELOW, hourDisplay.getId());
        startBtnDetails.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        startBtnDetails.setMargins(100,0,0,0);

        restartBtnDetails.addRule(RelativeLayout.BELOW, secDisplay.getId());
        restartBtnDetails.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        restartBtnDetails.setMargins(0,0,120,0);

        rLayout.addView(minDisplay, minDisplayDetails);
        rLayout.addView(hourDisplay, hourDisplayDetails);
        rLayout.addView(secDisplay, secDisplayDetails);
        rLayout.addView(startBtn, startBtnDetails);
        rLayout.addView(restartBtn, restartBtnDetails);
        setContentView(rLayout);
    }

    public RelativeLayout.LayoutParams wrapContent(){
        return  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
}
