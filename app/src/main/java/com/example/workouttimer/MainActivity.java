package com.example.workouttimer;
//Auth Chanakijkamjorn 218033849 SIT305 Task4.1P
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
    //Declare variables
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
        //Match IDs
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


        //Set button
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get input
                String inputtime = input.getText().toString();
                //Convert to long
                long timeinput = Long.parseLong(inputtime) * 60000;
                //Call settime function
                settime(timeinput);
                //Get input
                inputnumber = input.getText().toString();
                //Convert to int
                Max = Integer.parseInt(inputnumber) * 60;
                //Set max for progress bar
                progressBar.setMax(Max);
                //Set current progress
                progressBar.setProgress(i);
                //Clear edittext
                input.setText("");
            }
        });
        //Set button for rest phase
        restset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get input
                String inputresttime = restinput.getText().toString();
                //Convert to long
                long resttime = Long.parseLong(inputresttime) * 60000;
                //Call setresttime function
                setresttime(resttime);
                //Get input
                inputrest = restinput.getText().toString();
                //Convert to int
                max2 = Integer.parseInt(inputrest) * 60;
                //Set max for progress bar
                progressBar2.setMax(max2);
                //Set current progress
                progressBar2.setProgress(ii);
                //Clear edittext
                restinput.setText("");
            }
        });

        //Start/pause button
        startpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set condition
                if(running)
                {
                    //Call pausetimer
                    pausetimer();
                }
                else
                {
                    //Call starttimmer
                    starttimer();

                }
            }

        });
        //Start/pause button for rest phase
        reststartpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set condition
                if (restrunning){
                    //Call restpausetimer
                    restpausetimer();
                }
                else {
                    //Call reststarttimmer
                    reststarttimer();
                }
            }
        });
        //Reset button
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call resettimer
                resettimer();
            }
        });
        //Reset button for rest phase
        restreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call restresettimer
                restresettimer();
            }
        });


    }
    //Starttimer function
    private void starttimer()
    {
        //Make new countdowntimer and pass timeleft and countdown 1000 ms(1sec)
        countdown = new CountDownTimer(timeleft,1000) {
            @Override
            //Every 1sec
            public void onTick(long milliUtilFinised)
            {
                //set timeleft = milliUtilFinised
                timeleft = milliUtilFinised;
                //Call update function
                update();
                //Add 1 to int i
                i++;
                //Set progress bar
                progressBar.setProgress(i);
            }
            //When finish
            @Override
            public void onFinish() {
                //Set running to false
                running = false;
                //Change text on a button
                startpause.setText("Start");
                //Set to invisible
                startpause.setVisibility(View.INVISIBLE);
                //Set to visible
                reset.setVisibility(View.VISIBLE);
                //Set int i to 0
                i = 0;
                //Call reststarttimer function
                reststarttimer();
                //Call playsound function
                playsound();
            }
        }.start();
        //Set running to true
        running = true;
        //Change text on a button
        startpause.setText("Pause");
        //Set to invisible
        reset.setVisibility(View.INVISIBLE);
    }
    //Starttimer function for rest phase
    private void reststarttimer()
    {
        //Make new countdowntimer and pass resttimeleft and countdown 1000 ms(1sec)
        restcd = new CountDownTimer(resttimeleft,1000) {
            //Every 1sec
            @Override
            public void onTick(long restmilliUtilFinised) {
                //set timeleft = restmilliUtilFinised
                resttimeleft = restmilliUtilFinised;
                //Call restupdate function
                restupdate();
                //Add 1 to int i
                ii++;
                //Set progress bar
                progressBar2.setProgress(ii);
            }
            //When finish
            @Override
            public void onFinish() {
                //Set restrunning to false
                restrunning = false;
                //Change text on a button
                reststartpause.setText("Start");
                //Set to invisible
                reststartpause.setVisibility(View.INVISIBLE);
                //Set to visible
                restreset.setVisibility(View.VISIBLE);
                //Set int ii to 0
                ii = 0;
                //Call playsound function
                playsound();
            }
        }.start();
        //Set running to true
        restrunning = true;
        //Change text on a button
        reststartpause.setText("Pause");
        //Set to invisible
        restreset.setVisibility(View.INVISIBLE);
    }
    //pausetimer function
    private void pausetimer()
    {
        //Stop countdown
        countdown.cancel();
        //Set running to false
        running = false;
        //Change text on a button
        startpause.setText("start");
        //Set to visible
        reset.setVisibility(View.VISIBLE);

    }
    //pausetimer function for rest phase
    private void restpausetimer()
    {
        //Stop countdown
        restcd.cancel();
        //Set restrunning to false
        restrunning = false;
        //Change text on a button
        reststartpause.setText("start");
        //Set to visible
        restreset.setVisibility(View.VISIBLE);

    }
    //resettimer function
    private void resettimer()
    {
        //Set timeleft = starttime
        timeleft = Starttime;
        //Call update function
        update();
        //Set to invisible
        reset.setVisibility(View.INVISIBLE);
        //Set to visible
        startpause.setVisibility(View.VISIBLE);
        //Set int i = 0
        i = 0;
        //Set progressbar = i
        progressBar.setProgress(i);
    }
    //resettimer function for rest phase
    private void restresettimer()
    {
        //Set resttimeleft = reststarttime
        resttimeleft = reststarttime;
        //Call restupdate function
        restupdate();
        //Set to invisible
        restreset.setVisibility(View.INVISIBLE);
        //Set to visible
        reststartpause.setVisibility(View.VISIBLE);
        //Set int ii = 0
        ii = 0;
        //Set progressbar = ii
        progressBar2.setProgress(ii);
    }
    //update function
    private void update(){
        //Find a minute by divide timeleft/1000 with 60
        int min = (int) (timeleft / 1000) / 60;
        //Find a second by divide timeleft/1000 with 60 and get only number after dot
        // (sorry I don't know what it call in english)
        int sec = (int) (timeleft / 1000) % 60;
        //Set format and store in timeleftformat
        String timeleftformat = String.format(Locale.getDefault(),"%02d:%02d", min,sec);
        //Set text to timeleftformat
        textcountdown.setText(timeleftformat);

    }
    //update function for rest phase
    private void restupdate(){
        //Find a minute by divide timeleft/1000 with 60
        int restmin = (int) (resttimeleft / 1000) / 60;
        //Find a second by divide timeleft/1000 with 60 and get only number after dot
        // (sorry I don't know what it call in english)
        int restsec = (int) (resttimeleft / 1000) % 60;
        //Set format and store in resttimeleftformat
        String resttimeleftformat = String.format(Locale.getDefault(),"%02d:%02d", restmin,restsec);
        //Set text to timeleftformat
        resttextcd.setText(resttimeleftformat);

    }
    //settime function
    private void settime(long milliseconds)
    {
        //Set Starttime = milliseconds
        Starttime = milliseconds;
        //Call resettimer function
        resettimer();
    }
    //settime function for rest phase
    private void setresttime(long millisseconds)
    {
        //Set restStarttime = millisseconds
        reststarttime = millisseconds;
        //Call restresettimer function
        restresettimer();
    }
    //playsound function
    public void playsound()
    {
        //Match mediaPlayer with beep file
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.beep);
        //Play sound
        mediaPlayer.start();
    }


}