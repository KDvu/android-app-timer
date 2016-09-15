package com.example.kdvu.timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        final TextView hourDisplay = new TextView(this);
        final TextView minDisplay = new TextView(this);
        final TextView secDisplay = new TextView(this);

        hourDisplay.setText(hour + " :");
        minDisplay.setText(" " + min + " : ");
        secDisplay.setText(sec);

        hourDisplay.setId(R.id.hourDisplay);
        minDisplay.setId(R.id.minDisplay);
        secDisplay.setId(R.id.secDisplay);

        RelativeLayout.LayoutParams hourDisplayDetails = wrapContent();
        RelativeLayout.LayoutParams minDisplayDetails = wrapContent();
        RelativeLayout.LayoutParams secDisplayDetails = wrapContent();

        minDisplayDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        minDisplayDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        hourDisplayDetails.addRule(RelativeLayout.LEFT_OF, minDisplay.getId());
        hourDisplayDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        secDisplayDetails.addRule(RelativeLayout.RIGHT_OF, minDisplay.getId());
        secDisplayDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        rLayout.addView(minDisplay, minDisplayDetails);
        rLayout.addView(hourDisplay, hourDisplayDetails);
        rLayout.addView(secDisplay, secDisplayDetails);
        setContentView(rLayout);

    }

    public RelativeLayout.LayoutParams wrapContent(){
        return  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
}
