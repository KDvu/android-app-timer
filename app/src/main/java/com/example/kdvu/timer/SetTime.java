package com.example.kdvu.timer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;

public class SetTime extends AppCompatActivity {

    private static final String TAG = "com.example.kdvu.timer";

    private String hour = String.format("%02d", 0);
    private String min = String.format("%02d", 0);
    private String sec = String.format("%02d", 0);

    private int[][] times = new int[3][3];

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHandler = new MyDBHandler(this, null, null, 1);
        //Times t = new Times(1,2,3);
        //Times t2 = new Times(3,2,2);
        //dbHandler.addNewTime(t);
        //dbHandler.addNewTime(t2);
        //dbHandler.clearTimes();
        //dbHandler.insertTimes(times);
        getUsedTimes();
        //Layout
        RelativeLayout rLayout = new RelativeLayout(this);

        //Button
        Button setTimeButton = new Button(this);
        setTimeButton.setText("Set Time");
        setTimeButton.setId(R.id.setTimeButton);

        Button clearBtn = new Button(this);
        clearBtn.setText("clear");
        clearBtn.setId(R.id.clearBtn);

        //Items for ListViews + list adapators
        String[] hourList = new String[24];
        String[] minList = new String[60];
        String[] secList = new String[60];

        fillArray(hourList); //From 0 to 23
        fillArray(minList); //From 0 to 59
        fillArray(secList); //From 0 to 59

