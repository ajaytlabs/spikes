package com.novoda.tpbot;

import com.novoda.support.Observable;
import com.novoda.support.Observer;
import com.novoda.support.Result;
import com.novoda.tpbot.socket.io.SocketIOTpService;

public class Presenter {

    private final SocketIOTpService socketIOTpService;
    private final ConnectionView connectionView;

    private Observable<Result> observable;

    public Presenter(SocketIOTpService socketIOTpService, ConnectionView connectionView) {
        this.socketIOTpService = socketIOTpService;
        this.connectionView = connectionView;
    }

    public void startPresenting(String username) {
        observable = socketIOTpService.connect(username)
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
