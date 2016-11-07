package com.novoda.tpbot;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class CommunicationThread implements Runnable {

    private final Socket socket;
    private final BufferedReader bufferedReader;
    private final Handler handler;
    private final ResponseListener responseListener;

    public static CommunicationThread from(Socket socket, ResponseListener responseListener) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Handler handler = new Handler(Looper.getMainLooper());
        return new CommunicationThread(socket, bufferedReader, handler, responseListener);
    }

    private CommunicationThread(Socket socket, BufferedReader bufferedReader,
                                Handler handler, ResponseListener responseListener) {
        this.socket = socket;
        this.bufferedReader = bufferedReader;
        this.handler = handler;
        this.responseListener = responseListener;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                final String read = bufferedReader.readLine();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        responseListener.onResponseReceived(Response.from(read));
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
