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

public class ChatFragment extends Fragment {

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View chatView = inflater.inflate(R.layout.fragment_chat, container, false);

        webView = (WebView) chatView.findViewById(R.id.web_chat);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://server.wwsu1069.org/listen/chat?device=" + MainActivity.device());
        //Toast.makeText(getActivity(), "Device ID: " + MainActivity.device, Toast.LENGTH_SHORT).show();

        return chatView;
    }

}
