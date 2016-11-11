package com.novoda.tpbot.bot;

import com.novoda.tpbot.human.socket.io.Move;

public interface BotView {

    void onConnect(String message);

    void onError(String message);

    void move(Move move);
}
