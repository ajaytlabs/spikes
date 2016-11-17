package com.novoda.tpbot.human;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.simple.Log;
import com.novoda.tpbot.R;
import com.novoda.tpbot.human.controller.ControllerListener;
import com.novoda.tpbot.human.controller.ControllerView;
import com.novoda.tpbot.human.controller.Direction;
import com.novoda.tpbot.human.socket.io.HumanSocketIOTpService;

public class HumanActivity extends AppCompatActivity implements HumanView {

    private HumanPresenter humanPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_controller);
        ControllerView padView = Views.findById(this, R.id.bot_controller_direction_view);
        padView.setControllerListener(new ControllerListener() {
            @Override
            public void onDirectionPressed(Direction direction) {
                Log.d("onDirectionPressed: " + direction);
            }

            @Override
            public void onDirectionReleased(Direction direction) {
                Log.d("onDirectionReleased: " + direction);
            }

            @Override
            public void onLazersPressed() {
                Log.d("Pew!");
            }
        });

        humanPresenter = new HumanPresenter(HumanSocketIOTpService.getInstance(), this);
    }

    @Override
    protected void onResume() {
        super.onStart();
        humanPresenter.startPresenting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        humanPresenter.stopPresenting();
    }

    @Override
    public void onConnect(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisconnect() {
        Toast.makeText(this, "disconnected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
