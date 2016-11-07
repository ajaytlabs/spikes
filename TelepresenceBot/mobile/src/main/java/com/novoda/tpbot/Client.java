package com.novoda.tpbot;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends AsyncTask<Void, Void, Response> {

    private final String destinationAddress;
    private final int port;
    private final ResponseListener responseListener;

    public Client(String destinationAddress, int destinationPort, ResponseListener responseListener) {
        this.destinationAddress = destinationAddress;
        this.port = destinationPort;
        this.responseListener = responseListener;
    }

    @Override
    protected Response doInBackground(Void... params) {
        Socket socket = null;
        StringBuilder response = new StringBuilder();

        try {
            socket = new Socket(destinationAddress, port);

            ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response.append(byteArrayOutputStream.toString("UTF-8"));
            }

        } catch (UnknownHostException exception) {
            return Response.from(exception);
        } catch (IOException exception) {
            return Response.from(exception);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Response.from(response.toString());
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);

        responseListener.onResponseReceived(response);
    }
}
