package com.novoda.tpbot;

import android.os.Handler;
import android.os.Looper;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.novoda.tpbot.Event.*;

public class Server {

    private final ConnectionView connectionView;
    private Socket socket;
    private Handler handler;

    public Server(ConnectionView connectionView) {
        this.connectionView = connectionView;
        try {
            this.socket = IO.socket("http://192.168.86.152:3000");
            this.handler = new Handler(Looper.getMainLooper());
        } catch (URISyntaxException e) {
            connectionView.onConnectionError(ERROR.code());
        }
    }

    public void connectAs(String username) {
        socket.on(CONNECTED.code(), onConnected);
        socket.connect();
        socket.emit(JOIN.code(), username);
    }

    private Emitter.Listener onConnected = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    connectionView.onConnect();
                }
            });
        }
    };

    public void disconnect() {
        socket.disconnect();
        socket.off();
        connectionView.onDisconnect();
    }

}
