package com.novoda.tpbot.human;

import com.novoda.support.Observable;
import com.novoda.support.Observer;
import com.novoda.support.Result;
import com.novoda.tpbot.ConnectionView;
import com.novoda.tpbot.Mode;
import com.novoda.tpbot.socket.io.SocketIOTpService;

public class ControlsPresenter {

    private final SocketIOTpService socketIOTpService;
    private final ConnectionView connectionView;

    private Observable<Result> observable;

    public ControlsPresenter(SocketIOTpService socketIOTpService, ConnectionView connectionView) {
        this.socketIOTpService = socketIOTpService;
        this.connectionView = connectionView;
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
            connectionView.onConnect();
        }
    }

}
