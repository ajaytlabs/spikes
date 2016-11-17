package com.novoda.tpbot.bot;

import com.novoda.support.Observable;
import com.novoda.support.Observer;
import com.novoda.support.Result;
import com.novoda.tpbot.human.controller.Direction;

public class BotPresenter {

    private final BotTpService tpService;
    private final BotView botView;

    private Observable<Result> connectionObservable;
    private Observable<Direction> directionObservable;

    public BotPresenter(BotTpService tpService, BotView botView) {
        this.tpService = tpService;
        this.botView = botView;
    }

    public void startPresenting() {
        connectionObservable = tpService.connect()
                .addObserver(new ConnectionObserver());
    }

    public void stopPresenting() {
        if (connectionObservable != null) {
            connectionObservable.deleteObservers();
        }

        if (directionObservable != null) {
            directionObservable.deleteObservers();
        }
        tpService.disconnect();
    }

    public void startListeningForDirection() {
        directionObservable = tpService.listen()
                .addObserver(new DirectionObserver());
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

    private class DirectionObserver implements Observer<Direction> {

        @Override
        public void update(Direction direction) {
            botView.direct(direction);
        }
    }
}
