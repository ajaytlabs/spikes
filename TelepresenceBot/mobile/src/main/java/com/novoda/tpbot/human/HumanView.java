package com.novoda.tpbot.human;

public interface HumanView {

    void onConnect(String message);

    void onDisconnect();

    void onError(String message);

}
