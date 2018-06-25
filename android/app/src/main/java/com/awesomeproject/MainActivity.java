package com.awesomeproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.react.ReactActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final String GIT_LOCATION = "https://raw.githubusercontent.com/yoonghan/ReactNative-Modular/master/index.android.bundle?t=";
    private final String FILE_NAME = "index.android.bundle";
    private AppCompatActivity parent = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        addClick();
    }

    private void addClick() {
        Button clickButton = (Button) findViewById(R.id.button);
        Button clickButton2 = (Button) findViewById(R.id.button2);

        final AppCompatActivity self = this;
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, Main3Activity.class);
                startActivity(intent);
            }
        });

        clickButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFileFromURL().execute("");
            }
        });
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            InputStream input = null;
            FileOutputStream output = null;
            HttpURLConnection connection = null;
            try {
                long rand = System.currentTimeMillis();
                URL url = new URL(GIT_LOCATION + rand);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.i("Download", "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage());
                    return "";
                }
                else {
                    Log.i("Download", "Downloading");
                }

                // download the file
                input = connection.getInputStream();
                File jsCodeDir = parent.getDir("JSCode", Context.MODE_PRIVATE);
                if (!jsCodeDir.exists()) {
                    jsCodeDir.mkdirs();
                }
                File jsCodeFile = new File(jsCodeDir, FILE_NAME);
                output = new FileOutputStream(jsCodeFile);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                Log.i("Download", "Completed");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Download", "Fail");
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return "";
        }



        /**
         * After completing background task
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            System.out.println("Downloaded");
        }

    }
}


