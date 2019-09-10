package com.wwsu1069fm.mobile;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.URI;

public class MyService extends Service implements MediaPlayer.OnPreparedListener
{
    private static MediaPlayer serviceMediaPlayer; // Media Player Object

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        serviceMediaPlayer = MainActivity.mediaPlayer;
        try {
            serviceMediaPlayer.setDataSource(MainActivity.url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainActivity.mediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        serviceMediaPlayer.stop();
    }
}

