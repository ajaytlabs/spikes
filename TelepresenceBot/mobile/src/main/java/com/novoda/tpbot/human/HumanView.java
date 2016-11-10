package com.novoda.tpbot.human;

public interface HumanView {

    void onConnect();

    void onConnectionError(String code);

    void onDisconnect();

}
