package com.example.kdvu.timer;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.TypedValue;
import android.util.Log;

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
    private int a = 60;

    private int currentHour;
    private int currentMin;
    private int currentSec;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            TextView sec = (TextView) findViewById(R.id.secDisplay);
            TextView min = (TextView) findViewById(R.id.minDisplay);
            TextView hour = (TextView) findViewById(R.id.hourDisplay);

            if(currentSec != 0){
                currentSec--;
                sec.setText(String.format("%02d",currentSec));
            } else if (currentSec == 0){
                if (currentMin != 0){
                    currentSec = 59;
                    sec.setText(String.format("%02d",currentSec));

                    currentMin--;
                    min.setText(String.format("%02d",currentMin) + " : ");
                } else if (currentMin == 0 && currentHour != 0) {
                        currentSec = 59;
                        sec.setText(String.format("%02d",currentSec));

                        currentMin = 59;
                        min.setText(String.format("%02d",currentMin) + " : ");

                        currentHour--;
                        hour.setText(String.format("%02d",currentHour) + " : ");
                } else {
                    sec.setText(String.format("%02d",0));
                    mFinished = true;
                    MediaPlayer mPlayer = MediaPlayer.create(Timer.this, R.raw.sound);
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mPlayer.start();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle timerData = getIntent().getExtras();
        if (timerData == null){
            return;
        }

        final String hour = timerData.getString("hour");
        final String min = timerData.getString("min");
        final String sec = timerData.getString("sec");

        currentHour = Integer.parseInt(hour);
        currentMin = Integer.parseInt(min);
        currentSec = Integer.parseInt(sec);

        //Layout
        RelativeLayout rLayout = new RelativeLayout(this);

        //TextViews
        final TextView hourDisplay = new TextView(this);
        final TextView minDisplay = new TextView(this);
        final TextView secDisplay = new TextView(this);

        hourDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        minDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        secDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);

        resetTimer(hour,min,sec,hourDisplay,minDisplay,secDisplay);

        hourDisplay.setId(R.id.hourDisplay);
        minDisplay.setId(R.id.minDisplay);
        secDisplay.setId(R.id.secDisplay);

        //Buttons
        final Button startBtn = new Button(this);
        final Button restartBtn = new Button(this);

        startBtn.setBackgroundResource(android.R.drawable.btn_default);
        restartBtn.setBackgroundResource(android.R.drawable.btn_default);

        startBtn.setId(R.id.startBtn);
        restartBtn.setId(R.id.restartBtn);

        startBtn.setText("Start");
        restartBtn.setText("Reset");

        //Event Handlers
        startBtn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        changeButtonText(startBtn, "Pause");
                        startTimer(startBtn);
                    }
                }
        );

        restartBtn.setOnClickListener(
           new Button.OnClickListener(){
               public void onClick(View v){
                   //Pauses the timer if its still running
                   changeButtonText(startBtn, "Start");
                   startBtn.setBackgroundResource(android.R.drawable.btn_default);
                   synchronized (mPauseLock){
                       mPaused = true;
                   }
                   resetTimer(hour,min,sec,hourDisplay,minDisplay,secDisplay);
               }
           }
        );;

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

    public void changeButtonText(Button button, String text){
        button.setText(text);
    }

    public void startTimer(Button button){
        if(!started){
            started = true;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    while(!mFinished){
                        long oneSec = System.currentTimeMillis() + 1000;
                        while(System.currentTimeMillis() < oneSec) {
                            try {
                                wait(1000);
                            } catch (Exception e) {
                            }
                            synchronized (mPauseLock) {
                                while (mPaused) {
                                    try {
                                        mPauseLock.wait();
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }
                        }
                        handler.sendEmptyMessage(0);
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();
        } else if (!mPaused){
            changeButtonText(button, "Resume");

            synchronized (mPauseLock){
                mPaused = true;
            }
        } else if (mPaused){
            changeButtonText(button, "Pause");

            synchronized (mPauseLock){
                mPaused = false;
                mPauseLock.notifyAll();
            }
        }
    }

    public void resetTimer(String hour, String min, String sec, TextView hourDisplay, TextView minDisplay, TextView secDisplay){
        currentHour = Integer.parseInt(hour);
        currentMin = Integer.parseInt(min);
        currentSec = Integer.parseInt(sec);
        hourDisplay.setText(hour + " : ");
        minDisplay.setText(min + " : ");
        secDisplay.setText(sec);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(Menu.NONE, 1, Menu.NONE, "item name");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish(); //Close activity
                return true;
            case 1:
                return true;
            default:
                return false;
        }
    }
}
