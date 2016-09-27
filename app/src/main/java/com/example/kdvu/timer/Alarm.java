package com.example.kdvu.timer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Alarm extends AppCompatActivity {

    private Button stopBtn;
    private boolean cancelled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout rLayout = new RelativeLayout(this);

        stopBtn = new Button(this);
        stopBtn.setText("Stop");

        stopBtn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        cancelled = true;
                        //Toast.makeText(Alarm.this, "sssss", Toast.LENGTH_SHORT).show();

                    }
                }
        );

        RelativeLayout.LayoutParams stopBtnDetails = wrapContent();

        stopBtnDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        stopBtnDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                MediaPlayer mPlayer = MediaPlayer.create(Alarm.this, R.raw.sound);
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                while(!cancelled){
                    mPlayer.start();
                }
                mPlayer.reset();
                mPlayer.release();
            }
        };

        Thread t = new Thread(r);
        t.start();
        rLayout.addView(stopBtn, stopBtnDetails);
        setContentView(rLayout);
    }

    public RelativeLayout.LayoutParams wrapContent(){
        return  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
}
