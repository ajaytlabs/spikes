package com.novoda.tpbot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.novoda.tpbot.socket.io.SocketIOTpService;

public class TpBotActivity extends AppCompatActivity implements ConnectionView {

    private EditText usernameEntry;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp_bot);

        usernameEntry = (EditText) findViewById(R.id.username_entry);
        TextView connectButton = (TextView) findViewById(R.id.connect_button);

        presenter = new Presenter(SocketIOTpService.getInstance(), this);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startPresenting(usernameEntry.getText().toString());
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
