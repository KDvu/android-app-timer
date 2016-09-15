package com.example.kdvu.timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Layout
        RelativeLayout rLayout = new RelativeLayout(this);

        //TextViews
        TextView topHeading = new TextView(this);
        TextView hourHeading = new TextView(this);
        TextView minHeading = new TextView(this);
        TextView secHeading = new TextView(this);

        //Set TextView text
        topHeading.setText("Select Time");
        topHeading.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
        hourHeading.setText("Hour");
        minHeading.setText("Min");
        secHeading.setText("Sec");

        //Set TextView ids
        topHeading.setId(R.id.topHeading);
        hourHeading.setId(R.id.hourHeading);
        minHeading.setId(R.id.minHeading);
        secHeading.setId(R.id.secHeading);

        //Set size of TextView
        RelativeLayout.LayoutParams topHeadingDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams hourHeadingDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams minHeadingDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams secHeadingDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        //Create rules for positioning
        topHeadingDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        topHeadingDetails.setMargins(10,10,10,10);

        minHeadingDetails.addRule(RelativeLayout.BELOW, topHeading.getId());
        minHeadingDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        minHeadingDetails.setMargins(100,0,100,10);

        hourHeadingDetails.addRule(RelativeLayout.BELOW, topHeading.getId());
        hourHeadingDetails.addRule(RelativeLayout.LEFT_OF, minHeading.getId());
        hourHeadingDetails.setMargins(0,0,0,20);

        secHeadingDetails.addRule(RelativeLayout.BELOW, topHeading.getId());
        secHeadingDetails.addRule(RelativeLayout.RIGHT_OF, minHeading.getId());
        secHeadingDetails.setMargins(0,0,0,20);

        rLayout.addView(topHeading, topHeadingDetails);
        rLayout.addView(minHeading, minHeadingDetails);
        rLayout.addView(hourHeading, hourHeadingDetails);
        rLayout.addView(secHeading, secHeadingDetails);

        setContentView(rLayout);
    }
}
