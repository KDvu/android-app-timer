package com.example.kdvu.timer;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.TypedValue;

import org.w3c.dom.Text;

public class Timer extends AppCompatActivity {
/*
    private TextView hourDisplay;
    private TextView minDisplay;
    private TextView secDisplay;

    private Button startBtn = new Button(this);
    private Button restartBtn = new Button(this);
*/
    private Object mPauseLock = new Object();
    private boolean mPaused = false;
    private boolean mFinished = false;
    private boolean started = false;
    private int a = 0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            TextView sec = (TextView) findViewById(R.id.secDisplay);
            a++;
            sec.setText(String.valueOf(a));
        }
    };

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

        String current_hour = hour;
        String current_min = min;
        String current_sec = sec;

        //Layout
        RelativeLayout rLayout = new RelativeLayout(this);

        //TextViews
        final TextView hourDisplay = new TextView(this);
        final TextView minDisplay = new TextView(this);
        final TextView secDisplay = new TextView(this);

        hourDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        minDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        secDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);

        hourDisplay.setText(current_hour + " :");
        minDisplay.setText(" " + current_min + " : ");
        secDisplay.setText(current_sec);

        hourDisplay.setId(R.id.hourDisplay);
        minDisplay.setId(R.id.minDisplay);
        secDisplay.setId(R.id.secDisplay);

        //Buttons
        final Button startBtn = new Button(this);
        final Button restartBtn = new Button(this);

        startBtn.setId(R.id.startBtn);
        restartBtn.setId(R.id.restartBtn);

        startBtn.setText("Start");
        restartBtn.setText("Reset");

        //Event Handlers
        startBtn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        startTimer();
                    }
                }
        );

        //Positioning
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

    public void startTimer(){
        //minDisplay.setText("a");
        TextView hour = (TextView) findViewById(R.id.hourDisplay);
        TextView min = (TextView) findViewById(R.id.minDisplay);
        TextView sec = (TextView) findViewById(R.id.secDisplay);

        Button startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setText("Pause");
        startBtn.setBackgroundColor(Color.YELLOW);

        if(!started){
            started = true;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    while(!mFinished){
                        try{
                            wait(1000);
                        } catch (Exception e){}
                        handler.sendEmptyMessage(0);
                        synchronized (mPauseLock){
                            while (mPaused){
                                try{
                                    mPauseLock.wait();
                                } catch(InterruptedException e){}
                            }
                        }
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();
        } else if (!mPaused){
            synchronized (mPauseLock){
                mPaused = true;
            }
        } else if (mPaused){
            synchronized (mPauseLock){
                mPaused = false;
                mPauseLock.notifyAll();
            }
        }
    }
}