        ListAdapter listA1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hourList);
        ListAdapter listA2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, minList);
        ListAdapter listA3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, secList);

        //ListViews + setting adapters + ids
        ListView hourView = new ListView(this);
        ListView minView = new ListView(this);
        ListView secView = new ListView(this);

        hourView.setAdapter(listA1);
        minView.setAdapter(listA2);
        secView.setAdapter(listA3);

        hourView.setId(R.id.hourView);
        minView.setId(R.id.minView);
        secView.setId(R.id.secView);

        //TextViews
        TextView topHeading = new TextView(this);
        TextView hourHeading = new TextView(this);
        TextView minHeading = new TextView(this);
        TextView secHeading = new TextView(this);

        final TextView displayHour = new TextView(this);
        final TextView displayMin = new TextView(this);
        final TextView displaySec = new TextView(this);

        final TextView usedTime1 = new TextView(this);
        final TextView usedTime2 = new TextView(this);
        final TextView usedTime3 = new TextView(this);

        //Set TextView ids
        topHeading.setId(R.id.topHeading);
        hourHeading.setId(R.id.hourHeading);
        minHeading.setId(R.id.minHeading);
        secHeading.setId(R.id.secHeading);

        displayHour.setId(R.id.displayHour);
        displayMin.setId(R.id.displayMin);
        displaySec.setId(R.id.displaySec);

        usedTime1.setId(R.id.usedTime1);
        usedTime2.setId(R.id.usedTime2);
        usedTime3.setId(R.id.usedTime3);

        //Set TextView text
        topHeading.setText("Select Time");
        topHeading.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
        hourHeading.setText("Hour");
        minHeading.setText("Min");
        secHeading.setText("Sec");

        printTime(displayHour, displayMin, displaySec);

        displayHour.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        displayMin.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        displaySec.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);

        printUsedTimes(usedTime1, usedTime2, usedTime3);  //Print the previously used times receieved from the database
        usedTime1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        usedTime2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        usedTime3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

        //Set size of widgets
        RelativeLayout.LayoutParams setTimeButtonDetails = wrapContent();
        RelativeLayout.LayoutParams clearBtnDetails = wrapContent();
        RelativeLayout.LayoutParams topHeadingDetails = wrapContent();
        RelativeLayout.LayoutParams hourHeadingDetails = wrapContent();
        RelativeLayout.LayoutParams minHeadingDetails = wrapContent();
        RelativeLayout.LayoutParams secHeadingDetails = wrapContent();
        RelativeLayout.LayoutParams displayHourDetails = wrapContent();
        RelativeLayout.LayoutParams displayMinDetails = wrapContent();
        RelativeLayout.LayoutParams displaySecDetails = wrapContent();
        RelativeLayout.LayoutParams usedTime1Details = wrapContent();
        RelativeLayout.LayoutParams usedTime2Details = wrapContent();
        RelativeLayout.LayoutParams usedTime3Details = wrapContent();
        RelativeLayout.LayoutParams hourViewDetails = new RelativeLayout.LayoutParams(100, 200);
        RelativeLayout.LayoutParams minViewDetails = new RelativeLayout.LayoutParams(100, 200);
        RelativeLayout.LayoutParams secViewDetails = new RelativeLayout.LayoutParams(100, 200);

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

        minViewDetails.addRule(RelativeLayout.BELOW, hourHeading.getId());
        minViewDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        minViewDetails.setMargins(50,0,50,30);

        hourViewDetails.addRule(RelativeLayout.BELOW, hourHeading.getId());
        hourViewDetails.addRule(RelativeLayout.LEFT_OF, minView.getId());
        hourViewDetails.setMargins(0,0,0,30);

        secViewDetails.addRule(RelativeLayout.BELOW, secHeading.getId());
        secViewDetails.addRule(RelativeLayout.RIGHT_OF, minView.getId());
        secViewDetails.setMargins(0,0,0,30);

        displayMinDetails.addRule(RelativeLayout.BELOW, minView.getId());
        displayMinDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);

        displayHourDetails.addRule(RelativeLayout.BELOW, hourView.getId());
        displayHourDetails.addRule(RelativeLayout.LEFT_OF, displayMin.getId());

        displaySecDetails.addRule(RelativeLayout.BELOW, secView.getId());
        displaySecDetails.addRule(RelativeLayout.RIGHT_OF, displayMin.getId());

        setTimeButtonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        setTimeButtonDetails.addRule(RelativeLayout.BELOW, displayMin.getId());
        setTimeButtonDetails.setMargins(0,20,0,10);

        usedTime1Details.addRule(RelativeLayout.BELOW, setTimeButton.getId());
        usedTime1Details.addRule(RelativeLayout.CENTER_HORIZONTAL);
        usedTime1Details.setMargins(0,0,0,0);

        usedTime2Details.addRule(RelativeLayout.BELOW, usedTime1.getId());
        usedTime2Details.addRule(RelativeLayout.CENTER_HORIZONTAL);
        usedTime2Details.setMargins(0,5,0,5);

        usedTime3Details.addRule(RelativeLayout.BELOW, usedTime2.getId());
        usedTime3Details.addRule(RelativeLayout.CENTER_HORIZONTAL);
        usedTime3Details.setMargins(0,0,0,0);

        clearBtnDetails.addRule(RelativeLayout.BELOW, usedTime3.getId());
        clearBtnDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        clearBtnDetails.setMargins(0,20,0,0);

        //Event Handlers
        hourView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        hour = String.valueOf(parent.getItemAtPosition(position));
                        hour = String.format("%02d", Integer.parseInt(hour));
                        displayHour.setText(hour + " :");
                    }
                }
        );

        minView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        min = String.valueOf(parent.getItemAtPosition(position));
                        min = String.format("%02d", Integer.parseInt(min));
                        displayMin.setText(" " + min + " :");
                    }
                }
        );

        secView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        sec = String.valueOf(parent.getItemAtPosition(position));
                        sec = String.format("%02d", Integer.parseInt(sec));
                        displaySec.setText(" " + sec);
                    }
                }
        );

        setTimeButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        if(hour.equals("00") && min.equals("00") && sec.equals("00"))
                            Toast.makeText(SetTime.this, "You have not set a time", Toast.LENGTH_SHORT).show();
                        else{
                            dbHandler.addNewTime(new Times(strToInt(hour),strToInt(min),strToInt(sec)));
                            getUsedTimes();
                            printUsedTimes(usedTime1,usedTime2,usedTime3);
                            Intent i = new Intent(SetTime.this, Timer.class);
                            i.putExtra("hour", hour);
                            i.putExtra("min", min);
                            i.putExtra("sec", sec);
                            startActivity(i);
                        }
                    }
                }
        );

        clearBtn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        dbHandler.clearTimes();
                        emptyArray(times);
                        printUsedTimes(usedTime1,usedTime2,usedTime3);
                    }
                }
        );

        usedTime1.setOnClickListener(
                new TextView.OnClickListener(){
                    public void onClick(View v){
                        changeTimeDisplayed(0);
                    }
                }
        );

        usedTime2.setOnClickListener(
                new TextView.OnClickListener(){
                    public void onClick(View v){
                        changeTimeDisplayed(1);
                    }
                }
        );

        usedTime3.setOnClickListener(
                new TextView.OnClickListener(){
                    public void onClick(View v){
                        changeTimeDisplayed(2);
                    }
                }
        );

        //Add views
        rLayout.addView(topHeading, topHeadingDetails);
        rLayout.addView(minHeading, minHeadingDetails);
        rLayout.addView(hourHeading, hourHeadingDetails);
        rLayout.addView(secHeading, secHeadingDetails);

        rLayout.addView(minView, minViewDetails);
        rLayout.addView(hourView, hourViewDetails);
        rLayout.addView(secView, secViewDetails);

        rLayout.addView(displayMin, displayMinDetails);
        rLayout.addView(displayHour, displayHourDetails);
        rLayout.addView(displaySec, displaySecDetails);

        rLayout.addView(setTimeButton, setTimeButtonDetails);

        rLayout.addView(usedTime1, usedTime1Details);
        rLayout.addView(usedTime2, usedTime2Details);
        rLayout.addView(usedTime3, usedTime3Details);

        rLayout.addView(clearBtn, clearBtnDetails);

        setContentView(rLayout);
    }

    //***************DATABASE METHODS***********
    public void getUsedTimes(){
        dbHandler.insertTimes(times);
    }

    public void printUsedTimes(TextView v1, TextView v2, TextView v3){
        v1.setText(String.format("%02d",times[0][0]) + ":" + String.format("%02d",times[0][1]) + ":" + String.format("%02d",times[0][2]));
        v2.setText(String.format("%02d",times[1][0]) + ":" + String.format("%02d",times[1][1]) + ":" + String.format("%02d",times[1][2]));
        v3.setText(String.format("%02d",times[2][0]) + ":" + String.format("%02d",times[2][1]) + ":" + String.format("%02d",times[2][2]));
    }

    //***************UTILITY METHODS***********

    public void fillArray(String[] array){
        for(int i = 0; i < array.length; i++){
            array[i] = String.valueOf(i);
        }
    }

    public void emptyArray(int[][] array){
        for(int i = 0; i < array.length; i++){
            Arrays.fill(array[i], 0);
        }
    }

    public void printTime(TextView h, TextView m, TextView s){
        h.setText(hour + " : ");
        m.setText(min + " :");
        s.setText(" " + sec);
    }

    public void changeTimeDisplayed(int row){
        TextView h = (TextView) findViewById(R.id.displayHour);
        TextView m = (TextView) findViewById(R.id.displayMin);
        TextView s = (TextView) findViewById(R.id.displaySec);

        hour = String.format("%02d", times[row][0]);
        min = String.format("%02d", times[row][1]);
        sec = String.format("%02d", times[row][2]);

        printTime(h,m,s);
    }

    public RelativeLayout.LayoutParams wrapContent(){
        return  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public int strToInt(String value){
        return Integer.parseInt(value);
    }
}
