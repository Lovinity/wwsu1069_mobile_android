package com.wwsu1069fm.mobile;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //private DeviceIDGenerator didg;

    private Button connect;

    public static MediaPlayer mediaPlayer; // Media Player Object

    public static String url = "https://server.wwsu1069.org/stream"; // server url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectToRadioStationURL();
        activityChange();

        connect = (Button) findViewById(R.id.radio_station_connection_button);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Please wait while you connect to the server.", Toast.LENGTH_SHORT).show(); // you are connecting to the server
//                connectToRadioStationURL(v);
//                activityChange(v);
            }
        });
    }

    public void connectToRadioStationURL ()
    {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        boolean mediaDataSourceTest = false;
        boolean mediaPrepareTest = false;
        try {
            mediaPlayer.setDataSource(url);
            mediaDataSourceTest = true;

            try {
                mediaPlayer.prepare();
                mediaPrepareTest = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mediaDataSourceTest && mediaPrepareTest)
        {
            Toast.makeText(MainActivity.this, "Connected to Radio Station", Toast.LENGTH_SHORT).show(); // cannot connect to the radio station stream
            //Toast.makeText(MainActivity.this, "Device ID: " + device.toString(), Toast.LENGTH_SHORT).show();
            activityChange();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Cannot connect to Radio Station", Toast.LENGTH_SHORT).show(); // cannot connect to the radio station stream
            //Toast.makeText(MainActivity.this, "Device ID: " + device.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void activityChange() {

        Intent startActivity = new Intent(MainActivity.this, NavigationDrawer.class);
        startActivity(startActivity);

    }

    public static String device() {
        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
        if (status == null) return null;
        return status.getSubscriptionStatus().getUserId();
    }
}

