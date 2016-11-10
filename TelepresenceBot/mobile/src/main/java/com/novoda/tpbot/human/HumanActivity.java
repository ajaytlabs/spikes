package com.novoda.tpbot.human;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.novoda.tpbot.socket.io.SocketIOTpService;

public class HumanActivity extends AppCompatActivity implements HumanView {

    private ControlsPresenter controlsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controlsPresenter = new ControlsPresenter(SocketIOTpService.getInstance(), this);
    }

    @Override
    protected void onResume() {
        super.onStart();
        controlsPresenter.startPresenting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        controlsPresenter.stopPresenting();
    }

    @Override
    public void onConnect() {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionError(String code) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisconnect() {
        Toast.makeText(this, "disconnected", Toast.LENGTH_SHORT).show();
    }
}
