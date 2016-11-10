package com.novoda.tpbot;

import com.novoda.support.Observer;
import com.novoda.support.Result;
import com.novoda.tpbot.socket.io.SocketIOTpService;

public class Presenter {

    private final SocketIOTpService socketIOTpService;
    private final ConnectionView connectionView;

    public Presenter(SocketIOTpService socketIOTpService, ConnectionView connectionView) {
        this.socketIOTpService = socketIOTpService;
        this.connectionView = connectionView;
    }

    public void startPresenting(String username) {
        socketIOTpService.connect(username)
                .addObserver(new ConnectionObserver());
    }

    public void stopPresenting() {
        socketIOTpService.disconnect()
                .addObserver(new DisconnectionObserver());
    }

    private class ConnectionObserver implements Observer<Result> {

        @Override
        public void update(Result updatedValue) {
            connectionView.onConnect();
        }
    }

    private class DisconnectionObserver implements Observer<Result> {

        @Override
        public void update(Result updatedValue) {
            connectionView.onDisconnect();
        }
    }

}
