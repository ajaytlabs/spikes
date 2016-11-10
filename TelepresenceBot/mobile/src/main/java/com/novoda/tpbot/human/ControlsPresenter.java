package com.novoda.tpbot.human;

import com.novoda.support.Observable;
import com.novoda.support.Observer;
import com.novoda.support.Result;
import com.novoda.tpbot.Mode;
import com.novoda.tpbot.TpService;

public class ControlsPresenter {

    private final TpService tpService;
    private final HumanView humanView;

    private Observable<Result> observable;

    public ControlsPresenter(TpService tpService, HumanView humanView) {
        this.tpService = tpService;
        this.humanView = humanView;
    }

    public void startPresenting() {
        observable = tpService.connect(Mode.HUMAN)
                .addObserver(new ConnectionObserver());
    }

    public void stopPresenting() {
        observable.deleteObservers();
        tpService.disconnect();
    }

    private class ConnectionObserver implements Observer<Result> {

        @Override
        public void update(Result updatedValue) {
            humanView.onConnect();
        }
    }

}
