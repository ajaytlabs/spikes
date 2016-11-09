package com.novoda.tpbot;

public interface ConnectionView {

    void onConnect();

    void onConnectionError(String code);

    void onDisconnect();

}
