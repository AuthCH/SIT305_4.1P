package com.example.workouttimer;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    long Starttime, reststarttime;

    EditText input,restinput;
    TextView textcountdown,resttextcd;
    CountDownTimer countdown,restcd;
    Button startpause,reststartpause;
    Button reset,restreset;
    Button set,restset;
    boolean running,restrunning;
    long timeleft = Starttime;
    long resttimeleft = reststarttime;
    ProgressBar progressBar,progressBar2;
    String inputnumber,inputrest;
    int Max,max2;
    int i = 0,ii = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textcountdown = findViewById(R.id.textcountdown);
        startpause = findViewById(R.id.startpausebutton);
        reset = findViewById(R.id.resetbutton);
        input = findViewById(R.id.inputtime);
        set = findViewById(R.id.set);
        progressBar = findViewById(R.id.progressBar2);

        resttextcd = findViewById(R.id.textView4);
        reststartpause = findViewById(R.id.button2);
        restreset = findViewById(R.id.button3);
        restinput = findViewById(R.id.editText2);
        restset = findViewById(R.id.button);
        progressBar2 = findViewById(R.id.progressBar);



        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputtime = input.getText().toString();
                long timeinput = Long.parseLong(inputtime) * 60000;
                settime(timeinput);

                inputnumber = input.getText().toString();
                Max = Integer.parseInt(inputnumber) * 60;
                progressBar.setMax(Max);
                progressBar.setProgress(i);
                input.setText("");
            }
        });
        restset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputresttime = restinput.getText().toString();
                long resttime = Long.parseLong(inputresttime) * 60000;
                setresttime(resttime);

                inputrest = restinput.getText().toString();
                max2 = Integer.parseInt(inputrest) * 60;
                progressBar2.setMax(max2);
                progressBar2.setProgress(ii);
                restinput.setText("");
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
        reststartpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restrunning){
                    restpausetimer();
                }
                else {
                    reststarttimer();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettimer();
            }
        });
        restreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restresettimer();
            }
        });
        restupdate();

    }
    private void starttimer()
    {
        countdown = new CountDownTimer(timeleft,1000) {
            @Override
            public void onTick(long milliUtilFinised) {

                timeleft = milliUtilFinised;
                update();
                i++;
                progressBar.setProgress(i);
            }

            @Override
            public void onFinish() {
                running = false;
                startpause.setText("Start");
                startpause.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.VISIBLE);
                i = 0;
                reststarttimer();
                playsound();
            }
        }.start();
        running = true;
        startpause.setText("Pause");
        reset.setVisibility(View.INVISIBLE);
    }
    private void reststarttimer()
    {
        restcd = new CountDownTimer(resttimeleft,1000) {
            @Override
            public void onTick(long restmilliUtilFinised) {

                resttimeleft = restmilliUtilFinised;
                restupdate();
                ii++;
                progressBar2.setProgress(ii);
            }

            @Override
            public void onFinish() {
                restrunning = false;
                reststartpause.setText("Start");
                reststartpause.setVisibility(View.INVISIBLE);
                restreset.setVisibility(View.VISIBLE);
                ii = 0;
                playsound();
            }
        }.start();
        restrunning = true;
        reststartpause.setText("Pause");
        restreset.setVisibility(View.INVISIBLE);
    }
    private void pausetimer()
    {
        restcd.cancel();
        running = false;
        startpause.setText("start");
        reset.setVisibility(View.VISIBLE);

    }
    private void restpausetimer()
    {
        restcd.cancel();
        restrunning = false;
        reststartpause.setText("start");
        restreset.setVisibility(View.VISIBLE);

    }
    private void resettimer()
    {
        timeleft = Starttime;
        update();
        reset.setVisibility(View.INVISIBLE);
        startpause.setVisibility(View.VISIBLE);
        i = 0;
        progressBar.setProgress(i);
    }
    private void restresettimer()
    {
        resttimeleft = reststarttime;
        restupdate();
        restreset.setVisibility(View.INVISIBLE);
        reststartpause.setVisibility(View.VISIBLE);
        ii = 0;
        progressBar2.setProgress(ii);
    }
    private void update(){
        int min = (int) (timeleft / 1000) / 60;
        int sec = (int) (timeleft / 1000) % 60;

        String timeleftformat = String.format(Locale.getDefault(),"%02d:%02d", min,sec);

        textcountdown.setText(timeleftformat);

    }
    private void restupdate(){
        int restmin = (int) (resttimeleft / 1000) / 60;
        int restsec = (int) (resttimeleft / 1000) % 60;

        String resttimeleftformat = String.format(Locale.getDefault(),"%02d:%02d", restmin,restsec);

        resttextcd.setText(resttimeleftformat);

    }
    private void settime(long milliseconds)
    {
        Starttime = milliseconds;
        resettimer();
    }
    private void setresttime(long millisseconds)
    {
        reststarttime = millisseconds;
        restresettimer();
    }
    public void playsound()
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.beep);
        mediaPlayer.start();
    }


}