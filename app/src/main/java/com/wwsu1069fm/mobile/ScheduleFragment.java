package com.wwsu1069fm.mobile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ScheduleFragment extends Fragment {

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View scheduleView = inflater.inflate(R.layout.fragment_schedule, container, false);

        webView = (WebView) scheduleView.findViewById(R.id.web_schedule);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://server.wwsu1069.org/listen/calendar?device=" + MainActivity.device());
        //Toast.makeText(getActivity(), "Device ID: " + MainActivity.device, Toast.LENGTH_SHORT).show();

        return scheduleView;
    }

}
