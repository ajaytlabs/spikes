package com.novoda.tpbot.human;

import com.novoda.support.Observable;
import com.novoda.support.Observer;
import com.novoda.support.Result;
import com.novoda.tpbot.human.socket.io.Move;

public class ControlsPresenter {

    private final HumanTpService humanTpService;
    private final HumanView humanView;

    private Observable<Result> observable;

    public ControlsPresenter(HumanTpService tpService, HumanView humanView) {
        this.humanTpService = tpService;
        this.humanView = humanView;
    }

    public void startPresenting() {
        observable = humanTpService.connect()
                .addObserver(new ConnectionObserver());
    }

    public void move(Move move) {
        humanTpService.move(move);
    }

    public void stopPresenting() {
        observable.deleteObservers();
        humanTpService.disconnect();
    }

    private class ConnectionObserver implements Observer<Result> {

        @Override
        public void update(Result updatedValue) {
            humanView.onConnect();
        }
    }

}
