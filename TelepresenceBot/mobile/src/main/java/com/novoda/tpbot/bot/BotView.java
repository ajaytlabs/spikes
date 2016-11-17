package com.novoda.tpbot.bot;

import com.novoda.tpbot.human.controller.Direction;

public interface BotView {

    void onConnect(String message);

    void onError(String message);

    void direct(Direction direction);
}
