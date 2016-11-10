package com.novoda.tpbot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.novoda.tpbot.socket.io.SocketIOTpService;

public class TpBotActivity extends AppCompatActivity implements ConnectionView {

    private View humanSelection;
    private View botSelection;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp_bot);

        humanSelection = (TextView) findViewById(R.id.human_selection);
        botSelection = (TextView) findViewById(R.id.bot_selection);

        presenter = new Presenter(SocketIOTpService.getInstance(), this);

        humanSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To human controller activity.
            }
        });

        botSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To bot activity.
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopPresenting();
    }

    @Override
    public void onConnect() {
        Toast.makeText(TpBotActivity.this, "User connected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionError(String code) {
        Toast.makeText(TpBotActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisconnect() {
        Toast.makeText(TpBotActivity.this, "User disconnected", Toast.LENGTH_LONG).show();
    }
}
