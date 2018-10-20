package com.example.excalibur.httpurlconnection;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

/**
 * Created by excalibur on 2/10/2018.
 */

public class ProgressFragment extends Fragment {

    WebView webView;
    TextView textView;
    String data = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.progress_fragment, container, false);
        textView = fragment.findViewById(R.id.text);
        webView = fragment.findViewById(R.id.web_interface);
        if(data != null){
            textView.setText(data);
            webView.loadData(data, "text/html; charset=utf-8", "utf-8");
        }
        Button connect = fragment.findViewById(R.id.connect_button);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data == null){
                    textView.setText("loading...");
                    new ProgressTask().execute("https://developer.android.com/index.html");
                }
            }
        });
        return fragment;
    }

    class ProgressTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... path) {
            try{
                return getContent(path[0]);
            }
            catch (IOException ex){
                return ex.getCause().toString();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            data = s;
            textView.setText(data);
            webView.loadData(data, "text/html; charset=utf-8", "utf-8");
            Toast.makeText(getActivity(), "Loaded successfully!", Toast.LENGTH_SHORT).show();
        }

        private String getContent(String path) throws IOException{
            BufferedReader reader = null;
            try {
                URL url = new URL(path);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                connection.connect();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }
                return stringBuilder.toString();
            }
            finally {
                if(reader != null){
                    reader.close();
                }
            }
        }
    }
}
