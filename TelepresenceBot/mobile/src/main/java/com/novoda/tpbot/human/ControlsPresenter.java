package com.novoda.tpbot.human;

import com.novoda.support.Observable;
import com.novoda.support.Observer;
import com.novoda.support.Result;
import com.novoda.tpbot.Mode;
import com.novoda.tpbot.socket.io.SocketIOTpService;

public class ControlsPresenter {

    private final SocketIOTpService socketIOTpService;
    private final HumanView humanView;

    private Observable<Result> observable;

    public ControlsPresenter(SocketIOTpService socketIOTpService, HumanView humanView) {
        this.socketIOTpService = socketIOTpService;
        this.humanView = humanView;
    }

    public void startPresenting() {
        observable = socketIOTpService.connect(Mode.HUMAN)
                .addObserver(new ConnectionObserver());
    }

    public void stopPresenting() {
        observable.deleteObservers();
        socketIOTpService.disconnect();
    }

    private class ConnectionObserver implements Observer<Result> {

        @Override
        public void update(Result updatedValue) {
            humanView.onConnect();
        }
    }

}
