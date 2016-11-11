package com.novoda.tpbot.bot;

public interface BotView {

    void onConnect(String message);

    void onError(String message);
}
