package com.wwsu1069fm.mobile;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class RadioFragment extends Fragment {

    //private WebView webView;



    // The three buttons used in this current build.
    private Button play; // Play music
    private Button stop; // Pause music stream

    // TextViews for Meta Data
    private TextView dataJSONTextView;

    private String metaDataURL = "https://server.wwsu1069.org/meta/get";

    //DeviceIDGenerator deviceID;

    Timer timer = new Timer();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View radioView = inflater.inflate(R.layout.fragment_radio, container, false);

        //webView = (WebView) radioView.findViewById(R.id.web_radio);
        //WebSettings webSettings = webView.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        //webView.setWebViewClient(new WebViewClient());
        //webView.loadUrl("https://server.wwsu1069.org/listen/listen");

        dataJSONTextView = (TextView) radioView.findViewById(R.id.dataJSONDisplay);

        play = (Button) radioView.findViewById(R.id.playRadioButton);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRadio(v);
            }
        });

        stop = (Button) radioView.findViewById(R.id.stopRadioButton);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRadio(v);
            }
        });

        // Timer set to repeat and call the meta data to keep everything updated
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                new JSONTask().execute("https://server.wwsu1069.org/meta/get");
            }
        },0, 1000);

        return radioView;
    }

    private void playRadio(View v) {
        //boolean update = true;
        MainActivity.mediaPlayer.start(); // starting music playing
        //new JSONTask().execute("https://server.wwsu1069.org/meta/get");
        if (MainActivity.mediaPlayer.isPlaying())
        {
            //Toast.makeText(getActivity(), "Radio is Online", Toast.LENGTH_SHORT).show(); // music is playing
            //Toast.makeText(getActivity(), "Device ID: " + deviceID.generateDevice().toString(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Toast.makeText(getActivity(), "Radio is Offline", Toast.LENGTH_SHORT).show();// music is not playing
            //Toast.makeText(getActivity(), "Device ID: " + MainActivity.device, Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRadio(View v) {
        MainActivity.mediaPlayer.reset();
        MainActivity.mediaPlayer.release();

        MainActivity.mediaPlayer = new MediaPlayer();
        MainActivity.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            MainActivity.mediaPlayer.setDataSource(MainActivity.url);

            try
            {
                MainActivity.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //Toast.makeText(getActivity(), "MediaPlayer is ready, you can listen to the station again!", Toast.LENGTH_SHORT).show(); // music is ready to be played
                        //Toast.makeText(getActivity(), "Device ID: " + MainActivity.device, Toast.LENGTH_SHORT).show();
                    }
                });
                MainActivity.mediaPlayer.prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            // MetaData Server Connection and JSON Data Section
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line);
                }

                String finalJSON = stringBuffer.toString();

                JSONObject line1JSONObject = new JSONObject(finalJSON);
                JSONObject line2JSONObject = new JSONObject(finalJSON);
                String line1 = line1JSONObject.getString("line1");
                String line2 = line2JSONObject.getString("line2");

                return line1 + "\n" + line2;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null){
                    httpURLConnection.disconnect();
                }

                try {
                    if (bufferedReader != null){
                        bufferedReader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dataJSONTextView.setText(result);
        }
    }
}