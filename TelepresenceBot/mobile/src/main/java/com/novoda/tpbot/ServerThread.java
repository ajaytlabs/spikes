package com.novoda.tpbot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ServerThread implements Runnable {

    private static final int SERVER_PORT = 8080;
    private final ResponseListener responseListener;
    private ServerSocket serverSocket;

    public ServerThread(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void run() {
        Socket socket;

        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                socket = serverSocket.accept();
                CommunicationThread commThread = CommunicationThread.from(socket, responseListener);
                new Thread(commThread).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
