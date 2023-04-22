package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    long Starttime;
    EditText input;
    TextView textcountdown;
    CountDownTimer countdown;
    Button startpause;
    Button reset;
    Button set;
    boolean running;
    long timeleft = Starttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textcountdown = findViewById(R.id.textcountdown);
        startpause = findViewById(R.id.startpausebutton);
        reset = findViewById(R.id.resetbutton);
        input = findViewById(R.id.inputtime);
        set = findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputtime = input.getText().toString();
                long timeinput = Long.parseLong(inputtime) * 60000;
                settime(timeinput);
                input.setText("");
            }
        });



        startpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running)
                {
                    pausetimer();
                }
                else
                {
                    starttimer();
                }
            }

        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettimer();
            }
        });
        update();
    }
    private void starttimer()
    {
        countdown = new CountDownTimer(timeleft,1000) {
            @Override
            public void onTick(long milliUtilFinised) {
                timeleft = milliUtilFinised;
                update();
            }

            @Override
            public void onFinish() {
                running = false;
                startpause.setText("Start");
                startpause.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.VISIBLE);

            }
        }.start();
        running = true;
        startpause.setText("Pause");
        reset.setVisibility(View.INVISIBLE);


    }
    private void pausetimer()
    {
        countdown.cancel();
        running = false;
        startpause.setText("start");
        reset.setVisibility(View.VISIBLE);

    }
    private void resettimer()
    {
        timeleft = Starttime;
        update();
        reset.setVisibility(View.INVISIBLE);
        startpause.setVisibility(View.VISIBLE);
    }
    private void update(){
        int min = (int) (timeleft / 1000) / 60;
        int sec = (int) (timeleft / 1000) % 60;

        String timeleftformat = String.format(Locale.getDefault(),"%02d:%02d", min,sec);

        textcountdown.setText(timeleftformat);

    }
    private void settime(long milliseconds)
    {
        Starttime = milliseconds;
        resettimer();
    }
}