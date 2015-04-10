/*-----------------------------------------
Student name: Chris Loewer
Course: COSC 3403 - Software Engineering I
Assignment: Implementation
File name: AlarmScreen.java
Program's Purpose: Android alarm clock that requires
user to complete a scripture verse to turn off

Class Purpose: Activity that sounds an alarm and 
can wake up device from lock

Development Computer: Intel 4790k chipset
Operating System: Windows 8.1
Integrated Development Environment (IDE): Eclipse
Compiler: jdk 8.0_31
Program's Operational Status: Working
-----------------------------------------*/

package com.soft_eng.scripturealarm;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmScreen extends Activity {

    //TODO Wakelock to wake device up from sleep for alarm
    //TODO Override onPause, onResume to handle waking
    //TODO Media Player to play the sound
    // http://www.steventrigg.com/alarm-screen-wakelock-and-mediaplayer-create-an-alarm-clock-in-android-tutorial-part-7/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.alarm_screen);

        TextView preTV = (TextView)findViewById(R.id.pre_tv);
        TextView postTV = (TextView)findViewById(R.id.post_tv);
        final EditText userInputET = (EditText)findViewById(R.id.user_input_et);

        AlarmDBHelper dbHelper = new AlarmDBHelper(this);
        Scripture_t scripture = new Scripture_t();
        scripture = dbHelper.getRandomVerse();

        // split verse into array, select one word, combine words before and after selected word

        String[] slicedVerse = scripture.text.split("\\s"); //breaks verse into an array of strings

        int randomIndex = (int) (Math.random()*slicedVerse.length); //chooses a random index

//need to output verse with blank as a textbox
        String pretext = scripture.reference + "\n";
        String posttext ="";
        final String droppedWord= slicedVerse[randomIndex];
        for(int i=0; i <randomIndex; i++){
            pretext +=slicedVerse[i] + " ";
        }

        for(int i=randomIndex+1; i <slicedVerse.length; i++){
            posttext += slicedVerse[i] + " ";
        }

        preTV.setText(pretext);
        postTV.setText(posttext);

        final Context context = this;
        Toast.makeText(context, "Media Time", Toast.LENGTH_SHORT).show();
        final MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.alarmsound);

        try {
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
            mPlayer.setLooping(true);
            mPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button stopAlarmButton = (Button) findViewById(R.id.alarm_off_button);
        stopAlarmButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // Check user input
                String userInput = (String) userInputET.getText().toString();

                if(userInput.equals(droppedWord)) {

                    Toast.makeText(context, "Stopping Alarm", Toast.LENGTH_SHORT).show();
                    mPlayer.stop();
                    finish();
                }
                else{
                    Toast.makeText(context,"Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
