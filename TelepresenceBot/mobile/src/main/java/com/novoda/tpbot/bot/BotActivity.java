package com.novoda.tpbot.bot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.novoda.tpbot.bot.socket.io.BotSocketIOTpService;
import com.novoda.tpbot.human.controller.Direction;

public class BotActivity extends AppCompatActivity implements BotView {

    private BotPresenter botPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        botPresenter = new BotPresenter(BotSocketIOTpService.getInstance(), this);
    }

    @Override
    protected void onResume() {
        super.onStart();
        botPresenter.startPresenting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        botPresenter.stopPresenting();
    }

    @Override
    public void onConnect(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        botPresenter.startListeningForDirection();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void direct(Direction direction) {
        Toast.makeText(this, direction.name(), Toast.LENGTH_SHORT).show();
    }
}
