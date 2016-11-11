package com.novoda.tpbot.bot;

import com.novoda.support.Observable;
import com.novoda.support.Observer;
import com.novoda.support.Result;

public class BotPresenter {

    private final BotTpService tpService;
    private final BotView botView;

    private Observable<Result> observable;

    public BotPresenter(BotTpService tpService, BotView botView) {
        this.tpService = tpService;
        this.botView = botView;
    }

    public void startPresenting() {
        observable = tpService.connect()
                .addObserver(new ConnectionObserver());
    }

    public void stopPresenting() {
        observable.deleteObservers();
        tpService.disconnect();
    }

    private class ConnectionObserver implements Observer<Result> {

        @Override
        public void update(Result result) {
            if (result.isError()) {
                botView.onError(result.exception().get().getMessage());
            } else {
                botView.onConnect(result.message().get());
            }
        }
    }

}
