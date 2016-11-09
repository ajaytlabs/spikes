package com.novoda.tpbot.socket.io;

import android.os.Handler;
import android.os.Looper;

import com.novoda.tpbot.ConnectionView;
import com.novoda.tpbot.TpService;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.novoda.tpbot.socket.io.Event.*;

public class SocketIOTpService implements TpService {

    private final ConnectionView connectionView;
    private Socket socket;
    private Handler handler;

    public SocketIOTpService(ConnectionView connectionView) {
        this.connectionView = connectionView;
        try {
            this.socket = IO.socket("http://192.168.86.152:3000");
            this.handler = new Handler(Looper.getMainLooper());
        } catch (URISyntaxException e) {
            connectionView.onConnectionError(ERROR.code());
        }
    }

    @Override
    public void connect(String username) {
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

    @Override
    public void disconnect() {
        socket.disconnect();
        socket.off();
        connectionView.onDisconnect();
    }

}
