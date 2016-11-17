package com.novoda.tpbot.human;

import com.novoda.support.Observable;
import com.novoda.support.Observer;
import com.novoda.support.Result;
import com.novoda.tpbot.human.controller.Direction;

public class HumanPresenter {

    private final HumanTpService humanTpService;
    private final HumanView humanView;

    private Observable<Result> observable;

    public HumanPresenter(HumanTpService tpService, HumanView humanView) {
        this.humanTpService = tpService;
        this.humanView = humanView;
    }

    public void startPresenting() {
        observable = humanTpService.connect()
                .addObserver(new ConnectionObserver());
    }

    public void moveIn(Direction direction) {
        humanTpService.moveIn(direction);
    }

    public void stopPresenting() {
        observable.deleteObservers();
        humanTpService.disconnect();
    }

    private class ConnectionObserver implements Observer<Result> {

        @Override
        public void update(Result result) {
            if (result.isError()) {
                humanView.onError(result.exception().get().getMessage());
            } else {
                humanView.onConnect(result.message().get());
            }
        }
    }

}
