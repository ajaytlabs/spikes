package com.novoda.tpbot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TpBotActivity extends AppCompatActivity {

    private Thread server;
    private ServerThread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp_bot);

        final TextView responseText = (TextView) findViewById(R.id.response);

        serverThread = new ServerThread(new ResponseListener() {

            @Override
            public void onResponseReceived(Response response) {
                String text;
                if (response.response().isPresent()) {
                    text = getResources().getString(R.string.response, response.response());
                } else {
                    text = response.exception().get().getMessage();
                }

                responseText.setText(text);
            }

        });
        this.server = new Thread(serverThread);
        this.server.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (serverThread != null) {
            serverThread.stop();
        }
    }
}
