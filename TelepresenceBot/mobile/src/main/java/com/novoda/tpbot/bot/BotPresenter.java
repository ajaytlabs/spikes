package com.novoda.tpbot.bot;

import com.novoda.support.Observable;
import com.novoda.support.Observer;
import com.novoda.support.Result;
import com.novoda.tpbot.human.socket.io.Move;

public class BotPresenter {

    private final BotTpService tpService;
    private final BotView botView;

    private Observable<Result> connectionObservable;
    private Observable<Move> moveObservable;

    public BotPresenter(BotTpService tpService, BotView botView) {
        this.tpService = tpService;
        this.botView = botView;
    }

    public void startPresenting() {
        connectionObservable = tpService.connect()
                .addObserver(new ConnectionObserver());
    }

    public void stopPresenting() {
        connectionObservable.deleteObservers();
        moveObservable.deleteObservers();
        tpService.disconnect();
    }

    public void startListeningForMoves() {
        moveObservable = tpService.listen()
                .addObserver(new MoveObserver());
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

    private class MoveObserver implements Observer<Move> {

        @Override
        public void update(Move move) {
            botView.move(move);
        }
    }
}
