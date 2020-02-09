package com.kangjusang.musicismylife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        final Long startAt = System.currentTimeMillis();
        final Long lasfFor = 2000L;

        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Long beenFor = System.currentTimeMillis() - startAt;
                if ( beenFor > lasfFor) {
                    timer.cancel();
                    timer.purge();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0,300L);
    }
}
