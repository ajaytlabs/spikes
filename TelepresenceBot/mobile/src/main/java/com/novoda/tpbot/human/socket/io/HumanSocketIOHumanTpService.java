package com.novoda.tpbot.human.socket.io;

import android.os.Handler;
import android.os.Looper;

import com.novoda.support.Observable;
import com.novoda.support.Result;
import com.novoda.tpbot.human.HumanTpService;

import java.net.URI;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;

public class HumanSocketIOHumanTpService implements HumanTpService {

    private final Socket socket;
    private final Handler handler;

    public static HumanSocketIOHumanTpService getInstance() {
        return LazySingleton.INSTANCE;
    }

    private HumanSocketIOHumanTpService() {
        this.socket = IO.socket(URI.create("http://192.168.86.152:3000"));
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public Observable<Result> connect() {
        return new SocketConnectionObservable();
    }

    private class SocketConnectionObservable extends Observable<Result> {

        @Override
        public void start() {
            if (socket.connected()) {
                setChanged();
                notifyObservers(Result.from("Already connected"));
                return;
            }

            socket.connect();
            socket.emit("join_as_human", "", new Ack() {
                @Override
                public void call(Object... args) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setChanged();
                            notifyObservers(Result.from("Connected"));
                        }
                    });
                }
            });
        }

    }

    @Override
    public void move(Move move) {
        socket.emit("move", move);
    }

    @Override
    public void disconnect() {
        if (socket != null) {
            socket.disconnect();
            socket.off();
        }
    }

    private static class LazySingleton {
        private static final HumanSocketIOHumanTpService INSTANCE = new HumanSocketIOHumanTpService();
    }

}
